package io.sphere.sdk.models;

import java.util.Optional;

class ImageImpl extends Base implements Image {
    private final String url;
    private final ImageDimensions dimensions;
    private final Optional<String> label;

    ImageImpl(final String url, final ImageDimensions dimensions, final Optional<String> label) {
        this.url = url;
        this.dimensions = dimensions;
        this.label = label;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public ImageDimensions getDimensions() {
        return dimensions;
    }

    @Override
    public Optional<String> getLabel() {
        return label;
    }
}
