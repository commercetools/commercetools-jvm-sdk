package io.sphere.sdk.reviews.commands.updateCommands;

import io.sphere.sdk.reviews.ReviewDraftTest;
import io.sphere.sdk.reviews.ReviewImplTest;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Created by njacinto on 21/04/15.
 */
public class SetScoreTest {

    @Test
    public void testOf() throws Exception {
        SetScore setScore = SetScore.of(Optional.of(0.4f));
        assertEquals("setScore", setScore.getAction());
        assertTrue(ReviewImplTest.isScoreInValidRange(setScore.getScore().get(), 0.4f));
    }
}