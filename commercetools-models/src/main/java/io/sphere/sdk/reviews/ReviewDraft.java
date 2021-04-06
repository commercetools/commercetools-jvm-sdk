package io.sphere.sdk.reviews;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.models.WithKey;
import io.sphere.sdk.states.State;
import io.sphere.sdk.types.CustomDraft;
import io.sphere.sdk.types.CustomFieldsDraft;

import javax.annotation.Nullable;
import java.util.Locale;

/**
 * Draft for a new Review.
 *
 * @see Review
 * @see ReviewDraftBuilder
 * @see io.sphere.sdk.reviews.commands.ReviewCreateCommand
 */
@JsonDeserialize(as = ReviewDraftDsl.class)
@ResourceDraftValue(
        abstractBuilderClass = true,
        factoryMethods = {
            @FactoryMethod(methodName = "ofTitle", parameterNames = "title"),
            @FactoryMethod(methodName = "ofText", parameterNames = "text"),
            @FactoryMethod(methodName = "ofRating", parameterNames = "rating")})
public interface ReviewDraft extends WithKey, CustomDraft {
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

    /**
     * Target of this review. Currently are only {@link io.sphere.sdk.products.Product}s and {@link io.sphere.sdk.channels.Channel}s supported.
     * @return target
     */
    @Nullable
    ResourceIdentifier<?> getTarget();

    @Nullable
    String getText();

    @Nullable
    String getTitle();

    @Nullable
    String getUniquenessValue();
}
