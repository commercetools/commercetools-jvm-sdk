package io.sphere.sdk.reviews;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.models.Resource;
import io.sphere.sdk.models.Reference;

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

    default Reference<Review> toReference() {
        return Reference.of(typeId(), getId());
    }

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
}
