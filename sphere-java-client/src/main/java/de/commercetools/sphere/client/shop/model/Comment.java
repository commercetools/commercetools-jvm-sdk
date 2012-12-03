package de.commercetools.sphere.client.shop.model;

import org.joda.time.DateTime;

/**
 * Represents a comment on a product by a project customer. The score is in the range [0..1].
 * A customer can add one or more comments on a product.
 */
public class Comment {
    private String id;
    private int version;
    private String productId;
    private String customerId;
    private String authorName;
    private String title;
    private String text;
    private DateTime createdAt;
    private DateTime lastModifiedAt;

    // for JSON deserializer
    protected Comment() {}

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

    public String getAuthorName() {
        return authorName;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public DateTime getCreatedAt() {
        return createdAt;
    }

    public DateTime getLastModifiedAt() {
        return lastModifiedAt;
    }
}
