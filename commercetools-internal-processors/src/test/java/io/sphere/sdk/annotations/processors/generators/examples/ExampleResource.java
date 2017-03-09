package io.sphere.sdk.annotations.processors.generators.examples;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.sphere.sdk.annotations.ResourceValue;

import javax.annotation.Nullable;

/**
 * Example for a resource.
 */
@ResourceValue
public interface ExampleResource {
    String getName();

    @Nullable
    @JsonProperty("isReturn")
    Boolean isReturn();


    @JsonProperty("hasStagedChanges")
    Boolean hasStagedChanges();

    @JsonIgnore
    String calculateProperty();
}
