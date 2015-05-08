package io.sphere.sdk.reviews;

import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.products.ProductIdentifiable;

import java.util.Optional;

/**
 * Template to create a new Review.
 *
 * @see ReviewDraftBuilder
 */
public class ReviewDraft extends Base {
    private final String productId;
    private final String customerId;
    private final String authorName;
    private final String title;
    private final String text;
    private final Double score;

    ReviewDraft(final String productId, final String customerId, final Optional<String> authorName, final Optional<String> title,
                final Optional<String> text, final Optional<Double> score) {
        final boolean scoreIsInValidRange = score.map(scoreValue -> scoreValue >= 0.0 && scoreValue <= 1.0).orElse(true);
        if (!scoreIsInValidRange) {
            throw new IllegalArgumentException("Valid scores are in [0..1].");
        }
        this.productId = productId;
        this.customerId = customerId;
        this.authorName = authorName.orElse(null);
        this.title = title.orElse(null);
        this.text = text.orElse(null);
        this.score = score.orElse(null);
    }

    public static ReviewDraft of(final ProductIdentifiable product, final Identifiable<Customer> customer) {
        return of(product.getId(), customer.getId());
    }

    public static ReviewDraft of(final String productId, final String customerId) {
        return new ReviewDraft(productId, customerId, Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
    }

    public String getProductId() {
        return productId;
    }

    public String getCustomerId() {
        return customerId;
    }

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

    public ReviewDraft withAuthorName(final Optional<String> authorName) {
        return createBuilder().authorName(authorName).build();
    }

    public ReviewDraft withAuthorName(final String authorName) {
        return withAuthorName(Optional.of(authorName));
    }

    public ReviewDraft withTitle(final Optional<String> title) {
        return createBuilder().title(title).build();
    }  
    
    public ReviewDraft withTitle(final String title) {
        return withTitle(Optional.of(title));
    }    
    
    public ReviewDraft withText(final Optional<String> text) {
        return createBuilder().text(text).build();
    }  
    
    public ReviewDraft withText(final String text) {
        return withText(Optional.of(text));
    }

    public ReviewDraft withScore(final Optional<Double> score) {
        return createBuilder().score(score).build();
    }

    public ReviewDraft withScore(final double score) {
        return withScore(Optional.of(score));
    }

    private ReviewDraftBuilder createBuilder() {
        return ReviewDraftBuilder.of(this);
    }
}
