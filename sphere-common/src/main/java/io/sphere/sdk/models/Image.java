package io.sphere.sdk.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.annotation.Nullable;

@JsonDeserialize(as=ImageImpl.class)
public interface Image {
    String getUrl();

    ImageDimensions getDimensions();

    @Nullable
    String getLabel();

    static Image of(final String url, final ImageDimensions dimensions, final String label) {
        return ImageImpl.of(url, dimensions, label);
    }

    static Image ofWidthAndHeight(final String url, final int width, final int height, final String label) {
        return of(url, ImageDimensions.of(width, height), label);
    }

    static Image ofWidthAndHeight(final String url, final int width, final int height) {
        return of(url, ImageDimensions.of(width, height), null);
    }

    static Image of(final String url, final ImageDimensions dimensions) {
        return of(url, dimensions, null);
    }

    @JsonIgnore
    default int getWidth() {
        return getDimensions().getWidth();
    }

    @JsonIgnore
    default int getHeight() {
        return getDimensions().getHeight();
    }

    static TypeReference<Image> typeReference() {
        return new TypeReference<Image>() {
            @Override
            public String toString() {
                return "TypeReference<Image>";
            }
        };
    }
}
