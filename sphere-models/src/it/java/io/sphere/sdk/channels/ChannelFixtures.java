package io.sphere.sdk.channels;

import io.sphere.sdk.channels.commands.ChannelCreateCommand;
import io.sphere.sdk.channels.commands.ChannelDeleteCommand;
import io.sphere.sdk.channels.queries.ChannelByKeyFetch;
import io.sphere.sdk.client.TestClient;

import java.util.function.Consumer;

import static io.sphere.sdk.test.SphereTestUtils.*;

public class ChannelFixtures {
    public static void withPersistentChannel(final TestClient client, final ChannelRoles channelRole, final Consumer<Channel> f) {
        final String key = ChannelFixtures.class.getSimpleName() + "-" + channelRole;
        withPersistent(client, key, channelRole, f);
    }

    public static void withChannelOfRole(final TestClient client, final ChannelRoles channelRole, final Consumer<Channel> f) {
        final Channel channel = client.execute(ChannelCreateCommand.of(ChannelDraft.of(randomString()).withRoles(channelRole)));
        f.accept(channel);
        client.execute(ChannelDeleteCommand.of(channel));
    }

    public static void withOrderExportChannel(final TestClient client, final Consumer<Channel> f) {
        withPersistent(client, "jvm sdk export channel", ChannelRoles.ORDER_IMPORT, f);
    }

    private static void withPersistent(final TestClient client, final String key, final ChannelRoles roles, final Consumer<Channel> f) {
        final ChannelByKeyFetch channelByKeyFetch = ChannelByKeyFetch.of(key);
        final Channel channel =
                client.execute(channelByKeyFetch).orElseGet(() -> {
                    final ChannelCreateCommand channelCreateCommand =
                            ChannelCreateCommand.of(ChannelDraft.of(key).withRoles(roles));
                    return client.execute(channelCreateCommand);
                });
        f.accept(channel);
    }

    public static void cleanUpChannelByKey(final TestClient client, final String channelKey) {
        client.execute(ChannelByKeyFetch.of(channelKey)).ifPresent(channel -> client.execute(ChannelDeleteCommand.of(channel)));
    }
}
