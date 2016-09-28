package io.sphere.sdk.channels;

import io.sphere.sdk.channels.commands.ChannelCreateCommand;
import io.sphere.sdk.channels.commands.ChannelDeleteCommand;
import io.sphere.sdk.channels.commands.ChannelUpdateCommand;
import io.sphere.sdk.channels.commands.updateactions.SetCustomField;
import io.sphere.sdk.channels.commands.updateactions.SetCustomType;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.types.CustomFieldsDraft;
import io.sphere.sdk.types.CustomFieldsDraftBuilder;
import org.junit.Test;

import java.util.HashMap;

import static io.sphere.sdk.channels.ChannelFixtures.withUpdatableChannelOfRole;
import static io.sphere.sdk.test.SphereTestUtils.randomKey;
import static io.sphere.sdk.types.TypeFixtures.STRING_FIELD_NAME;
import static io.sphere.sdk.types.TypeFixtures.withUpdateableType;
import static org.assertj.core.api.Assertions.assertThat;

public class ChannelCustomFieldsIntegrationTest extends IntegrationTest {
    @Test
    public void createChannelWithCustomType() {
        withUpdateableType(client(), type -> {
            final CustomFieldsDraft customFieldsDraft = CustomFieldsDraftBuilder.ofType(type)
                    .addObject(STRING_FIELD_NAME, "a value")
                    .build();


            final ChannelDraft channelDraft = ChannelDraft.of(randomKey()).withCustom(customFieldsDraft);
            final Channel channel = client().executeBlocking(ChannelCreateCommand.of(channelDraft));
            assertThat(channel.getCustom().getFieldAsString(STRING_FIELD_NAME))
                    .isEqualTo("a value");

            final ChannelUpdateCommand channelUpdateCommand = 
                    ChannelUpdateCommand.of(channel, SetCustomField.ofObject(STRING_FIELD_NAME, "a new value"));
            final Channel updatedChannel = client().executeBlocking(channelUpdateCommand);
            assertThat(updatedChannel.getCustom()
                    .getFieldAsString(STRING_FIELD_NAME)).isEqualTo("a new value");

            //clean up
            client().executeBlocking(ChannelDeleteCommand.of(updatedChannel));
            return type;
        });
    }

    @Test
    public void setCustomType() {
        withUpdateableType(client(), type -> {
            withUpdatableChannelOfRole(client(), ChannelRole.INVENTORY_SUPPLY, channel -> {
                final HashMap<String, Object> fields = new HashMap<>();
                fields.put(STRING_FIELD_NAME, "hello");
                final ChannelUpdateCommand updateCommand =
                        ChannelUpdateCommand.of(channel, SetCustomType.ofTypeIdAndObjects(type.getId(), fields));
                final Channel updatedChannel = client().executeBlocking(updateCommand);
                assertThat(updatedChannel.getCustom().getType()).isEqualTo(type.toReference());
                assertThat(updatedChannel.getCustom().getFieldAsString(STRING_FIELD_NAME)).isEqualTo("hello");

                final Channel updated2 =
                        client().executeBlocking(ChannelUpdateCommand.of(updatedChannel, SetCustomType.ofRemoveType()));
                assertThat(updated2.getCustom()).isNull();
                return updated2;
            });
            return type;
        });
    }
}
