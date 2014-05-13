package io.sphere.client.shop.model;

import net.jcip.annotations.Immutable;

import javax.annotation.Nonnull;

/** One specific size of an {@link Image}. */
@Immutable
public class ScaledImage {
    @Nonnull private final ImageSize size;
    private final String url;
    private final String label;
    @Nonnull private final Dimensions dimensions;

    public ScaledImage(ImageSize size, String url, String label, Dimensions dimensions) {
        if (size == null) throw new NullPointerException("size");
        if (dimensions == null) throw new NullPointerException("dimensions");
        this.size = size;
        this.url = url;
        this.label = label != null ? label : "";
        this.dimensions = dimensions;
    }

    /** Size of this image. */
    @Nonnull public ImageSize getSize() { return size; }

    /** Url of this image. */
    public String getUrl() { return url; }

    /** Optional label of this image, useful for rendering image description or alt attribute. */
    public String getLabel() { return label; }

    /** Width of this image in pixels. */
    @Nonnull public int getWidth() { return dimensions.getWidth(); }

    /** Height of this image in pixels. */
    @Nonnull public int getHeight() { return dimensions.getHeight(); }

    @Override
    public String toString() {
        return "ScaledImage{" +
                "size=" + size +
                ", url='" + url + '\'' +
                ", label='" + label + '\'' +
                ", dimensions=" + dimensions +
                '}';
    }
}
