package io.sphere.sdk.reviews;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.DefaultModelImpl;

import java.time.ZonedDateTime;
import java.util.Optional;

final class ReviewImpl extends DefaultModelImpl<Review> implements Review {
    private final String productId;
    private final String customerId;
    private final String authorName;
    private final String title;
    private final String text;
    private final Double score;

    @JsonCreator
    ReviewImpl(final String id, final long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt,
               final String productId, final String customerId, final Optional<String> authorName, final Optional<String> title,
               final Optional<String> text, final Optional<Double> score) {
        super(id, version, createdAt, lastModifiedAt);
        this.productId = productId;
        this.customerId = customerId;
        this.authorName = authorName.orElse(null);
        this.title = title.orElse(null);
        this.text = text.orElse(null);
        this.score = score.orElse(null);
    }

    public String getProductId() {
        return productId;
    }

    public String getCustomerId(){
        return customerId;
    }

    @Override
    public Optional<String> getAuthorName() {
        return Optional.ofNullable(authorName);
    }

    public Optional<String> getTitle() {
        return Optional.ofNullable(title);
    }

    public Optional<String> getText() {
        return Optional.ofNullable(text);
    }

    public Optional<Double> getScore() {
        return Optional.ofNullable(score);
    }
}
