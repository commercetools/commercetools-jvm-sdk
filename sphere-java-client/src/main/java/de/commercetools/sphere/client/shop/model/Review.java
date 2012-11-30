package de.commercetools.sphere.client.shop.model;

import net.jcip.annotations.Immutable;
import org.joda.time.DateTime;

/**
 * Represents a review on a product by a project customer. The score is in the range [0..1].
 * A customer can add only one review on a product.
 */
public class Review {
    private String id;
    private int version;
    private String productId;
    private String customerId;
    private String title;
    private String text;
    private Double score;
    private DateTime createdAt;
    private DateTime lastModifiedAt;

    // for JSON deserializer
    private Review() {}

    public String getId() {
        return id;
    }

    public int getVersion() {
        return version;
    }

    public String getProductId() {
        return productId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public Double getScore() {
        return score;
    }

    public DateTime getCreatedAt() {
        return createdAt;
    }

    public DateTime getLastModifiedAt() {
        return lastModifiedAt;
    }
}
