package dev.adryell.personalpage.projections;

public interface TagProjection {
    Long id();
    String name();
    String slug();
    Boolean active();
    String iconURL();
}
