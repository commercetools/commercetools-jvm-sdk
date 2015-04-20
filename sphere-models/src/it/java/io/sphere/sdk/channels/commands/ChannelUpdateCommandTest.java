package io.sphere.sdk.channels.commands;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.channels.ChannelFixtures;
import io.sphere.sdk.channels.commands.updateactions.ChangeDescription;
import io.sphere.sdk.channels.commands.updateactions.ChangeKey;
import io.sphere.sdk.channels.commands.updateactions.ChangeName;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.channels.ChannelRoles.INVENTORY_SUPPLY;
import static io.sphere.sdk.test.SphereTestUtils.randomKey;
import static io.sphere.sdk.test.SphereTestUtils.randomSlug;
import static org.fest.assertions.Assertions.assertThat;
import static io.sphere.sdk.test.OptionalAssert.assertThat;

public class ChannelUpdateCommandTest extends IntegrationTest {
    @Test
    public void changeKey() throws Exception {
        ChannelFixtures.withUpdatableChannelOfRole(client(), INVENTORY_SUPPLY, channel -> {
            final String newKey = randomKey();
            final Channel updatedChannel = execute(ChannelUpdateCommand.of(channel, ChangeKey.of(newKey)));
            assertThat(updatedChannel.getKey()).isEqualTo(newKey).isNotEqualTo(channel.getKey());
            return updatedChannel;
        });
    }

    @Test
    public void changeName() throws Exception {
        ChannelFixtures.withUpdatableChannelOfRole(client(), INVENTORY_SUPPLY, channel -> {
            final LocalizedStrings newName = randomSlug();
            final Channel updatedChannel = execute(ChannelUpdateCommand.of(channel, ChangeName.of(newName)));
            assertThat(updatedChannel.getName()).isPresentAs(newName);
            return updatedChannel;
        });
    }

    @Test
    public void changeDescription() throws Exception {
        ChannelFixtures.withUpdatableChannelOfRole(client(), INVENTORY_SUPPLY, channel -> {
            final LocalizedStrings newDescription = randomSlug();
            final Channel updatedChannel = execute(ChannelUpdateCommand.of(channel, ChangeDescription.of(newDescription)));
            assertThat(updatedChannel.getDescription()).isPresentAs(newDescription);
            return updatedChannel;
        });
    }
}