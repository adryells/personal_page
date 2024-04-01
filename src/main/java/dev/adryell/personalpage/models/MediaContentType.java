package dev.adryell.personalpage.models;

import jakarta.persistence.*;

@Entity
public class MediaContentType extends BaseDateTime{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(unique = true)
    private String slug;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private boolean active = true;

    public String getDescription() {
        return description;
    }

    public Long getId() {
        return id;
    }

    public String getSlug() {
        return slug;
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return active;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public void setName(String name) {
        this.name = name;
    }
}
