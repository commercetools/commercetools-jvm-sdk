package de.commercetools.sphere.client.shop.model;

/** Represents accumulated average review score for a {@link Product}. */
public class ReviewRating {
    private Double score;
    private int count;

    // for JSON deserializer
    private ReviewRating() {}

    /** Average review score of a {@link Product} */
    public Double getScore() { return score; }

    /** Count of reviews for a {@link Product} */
    public int getCount() { return count; }
}
