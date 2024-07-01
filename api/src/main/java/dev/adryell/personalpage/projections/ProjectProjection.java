package dev.adryell.personalpage.projections;

import java.util.List;
import java.util.UUID;

public interface ProjectProjection {
    Long id();
    String title();
    String description();
    Boolean active();
    UUID creatorId();
    List<String> tags();
    String content();
}
