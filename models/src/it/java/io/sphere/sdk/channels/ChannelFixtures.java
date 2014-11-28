package io.sphere.sdk.channels;

import io.sphere.sdk.channels.commands.ChannelCreateCommand;
import io.sphere.sdk.channels.commands.ChannelDeleteByIdCommand;
import io.sphere.sdk.channels.queries.ChannelFetchByKey;
import io.sphere.sdk.client.TestClient;

import java.util.function.Consumer;

import static io.sphere.sdk.test.SphereTestUtils.*;

public class ChannelFixtures {
    public static void withChannelOfRole(final TestClient client, final ChannelRoles channelRole, final Consumer<Channel> f) {
        final Channel channel = client.execute(new ChannelCreateCommand(ChannelDraft.of(randomString()).withRoles(channelRole)));
        f.accept(channel);
        client.execute(new ChannelDeleteByIdCommand(channel));
    }

    public static void withOrderExportChannel(final TestClient client, final Consumer<Channel> f) {
        final String key = "jvm sdk export channel";
        final ChannelFetchByKey channelFetchByKey = new ChannelFetchByKey(key);
        final Channel channel =
                client.execute(channelFetchByKey).orElseGet(() -> {
                    final ChannelCreateCommand channelCreateCommand =
                            new ChannelCreateCommand(ChannelDraft.of(key).withRoles(ChannelRoles.OrderImport));
                    return client.execute(channelCreateCommand);
                });
        f.accept(channel);
    }
}
