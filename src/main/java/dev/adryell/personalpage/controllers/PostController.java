package dev.adryell.personalpage.controllers;

import dev.adryell.personalpage.dtos.PostDTO;
import dev.adryell.personalpage.dtos.UpdatePostDTO;
import dev.adryell.personalpage.models.Post;
import dev.adryell.personalpage.models.Tag;
import dev.adryell.personalpage.models.User;
import dev.adryell.personalpage.repositories.AuthTokenRepository;
import dev.adryell.personalpage.repositories.PostRepository;
import dev.adryell.personalpage.repositories.TagRepository;
import dev.adryell.personalpage.repositories.UserRepository;
import dev.adryell.personalpage.services.RequiresPermission;
import dev.adryell.personalpage.utils.enums.Permissions;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    PostRepository postRepository;

    @Autowired
    AuthTokenRepository authTokenRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    UserRepository userRepository;

    @RequiresPermission(Permissions.CREATE_POST)
    @PostMapping("/create")
    ResponseEntity<Object> createPost(@RequestBody @Valid PostDTO postData, HttpServletRequest request) {
        Post post = new Post();

        post.setTitle(postData.title());
        post.setDescription(postData.description());
        post.setContent(postData.content());

        UUID token = UUID.fromString(request.getHeader("Authorization").replace("Bearer ", ""));
        User user = authTokenRepository.findByTokenAndActiveTrue(token).getUser();
        post.setCreator(user);

        if (postData.tagIds() != null) {
            Set<Tag> tags = this.findTagsByIds(postData.tagIds());

            if (tags == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("One or more tags not found.");
            }

            post.setTags(tags);
        }

        postRepository.save(post);

        return ResponseEntity.status(HttpStatus.CREATED).body(post.getId());
    }

    private Set<Tag> findTagsByIds(List<Long> tagIds) {
        Set<Tag> tags = new HashSet<>();
        for (Long tagId : tagIds) {
            Optional<Tag> tagOptional = tagRepository.findById(tagId);
            if (tagOptional.isPresent()) {
                tags.add(tagOptional.get());
            } else {
                return null;
            }
        }
        return tags;
    }

    @RequiresPermission(Permissions.UPDATE_POST)
    @PutMapping("/update/{id}")
    ResponseEntity<Object> updatePost(@RequestBody @Valid UpdatePostDTO postData, @PathVariable Long id) {
        Optional<Post> existingPost = postRepository.findById(id);

        if (existingPost.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found.");
        }

        Post post = existingPost.get();

        if (postData.title() != null) {
            post.setTitle(postData.title());
        }

        if (postData.description() != null) {
            post.setDescription(postData.description());
        }

        if (postData.content() != null) {
            post.setContent(postData.content());
        }

        if (postData.active() != null) {
            post.setActive(postData.active());
        }

        if (postData.creatorId() != null) {
            Optional<User> creator = userRepository.findById(postData.creatorId());

            if (creator.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Creator not found.");
            }

            post.setCreator(creator.get());
        }

        if (postData.tagIds() != null) {
            Set<Tag> tags = this.findTagsByIds(postData.tagIds());

            if (tags == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("One or more tags not found.");
            }

            post.setTags(tags);
        }

        return ResponseEntity.status(HttpStatus.OK).body("Update successfully.");
    }

    @RequiresPermission(Permissions.DELETE_POST)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deletePost(@PathVariable Long id) {
        Optional<Post> existingPost = postRepository.findById(id);

        if (existingPost.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found.");
        }

        postRepository.delete(existingPost.get());

        return ResponseEntity.ok("Deleted successful.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getPostById(@PathVariable Long id) {
        Optional<Post> existingPost = postRepository.findById(id);

        return existingPost.
                <ResponseEntity<Object>>map(post -> ResponseEntity.status(HttpStatus.OK).body(post))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found."));
    }

    @GetMapping("/")
    public Page<Post> getPosts(
            @RequestParam(required = false) Boolean active,
            @RequestParam(required = false) UUID creatorId,
            Pageable pageable
    ) {
        Specification<Post> specification = Specification.where(null);

        if (active != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("active"), active));
        }

        if (creatorId != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("creator").get("id"), creatorId));
        }

        return postRepository.findAll(specification, pageable);
    }

}
