package io.sphere.sdk.reviews;

import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Builder;
import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.products.ProductIdentifiable;

import java.util.Optional;

public class ReviewDraftBuilder extends Base implements Builder<ReviewDraft> {
    private final String productId;
    private final String customerId;
    private Optional<String> authorName = Optional.empty();
    private Optional<String> title = Optional.empty();
    private Optional<String> text = Optional.empty();
    private Optional<Double> score = Optional.empty();

    private ReviewDraftBuilder(final String productId, final String customerId) {
        this.productId = productId;
        this.customerId = customerId;
    }

    public static ReviewDraftBuilder of(final ProductIdentifiable product, final Identifiable<Customer> customer) {
        return of(product.getId(), customer.getId());
    }

    public static ReviewDraftBuilder of(final String productId, final String customerId) {
        return new ReviewDraftBuilder(productId, customerId);
    }

    public static ReviewDraftBuilder of(final ReviewDraft template) {
        return new ReviewDraftBuilder(template.getProductId(), template.getCustomerId())
                .authorName(template.getAuthorName()).title(template.getTitle())
                .text(template.getText()).score(template.getScore());
    }

    public ReviewDraftBuilder authorName(final Optional<String> authorName) {
        this.authorName = authorName;
        return this;
    }

    public ReviewDraftBuilder authorName(final String authorName) {
        return authorName(Optional.ofNullable(authorName));
    }

    public ReviewDraftBuilder title(final Optional<String> title) {
        this.title = title;
        return this;
    }

    public ReviewDraftBuilder title(final String title) {
        return title(Optional.ofNullable(title));
    }

    public ReviewDraftBuilder text(final Optional<String> text) {
        this.text = text;
        return this;
    }

    public ReviewDraftBuilder text(final String text) {
        return text(Optional.ofNullable(text));
    }

    public ReviewDraftBuilder score(final Optional<Double> score) {
        this.score = score;
        return this;
    }

    public ReviewDraftBuilder score(final Double score) {
        return score(Optional.ofNullable(score));
    }

    @Override
    public ReviewDraft build() {
        return new ReviewDraft(productId, customerId, authorName, title, text, score);
    }
}
