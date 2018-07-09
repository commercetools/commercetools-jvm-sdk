package io.sphere.sdk.channels;

import io.sphere.sdk.types.CustomFieldsDraft;
import org.junit.Test;

import static java.util.Collections.emptyMap;
import static org.assertj.core.api.Assertions.assertThat;

public class ChannelDraftBuilderTest {
    @Test
    public void key() {
        assertThat(ChannelDraftBuilder.of("key").build().getKey()).isEqualTo("key");
    }

    @Test
    public void custom_withAnyCustomFieldsDraft_ShouldSetCustomFieldsDraftOnBuilder() {
        final CustomFieldsDraft customFieldsDraft = CustomFieldsDraft.ofTypeIdAndJson("foo", emptyMap());

        final ChannelDraftBuilder channelDraftBuilder = ChannelDraftBuilder.of("key")
                                                                           .custom(customFieldsDraft);

        assertThat(channelDraftBuilder.getCustom()).isEqualTo(customFieldsDraft);
    }

    @Test
    public void custom_withNullCustomFieldsDraft_ShouldSetNullCustomFieldsDraftOnBuilder() {
        final ChannelDraftBuilder channelDraftBuilder = ChannelDraftBuilder.of("key")
                                                                           .custom(null);

        assertThat(channelDraftBuilder.getCustom()).isNull();
    }

    @Test
    public void of_withNoCustomFieldsDraft_ShouldHaveNullCustomFields() {
        final ChannelDraftBuilder channelDraftBuilder = ChannelDraftBuilder.of("key");

        assertThat(channelDraftBuilder.getCustom()).isNull();
    }

    @Test
    public void build_withAnyCustomFieldsDraft_ShouldBuildChannelDraftWithCustomFields() {
        final CustomFieldsDraft customFieldsDraft = CustomFieldsDraft.ofTypeIdAndJson("foo", emptyMap());
        final ChannelDraftBuilder channelDraftBuilder = ChannelDraftBuilder.of("key").custom(customFieldsDraft);

        final ChannelDraft channelDraft = channelDraftBuilder.build();

        assertThat(channelDraft.getCustom()).isEqualTo(customFieldsDraft);
    }
}