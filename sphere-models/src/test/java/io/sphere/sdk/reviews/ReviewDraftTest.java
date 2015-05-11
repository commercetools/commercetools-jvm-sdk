package io.sphere.sdk.reviews;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class ReviewDraftTest {
    @Test
    public void checkScoreRange() throws Exception {
        assertThatThrownBy(() -> ReviewDraft.of("productId", "customerId").withScore(5.0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("score").hasMessageContaining("[0..1]");
    }
}