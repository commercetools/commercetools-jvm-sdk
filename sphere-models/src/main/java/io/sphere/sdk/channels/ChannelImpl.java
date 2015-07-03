package io.sphere.sdk.channels;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.DefaultModelImpl;
import io.sphere.sdk.models.LocalizedStrings;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.Set;

class ChannelImpl extends DefaultModelImpl<Channel> implements Channel {
    private final String key;
    private final Set<ChannelRole> roles;
    private final Optional<LocalizedStrings> name;
    private final Optional<LocalizedStrings> description;

    @JsonCreator
    ChannelImpl(final String id, final long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final String key, final Set<ChannelRole> roles, final Optional<LocalizedStrings> name, final Optional<LocalizedStrings> description) {
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

    public Optional<LocalizedStrings> getName() {
        return name;
    }

    public Optional<LocalizedStrings> getDescription() {
        return description;
    }
}
