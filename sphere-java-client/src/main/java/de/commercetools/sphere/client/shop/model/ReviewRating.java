package de.commercetools.sphere.client.shop.model;

/**
 * Represents the accumulated review scores as a part of the [[Product]].
 */
public class ReviewRating {
    private Double score;
    private int count;

    // for JSON deserializer
    private ReviewRating() {};

    public Double getScore() {
        return score;
    }

    public int getCount() {
        return count;
    }
}
