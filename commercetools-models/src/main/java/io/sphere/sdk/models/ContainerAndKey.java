package io.sphere.sdk.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as= ContainerAndKeyImpl.class)
public interface ContainerAndKey {
    String getContainer();
    String getKey();

    static ContainerAndKey of(final String container, final String key) {
        return ContainerAndKeyImpl.of(container, key);
    }

    @JsonIgnore
    static ContainerAndKey ofContainerAndKey(final String container, final String key) {
        return of(container, key);
    }
}
