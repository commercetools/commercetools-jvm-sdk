package io.sphere.sdk.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

final class ContainerAndKeyImpl extends Base implements ContainerAndKey{
    private final String container;
    private final String key;

    @JsonCreator
    public ContainerAndKeyImpl(String container, String key) {
        this.container = container;
        this.key = key;
    }

    @JsonIgnore
    public static ContainerAndKey of(final String container, final String key) {
        return new ContainerAndKeyImpl(container, key);
    }

    @Override
    public String getContainer() {
        return container;
    }

    @Override
    public String getKey() {
        return key;
    }
}
