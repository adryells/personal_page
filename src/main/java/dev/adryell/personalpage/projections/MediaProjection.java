package dev.adryell.personalpage.projections;

import java.util.List;
import java.util.UUID;

public interface MediaProjection {
    Long id();
    String title();
    Boolean active();
    UUID creatorId();
    String size();
    String mimetype();
    Long mediaContentTypeId();
}
