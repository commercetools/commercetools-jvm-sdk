package io.sphere.client.shop.model;

import io.sphere.client.model.VersionedId;
import org.codehaus.jackson.annotate.JsonProperty;
import org.joda.time.DateTime;

import javax.annotation.Nonnull;

/** Review of a product by a project customer (anonymous reviews are currently not supported).
 *  A customer can add only one review on a product.
 *  The review score is in the range [0..1]. */
public class Review {
    @Nonnull private String id;
    private int version;
    @Nonnull private String productId;
    @Nonnull private String customerId;
    @JsonProperty("authorName") private String author;
    private String title = "";
    private String text = "";
    private Double score;
    @Nonnull private DateTime createdAt;
    @Nonnull private DateTime lastModifiedAt;

    // for JSON deserializer
    protected Review() {}

    /** The unique id. */
    @Nonnull public String getId() { return id; }

    /** The {@link #getId() id} plus version. */
    @Nonnull public VersionedId getIdAndVersion() { return VersionedId.create(id, version); }

    /** Id of the product for which this review was written. */
    @Nonnull public String getProductId() { return productId; }

    /** Id of the customer who wrote the product review. */
    @Nonnull public String getCustomerId() { return customerId; }

    /** Custom name of the author of the review, not tied to a customer name. */
    public String getAuthor() { return author; }

    /** Title of the review. */
    public String getTitle() { return title; }

    /** Text of the review. */
    public String getText() { return text; }

    /** Score of the review, in the range [0..1]. */
    public Double getScore() { return score; }

    /** Time when this review was created, in UTC. */
    @Nonnull public DateTime getCreatedAt() { return createdAt; }

    /** Time when this review was last modified, in UTC. */
    @Nonnull public DateTime getLastModifiedAt() { return lastModifiedAt; }
}
