package io.sphere.sdk.reviews;

import io.sphere.sdk.models.Base;
import java.util.Optional;

/**
 * Template to create a new Review.
 *
 * @see ReviewDraftBuilder
 */
public class ReviewDraft extends Base {
    private final String productId;
    private final String customerId;
    private final Optional<String> authorName;
    private final Optional<String> title;
    private final Optional<String> text;
    private final Optional<Float> score;

    ReviewDraft(final String productId, final String customerId, final Optional<String> authorName, final Optional<String> title,
                final Optional<String> text, final Optional<Float> score) {
        this.productId = productId;
        this.customerId = customerId;
        this.authorName = authorName;
        this.title = title;
        this.text = text;
        this.score = score;
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
        return authorName;
    }

    public Optional<String> getTitle() {
        return title;
    }

    public Optional<String> getText() {
        return text;
    }

    public Optional<Float> getScore() {
        return score;
    }

    public ReviewDraft withAuthorName(final Optional<String> authorName) {
        return ReviewDraftBuilder.of(this).authorName(authorName).build();
    }

    public ReviewDraft withAuthorName(final String authorName) {
        return withAuthorName(Optional.of(authorName));
    }


    public ReviewDraft withTitle(final Optional<String> title) {
        return ReviewDraftBuilder.of(this).title(title).build();
    }  
    
    public ReviewDraft withTitle(final String title) {
        return withTitle(Optional.of(title));
    }    
    
    public ReviewDraft withText(final Optional<String> text) {
        return ReviewDraftBuilder.of(this).text(text).build();
    }  
    
    public ReviewDraft withText(final String text) {
        return withText(Optional.of(text));
    }

    public ReviewDraft withScore(final Optional<Float> score) {
        return ReviewDraftBuilder.of(this).score(score).build();
    }

    public ReviewDraft withScore(final Float score) {
        return withScore(Optional.of(score));
    }

}
