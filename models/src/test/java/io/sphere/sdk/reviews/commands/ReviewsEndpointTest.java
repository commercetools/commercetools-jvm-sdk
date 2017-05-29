package io.sphere.sdk.reviews.commands;

import io.sphere.sdk.reviews.Review;
import io.sphere.sdk.reviews.queries.*;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by njacinto on 21/04/15.
 */
public class ReviewsEndpointTest {

    @Test
    public void testEndpoint(){
        assertEquals("/reviews", io.sphere.sdk.reviews.commands.ReviewsEndpoint.ENDPOINT.endpoint());
        assertEquals(Review.typeReference(), io.sphere.sdk.reviews.commands.ReviewsEndpoint.ENDPOINT.typeReference());
    }

}