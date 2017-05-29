package io.sphere.sdk.reviews.commands.updateCommands;

import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Created by njacinto on 21/04/15.
 */
public class SetTitleTest {

    @Test
    public void testOf() throws Exception {
        SetTitle set = SetTitle.of(Optional.of("test string"));
        assertEquals("setTitle", set.getAction());
        assertEquals("test string", set.getTitle().get());
    }
}