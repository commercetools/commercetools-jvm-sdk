package io.sphere.sdk.channels;

import io.sphere.sdk.channels.commands.ChannelCreateCommand;
import io.sphere.sdk.channels.commands.ChannelDeleteCommand;
import io.sphere.sdk.channels.queries.ChannelQuery;
import io.sphere.sdk.client.BlockingSphereClient;

import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import static io.sphere.sdk.test.SphereTestUtils.randomKey;
import static io.sphere.sdk.test.SphereTestUtils.randomString;
import static io.sphere.sdk.utils.SphereInternalUtils.asSet;

public class ChannelFixtures {
    public static void withPersistentChannel(final BlockingSphereClient client, final ChannelRole channelRole, final Consumer<Channel> f) {
        final String key = getKeyForPersistentChannel(channelRole);
        withPersistent(client, key, channelRole, f);
    }

    private static String getKeyForPersistentChannel(final ChannelRole channelRole) {
        return ChannelFixtures.class.getSimpleName() + "-" + channelRole;
    }

    public static void withChannelOfRole(final BlockingSphereClient client, final ChannelRole channelRole, final Consumer<Channel> f) {
        final Channel channel = client.executeBlocking(ChannelCreateCommand.of(ChannelDraftDsl.of(randomString()).withRoles(channelRole)));
        f.accept(channel);
        client.executeBlocking(ChannelDeleteCommand.of(channel));
    }

    public static void withUpdatableChannelOfRole(final BlockingSphereClient client, final ChannelRole channelRole, final Function<Channel, Channel> f) {
        withUpdatableChannelOfRole(client, asSet(channelRole), f);
    }

    public static void withUpdatableChannelOfRole(final BlockingSphereClient client, final Set<ChannelRole> roles, final Function<Channel, Channel> f) {
        final Channel channel = client.executeBlocking(ChannelCreateCommand.of(ChannelDraftDsl.of(randomString()).withRoles(roles)));
        final Channel updateChannel = f.apply(channel);
        client.executeBlocking(ChannelDeleteCommand.of(updateChannel));
    }

    public static void withOrderExportChannel(final BlockingSphereClient client, final Consumer<Channel> f) {
        withPersistent(client, "jvm sdk export channel", ChannelRole.ORDER_IMPORT, f);
    }

    private static void withChannel(final BlockingSphereClient client, final UnaryOperator<ChannelDraftBuilder> builderUnaryOperator, final Consumer<Channel> f) {
        final ChannelDraftBuilder channelDraftBuilder = builderUnaryOperator.apply(ChannelDraftBuilder.of(randomKey()));
        final ChannelDraft channelDraft = channelDraftBuilder.build();
        withChannel(client, channelDraft, f);
    }

    public static void withChannel(final BlockingSphereClient client, final ChannelDraft channelDraft, final Consumer<Channel> f) {
        final Channel channel = client.executeBlocking(ChannelCreateCommand.of(channelDraft));
        f.accept(channel);
        client.executeBlocking(ChannelDeleteCommand.of(channel));
    }

    private static void withPersistent(final BlockingSphereClient client, final String key, final ChannelRole roles, final Consumer<Channel> f) {
        final Channel channel = getOrCreateChannel(client, key, roles);
        f.accept(channel);
    }

    public static Channel persistentChannelOfRole(final BlockingSphereClient client, final ChannelRole roles) {
        return getOrCreateChannel(client, getKeyForPersistentChannel(roles), roles);
    }

    private static Channel getOrCreateChannel(final BlockingSphereClient client, final String key, final ChannelRole roles) {
        return client.executeBlocking(ChannelQuery.of().byKey(key)).head().orElseGet(() -> {
            final ChannelCreateCommand channelCreateCommand =
                    ChannelCreateCommand.of(ChannelDraftDsl.of(key).withRoles(roles));
            return client.executeBlocking(channelCreateCommand);
        });
    }

    public static void cleanUpChannelByKey(final BlockingSphereClient client, final String channelKey) {
        client.executeBlocking(ChannelQuery.of().byKey(channelKey), 45, TimeUnit.SECONDS).head()
                .ifPresent(channel -> client.executeBlocking(ChannelDeleteCommand.of(channel), 45, TimeUnit.SECONDS));
    }
}
