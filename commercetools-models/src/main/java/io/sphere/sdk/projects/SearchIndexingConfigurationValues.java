package io.sphere.sdk.projects;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.ResourceValue;
import io.sphere.sdk.models.LastModifiedBy;

import java.time.ZonedDateTime;

@ResourceValue
@JsonDeserialize(as = SearchIndexingConfigurationValuesImpl.class)
public interface SearchIndexingConfigurationValues {
    SearchIndexingConfigurationStatus getStatus();

    ZonedDateTime getLastModifiedAt();

    LastModifiedBy getLastModifiedBy();
}
