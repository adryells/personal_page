package dev.adryell.personalpage.models;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Post extends BaseDateTime{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private boolean active = false;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    @ManyToMany
    @JoinTable(
            name = "post_tag",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags;

    public Long getId() {
        return id;
    }

    public User getCreator() {
        return creator;
    }

    public String getDescription() {
        return description;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
