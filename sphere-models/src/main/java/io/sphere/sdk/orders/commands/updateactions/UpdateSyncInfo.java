package io.sphere.sdk.orders.commands.updateactions;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.orders.Order;

import java.time.Instant;
import java.util.Optional;

/**

 {@include.example io.sphere.sdk.orders.commands.OrderUpdateCommandTest#updateSyncInfo()}
 */
public class UpdateSyncInfo extends UpdateAction<Order> {
    private final Reference<Channel> channel;
    private final Optional<String> externalId;
    private final Optional<Instant> syncedAt;


    private UpdateSyncInfo(final Reference<Channel> channel, final Optional<String> externalId, final Optional<Instant> syncedAt) {
        super("updateSyncInfo");
        this.channel = channel;
        this.externalId = externalId;
        this.syncedAt = syncedAt;
    }

    public static UpdateSyncInfo of(final Reference<Channel> channel, final Optional<String> externalId, final Optional<Instant> syncedAt) {
        return new UpdateSyncInfo(channel, externalId, syncedAt);
    }

    public static UpdateSyncInfo of(final Referenceable<Channel> channel) {
        return of(channel.toReference(), Optional.empty(), Optional.empty());
    }

    public UpdateSyncInfo withExternalId(final String externalId) {
        return of(channel, Optional.of(externalId), syncedAt);
    }

    public UpdateSyncInfo withSyncedAt(final Instant syncedAt) {
        return of(channel, externalId, Optional.of(syncedAt));
    }

    public Reference<Channel> getChannel() {
        return channel;
    }

    public Optional<String> getExternalId() {
        return externalId;
    }

    public Optional<Instant> getSyncedAt() {
        return syncedAt;
    }
}
