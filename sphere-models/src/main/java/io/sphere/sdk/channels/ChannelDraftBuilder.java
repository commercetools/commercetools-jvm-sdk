package io.sphere.sdk.channels;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Builder;
import io.sphere.sdk.models.LocalizedStrings;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Set;

public class ChannelDraftBuilder extends Base implements Builder<ChannelDraft> {
    private final String key;
    private Set<ChannelRole> roles = Collections.emptySet();
    @Nullable
    private LocalizedStrings name;
    @Nullable
    private LocalizedStrings description;

    private ChannelDraftBuilder(final String key) {
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

    public ChannelDraftBuilder description(@Nullable final LocalizedStrings description) {
        this.description = description;
        return this;
    }
    
    public ChannelDraftBuilder name(@Nullable final LocalizedStrings name) {
        this.name = name;
        return this;
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
