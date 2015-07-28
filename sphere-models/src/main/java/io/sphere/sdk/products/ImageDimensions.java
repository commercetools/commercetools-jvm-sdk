package io.sphere.sdk.products;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.sphere.sdk.models.Base;

public class ImageDimensions extends Base {
    @JsonProperty("w")
    private final int width;
    @JsonProperty("h")
    private final int height;

    @JsonCreator
    private ImageDimensions(final int width, final int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @JsonIgnore
    public static ImageDimensions of(final int width, final int height) {
        return new ImageDimensions(width, height);
    }
}
