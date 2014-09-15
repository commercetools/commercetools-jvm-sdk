package io.sphere.sdk.models;

public class ImageDimensions extends Base {
    private final int width;
    private final int height;

    private ImageDimensions(final int width, final int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public static ImageDimensions of(final int width, final int height) {
        return new ImageDimensions(width, height);
    }
}
