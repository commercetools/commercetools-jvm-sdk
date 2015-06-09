package io.sphere.sdk.orders;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;

import java.time.ZonedDateTime;
import java.util.Optional;

public class SyncInfo extends Base {
    private final Reference<Channel> channel;
    private final Optional<String> externalId;
    private final ZonedDateTime syncedAt;

    @JsonCreator
    private SyncInfo(final Reference<Channel> channel, final ZonedDateTime syncedAt, final Optional<String> externalId) {
        this.channel = channel;
        this.externalId = externalId;
        this.syncedAt = syncedAt;
    }

    public static SyncInfo of(final Referenceable<Channel> channel, final ZonedDateTime syncedAt, final Optional<String> externalId) {
        return new SyncInfo(channel.toReference(), syncedAt, externalId);

    }

    public Reference<Channel> getChannel() {
        return channel;
    }

    public Optional<String> getExternalId() {
        return externalId;
    }

    public ZonedDateTime getSyncedAt() {
        return syncedAt;
    }
}
