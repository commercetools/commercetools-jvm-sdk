package io.sphere.sdk.reviews.queries;

import io.sphere.sdk.reviews.Review;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by njacinto on 21/04/15.
 */
public class ReviewsEndpointTest {

    @Test
    public void testEndpoint(){
        assertEquals("/reviews", ReviewsEndpoint.ENDPOINT.endpoint());
        assertEquals(Review.typeReference(), ReviewsEndpoint.ENDPOINT.typeReference());
    }
}