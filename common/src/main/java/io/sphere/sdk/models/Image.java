package io.sphere.sdk.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Optional;

@JsonDeserialize(as=ImageImpl.class)
public interface Image {
    public String getUrl();

    public ImageDimensions getDimensions();

    public Optional<String> getLabel();

    static Image of(final String url, final ImageDimensions dimensions, final Optional<String> label) {
        return new ImageImpl(url, dimensions, label);
    }

    static Image of(final String url, final ImageDimensions dimensions, final String label) {
        return of(url, dimensions, Optional.of(label));
    }

    static Image ofWidthAndHeight(final String url, final int width, final int height, final String label) {
        return of(url, ImageDimensions.of(width, height), Optional.of(label));
    }

    static Image of(final String url, final ImageDimensions dimensions) {
        return of(url, dimensions, Optional.empty());
    }
}
