package io.sphere.sdk.reviews.commands.updateCommands;

import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Created by njacinto on 21/04/15.
 */
public class SetAuthorNameTest {

    @Test
    public void testOf() throws Exception {
        SetAuthorName set = SetAuthorName.of(Optional.of("test string"));
        assertEquals("setAuthorName", set.getAction());
        assertEquals("test string", set.getAuthorName().get());
    }
}