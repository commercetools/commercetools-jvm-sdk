package io.sphere.sdk.reviews;

import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Created by njacinto on 21/04/15.
 */
public class ReviewDraftTest {
    static final ReviewDraft DEFAULT_REVIEW;


    static {
        DEFAULT_REVIEW = ReviewDraft.of(ReviewImplTest.DEFAULT_PRODUCT_ID, ReviewImplTest.DEFAULT_CUSTOMER_ID)
                .withAuthorName(ReviewImplTest.DEFAULT_AUTHOR_NAME).withTitle(ReviewImplTest.DEFAULT_TITLE)
                .withText(ReviewImplTest.DEFAULT_TEXT).withScore(ReviewImplTest.DEFAULT_SCORE);
    }

    @Test
    public void testOf() throws Exception {
        ReviewDraft review = ReviewDraft.of(ReviewImplTest.DEFAULT_PRODUCT_ID, ReviewImplTest.DEFAULT_CUSTOMER_ID);
        assertEquals(ReviewImplTest.DEFAULT_PRODUCT_ID, review.getProductId());
        assertEquals(ReviewImplTest.DEFAULT_CUSTOMER_ID, review.getCustomerId());
    }

    @Test
    public void testGetProductId() throws Exception {
        assertEquals(ReviewImplTest.DEFAULT_PRODUCT_ID, DEFAULT_REVIEW.getProductId());
    }

    @Test
    public void testGetCustomerId() throws Exception {
        assertEquals(ReviewImplTest.DEFAULT_CUSTOMER_ID, DEFAULT_REVIEW.getCustomerId());
    }

    @Test
    public void testGetAuthorName() throws Exception {
        assertEquals(ReviewImplTest.DEFAULT_AUTHOR_NAME, DEFAULT_REVIEW.getAuthorName().get());
    }

    @Test
    public void testGetTitle() throws Exception {
        assertEquals(ReviewImplTest.DEFAULT_TITLE, DEFAULT_REVIEW.getTitle().get());
    }

    @Test
    public void testGetText() throws Exception {
        assertEquals(ReviewImplTest.DEFAULT_TEXT, DEFAULT_REVIEW.getText().get());
    }

    @Test
    public void testGetScore() throws Exception {
        assertTrue(ReviewImplTest.isScoreInValidRange(DEFAULT_REVIEW.getScore().get(), ReviewImplTest.DEFAULT_SCORE));
    }

    @Test
    public void testWithTitle() throws Exception {
        ReviewDraft review = ReviewDraft.of(ReviewImplTest.DEFAULT_PRODUCT_ID, ReviewImplTest.DEFAULT_CUSTOMER_ID);
        assertFalse(review.getTitle().isPresent());
        ReviewDraft reviewWith = review.withTitle(ReviewImplTest.DEFAULT_TITLE);
        assertFalse(review==reviewWith);
        assertEquals(ReviewImplTest.DEFAULT_TITLE, reviewWith.getTitle().get());
    }

    @Test
    public void testWithText() throws Exception {
        ReviewDraft review = ReviewDraft.of(ReviewImplTest.DEFAULT_PRODUCT_ID, ReviewImplTest.DEFAULT_CUSTOMER_ID);
        assertFalse(review.getText().isPresent());
        ReviewDraft reviewWith = review.withText(ReviewImplTest.DEFAULT_TEXT);
        assertFalse(review==reviewWith);
        assertEquals(ReviewImplTest.DEFAULT_TEXT, reviewWith.getText().get());
    }

    @Test
    public void testWithScore() throws Exception {
        ReviewDraft review = ReviewDraft.of(ReviewImplTest.DEFAULT_PRODUCT_ID, ReviewImplTest.DEFAULT_CUSTOMER_ID);
        assertFalse(review.getScore().isPresent());
        ReviewDraft reviewWith = review.withScore(ReviewImplTest.DEFAULT_SCORE);
        assertFalse(review==reviewWith);
        assertTrue(ReviewImplTest.isScoreInValidRange(reviewWith.getScore().get(), ReviewImplTest.DEFAULT_SCORE));
    }

    @Test
    public void testWithAuthorName() throws Exception {
        ReviewDraft review = ReviewDraft.of(ReviewImplTest.DEFAULT_PRODUCT_ID,ReviewImplTest.DEFAULT_CUSTOMER_ID);
        assertFalse(review.getAuthorName().isPresent());
        ReviewDraft reviewWith = review.withAuthorName(ReviewImplTest.DEFAULT_AUTHOR_NAME);
        assertFalse(review==reviewWith);
        assertEquals(ReviewImplTest.DEFAULT_AUTHOR_NAME, reviewWith.getAuthorName().get());
    }

    @Test
    public void testWithTitleOptional() throws Exception {
        ReviewDraft review = ReviewDraft.of(ReviewImplTest.DEFAULT_PRODUCT_ID, ReviewImplTest.DEFAULT_CUSTOMER_ID);
        assertFalse(review.getTitle().isPresent());
        ReviewDraft reviewWith = review.withTitle(Optional.of(ReviewImplTest.DEFAULT_TITLE));
        assertFalse(review==reviewWith);
        assertEquals(ReviewImplTest.DEFAULT_TITLE, reviewWith.getTitle().get());
        reviewWith = review.withTitle(Optional.empty());
        assertFalse(review==reviewWith);
        assertFalse(reviewWith.getTitle().isPresent());
    }

    @Test
    public void testWithTextOptional() throws Exception {
        ReviewDraft review = ReviewDraft.of(ReviewImplTest.DEFAULT_PRODUCT_ID, ReviewImplTest.DEFAULT_CUSTOMER_ID);
        assertFalse(review.getText().isPresent());
        ReviewDraft reviewWith = review.withText(Optional.of(ReviewImplTest.DEFAULT_TEXT));
        assertFalse(review==reviewWith);
        assertEquals(ReviewImplTest.DEFAULT_TEXT, reviewWith.getText().get());
        reviewWith = review.withText(Optional.empty());
        assertFalse(review==reviewWith);
        assertFalse(reviewWith.getText().isPresent());
    }

    @Test
    public void testWithScoreOptional() throws Exception {
        ReviewDraft review = ReviewDraft.of(ReviewImplTest.DEFAULT_PRODUCT_ID, ReviewImplTest.DEFAULT_CUSTOMER_ID);
        assertFalse(review.getScore().isPresent());
        ReviewDraft reviewWith = review.withScore(Optional.of(ReviewImplTest.DEFAULT_SCORE));
        assertFalse(review == reviewWith);
        assertTrue(ReviewImplTest.isScoreInValidRange(reviewWith.getScore().get(), ReviewImplTest.DEFAULT_SCORE));
        reviewWith = review.withScore(Optional.empty());
        assertFalse(review==reviewWith);
        assertFalse(reviewWith.getScore().isPresent());
    }

    @Test
    public void testWithAuthorNameOptional() throws Exception {
        ReviewDraft review = ReviewDraft.of(ReviewImplTest.DEFAULT_PRODUCT_ID, ReviewImplTest.DEFAULT_CUSTOMER_ID);
        assertFalse(review.getAuthorName().isPresent());
        ReviewDraft reviewWith = review.withAuthorName(Optional.of(ReviewImplTest.DEFAULT_AUTHOR_NAME));
        assertFalse(review==reviewWith);
        assertEquals(ReviewImplTest.DEFAULT_AUTHOR_NAME, reviewWith.getAuthorName().get());
        reviewWith = review.withAuthorName(Optional.empty());
        assertFalse(review==reviewWith);
        assertFalse(reviewWith.getAuthorName().isPresent());
    }
}