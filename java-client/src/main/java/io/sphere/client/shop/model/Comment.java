package io.sphere.client.shop.model;

import org.joda.time.DateTime;

/** A comment on a product by a project customer. The score is in the range [0..1].
 * A customer can add one or more comments on a product. */
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

    /** Unique id of this comment. */
    public String getId() { return id; }

    /** Version of this comment. */
    public int getVersion() { return version; }

    /** Id of the product this comment is attached to. */
    public String getProductId() { return productId; }

    /** Id of the customer . */
    public String getCustomerId() { return customerId; }

    /** Name of the person who commented. */
    public String getAuthorName() { return authorName; }

    /** Title of this comment. */
    public String getTitle() { return title; }

    /** Text of this comment. */
    public String getText() { return text; }

    /** Date and time when this comment was created. */
    public DateTime getCreatedAt() { return createdAt; }

    /** Date and time when this comment was last modified. */
    public DateTime getLastModifiedAt() { return lastModifiedAt; }
}
