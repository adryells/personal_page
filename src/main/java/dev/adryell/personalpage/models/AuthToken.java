package dev.adryell.personalpage.models;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class AuthToken extends BaseDateTime{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private UUID token;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String sourceIPAddress;

    @Column(nullable = false)
    private boolean active = true;

    @PrePersist
    public void generateToken() {
        if (this.token == null) {
            this.token = UUID.randomUUID();
        }
    }

    public Long getId() {
        return id;
    }

    public String getSourceIPAddress() {
        return sourceIPAddress;
    }

    public UUID getToken() {
        return token;
    }

    public User getUser() {
        return user;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setToken(UUID token) {
        this.token = token;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setSourceIPAddress(String sourceIPAddress) {
        this.sourceIPAddress = sourceIPAddress;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
