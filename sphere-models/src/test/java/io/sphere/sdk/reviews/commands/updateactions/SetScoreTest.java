package io.sphere.sdk.reviews.commands.updateactions;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class SetScoreTest {
    @Test
    public void checkScoreRange() throws Exception {
        assertThatThrownBy(() -> SetScore.of(5.0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("score").hasMessageContaining("[0..1]");
    }
}