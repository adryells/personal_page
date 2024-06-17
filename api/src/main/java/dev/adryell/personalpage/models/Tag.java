package dev.adryell.personalpage.models;

import dev.adryell.personalpage.utils.enums.MediaContentTypes;
import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Tag extends BaseDateTime{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String slug;

    @Column(nullable = false)
    private boolean active = true;

    @ManyToMany
    @JoinTable(
            name = "tag_media",
            joinColumns = @JoinColumn(name = "tag_id"),
            inverseJoinColumns = @JoinColumn(name = "media_id")
    )
    private Set<Media> medias;

    public Media getIcon(){
        return getMedias()
                .stream()
                .filter(
                        media -> MediaContentTypes.TAG_ICON.toString().toLowerCase().equalsIgnoreCase(
                                media.getMediaContentType().getSlug()
                        )
                )
                .findFirst()
                .orElse(null);
    }

    public Set<Media> getMedias() {
        return medias;
    }

    public boolean isActive() {
        return active;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSlug() {
        return slug;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public void setMedias(Set<Media> medias) {
        this.medias = medias;
    }
}
