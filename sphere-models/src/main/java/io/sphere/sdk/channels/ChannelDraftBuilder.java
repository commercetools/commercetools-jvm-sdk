package io.sphere.sdk.channels;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Builder;
import io.sphere.sdk.models.LocalizedStrings;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

public class ChannelDraftBuilder extends Base implements Builder<ChannelDraft> {
    private final String key;
    private Set<ChannelRole> roles = Collections.emptySet();
    private Optional<LocalizedStrings> name = Optional.empty();
    private Optional<LocalizedStrings> description = Optional.empty();

    public ChannelDraftBuilder(final String key) {
        this.key = key;
    }

    public static ChannelDraftBuilder of(final String key) {
        return new ChannelDraftBuilder(key);
    }

    public static ChannelDraftBuilder of(final ChannelDraft template) {
        return new ChannelDraftBuilder(template.getKey())
                .roles(template.getRoles())
                .name(template.getName())
                .description(template.getDescription());
    }

    public ChannelDraftBuilder description(final Optional<LocalizedStrings> description) {
        this.description = description;
        return this;
    }

    public ChannelDraftBuilder description(final LocalizedStrings description) {
        return description(Optional.ofNullable(description));
    }
    
    public ChannelDraftBuilder name(final Optional<LocalizedStrings> name) {
        this.name = name;
        return this;
    }

    public ChannelDraftBuilder name(final LocalizedStrings name) {
        return name(Optional.ofNullable(name));
    }

    public ChannelDraftBuilder roles(final Set<ChannelRole> roles) {
        this.roles = roles;
        return this;
    }

    @Override
    public ChannelDraft build() {
        return new ChannelDraft(key, roles, name, description);
    }
}
