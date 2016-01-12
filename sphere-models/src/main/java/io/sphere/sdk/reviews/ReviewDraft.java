package io.sphere.sdk.reviews;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.states.State;
import io.sphere.sdk.types.CustomFieldsDraft;

import javax.annotation.Nullable;
import java.util.Locale;

@JsonDeserialize(as = ReviewDraftImpl.class)
public interface ReviewDraft {
    @Nullable
    String getAuthorName();

    @Nullable
    CustomFieldsDraft getCustom();

    @Nullable
    ResourceIdentifier<Customer> getCustomer();

    @Nullable
    String getKey();

    @Nullable
    Locale getLocale();

    @Nullable
    Integer getRating();

    @Nullable
    ResourceIdentifier<State> getState();

    @Nullable
    ResourceIdentifier<?> getTarget();

    @Nullable
    String getText();

    @Nullable
    String getTitle();

    @Nullable
    String getUniquenessValue();
}
