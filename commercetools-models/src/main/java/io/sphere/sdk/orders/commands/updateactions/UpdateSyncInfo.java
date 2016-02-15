package io.sphere.sdk.orders.commands.updateactions;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.orders.Order;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

/**
 Updates the synchronization info.

 {@doc.gen intro}

 {@include.example io.sphere.sdk.orders.commands.OrderUpdateCommandIntegrationTest#updateSyncInfo()}
 */
public final class UpdateSyncInfo extends UpdateActionImpl<Order> {
    private final Reference<Channel> channel;
    @Nullable
    private final String externalId;
    @Nullable
    private final ZonedDateTime syncedAt;


    private UpdateSyncInfo(final Reference<Channel> channel, @Nullable final String externalId, @Nullable final ZonedDateTime syncedAt) {
        super("updateSyncInfo");
        this.channel = channel;
        this.externalId = externalId;
        this.syncedAt = syncedAt;
    }

    public static UpdateSyncInfo of(final Reference<Channel> channel, final String externalId, final ZonedDateTime syncedAt) {
        return new UpdateSyncInfo(channel, externalId, syncedAt);
    }

    public static UpdateSyncInfo of(final Referenceable<Channel> channel) {
        return of(channel.toReference(), null, null);
    }

    public UpdateSyncInfo withExternalId(final String externalId) {
        return of(channel, externalId, syncedAt);
    }

    public UpdateSyncInfo withSyncedAt(final ZonedDateTime syncedAt) {
        return of(channel, externalId, syncedAt);
    }

    public Reference<Channel> getChannel() {
        return channel;
    }

    @Nullable
    public String getExternalId() {
        return externalId;
    }

    @Nullable
    public ZonedDateTime getSyncedAt() {
        return syncedAt;
    }
}
