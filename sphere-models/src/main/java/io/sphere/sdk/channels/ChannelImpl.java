package io.sphere.sdk.channels;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.DefaultModelImpl;
import io.sphere.sdk.models.LocalizedStrings;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.Set;

class ChannelImpl extends DefaultModelImpl<Channel> implements Channel {
    private final String key;
    private final Set<ChannelRole> roles;
    @Nullable
    private final LocalizedStrings name;
    @Nullable
    private final LocalizedStrings description;

    @JsonCreator
    ChannelImpl(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final String key, final Set<ChannelRole> roles, final LocalizedStrings name, final LocalizedStrings description) {
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
    public LocalizedStrings getName() {
        return name;
    }

    @Nullable
    public LocalizedStrings getDescription() {
        return description;
    }
}
