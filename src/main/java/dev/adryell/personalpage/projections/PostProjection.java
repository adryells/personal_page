package dev.adryell.personalpage.projections;

import java.util.List;
import java.util.UUID;

public interface PostProjection {
    Long id();
    String title();
    String description();
    String content();
    Boolean active();
    UUID creatorId();
    List<String> tags();
}
