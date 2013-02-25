package de.commercetools.sphere.client.shop.model;

import de.commercetools.internal.util.Url;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

/** Image attached to a {@link de.commercetools.sphere.client.shop.model.Variant}. */
public class Image {
    private final String url;
    private final String urlBase;
    // extension, including the dot (".jpg", ".jpeg" ".png", ".gif")
    private final String urlExtension;
    private final String label;
    private final Dimensions dimensions;

     @JsonCreator
    public Image(@JsonProperty("url") String url, @JsonProperty("label") String label, @JsonProperty("dimensions") Dimensions dimensions) {
        this.url = (url != null) ? url : "";
        this.urlBase = Url.stripExtension(url);
        this.urlExtension = Url.getExtension(url);
        this.label = (label != null) ? label : "";
        this.dimensions = (dimensions != null) ? dimensions : new Dimensions(0, 0);
    }

    /** Optional label of this image, useful for rendering image description or alt attribute. */
    public String getLabel() { return label; }

    /** Returns the variant of this image in one of standard sizes. */
    public ScaledImage getSize(ImageSize size) {
        Dimensions scaledDimensions = getDimensions(size);
        // maintains aspect ratio
        double scaleRatio = size == ImageSize.ORIGINAL ? 1.0 : (double)scaledDimensions.getWidth() / (double)dimensions.getWidth();
        return new ScaledImage(size, getUrl(size), label, scaledDimensions, scaleRatio);
    }

    // --------------------------------
    // Helpers
    // --------------------------------

    /** Returns the url of this image in given {@code size}. */
    private String getUrl(ImageSize size) {
        String sizeSuffix;
        switch (size) {
            case THUMBNAIL: sizeSuffix = "-thumb"; break;
            case SMALL: sizeSuffix = "-small"; break;
            case MEDIUM: sizeSuffix = "-medium"; break;
            case LARGE: sizeSuffix = "-large"; break;
            case ZOOM: sizeSuffix = "-zoom"; break;
            case ORIGINAL: return url;
            default: throw new IllegalArgumentException("Unknown image size: " + size);
        }
        return urlBase + sizeSuffix + urlExtension;
    }

    private int getBoundingSquareSize(ImageSize size) {
        switch (size) {
            case THUMBNAIL: return 50;
            case SMALL: return 150;
            case MEDIUM: return 400;
            case LARGE: return 700;
            case ZOOM: return 1400;
            default: throw new IllegalArgumentException("Unknown image size: " + size);
        }
    }

    /** Returns the size of this image fitted into a square defined by {@code size}. */
    private Dimensions getDimensions(ImageSize size) {
        switch (size) {
            case ORIGINAL: return dimensions;
            default: return scaleToFit(dimensions, getBoundingSquareSize(size));
        }
    }

    /** Fits {@code dimensions} inside a square of given size, maintaining aspect ratio. */
    private Dimensions scaleToFit(Dimensions current, int squareSize) {
        // <= 1 is a square or landscape-oriented image, > 1 is a portrait.
        float ratio = ((float)current.getHeight() / (float)current.getWidth());
        int targetWidth = squareSize;
        int targetHeight = squareSize;
        if (ratio <= 1) {
            // landscape
            if (targetWidth == current.getWidth()) return current;
            targetHeight = Math.round(targetWidth * ratio);
        } else {
            // portrait
            if (targetHeight == current.getHeight()) return current;
            targetWidth = Math.round(targetHeight / ratio);
        }
        return new Dimensions(targetWidth, targetHeight);
    }

    // ---------------------------------
    // equals() and hashCode()
    // ---------------------------------

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        if (!dimensions.equals(image.dimensions)) return false;
        if (!label.equals(image.label)) return false;
        if (!url.equals(image.url)) return false;
        return true;
    }

    @Override public int hashCode() {
        int result = url.hashCode();
        result = 31 * result + label.hashCode();
        result = 31 * result + dimensions.hashCode();
        return result;
    }
}
