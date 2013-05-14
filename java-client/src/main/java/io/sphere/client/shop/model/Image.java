package io.sphere.client.shop.model;

import io.sphere.internal.util.Url;
import net.jcip.annotations.Immutable;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

/** Image attached to a {@link io.sphere.client.shop.model.Variant}. */
@Immutable
public class Image {
    private final String url;
    private final String urlBase;
    // extension, including the dot (".jpg", ".jpeg" ".png", ".gif")
    private final String urlExtension;
    private final String label;
    private final Dimensions dimensions;

    /** Null object to prevent NPEs. */
    public static Image none() {
        return new Image("", "", new Dimensions(0, 0));
    }

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
        return new ScaledImage(size, getUrl(size), label, scaledDimensions);
    }

    /** Returns true if this image is available in given size.
     *  Images are never scaled up, so this method returns true only if the original image was at least as large
     *  as {@code size} in both dimensions. */
    public boolean isSizeAvailable(ImageSize size) {
        Dimensions resized = getDimensions(size);
        return (size == ImageSize.THUMBNAIL) || (size == ImageSize.SMALL) || (size == ImageSize.ORIGINAL) ||
                (dimensions.getWidth() >= resized.getWidth() &&  dimensions.getHeight() >= resized.getHeight());
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
