package io.sphere.sdk.products;

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

    static Image of(final String url, final ImageDimensions dimensions, @Nullable final String label) {
        return ImageImpl.of(url, dimensions, label);
    }

    static Image ofWidthAndHeight(final String url, final Integer width, final Integer height, @Nullable final String label) {
        return of(url, ImageDimensions.of(width, height), label);
    }

    static Image ofWidthAndHeight(final String url, final Integer width, final Integer height) {
        return of(url, ImageDimensions.of(width, height), null);
    }

    static Image of(final String url, final ImageDimensions dimensions) {
        return of(url, dimensions, null);
    }

    @JsonIgnore
    default Integer getWidth() {
        return getDimensions().getWidth();
    }

    @JsonIgnore
    default Integer getHeight() {
        return getDimensions().getHeight();
    }

    /**
     * Creates a container which contains the full Java type information to deserialize this class from JSON.
     *
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(byte[], TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(String, TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(com.fasterxml.jackson.databind.JsonNode, TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObjectFromResource(String, TypeReference)
     *
     * @return type reference
     */
    static TypeReference<Image> typeReference() {
        return new TypeReference<Image>() {
            @Override
            public String toString() {
                return "TypeReference<Image>";
            }
        };
    }
}
