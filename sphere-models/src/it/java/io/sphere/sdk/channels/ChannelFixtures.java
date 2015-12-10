package io.sphere.sdk.channels;

import io.sphere.sdk.channels.commands.ChannelCreateCommand;
import io.sphere.sdk.channels.commands.ChannelDeleteCommand;
import io.sphere.sdk.channels.queries.ChannelQuery;
import io.sphere.sdk.client.TestClient;

import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

import static io.sphere.sdk.test.SphereTestUtils.randomString;
import static io.sphere.sdk.utils.SetUtils.asSet;

public class ChannelFixtures {
    public static void withPersistentChannel(final TestClient client, final ChannelRole channelRole, final Consumer<Channel> f) {
        final String key = getKeyForPersistentChannel(channelRole);
        withPersistent(client, key, channelRole, f);
    }

    private static String getKeyForPersistentChannel(final ChannelRole channelRole) {
        return ChannelFixtures.class.getSimpleName() + "-" + channelRole;
    }

    public static void withChannelOfRole(final TestClient client, final ChannelRole channelRole, final Consumer<Channel> f) {
        final Channel channel = client.executeBlocking(ChannelCreateCommand.of(ChannelDraft.of(randomString()).withRoles(channelRole)));
        f.accept(channel);
        client.executeBlocking(ChannelDeleteCommand.of(channel));
    }

    public static void withUpdatableChannelOfRole(final TestClient client, final ChannelRole channelRole, final Function<Channel, Channel> f) {
        withUpdatableChannelOfRole(client, asSet(channelRole), f);
    }

    public static void withUpdatableChannelOfRole(final TestClient client, final Set<ChannelRole> roles, final Function<Channel, Channel> f) {
        final Channel channel = client.executeBlocking(ChannelCreateCommand.of(ChannelDraft.of(randomString()).withRoles(roles)));
        final Channel updateChannel = f.apply(channel);
        client.executeBlocking(ChannelDeleteCommand.of(updateChannel));
    }

    public static void withOrderExportChannel(final TestClient client, final Consumer<Channel> f) {
        withPersistent(client, "jvm sdk export channel", ChannelRole.ORDER_IMPORT, f);
    }

    private static void withPersistent(final TestClient client, final String key, final ChannelRole roles, final Consumer<Channel> f) {
        final Channel channel = getOrCreateChannel(client, key, roles);
        f.accept(channel);
    }

    public static Channel persistentChannelOfRole(final TestClient client, final ChannelRole roles) {
        return getOrCreateChannel(client, getKeyForPersistentChannel(roles), roles);
    }

    private static Channel getOrCreateChannel(final TestClient client, final String key, final ChannelRole roles) {
        return client.executeBlocking(ChannelQuery.of().byKey(key)).head().orElseGet(() -> {
            final ChannelCreateCommand channelCreateCommand =
                    ChannelCreateCommand.of(ChannelDraft.of(key).withRoles(roles));
            return client.executeBlocking(channelCreateCommand);
        });
    }

    public static void cleanUpChannelByKey(final TestClient client, final String channelKey) {
        client.executeBlocking(ChannelQuery.of().byKey(channelKey)).head()
                .ifPresent(channel -> client.executeBlocking(ChannelDeleteCommand.of(channel)));
    }
}
