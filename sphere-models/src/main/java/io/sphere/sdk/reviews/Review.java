package io.sphere.sdk.reviews;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Resource;
import io.sphere.sdk.states.State;
import io.sphere.sdk.types.Custom;
import io.sphere.sdk.types.CustomFields;

import javax.annotation.Nullable;
import java.util.Locale;

/**
  Reviews are used to evaluate products and channels.

  <h3 id=review-approval-process>Review Approval Process</h3>

  <p>If you do not need any approval process, skip this part.</p>

 <p>If we have an approval process for a review to be used for a product or a channel, we model the approval process with a state machine.</p>

 <p>First of all, we create the approved state. Then we create the initial to-approve state, which has a possible transition to the approved state:</p>

 {@include.example io.sphere.sdk.reviews.approvaldemo.CreateReviewStates}

 Only states with {@link io.sphere.sdk.states.StateRole#REVIEW_INCLUDED_IN_STATISTICS} makes review ratings count in statistics.

 <h4>Creating reviews</h4>
 <p>Now we can create a review in the initial state to-approve:</p>

 {@include.example io.sphere.sdk.reviews.approvaldemo.CreateReviewToApprove}

 <h4>Query which reviews should be approved</h4>

 {@include.example io.sphere.sdk.reviews.approvaldemo.QueryReviewsToApprove}

 <h4>Approving a review</h4>


 {@include.example io.sphere.sdk.reviews.approvaldemo.ApprovingAReview}




 */
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
