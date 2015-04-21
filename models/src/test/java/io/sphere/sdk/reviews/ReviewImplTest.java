package io.sphere.sdk.reviews;

import org.junit.Test;

import java.time.Instant;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Created by njacinto on 21/04/15.
 */
public class ReviewImplTest {
    static final ReviewImpl DEFAULT_REVIEW;
    static final String DEFAULT_REVIEW_ID = "review-id";
    static final long DEFAULT_VERSION = 2;
    static final Instant DEFAULT_CREATED_AT = Instant.now().minusSeconds(60);
    static final Instant DEFAULT_LAST_MODIFIED_AT = Instant.now();
    static final String DEFAULT_PRODUCT_ID = "product-id";
    static final String DEFAULT_CUSTOMER_ID = "customer-id";
    static final String DEFAULT_AUTHOR_NAME = "customer-id";
    static final String DEFAULT_TITLE = "customer-id";
    static final String DEFAULT_TEXT = "customer-id";
    static final float DEFAULT_SCORE = 0.3f;

    static {
        DEFAULT_REVIEW = new ReviewImpl(DEFAULT_REVIEW_ID, DEFAULT_VERSION, DEFAULT_CREATED_AT, DEFAULT_LAST_MODIFIED_AT,
                DEFAULT_PRODUCT_ID, DEFAULT_CUSTOMER_ID, Optional.of(DEFAULT_AUTHOR_NAME),
                Optional.of(DEFAULT_TITLE), Optional.of(DEFAULT_TEXT), Optional.of(DEFAULT_SCORE));
    }

    @Test
    public void testGetProductId() throws Exception {
        assertNotNull(DEFAULT_REVIEW.getProductId());
        assertEquals(DEFAULT_PRODUCT_ID, DEFAULT_REVIEW.getProductId());
    }

    @Test
    public void testGetCustomerId() throws Exception {
        assertNotNull(DEFAULT_REVIEW.getCustomerId());
        assertEquals(DEFAULT_CUSTOMER_ID, DEFAULT_REVIEW.getCustomerId());
    }

    @Test
    public void testGetAuthorName() throws Exception {
        assertTrue(DEFAULT_REVIEW.getAuthorName().isPresent());
        assertEquals(DEFAULT_AUTHOR_NAME, DEFAULT_REVIEW.getAuthorName().get());
    }

    @Test
    public void testGetTitle() throws Exception {
        assertTrue(DEFAULT_REVIEW.getTitle().isPresent());
        assertEquals(DEFAULT_TITLE, DEFAULT_REVIEW.getTitle().get());
    }

    @Test
    public void testGetText() throws Exception {
        assertTrue(DEFAULT_REVIEW.getText().isPresent());
        assertEquals(DEFAULT_TEXT, DEFAULT_REVIEW.getText().get());
    }

    @Test
    public void testGetScore() throws Exception {
        assertTrue(DEFAULT_REVIEW.getScore().isPresent());
        assertTrue(isScoreInValidRange(DEFAULT_REVIEW.getScore().get(), DEFAULT_SCORE));
    }

    public static boolean isScoreInValidRange(float score, float referenceValue){
        return referenceValue + 0.1 > score && referenceValue - 0.1 < score;
    }
}