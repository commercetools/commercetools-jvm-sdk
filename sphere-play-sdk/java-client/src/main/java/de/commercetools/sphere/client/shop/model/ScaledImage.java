package de.commercetools.sphere.client.shop.model;

import net.jcip.annotations.Immutable;

/** One specific size of an {@link Image}. */
@Immutable
public class ScaledImage {
    private final ImageSize size;
    private final String url;
    private final String label;
    private final Dimensions dimensions;
    private final double scaleRatio;

    public ScaledImage(ImageSize size, String url, String label, Dimensions dimensions, double scaleRatio) {
        if (size == null) throw new NullPointerException("size");
        if (size == null) throw new NullPointerException("dimensions");
        this.size = size;
        this.url = url;
        this.label = label != null ? label : "";
        this.dimensions = dimensions;
        this.scaleRatio = scaleRatio;
    }

    /** Size of this image. */
    public ImageSize getSize() { return size; }

    /** Url of this image. */
    public String getUrl() { return url; }

    /** Optional label of this image, useful for rendering image description or alt attribute. */
    public String getLabel() { return label; }

    /** Width of this image in pixels. */
    public int getWidth() { return dimensions.getWidth(); }

    /** Height of this image in pixels. */
    public int getHeight() { return dimensions.getHeight(); }

    /** The ratio by which this image was scaled.
     *
     * Greater than one means the image was scaled up (and potentially is bad quality).
     * Less than one means the image was scaled down.
     * Exactly one means this size matches the original size of the image.
     *
     * This is useful e.g. for determining whether a zooming UI should be shown:
     * {@code image.getSize(ImageSize.ZOOM).getScaleRatio() <= 1}. */
    public double getScaleRatio() { return scaleRatio; }
}
