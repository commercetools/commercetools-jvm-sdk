package io.sphere.client.shop.model;

import io.sphere.client.model.VersionedId;
import org.codehaus.jackson.annotate.JsonProperty;
import org.joda.time.DateTime;

import javax.annotation.Nonnull;

/** A comment on a product by a project customer. The score is in the range [0..1], inclusive.
 * A customer can add one or more comments on a product. */
public class Comment {
    @Nonnull private String id;
    @JsonProperty("version") private int version;
    private String productId;
    private String customerId;
    @JsonProperty("authorName") private String author;
    private String title;
    private String text;
    private DateTime createdAt;
    private DateTime lastModifiedAt;

    // for JSON deserializer
    protected Comment() {}

    /** The unique id. */
    @Nonnull public String getId() { return id; }

    /** The {@link #getId() id} plus version. */
    @Nonnull public VersionedId getIdAndVersion() { return VersionedId.create(id, version); }

    /** Id of the product this comment is attached to. */
    public String getProductId() { return productId; }

    /** Id of the customer . */
    public String getCustomerId() { return customerId; }

    /** Custom name of the author of the comment, not tied to a customer name. */
    public String getAuthor() { return author; }

    /** Title of this comment. */
    public String getTitle() { return title; }

    /** Text of this comment. */
    public String getText() { return text; }

    /** Date and time when this comment was created. */
    public DateTime getCreatedAt() { return createdAt; }

    /** Date and time when this comment was last modified. */
    public DateTime getLastModifiedAt() { return lastModifiedAt; }
}
