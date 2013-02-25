package de.commercetools.sphere.client.shop.model;

/** One specific size of an {@link Image}. */
public class ScaledImage {
    private final ImageSize size;
    private final String url;
    private final String label;
    private final Dimensions dimensions;
    private final double scaleRatio;

    public ScaledImage(ImageSize size, String url, String label, Dimensions dimensions, double scaleRatio) {
        if (size == null) throw new NullPointerException("size");
        if (url == null || url.length() == 0) throw new IllegalArgumentException("Image url can't be empty.");
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
     * Greater than one means the image was scaled up (and will potentially be bad quality).
     * Less than one means the image was scaled down.
     * Exactly one means this size matches the original size of the image. */
    public double getScaleRatio() { return scaleRatio; }

    /** Returns true if the image was scaled up from the original ({@link #getScaleRatio()} is greater than 1.0),
     * and is potentially low quality. */
    public boolean isScaledUp() { return getScaleRatio() > 1.0; }

    /** Returns true if the image was scaled down from the original, or no scaling was performed
     * ({@link #getScaleRatio()} is lesser or equal to 1.0).
     * If this method returns true, this is a good quality image. */
    public boolean isScaledDown() { return !isScaledUp(); }
}
