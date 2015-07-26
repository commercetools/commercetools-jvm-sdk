package io.sphere.sdk.reviews;

import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.products.ProductIdentifiable;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * Template to create a new Review.
 *
 * @see ReviewDraftBuilder
 */
public class ReviewDraft extends Base {
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

    ReviewDraft(final String productId, final String customerId, @Nullable final String authorName, @Nullable final String title,
                @Nullable final String text, @Nullable final Double score) {
        final boolean scoreIsInValidRange = Optional.ofNullable(score).map(scoreValue -> scoreValue >= 0.0 && scoreValue <= 1.0).orElse(true);
        if (!scoreIsInValidRange) {
            throw new IllegalArgumentException("Valid scores are in [0..1].");
        }
        this.productId = productId;
        this.customerId = customerId;
        this.authorName = authorName;
        this.title = title;
        this.text = text;
        this.score = score;
    }

    public static ReviewDraft of(final ProductIdentifiable product, final Identifiable<Customer> customer) {
        return of(product.getId(), customer.getId());
    }

    public static ReviewDraft of(final String productId, final String customerId) {
        return new ReviewDraft(productId, customerId, null, null, null, null);
    }

    public String getProductId() {
        return productId;
    }

    public String getCustomerId() {
        return customerId;
    }

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

    public ReviewDraft withAuthorName(@Nullable final String authorName) {
        return createBuilder().authorName(authorName).build();
    }

    public ReviewDraft withTitle(@Nullable final String title) {
        return createBuilder().title(title).build();
    }   
    
    public ReviewDraft withText(@Nullable final String text) {
        return createBuilder().text(text).build();
    }  

    public ReviewDraft withScore(@Nullable final Double score) {
        return createBuilder().score(score).build();
    }

    private ReviewDraftBuilder createBuilder() {
        return ReviewDraftBuilder.of(this);
    }
}
