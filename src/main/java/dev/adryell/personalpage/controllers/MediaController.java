package dev.adryell.personalpage.controllers;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import dev.adryell.personalpage.config.GeneralConfig;
import dev.adryell.personalpage.models.*;
import dev.adryell.personalpage.projections.MediaProjection;
import dev.adryell.personalpage.projections.PostProjection;
import dev.adryell.personalpage.repositories.AuthTokenRepository;
import dev.adryell.personalpage.repositories.MediaContentTypeRepository;
import dev.adryell.personalpage.repositories.MediaRepository;
import dev.adryell.personalpage.services.RequiresPermission;
import dev.adryell.personalpage.utils.enums.Permissions;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/media")
public class MediaController {

    @Autowired
    private Storage storage;

    private final GeneralConfig config = new GeneralConfig();

    @Autowired
    AuthTokenRepository authTokenRepository;

    @Autowired
    MediaRepository mediaRepository;

    @Autowired
    MediaContentTypeRepository mediaContentTypeRepository;

//    public MediaController(Storage storage, GeneralConfig config){
//        this.storage = storage;
//        this.config = config;
//    }

    @RequiresPermission(Permissions.UPLOAD_MEDIA)
    @PostMapping("/upload-media")
    public Object uploadMedia(@RequestParam("file") MultipartFile file, @RequestParam("content_type_slug") String contentTypeSlug, HttpServletRequest request) throws IOException {
        String fileName = (file.getOriginalFilename() != null) ? file.getOriginalFilename() : file.getName();
        Long size = file.getSize();
        String mimetype = file.getContentType();

        Media media;
        try {
            BlobId id = BlobId.of(config.getGCP_BUCKET_NAME(), fileName);
            BlobInfo info = BlobInfo.newBuilder(id).build();
            byte[] arr = file.getBytes();
            storage.create(info, arr);

            media = new Media();

            Optional<MediaContentType> mediaContentType = mediaContentTypeRepository.findBySlug(contentTypeSlug);

            if (mediaContentType.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Media content type not found.");
            }

            UUID token = UUID.fromString(request.getHeader("Authorization").replace("Bearer ", ""));
            User user = authTokenRepository.findByTokenAndActiveTrue(token).getUser();
            media.setCreator(user);

            media.setTitle(fileName);
            media.setSize(size.toString());
            media.setMimetype(mimetype);
            media.setMediaContentType(mediaContentType.get());

            mediaRepository.save(media);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(createMediaProjection(media));
    }

    private MediaProjection createMediaProjection(Media media){
        return new MediaProjectionImpl(
                media.getId(),
                media.getTitle(),
                media.getActive(),
                media.getCreator().getId(),
                media.getSize(),
                media.getMimetype(),
                media.getMediaContentType().getId()
        );
    }

    private record MediaProjectionImpl(
            Long id,
            String title,
            Boolean active,
            UUID creatorId,
            String size,
            String mimetype,
            Long mediaContentTypeId
    ) implements MediaProjection {
    }
}
