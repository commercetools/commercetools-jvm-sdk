package io.sphere.sdk.orders;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Reference;

import java.time.Instant;
import java.util.Optional;

public class SyncInfo extends Base {
    private final Reference<Channel> channel;
    private final Optional<String> externalId;
    private final Instant syncedAt;

    private SyncInfo(final Reference<Channel> channel, final Instant syncedAt, final Optional<String> externalId) {
        this.channel = channel;
        this.externalId = externalId;
        this.syncedAt = syncedAt;
    }

    public static SyncInfo of(final Reference<Channel> channel, final Instant syncedAt, final Optional<String> externalId) {
        return new SyncInfo(channel, syncedAt, externalId);

    }

    public Reference<Channel> getChannel() {
        return channel;
    }

    public Optional<String> getExternalId() {
        return externalId;
    }

    public Instant getSyncedAt() {
        return syncedAt;
    }
}
