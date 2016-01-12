package io.sphere.sdk.reviews;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Resource;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.states.State;
import io.sphere.sdk.types.Custom;
import io.sphere.sdk.types.CustomFields;
import io.sphere.sdk.types.CustomFieldsDraft;

import javax.annotation.Nullable;
import java.util.Locale;

@JsonDeserialize(as = ReviewImpl.class)
public interface Review extends Resource<Review>, Custom {
    @Nullable
    String getAuthorName();

    @Nullable
    CustomFields getCustom();

    @Nullable
    Reference<Customer> getCustomer();

    @Nullable
    String getKey();

    @Nullable
    Locale getLocale();

    @Nullable
    Integer getRating();

    @Nullable
    Reference<State> getState();

    @Nullable
    Reference<?> getTarget();

    @Nullable
    String getText();

    @Nullable
    String getTitle();

    @Nullable
    String getUniquenessValue();

    static String referenceTypeId() {
        return "review";
    }

    static String resourceTypeId() {
        return "review";
    }

    static TypeReference<Review> typeReference(){
        return new TypeReference<Review>() {
            @Override
            public String toString() {
                return "TypeReference<Review>";
            }
        };
    }

    static Reference<Review> referenceOfId(final String id) {
        return Reference.of(referenceTypeId(), id);
    }

    default Reference<Review> toReference() {
        return Reference.of(referenceTypeId(), getId(), this);
    }
}
