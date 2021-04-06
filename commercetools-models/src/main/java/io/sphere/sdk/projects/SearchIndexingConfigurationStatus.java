package io.sphere.sdk.projects;

import com.fasterxml.jackson.annotation.JsonCreator;

import io.sphere.sdk.models.SphereEnumeration;


public enum SearchIndexingConfigurationStatus {
    DEACTIVATED,
    INDEXING,
    ACTIVATED
    ;

    public static SearchIndexingConfigurationStatus defaultValue() {
        return DEACTIVATED;
    }

    @JsonCreator
    public static SearchIndexingConfigurationStatus ofSphereValue(final String value) {
        return SphereEnumeration.findBySphereName(values(), value).get();
    }
}
