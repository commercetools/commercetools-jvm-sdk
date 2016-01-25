package io.sphere.sdk.reviews;

import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Builder;
import io.sphere.sdk.models.ResourceIdentifiable;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.states.State;
import io.sphere.sdk.types.CustomFieldsDraft;

import javax.annotation.Nullable;
import java.util.Locale;
import java.util.Optional;

/**
 * DraftBuilder for a new Review.
 *
 * @see Review
 * @see ReviewDraft
 * @see io.sphere.sdk.reviews.commands.ReviewCreateCommand
 */
public final class ReviewDraftBuilder extends Base implements Builder<ReviewDraft> {
    @Nullable
    private String key;
    @Nullable
    private String uniquenessValue;
    @Nullable
    private String authorName;
    @Nullable
    private String text;
    @Nullable
    private String title;
    @Nullable
    private Locale locale;
    @Nullable
    private ResourceIdentifier<?> target;
    @Nullable
    private ResourceIdentifier<State> state;
    @Nullable
    private Integer rating;
    @Nullable
    private ResourceIdentifier<Customer> customer;
    @Nullable
    private CustomFieldsDraft custom;

    public static ReviewDraftBuilder ofTitle(final String title) {
        return new ReviewDraftBuilder().title(title);
    }
    
    public static ReviewDraftBuilder ofText(final String text) {
        return new ReviewDraftBuilder().text(text);
    }

    public static ReviewDraftBuilder ofRating(final Integer rating) {
        return new ReviewDraftBuilder().rating(rating);
    }

    ReviewDraftBuilder() {
    }

    public ReviewDraftBuilder key(@Nullable final String key) {
        this.key = key;
        return this;
    }

    public ReviewDraftBuilder uniquenessValue(@Nullable final String uniquenessValue) {
        this.uniquenessValue = uniquenessValue;
        return this;
    }

    public ReviewDraftBuilder authorName(@Nullable final String authorName) {
        this.authorName = authorName;
        return this;
    }

    public ReviewDraftBuilder text(@Nullable final String text) {
        this.text = text;
        return this;
    }

    public ReviewDraftBuilder title(@Nullable final String title) {
        this.title = title;
        return this;
    }

    public ReviewDraftBuilder locale(@Nullable final Locale locale) {
        this.locale = locale;
        return this;
    }

    public ReviewDraftBuilder target(@Nullable final ResourceIdentifiable<?> target) {
        this.target = Optional.ofNullable(target).map(ResourceIdentifiable::toResourceIdentifier).orElse(null);
        return this;
    }

    public ReviewDraftBuilder state(@Nullable final ResourceIdentifiable<State> state) {
        this.state = Optional.ofNullable(state).map(ResourceIdentifiable::toResourceIdentifier).orElse(null);
        return this;
    }

    public ReviewDraftBuilder rating(@Nullable final Integer rating) {
        this.rating = rating;
        return this;
    }

    public ReviewDraftBuilder customer(@Nullable final ResourceIdentifiable<Customer> customer) {
        this.customer = Optional.ofNullable(customer).map(ResourceIdentifiable::toResourceIdentifier).orElse(null);
        return this;
    }

    public ReviewDraftBuilder custom(@Nullable final CustomFieldsDraft custom) {
        this.custom = custom;
        return this;
    }

    @Override
    public ReviewDraft build() {
        return new ReviewDraftImpl(authorName, key, uniquenessValue, text, title, locale, target, state, rating, customer, custom);
    }
}
