package io.sphere.sdk.reviews;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.models.DefaultModel;
import io.sphere.sdk.models.Reference;

import java.util.Optional;

/**
 Review of a product by a customer. A customer can create only one review per product.
 */
@JsonDeserialize(as = ReviewImpl.class)
public interface Review extends DefaultModel<Review> {

    String getProductId();

    String getCustomerId();

    Optional<String> getAuthorName();

    Optional<String> getTitle();

    Optional<String> getText();

    Optional<Double> getScore();

    default Reference<Review> toReference() {
        return Reference.of(typeId(), getId());
    }

    static String typeId(){
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
}
