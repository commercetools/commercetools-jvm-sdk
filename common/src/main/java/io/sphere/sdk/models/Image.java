package io.sphere.sdk.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Optional;

@JsonDeserialize(as=ImageImpl.class)
public interface Image {
    public String getUrl();

    public ImageDimensions getDimensions();

    public Optional<String> getLabel();

}
