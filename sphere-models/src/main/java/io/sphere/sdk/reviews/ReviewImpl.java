package io.sphere.sdk.reviews;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.DefaultModelImpl;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

final class ReviewImpl extends DefaultModelImpl<Review> implements Review {
    private final String productId;
    private final String customerId;
    @Nullable
    private final String authorName;
    @Nullable
    private final String title;
    @Nullable
    private final String text;
    @Nullable
    private final Double score;

    @JsonCreator
    ReviewImpl(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt,
               final String productId, final String customerId, @Nullable final String authorName, @Nullable final String title,
               @Nullable final String text, @Nullable final Double score) {
        super(id, version, createdAt, lastModifiedAt);
        this.productId = productId;
        this.customerId = customerId;
        this.authorName = authorName;
        this.title = title;
        this.text = text;
        this.score = score;
    }

    public String getProductId() {
        return productId;
    }

    public String getCustomerId(){
        return customerId;
    }

    @Override
    @Nullable
    public String getAuthorName() {
        return authorName;
    }

    @Nullable
    public String getTitle() {
        return title;
    }

    @Nullable
    public String getText() {
        return text;
    }

    @Nullable
    public Double getScore() {
        return score;
    }
}
