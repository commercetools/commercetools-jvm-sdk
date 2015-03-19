package io.sphere.sdk.customobjects.demo;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;

public class BinaryData extends Base {
    private final String name;
    private final byte[] data;

    @JsonCreator
    public BinaryData(final String name, final byte[] data) {
        this.name = name;
        this.data = data;
    }

    public byte[] getData() {
        return data;
    }

    public String getName() {
        return name;
    }
}
