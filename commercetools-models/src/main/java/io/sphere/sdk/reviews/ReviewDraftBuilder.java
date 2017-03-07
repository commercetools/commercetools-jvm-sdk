package io.sphere.sdk.reviews;

import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.models.ResourceIdentifiable;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.states.State;
import io.sphere.sdk.types.CustomFieldsDraft;

import javax.annotation.Nullable;
import java.util.Locale;
import java.util.Optional;

public final class ReviewDraftBuilder extends ReviewDraftBuilderBase<ReviewDraftBuilder> {
    ReviewDraftBuilder() {
    }

    ReviewDraftBuilder(@Nullable final String authorName, @Nullable final CustomFieldsDraft custom, @Nullable final ResourceIdentifier<Customer> customer, @Nullable final String key, @Nullable final Locale locale, @Nullable final Integer rating, @Nullable final ResourceIdentifier<State> state, @Nullable final ResourceIdentifier<?> target, @Nullable final String text, @Nullable final String title, @Nullable final String uniquenessValue) {
        super(authorName, custom, customer, key, locale, rating, state, target, text, title, uniquenessValue);
    }

    public static ReviewDraftBuilder ofTitle(final String title) {
        return new ReviewDraftBuilder().title(title);
    }

    public static ReviewDraftBuilder ofText(final String text) {
        return new ReviewDraftBuilder().text(text);
    }

    public static ReviewDraftBuilder ofRating(final Integer rating) {
        return new ReviewDraftBuilder().rating(rating);
    }

    public ReviewDraftBuilder target(@Nullable final ResourceIdentifiable<?> target) {
        this.target = Optional.ofNullable(target).map(ResourceIdentifiable::toResourceIdentifier).orElse(null);
        return this;
    }

    public ReviewDraftBuilder state(@Nullable final ResourceIdentifiable<io.sphere.sdk.states.State> state) {
        this.state = Optional.ofNullable(state).map(ResourceIdentifiable::toResourceIdentifier).orElse(null);
        return this;
    }

    public ReviewDraftBuilder customer(@Nullable final ResourceIdentifiable<Customer> customer) {
        this.customer = Optional.ofNullable(customer).map(ResourceIdentifiable::toResourceIdentifier).orElse(null);
        return this;
    }
}
