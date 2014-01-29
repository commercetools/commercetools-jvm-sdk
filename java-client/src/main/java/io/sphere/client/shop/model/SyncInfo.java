package io.sphere.client.shop.model;

import io.sphere.client.model.Reference;
import org.joda.time.DateTime;

import javax.annotation.Nullable;

import static com.google.common.base.Strings.nullToEmpty;

public final class SyncInfo {
    private Reference<Channel> channel;
    private String externalId;
    private DateTime syncedAt;

    public SyncInfo(Reference<Channel> channel, String externalId, DateTime syncedAt) {
        this.channel = channel;
        this.externalId = externalId;
        this.syncedAt = syncedAt;
    }

    //for JSON mapper
    protected SyncInfo() {
    }

    public SyncInfo(Reference<Channel> channel, String externalId) {
        this(channel, externalId, DateTime.now());
    }

    public SyncInfo(Reference<Channel> channel, DateTime syncedAt) {
        this(channel, "", syncedAt);
    }

    public SyncInfo(Reference<Channel> channel) {
        this(channel, "");
    }

    public Reference<Channel> getChannel() {
        return channel;
    }

    public String getExternalId() {
        return externalId;
    }

    public DateTime getSyncedAt() {
        return syncedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SyncInfo syncInfo = (SyncInfo) o;

        if (!channel.equals(syncInfo.channel)) return false;
        if (externalId != null ? !externalId.equals(syncInfo.externalId) : syncInfo.externalId != null) return false;
        if (syncedAt.getMillis() != syncInfo.syncedAt.getMillis()) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = channel.hashCode();
        result = 31 * result + (externalId != null ? externalId.hashCode() : 0);
        result = 31 * result + syncedAt.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "SyncInfo{" +
                "channel=" + channel +
                ", externalId='" + externalId + '\'' +
                ", syncedAt=" + syncedAt +
                '}';
    }
}
