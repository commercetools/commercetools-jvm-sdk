package io.sphere.sdk.channels;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.types.CustomFieldsDraft;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Set;

import static io.sphere.sdk.utils.SphereInternalUtils.asSet;

public final class ChannelDraftDsl extends Base implements ChannelDraft {
    private final String key;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Nullable
    private final Set<ChannelRole> roles;
    @Nullable
    private final LocalizedString name;
    @Nullable
    private final LocalizedString description;

    @Nullable
    private final CustomFieldsDraft custom;

    @JsonCreator
    ChannelDraftDsl(final String key, @Nullable final Set<ChannelRole> roles, @Nullable final LocalizedString name,
                    @Nullable final LocalizedString description, @Nullable final CustomFieldsDraft custom) {
        this.key = key;
        this.roles = roles;
        this.name = name;
        this.description = description;
        this.custom = custom;
    }

    public static ChannelDraftDsl of(final String key) {
        return new ChannelDraftDsl(key, Collections.emptySet(), null, null, null);
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    @Nullable
    public Set<ChannelRole> getRoles() {
        return roles;
    }

    @Override
    @Nullable
    public LocalizedString getName() {
        return name;
    }

    @Override
    @Nullable
    public LocalizedString getDescription() {
        return description;
    }

    @Nullable
    public CustomFieldsDraft getCustom() {
        return custom;
    }

    public ChannelDraftDsl withRoles(@Nullable final Set<ChannelRole> roles) {
        return newBuilder().roles(roles).build();
    }

    public ChannelDraftDsl withRoles(final ChannelRole ... roles) {
        return newBuilder().roles(asSet(roles)).build();
    }
    
    public ChannelDraftDsl withName(@Nullable final LocalizedString name) {
        return newBuilder().name(name).build();
    }

    public ChannelDraftDsl withCustom(@Nullable final CustomFieldsDraft custom) {
        return newBuilder().custom(custom).build();
    }

    private ChannelDraftBuilder newBuilder() {
        return ChannelDraftBuilder.of(this);
    }

    public ChannelDraftDsl withDescription(@Nullable final LocalizedString description) {
        return newBuilder().description(description).build();
    }
}
