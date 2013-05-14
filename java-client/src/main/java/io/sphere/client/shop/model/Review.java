package io.sphere.client.shop.model;

import org.joda.time.DateTime;

/** Review of a product by a customer. Anonymous reviews are not supported.
 *  A customer can add only one review on a product.
 *  The score is in the range [0..1]. */
public class Review {
    private String id;
    private int version;
    private String productId;
    private String customerId;
    private String authorName;
    private String title;
    private String text;
    private Double score;
    private DateTime createdAt;
    private DateTime lastModifiedAt;

    // for JSON deserializer
    protected Review() {}

    /** Unique id of the review. */
    public String getId() { return id; }

    /** Version of the review. */
    public int getVersion() { return version; }

    /** Id of the customer who wrote the review. */
    public String getProductId() { return productId; }

    /** Id of the customer who wrote the review.  */
    public String getCustomerId() { return customerId; }

    /** Custom name of the author of the review, not tied to customer names. */
    public String getAuthorName() { return authorName; }

    /** Title of the review. */
    public String getTitle() { return title; }

    /** Text of the review. */
    public String getText() { return text; }

    /** Score of the review, in the range [0..1]. */
    public Double getScore() { return score; }

    /** Time when this review was created, in UTC. */
    public DateTime getCreatedAt() { return createdAt; }

    /** Time when this review was last modified, in UTC. */
    public DateTime getLastModifiedAt() { return lastModifiedAt; }
}
