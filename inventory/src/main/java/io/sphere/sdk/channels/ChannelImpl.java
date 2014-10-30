package io.sphere.sdk.channels;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.DefaultModelImpl;
import io.sphere.sdk.models.LocalizedStrings;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;

class ChannelImpl extends DefaultModelImpl<Channel> implements Channel {
    private final String key;
    private final Set<ChannelRoles> roles;
    private final Optional<LocalizedStrings> name;
    private final Optional<LocalizedStrings> description;

    @JsonCreator
    ChannelImpl(final String id, final long version, final Instant createdAt, final Instant lastModifiedAt, final String key, final Set<ChannelRoles> roles, final Optional<LocalizedStrings> name, final Optional<LocalizedStrings> description) {
        super(id, version, createdAt, lastModifiedAt);
        this.key = key;
        this.roles = roles;
        this.name = name;
        this.description = description;
    }

    public String getKey() {
        return key;
    }

    public Set<ChannelRoles> getRoles() {
        return roles;
    }

    public Optional<LocalizedStrings> getName() {
        return name;
    }

    public Optional<LocalizedStrings> getDescription() {
        return description;
    }
}
