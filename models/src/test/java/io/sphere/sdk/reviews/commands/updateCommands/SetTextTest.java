package io.sphere.sdk.reviews.commands.updateCommands;

import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Created by njacinto on 21/04/15.
 */
public class SetTextTest {

    @Test
    public void testOf() throws Exception {
        SetText set = SetText.of(Optional.of("test string"));
        assertEquals("setText", set.getAction());
        assertEquals("test string", set.getText().get());
    }
}