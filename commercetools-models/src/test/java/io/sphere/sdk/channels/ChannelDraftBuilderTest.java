package io.sphere.sdk.channels;

import io.sphere.sdk.channels.*;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class ChannelDraftBuilderTest {
    @Test
    public void key() {
        assertThat(ChannelDraftBuilder.of("key").build().getKey()).isEqualTo("key");
    }
}