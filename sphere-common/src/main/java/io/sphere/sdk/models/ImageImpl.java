package io.sphere.sdk.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.annotation.Nullable;

final class ImageImpl extends Base implements Image {
    private final String url;
    private final ImageDimensions dimensions;
    @Nullable
    private final String label;

    @JsonCreator
    private ImageImpl(final String url, final ImageDimensions dimensions, @Nullable final String label) {
        this.url = url;
        this.dimensions = dimensions;
        this.label = label;
    }

    @JsonIgnore
    public static Image of(final String url, final ImageDimensions dimensions, @Nullable final String label) {
        return new ImageImpl(url, dimensions, label);
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public ImageDimensions getDimensions() {
        return dimensions;
    }

    @Nullable
    @Override
    public String getLabel() {
        return label;
    }
}
