package de.commercetools.sphere.client.shop.model;

/** Represents accumulated average review score for a {@link Product}. */
public class ReviewRating {
    private double score;
    private int count;

    // for JSON deserializer
    private ReviewRating() {}

    ReviewRating(double score, int count) {
        this.score = score;
        this.count = count;
    }
    /** Null object to prevent NPEs. */
    public static ReviewRating empty() {
        return new ReviewRating(0.0, 0);
    }

    /** Average review score of a {@link Product} */
    public double getScore() { return score; }

    /** Count of reviews for a {@link Product} */
    public int getCount() { return count; }
}
