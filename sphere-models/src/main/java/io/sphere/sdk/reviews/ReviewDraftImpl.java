package io.sphere.sdk.reviews;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.states.State;
import io.sphere.sdk.types.CustomFieldsDraft;

import javax.annotation.Nullable;
import java.util.Locale;

final class ReviewDraftImpl extends Base implements ReviewDraft {
    @Nullable
    private final String key;
    @Nullable
    private final String uniquenessValue;
    @Nullable
    private final String authorName;
    @Nullable
    private final String text;
    @Nullable
    private final String title;
    @Nullable
    private final Locale locale;
    @Nullable
    private final ResourceIdentifier<?> target;
    @Nullable
    private final ResourceIdentifier<State> state;
    @Nullable
    private final Integer rating;
    @Nullable
    private final ResourceIdentifier<Customer> customer;
    @Nullable
    private final CustomFieldsDraft custom;

    @JsonCreator
    ReviewDraftImpl(final String authorName, final String key, final String uniquenessValue, final String text, final String title, final Locale locale, final ResourceIdentifier<?> target, final ResourceIdentifier<State> state, final Integer rating, final ResourceIdentifier<Customer> customer, final CustomFieldsDraft custom) {
        this.authorName = authorName;
        this.key = key;
        this.uniquenessValue = uniquenessValue;
        this.text = text;
        this.title = title;
        this.locale = locale;
        this.target = target;
        this.state = state;
        this.rating = rating;
        this.customer = customer;
        this.custom = custom;
    }

    @Override
    @Nullable
    public String getAuthorName() {
        return authorName;
    }

    @Override
    @Nullable
    public CustomFieldsDraft getCustom() {
        return custom;
    }

    @Override
    @Nullable
    public ResourceIdentifier<Customer> getCustomer() {
        return customer;
    }

    @Override
    @Nullable
    public String getKey() {
        return key;
    }

    @Override
    @Nullable
    public Locale getLocale() {
        return locale;
    }

    @Override
    @Nullable
    public Integer getRating() {
        return rating;
    }

    @Override
    @Nullable
    public ResourceIdentifier<State> getState() {
        return state;
    }

    @Override
    @Nullable
    public ResourceIdentifier<?> getTarget() {
        return target;
    }

    @Override
    @Nullable
    public String getText() {
        return text;
    }

    @Override
    @Nullable
    public String getTitle() {
        return title;
    }

    @Override
    @Nullable
    public String getUniquenessValue() {
        return uniquenessValue;
    }
}
