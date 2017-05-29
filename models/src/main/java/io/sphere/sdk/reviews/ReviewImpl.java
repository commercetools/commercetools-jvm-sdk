package io.sphere.sdk.reviews;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.DefaultModelImpl;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.models.Reference;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;

class ReviewImpl extends DefaultModelImpl<Review> implements Review {
    private final String productId;
    private final String customerId;
    private final Optional<String> authorName;
    private final Optional<String> title;
    private final Optional<String> text;
    private final Optional<Float> score;

    @JsonCreator
    ReviewImpl(final String id, final long version, final Instant createdAt, final Instant lastModifiedAt,
               final String productId, final String customerId, final Optional<String> authorName, final Optional<String> title,
               final Optional<String> text, final Optional<Float> score) {
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
    public Optional<String> getAuthorName() {
        return authorName;
    }

    public Optional<String> getTitle() {
        return title;
    }

    public Optional<String> getText() {
        return text;
    }

    public Optional<Float> getScore(){
        return score;
    }
}
