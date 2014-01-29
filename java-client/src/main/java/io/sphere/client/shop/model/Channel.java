package io.sphere.client.shop.model;

import io.sphere.client.model.EmptyReference;
import io.sphere.client.model.Reference;
import io.sphere.client.model.VersionedId;
import org.codehaus.jackson.annotate.JsonProperty;

import javax.annotation.Nonnull;
import java.util.Set;

public class Channel {
    @Nonnull private String id;
    @JsonProperty("version") private int version;
    private String key;
    private Set<ChannelRoles> roles;

    protected Channel() {}

    public String getKey() {
        return key;
    }

    @Nonnull
    public String getId() {
        return id;
    }

    public Set<ChannelRoles> getRoles() {
        return roles;
    }

    @Nonnull public VersionedId getIdAndVersion() { return VersionedId.create(id, version); }

    public Reference<Channel> getReference() {
        return reference(getId());
    }

    public static Reference<Channel> reference(final String channelId) {
        return channelId != null ? Reference.<Channel>create("channel", channelId) : emptyReference();
    }

    public static Reference<Channel> emptyReference() {
        return EmptyReference.<Channel>create("channel");
    }

    @Override
    public String toString() {
        return "Channel{" +
                "id='" + id + '\'' +
                ", version=" + version +
                ", key='" + key + '\'' +
                ", roles=" + roles +
                '}';
    }
}
