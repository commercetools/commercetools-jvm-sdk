package io.sphere.sdk.channels;

import io.sphere.sdk.channels.commands.ChannelCreateCommand;
import io.sphere.sdk.channels.commands.ChannelDeleteCommand;
import io.sphere.sdk.channels.queries.ChannelByKeyFetch;
import io.sphere.sdk.client.TestClient;

import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

import static io.sphere.sdk.test.SphereTestUtils.*;
import static io.sphere.sdk.utils.SetUtils.asSet;

public class ChannelFixtures {
    public static void withPersistentChannel(final TestClient client, final ChannelRoles channelRole, final Consumer<Channel> f) {
        final String key = getKeyForPersistentChannel(channelRole);
        withPersistent(client, key, channelRole, f);
    }

    private static String getKeyForPersistentChannel(final ChannelRoles channelRole) {
        return ChannelFixtures.class.getSimpleName() + "-" + channelRole;
    }

    public static void withChannelOfRole(final TestClient client, final ChannelRoles channelRole, final Consumer<Channel> f) {
        final Channel channel = client.execute(ChannelCreateCommand.of(ChannelDraft.of(randomString()).withRoles(channelRole)));
        f.accept(channel);
        client.execute(ChannelDeleteCommand.of(channel));
    }

    public static void withUpdatableChannelOfRole(final TestClient client, final ChannelRoles channelRole, final Function<Channel, Channel> f) {
        withUpdatableChannelOfRole(client, asSet(channelRole), f);
    }

    public static void withUpdatableChannelOfRole(final TestClient client, final Set<ChannelRoles> roles, final Function<Channel, Channel> f) {
        final Channel channel = client.execute(ChannelCreateCommand.of(ChannelDraft.of(randomString()).withRoles(roles)));
        final Channel updateChannel = f.apply(channel);
        client.execute(ChannelDeleteCommand.of(updateChannel));
    }

    public static void withOrderExportChannel(final TestClient client, final Consumer<Channel> f) {
        withPersistent(client, "jvm sdk export channel", ChannelRoles.ORDER_IMPORT, f);
    }

    private static void withPersistent(final TestClient client, final String key, final ChannelRoles roles, final Consumer<Channel> f) {
        final Channel channel = getOrCreateChannel(client, key, roles);
        f.accept(channel);
    }

    public static Channel persistentChannelOfRole(final TestClient client, final ChannelRoles roles) {
        return getOrCreateChannel(client, getKeyForPersistentChannel(roles), roles);
    }

    private static Channel getOrCreateChannel(final TestClient client, final String key, final ChannelRoles roles) {
        final ChannelByKeyFetch channelByKeyFetch = ChannelByKeyFetch.of(key);
        return client.execute(channelByKeyFetch).orElseGet(() -> {
            final ChannelCreateCommand channelCreateCommand =
                    ChannelCreateCommand.of(ChannelDraft.of(key).withRoles(roles));
            return client.execute(channelCreateCommand);
        });
    }

    public static void cleanUpChannelByKey(final TestClient client, final String channelKey) {
        client.execute(ChannelByKeyFetch.of(channelKey)).ifPresent(channel -> client.execute(ChannelDeleteCommand.of(channel)));
    }
}
