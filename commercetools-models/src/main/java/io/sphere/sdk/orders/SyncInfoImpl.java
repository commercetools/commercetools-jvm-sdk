package io.sphere.sdk.orders;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Reference;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

final class SyncInfoImpl extends Base implements SyncInfo {
    private final Reference<Channel> channel;
    @Nullable
    private final String externalId;
    private final ZonedDateTime syncedAt;

    @JsonCreator
    SyncInfoImpl(final Reference<Channel> channel, final ZonedDateTime syncedAt, @Nullable final String externalId) {
        this.channel = channel;
        this.externalId = externalId;
        this.syncedAt = syncedAt;
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
