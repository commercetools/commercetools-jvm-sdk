package io.sphere.sdk.reviews;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.models.DefaultModel;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.models.Reference;

import java.util.Optional;
import java.util.Set;

/** Review represents the opinion of ... concerning a specific product.

    <p id=operations>Operations:</p>
    <ul>
        <li>Create a Review with {@link io.sphere.sdk.reviews.commands.ReviewCreateCommand}.</li>
        <li>Query a Review with {@link io.sphere.sdk.reviews.queries.ReviewQuery}.</li>
    </ul>
 */
@JsonDeserialize(as = ReviewImpl.class)
public interface Review extends DefaultModel<Review> {
    public static final TypeReference<Review> JSON_TYPE_REFERENCE = new TypeReference<Review>() {
            @Override
            public String toString() {
                return "TypeReference<Review>";
            }
        };
    
    public String getProductId();

    public String getCustomerId();

    public Optional<String> getAuthorName();

    public Optional<String> getTitle();

    public Optional<String> getText();

    public Optional<Float> getScore();

    public default Reference<Review> toReference() {
        return Reference.of(typeId(), getId());
    }

    public static String typeId(){
        return "review"; // why not a number?
    }

    public static TypeReference<Review> typeReference(){
        return JSON_TYPE_REFERENCE;
    }

}
