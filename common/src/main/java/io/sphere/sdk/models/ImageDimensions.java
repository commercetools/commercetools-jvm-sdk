package io.sphere.sdk.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class ImageDimensions extends Base {
    private final int width;
    private final int height;

    @JsonCreator
    private ImageDimensions(final int w, final int h) {
        this.width = w;
        this.height = h;
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
