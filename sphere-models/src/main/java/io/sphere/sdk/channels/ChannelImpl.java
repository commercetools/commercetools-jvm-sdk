package io.sphere.sdk.channels;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.ResourceImpl;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.Set;

class ChannelImpl extends ResourceImpl<Channel> implements Channel {
    private final String key;
    private final Set<ChannelRole> roles;
    @Nullable
    private final LocalizedString name;
    @Nullable
    private final LocalizedString description;

    @JsonCreator
    ChannelImpl(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final String key, final Set<ChannelRole> roles, final LocalizedString name, final LocalizedString description) {
        super(id, version, createdAt, lastModifiedAt);
        this.key = key;
        this.roles = roles;
        this.name = name;
        this.description = description;
    }

    public String getKey() {
        return key;
    }

    public Set<ChannelRole> getRoles() {
        return roles;
    }

    @Nullable
    public LocalizedString getName() {
        return name;
    }

    @Nullable
    public LocalizedString getDescription() {
        return description;
    }
}
