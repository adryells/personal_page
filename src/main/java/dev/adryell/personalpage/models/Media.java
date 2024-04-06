package dev.adryell.personalpage.models;

import jakarta.persistence.*;

@Entity
public class Media extends BaseDateTime{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    private String mimetype;
    private String size;
    @Column(nullable = false)
    private Boolean active = true;
    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;
    @ManyToOne
    @JoinColumn(nullable = false)
    private MediaContentType mediaContentType;

    public Long getId() {
        return id;
    }

    public User getCreator() {
        return creator;
    }

    public Boolean getActive() {
        return active;
    }

    public String getTitle() {
        return title;
    }

    public String getMimetype() {
        return mimetype;
    }

    public String getSize() {
        return size;
    }

    public MediaContentType getMediaContentType() {
        return mediaContentType;
    }

    public String getURL(){
        return "https://storage.googleapis.com/yell-personal-page/" + getTitle();
    }

    public void setMediaContentType(MediaContentType mediaContentType) {
        this.mediaContentType = mediaContentType;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
