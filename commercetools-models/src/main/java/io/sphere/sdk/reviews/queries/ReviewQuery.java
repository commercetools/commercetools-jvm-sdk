package io.sphere.sdk.reviews.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.queries.MetaModelQueryDsl;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.reviews.Review;
import io.sphere.sdk.reviews.expansion.ReviewExpansionModel;


/** {@doc.gen summary reviews}


    @see Review
 */
public interface ReviewQuery extends MetaModelQueryDsl<Review, ReviewQuery, ReviewQueryModel, ReviewExpansionModel<Review>> {
    /**
     * Creates a container which contains the full Java type information to deserialize the query result (NOT this class) from JSON.
     *
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(byte[], TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(String, TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(com.fasterxml.jackson.databind.JsonNode, TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObjectFromResource(String, TypeReference)
     *
     * @return type reference
     */
    static TypeReference<PagedQueryResult<Review>> resultTypeReference() {
        return new TypeReference<PagedQueryResult<Review>>(){
            @Override
            public String toString() {
                return "TypeReference<PagedQueryResult<Review>>";
            }
        };
    }

    static ReviewQuery of() {
        return new ReviewQueryImpl();
    }
}
