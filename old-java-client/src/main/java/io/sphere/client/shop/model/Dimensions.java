package io.sphere.client.shop.model;

import net.jcip.annotations.Immutable;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/** Product image dimensions (width and height). */
@Immutable
public class Dimensions {
    private final int width;
    private final int height;

    @JsonCreator
    public Dimensions(@JsonProperty("w") int width, @JsonProperty("h") int height) {
        this.width = width;
        this.height = height;
    }

    /** Image width in pixels. */
    public int getWidth() { return width; }

    /** Image height in pixels. */
    public int getHeight() { return height; }

    @Override public String toString() {
        return "[" + getWidth() + "x" + getHeight() + "]";
    }

    // ---------------------------------
    // equals() and hashCode()
    // ---------------------------------

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dimensions that = (Dimensions) o;
        if (height != that.height) return false;
        if (width != that.width) return false;

        return true;
    }

    @Override public int hashCode() {
        int result = width;
        result = 31 * result + height;
        return result;
    }
}
