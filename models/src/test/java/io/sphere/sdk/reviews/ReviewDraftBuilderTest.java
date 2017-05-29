package io.sphere.sdk.reviews;

import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Created by njacinto on 21/04/15.
 */
public class ReviewDraftBuilderTest {

    @Test
    public void testOf() throws Exception {
        ReviewDraftBuilder builder = ReviewDraftBuilder.of(ReviewDraftTest.DEFAULT_REVIEW);
        assertEquals(ReviewDraftTest.DEFAULT_REVIEW, builder.build());
    }

    @Test
    public void testOf1() throws Exception {
        ReviewDraftBuilder builder = ReviewDraftBuilder.of(ReviewImplTest.DEFAULT_PRODUCT_ID, ReviewImplTest.DEFAULT_CUSTOMER_ID);
        ReviewDraft draft = builder.build();
        assertEquals(ReviewImplTest.DEFAULT_PRODUCT_ID, draft.getProductId());
        assertEquals(ReviewImplTest.DEFAULT_CUSTOMER_ID, draft.getCustomerId());
    }

    @Test
    public void testAuthorName() throws Exception {
        ReviewDraftBuilder builder = ReviewDraftBuilder.of(ReviewImplTest.DEFAULT_PRODUCT_ID, ReviewImplTest.DEFAULT_CUSTOMER_ID);
        ReviewDraft draft = builder.build();
        assertFalse(draft.getAuthorName().isPresent());
        builder.authorName(ReviewImplTest.DEFAULT_AUTHOR_NAME); // set value
        draft = builder.build();
        assertEquals(ReviewImplTest.DEFAULT_AUTHOR_NAME, draft.getAuthorName().get());
        builder.authorName(Optional.empty()); // set empty optional
        draft = builder.build();
        assertFalse(draft.getAuthorName().isPresent());
        builder.authorName(Optional.of(ReviewImplTest.DEFAULT_AUTHOR_NAME)); // set optional
        draft = builder.build();
        assertEquals(ReviewImplTest.DEFAULT_AUTHOR_NAME, draft.getAuthorName().get());
    }

    @Test
    public void testTitle() throws Exception {
        ReviewDraftBuilder builder = ReviewDraftBuilder.of(ReviewImplTest.DEFAULT_PRODUCT_ID, ReviewImplTest.DEFAULT_CUSTOMER_ID);
        ReviewDraft draft = builder.build();
        assertFalse(draft.getTitle().isPresent());
        builder.title(ReviewImplTest.DEFAULT_TITLE); // set value
        draft = builder.build();
        assertEquals(ReviewImplTest.DEFAULT_TITLE, draft.getTitle().get());
        builder.title(Optional.empty()); // set empty optional
        draft = builder.build();
        assertFalse(draft.getTitle().isPresent());
        builder.title(Optional.of(ReviewImplTest.DEFAULT_TITLE)); // set optional
        draft = builder.build();
        assertEquals(ReviewImplTest.DEFAULT_TITLE, draft.getTitle().get());

    }

    @Test
    public void testText() throws Exception {
        ReviewDraftBuilder builder = ReviewDraftBuilder.of(ReviewImplTest.DEFAULT_PRODUCT_ID, ReviewImplTest.DEFAULT_CUSTOMER_ID);
        ReviewDraft draft = builder.build();
        assertFalse(draft.getText().isPresent());
        builder.text(ReviewImplTest.DEFAULT_TEXT); // set value
        draft = builder.build();
        assertEquals(ReviewImplTest.DEFAULT_TEXT, draft.getText().get());
        builder.text(Optional.empty()); // set empty optional
        draft = builder.build();
        assertFalse(draft.getText().isPresent());
        builder.text(Optional.of(ReviewImplTest.DEFAULT_TEXT)); // set optional
        draft = builder.build();
        assertEquals(ReviewImplTest.DEFAULT_TEXT, draft.getText().get());

    }

    @Test
    public void testScore() throws Exception {
        ReviewDraftBuilder builder = ReviewDraftBuilder.of(ReviewImplTest.DEFAULT_PRODUCT_ID, ReviewImplTest.DEFAULT_CUSTOMER_ID);
        ReviewDraft draft = builder.build();
        assertFalse(draft.getScore().isPresent());
        builder.score(ReviewImplTest.DEFAULT_SCORE); // set value
        draft = builder.build();
        assertTrue(ReviewImplTest.isScoreInValidRange(draft.getScore().get(), ReviewImplTest.DEFAULT_SCORE));
        builder.score(Optional.empty()); // set empty optional
        draft = builder.build();
        assertFalse(draft.getScore().isPresent());
        builder.score(Optional.of(ReviewImplTest.DEFAULT_SCORE)); // set optional
        draft = builder.build();
        assertTrue(ReviewImplTest.isScoreInValidRange(draft.getScore().get(), ReviewImplTest.DEFAULT_SCORE));
    }
}