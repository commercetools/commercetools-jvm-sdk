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
