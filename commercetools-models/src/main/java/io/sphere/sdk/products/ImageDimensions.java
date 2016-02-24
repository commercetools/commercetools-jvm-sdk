package io.sphere.sdk.products;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.sphere.sdk.models.Base;

public final class ImageDimensions extends Base {
    @JsonProperty("w")
    private final Integer width;
    @JsonProperty("h")
    private final Integer height;

    @JsonCreator
    private ImageDimensions(final Integer width, final Integer height) {
        this.width = width;
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }

    @JsonIgnore
    public static ImageDimensions of(final Integer width, final Integer height) {
        return new ImageDimensions(width, height);
    }

    @JsonIgnore
    public static ImageDimensions ofWidthAndHeight(final Integer width, final Integer height) {
        return of(width, height);
    }
}
