package io.sphere.client.shop.model;

/** Represents accumulated average review score for a {@link Product}. */
public class ReviewRating {
    private double score;
    private int count;

    // for JSON deserializer
    private ReviewRating() {}

    ReviewRating(double score, int numberOfReviews) {
        this.score = score;
        this.count = numberOfReviews;
    }
    /** Creates a null object to help prevent NPEs. */
    public static ReviewRating empty() {
        return new ReviewRating(0.0, 0);
    }

    /** Average review score for a {@link Product}. */
    public double getScore() { return score; }

    /** Number of reviews for a {@link Product}. */
    public int getCount() { return count; }
}
