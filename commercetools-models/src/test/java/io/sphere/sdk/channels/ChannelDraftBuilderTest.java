package io.sphere.sdk.channels;

import io.sphere.sdk.channels.*;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class ChannelDraftBuilderTest {
    @Test
    public void name() {
        ChannelDraftBuilder.of("key").build();
        final Class<GeneratedChannelDraftBuilder> generatedChannelDraftBuilderClass = GeneratedChannelDraftBuilder.class;
        final Class<GeneratedChannelDraftDsl> generatedChannelDraftDslClass = GeneratedChannelDraftDsl.class;
    }
}