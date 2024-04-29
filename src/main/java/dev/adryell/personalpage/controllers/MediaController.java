package dev.adryell.personalpage.controllers;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import dev.adryell.personalpage.config.GeneralConfig;
import dev.adryell.personalpage.models.*;
import dev.adryell.personalpage.projections.MediaProjection;
import dev.adryell.personalpage.repositories.AuthTokenRepository;
import dev.adryell.personalpage.repositories.MediaContentTypeRepository;
import dev.adryell.personalpage.repositories.MediaRepository;
import dev.adryell.personalpage.services.ConfigService;
import dev.adryell.personalpage.services.RequiresPermission;
import dev.adryell.personalpage.utils.enums.Permissions;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/media")
public class MediaController {

    @Autowired
    private Storage storage;

    @Autowired
    private GeneralConfig config;

//    @Autowired
//    public MediaController(GeneralConfig config) {
//        this.config = config;
//    }

    @Autowired
    AuthTokenRepository authTokenRepository;

    @Autowired
    MediaRepository mediaRepository;

    @Autowired
    MediaContentTypeRepository mediaContentTypeRepository;

    @RequiresPermission(Permissions.UPLOAD_MEDIA)
    @PostMapping("/upload-media")
    public Object uploadMedia(@RequestParam("file") MultipartFile file, @RequestParam("content_type_slug") String contentTypeSlug, HttpServletRequest request) throws IOException {
        String fileName = (file.getOriginalFilename() != null) ? file.getOriginalFilename() : file.getName();
        Long size = file.getSize();
        String mimetype = file.getContentType();

        Media media;
        try {
            String encodedFileName = encodeFileName(fileName);
            BlobId id = BlobId.of(config.getGCP_BUCKET_NAME(), encodedFileName);
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

            media.setTitle(encodedFileName);
            media.setSize(size.toString());
            media.setMimetype(mimetype);
            media.setMediaContentType(mediaContentType.get());

            mediaRepository.save(media);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(createMediaProjection(media));
    }

    private String encodeFileName(String originalFileName){
        String randomUUID = UUID.randomUUID().toString();

        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String formattedDateTime = currentDateTime.format(formatter);

        return randomUUID + "_" + formattedDateTime + "_" + originalFileName;
    }

    private MediaProjection createMediaProjection(Media media){
        return new MediaProjectionImpl(
                media.getId(),
                media.getTitle(),
                media.getActive(),
                media.getCreator().getId(),
                media.getSize(),
                media.getMimetype(),
                media.getMediaContentType().getId(),
                media.getURL()
        );
    }

    private record MediaProjectionImpl(
            Long id,
            String title,
            Boolean active,
            UUID creatorId,
            String size,
            String mimetype,
            Long mediaContentTypeId,
            String url
    ) implements MediaProjection {
    }

    @RequiresPermission(Permissions.DELETE_MEDIA)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteMedia(@PathVariable Long id) {
        Optional<Media> existingMedia = mediaRepository.findById(id);

        if (existingMedia.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Media not found.");
        }

        mediaRepository.delete(existingMedia.get());

        return ResponseEntity.ok("Deleted successful.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getMediaById(@PathVariable Long id) {
        Optional<Media> existingMedia = mediaRepository.findById(id);

        return existingMedia.
                <ResponseEntity<Object>>map(tag -> ResponseEntity.status(HttpStatus.OK).body(createMediaProjection(tag)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Media not found."));
    }
}
