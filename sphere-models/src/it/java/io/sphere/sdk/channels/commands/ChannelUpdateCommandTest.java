package io.sphere.sdk.channels.commands;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.channels.ChannelRole;
import io.sphere.sdk.channels.commands.updateactions.*;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.Set;

import static io.sphere.sdk.channels.ChannelFixtures.withUpdatableChannelOfRole;
import static io.sphere.sdk.channels.ChannelRole.*;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static io.sphere.sdk.utils.SetUtils.asSet;
import static org.assertj.core.api.Assertions.assertThat;

public class ChannelUpdateCommandTest extends IntegrationTest {
    @Test
    public void changeKey() throws Exception {
        withUpdatableChannelOfRole(client(), INVENTORY_SUPPLY, channel -> {
            final String newKey = randomKey();
            final Channel updatedChannel = client().executeBlocking(ChannelUpdateCommand.of(channel, ChangeKey.of(newKey)));
            assertThat(updatedChannel.getKey()).isEqualTo(newKey).isNotEqualTo(channel.getKey());
            return updatedChannel;
        });
    }

    @Test
    public void changeName() throws Exception {
        withUpdatableChannelOfRole(client(), INVENTORY_SUPPLY, channel -> {
            final LocalizedString newName = randomSlug();
            final Channel updatedChannel = client().executeBlocking(ChannelUpdateCommand.of(channel, ChangeName.of(newName)));
            assertThat(updatedChannel.getName()).isEqualTo(newName);
            return updatedChannel;
        });
    }

    @Test
    public void changeDescription() throws Exception {
        withUpdatableChannelOfRole(client(), INVENTORY_SUPPLY, channel -> {
            final LocalizedString newDescription = randomSlug();
            final Channel updatedChannel = client().executeBlocking(ChannelUpdateCommand.of(channel, ChangeDescription.of(newDescription)));
            assertThat(updatedChannel.getDescription()).isEqualTo(newDescription);
            return updatedChannel;
        });
    }

    @Test
    public void setRoles() throws Exception {
        withUpdatableChannelOfRole(client(), ORDER_IMPORT, channel -> {
            final Set<ChannelRole> roles = asSet(ORDER_EXPORT, INVENTORY_SUPPLY);
            final Channel updatedChannel = client().executeBlocking(ChannelUpdateCommand.of(channel, SetRoles.of(roles)));
            assertThat(updatedChannel.getRoles()).containsOnly(ORDER_EXPORT, INVENTORY_SUPPLY);
            return updatedChannel;
        });
    }

    @Test
    public void addRoles() throws Exception {
        withUpdatableChannelOfRole(client(), ORDER_IMPORT, channel -> {
            final Set<ChannelRole> roles = asSet(ORDER_EXPORT, INVENTORY_SUPPLY);
            final Channel updatedChannel = client().executeBlocking(ChannelUpdateCommand.of(channel, AddRoles.of(roles)));
            assertThat(updatedChannel.getRoles()).containsOnly(ORDER_EXPORT, INVENTORY_SUPPLY, ORDER_IMPORT);
            return updatedChannel;
        });
    }

    @Test
    public void removeRoles() throws Exception {
        withUpdatableChannelOfRole(client(), asSet(ORDER_IMPORT, PRIMARY), channel -> {
            final Set<ChannelRole> roles = asSet(ORDER_IMPORT);
            final Channel updatedChannel = client().executeBlocking(ChannelUpdateCommand.of(channel, RemoveRoles.of(roles)));
            assertThat(updatedChannel.getRoles()).containsOnly(PRIMARY);
            return updatedChannel;
        });
    }
}