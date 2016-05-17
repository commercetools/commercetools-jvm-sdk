package io.sphere.sdk.orders;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

public interface SyncInfo {
    static SyncInfo of(final Referenceable<Channel> channel, final ZonedDateTime syncedAt, @Nullable final String externalId) {
        return new SyncInfoImpl(channel.toReference(), syncedAt, externalId);

    }

    Reference<Channel> getChannel();

    @Nullable
    String getExternalId();

    ZonedDateTime getSyncedAt();
}
