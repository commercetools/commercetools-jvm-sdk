package io.sphere.sdk.channels;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class ChannelDraftBuilderTest {
    @Test
    public void name() {
        ChannelDraftBuilder.of("key").build();

    }
}