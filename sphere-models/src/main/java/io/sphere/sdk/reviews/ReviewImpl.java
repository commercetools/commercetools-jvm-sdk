package io.sphere.sdk.reviews;

import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.ResourceImpl;
import io.sphere.sdk.states.State;
import io.sphere.sdk.types.CustomFields;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.Locale;

final class ReviewImpl extends ResourceImpl<Review> implements Review {
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
    private final Reference<JsonNode> target;
    @Nullable
    private final Reference<State> state;
    @Nullable
    private final Integer rating;
    @Nullable
    private final Reference<Customer> customer;
    @Nullable
    private final CustomFields custom;
    private final Boolean includedInStatistics;

    ReviewImpl(final String id, final Long version, final ZonedDateTime createdAt,
               final ZonedDateTime lastModifiedAt,
               @Nullable final String authorName, @Nullable final String key, @Nullable final String uniquenessValue, @Nullable final String text,
               @Nullable final String title, @Nullable final Locale locale, @Nullable final Reference<JsonNode> target,
               @Nullable final Reference<State> state, @Nullable final Integer rating, @Nullable final Reference<Customer> customer,
               @Nullable final CustomFields custom, final Boolean includedInStatistics) {
        super(id, version, createdAt, lastModifiedAt);
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
        this.includedInStatistics = includedInStatistics;
    }

    @Override
    @Nullable
    public String getAuthorName() {
        return authorName;
    }

    @Override
    @Nullable
    public CustomFields getCustom() {
        return custom;
    }

    @Override
    @Nullable
    public Reference<Customer> getCustomer() {
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
    public Reference<State> getState() {
        return state;
    }

    @Override
    @Nullable
    public Reference<JsonNode> getTarget() {
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

    public Boolean isIncludedInStatistics() {
        return includedInStatistics;
    }
}
