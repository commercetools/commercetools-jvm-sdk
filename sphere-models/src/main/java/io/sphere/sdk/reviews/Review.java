package io.sphere.sdk.reviews;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.models.Resource;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.states.State;

import javax.annotation.Nullable;

/**
 Review of a product by a customer. A customer can create only one review per product.
 */
@JsonDeserialize(as = ReviewImpl.class)
public interface Review extends Resource<Review> {

    String getProductId();

    String getCustomerId();

    @Nullable
    String getAuthorName();

    @Nullable
    String getTitle();

    @Nullable
    String getText();

    @Nullable
    Double getScore();

    /**
     * Returns this state of this Review.
     *
     * @return state of this review or null
     *
     * @see io.sphere.sdk.reviews.commands.updateactions.TransitionState
     */
    @Nullable
    Reference<State> getState();

    default Reference<Review> toReference() {
        return Reference.of(referenceTypeId(), getId());
    }

    static String referenceTypeId() {
        return "review";
    }

    /**
     *
     * @deprecated use {@link #referenceTypeId()} instead
     * @return referenceTypeId
     */
    @Deprecated
    static String typeId() {
        return "review";
    }

    static TypeReference<Review> typeReference() {
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
}
