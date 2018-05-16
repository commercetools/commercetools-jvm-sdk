package io.sphere.sdk.reviews;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.*;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Resource;
import io.sphere.sdk.models.WithKey;
import io.sphere.sdk.states.State;
import io.sphere.sdk.types.Custom;
import io.sphere.sdk.types.CustomFields;
import io.sphere.sdk.types.TypeDraft;

import javax.annotation.Nullable;
import java.util.Locale;

/**
  Reviews are used to evaluate products and channels.

  <h3 id=review-approval-process>Review Approval Process</h3>

  <p>If you do not need any approval process, skip this part.</p>

 <p>If we have an approval process for a review to be used for a product or a channel, we model the approval process with a state machine.</p>

 <p>First of all, once per commercetools project, we create the approved state. Then we create the initial to-approve state, which has a possible transition to the approved state:</p>

 {@include.example io.sphere.sdk.reviews.approvaldemo.CreateReviewStates}

 Only states with {@link io.sphere.sdk.states.StateRole#REVIEW_INCLUDED_IN_STATISTICS} makes review ratings count in statistics.

 <h4 id=creating-reviews>Creating reviews</h4>
 <p>Now we can create a review in the initial state to-approve:</p>

 {@include.example io.sphere.sdk.reviews.approvaldemo.CreateReviewToApprove}

 <h4 id=query-to-approve-reviews>Querying which reviews should be approved</h4>

 {@include.example io.sphere.sdk.reviews.approvaldemo.QueryReviewsToApprove}

 <h4 id=approve-a-review>Approving a review</h4>


 {@include.example io.sphere.sdk.reviews.approvaldemo.ApprovingAReview}

<h3 id=displaying-products>Displaying Products</h3>
<h4 id=search-products-for-minimal-rating>Searching for Products with a Minimal Rating</h4>
 <p>We can display all products that:</p>
<ul>
 <li>have at least 2 stars (average rating superior to 2)</li>
 <li>with facets about the number of products rated with an average in the different ranges 0 to 1 star, 1 to 2 stars, 2 to 3 stars, 3 to 4 stars and 4 to 5 stars.</li>
 <li>sorted by average ratings</li>
 </ul>

 {@include.example io.sphere.sdk.reviews.ReviewProductProjectionSearchIntegrationTest#searchForReviewsWithAverageRatingGreaterThan2()}

 <h4 id=query-approved-reviews-for-one-product>Getting reviews for one product (only approved)</h4>
 {@include.example io.sphere.sdk.reviews.ReviewProductProjectionSearchIntegrationTest#getApprovedReviewsForOneProduct()}

 <h4 id=query-reviews-for-one-product>Getting reviews for one product</h4>
 {@include.example io.sphere.sdk.reviews.ReviewProductProjectionSearchIntegrationTest#getReviewsForOneProduct()}

 @see io.sphere.sdk.reviews.commands.ReviewCreateCommand
 @see io.sphere.sdk.reviews.commands.ReviewUpdateCommand
 @see io.sphere.sdk.reviews.commands.ReviewDeleteCommand
 @see io.sphere.sdk.reviews.queries.ReviewQuery
 @see io.sphere.sdk.reviews.queries.ReviewByIdGet
 @see io.sphere.sdk.reviews.queries.ReviewByKeyGet
 */
@JsonDeserialize(as = ReviewImpl.class)
@ResourceValue
@HasQueryEndpoint()
@ResourceInfo(pluralName = "reviews", pathElement = "reviews")
@HasByIdGetEndpoint(includeExamples = "io.sphere.sdk.reviews.queries.ReviewByIdGetIntegrationTest#execution()", javadocSummary = "Retrieves a review by a known ID.")
@HasByKeyGetEndpoint
@HasCreateCommand(includeExamples = "io.sphere.sdk.reviews.commands.ReviewCreateCommandIntegrationTest#createByCode()")
@HasUpdateCommand(javadocSummary = "Updates a review.", updateWith = "key")
@HasDeleteCommand(deleteWith = {"key","id"}, canEraseUsersData = true)
@HasQueryModel()
public interface Review extends Resource<Review>, Custom, WithKey {
    /**
     * The name of the author which created this review or null.
     *
     * @see io.sphere.sdk.reviews.commands.updateactions.SetAuthorName
     * @return author name
     */
    @Nullable
    String getAuthorName();

    /**
     * Gets the custom fields of this review or null.
     * @see io.sphere.sdk.reviews.commands.updateactions.SetCustomField
     * @see io.sphere.sdk.reviews.commands.updateactions.SetCustomType
     * @return custom fields
     */
    @Nullable
    CustomFields getCustom();

    /**
     * Gets the customer which created this review or null.
     * @see io.sphere.sdk.reviews.commands.updateactions.SetCustomer
     * @return customer
     */
    @Nullable
    Reference<Customer> getCustomer();

    /**
     * Gets the key assigned to this review or null.
     *
     * @see io.sphere.sdk.reviews.commands.updateactions.SetKey
     * @return key
     */
    @Nullable
    String getKey();

    /**
     * Gets the locale (language) in which the text and title are or null.
     * @return locale
     */
    @Nullable
    @QueryModelHint(type = "io.sphere.sdk.queries.LocaleQuerySortingModel<io.sphere.sdk.reviews.Review>", impl = "return localeQuerySortingModel(fieldName);")
    Locale getLocale();

    /**
     * Gets the rating or null.
     * @see io.sphere.sdk.reviews.commands.updateactions.SetRating
     * @return the rating
     */
    @Nullable
    Integer getRating();

    /**
     * Gets the state of this review or null.
     * @see io.sphere.sdk.reviews.commands.updateactions.TransitionState
     * @return state
     */
    @Nullable
    Reference<State> getState();

    /**
     * Identifies the target of the review. Can be a Product or a Channel or nothing.
     * @see io.sphere.sdk.reviews.commands.updateactions.SetTarget
     * @return the target of this review or null
     */
    @Nullable
    Reference<?> getTarget();

    /**
     * Gets the text of this review or null.
     * @see io.sphere.sdk.reviews.commands.updateactions.SetText
     * @return text
     */
    @Nullable
    String getText();

    /**
     * Gets the title of this review or null.
     * @see io.sphere.sdk.reviews.commands.updateactions.SetTitle
     * @return title
     */
    @Nullable
    String getTitle();

    @Nullable
    String getUniquenessValue();

    /**
     * Indicates if this review is taken into account in the ratings statistics of the target.
     A review is per default used in the statistics, unless the review is in a state that does not have the role ReviewIncludedInStatistics.
     If the role of a State is modified after the calculation of this field, the calculation is not updated.
     * @return is included in review rating of target
     */
    @JsonProperty("includedInStatistics")
    Boolean isIncludedInStatistics();

    /**
     * A type hint for references which resource type is linked in a reference.
     * @see Reference#getTypeId()
     * @return type hint
     */
    static String referenceTypeId() {
        return "review";
    }

    /**
     * An identifier for this resource which supports {@link CustomFields}.
     * @see TypeDraft#getResourceTypeIds()
     * @see io.sphere.sdk.types.Custom
     * @return ID of this resource type
     */
    static String resourceTypeId() {
        return "review";
    }

    /**
     * Creates a container which contains the full Java type information to deserialize this class from JSON.
     *
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(byte[], TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(String, TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(com.fasterxml.jackson.databind.JsonNode, TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObjectFromResource(String, TypeReference)
     *
     * @return type reference
     */
    static TypeReference<Review> typeReference() {
        return new TypeReference<Review>() {
            @Override
            public String toString() {
                return "TypeReference<Review>";
            }
        };
    }

    /**
     * Creates a reference for one item of this class by a known ID.
     *
     * <p>An example for categories but this applies for other resources, too:</p>
     * {@include.example io.sphere.sdk.categories.CategoryTest#referenceOfId()}
     *
     * <p>If you already have a resource object, then use {@link #toReference()} instead:</p>
     *
     * {@include.example io.sphere.sdk.categories.CategoryTest#toReference()}
     *
     * @param id the ID of the resource which should be referenced.
     * @return reference
     */
    static Reference<Review> referenceOfId(final String id) {
        return Reference.of(referenceTypeId(), id);
    }

    default Reference<Review> toReference() {
        return Reference.of(referenceTypeId(), getId(), this);
    }
}
