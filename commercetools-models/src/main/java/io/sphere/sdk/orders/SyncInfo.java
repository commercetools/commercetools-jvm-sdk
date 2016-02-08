package io.sphere.sdk.orders;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

public final class SyncInfo extends Base {
    private final Reference<Channel> channel;
    @Nullable
    private final String externalId;
    private final ZonedDateTime syncedAt;

    @JsonCreator
    private SyncInfo(final Reference<Channel> channel, final ZonedDateTime syncedAt, @Nullable final String externalId) {
        this.channel = channel;
        this.externalId = externalId;
        this.syncedAt = syncedAt;
    }

    public static SyncInfo of(final Referenceable<Channel> channel, final ZonedDateTime syncedAt, @Nullable final String externalId) {
        return new SyncInfo(channel.toReference(), syncedAt, externalId);

    }

    public Reference<Channel> getChannel() {
        return channel;
    }

    @Nullable
    public String getExternalId() {
        return externalId;
    }

    public ZonedDateTime getSyncedAt() {
        return syncedAt;
    }
}
