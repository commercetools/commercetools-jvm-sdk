package io.sphere.sdk.reviews.queries;

import io.sphere.sdk.queries.Predicate;
import io.sphere.sdk.queries.StringQueryModel;
import io.sphere.sdk.queries.StringQuerySortingModel;
import io.sphere.sdk.reviews.Review;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by njacinto on 21/04/15.
 */
public class ReviewQueryModelTest {

    @Test
    public void testId() throws Exception {
        StringQueryModel<Review> query = ReviewQueryModel.get().id();
        Predicate<Review> predicate =  query.is("review-id");
        String queryStr = predicate.toSphereQuery();
        assertEquals("id=\"review-id\"", queryStr);
    }

    @Test
    public void testProductId() throws Exception {
        StringQueryModel<Review> query = ReviewQueryModel.get().productId();
        Predicate<Review> predicate =  query.is("product-id");
        String queryStr = predicate.toSphereQuery();
        assertEquals("productId=\"product-id\"", queryStr);
    }
}