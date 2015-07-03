package io.sphere.sdk.channels;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.LocalizedStrings;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static io.sphere.sdk.utils.SetUtils.asSet;

/**
 * Template to create a new Channel.
 *
 * @see ChannelDraftBuilder
 */
public class ChannelDraft extends Base {
    private final String key;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final Set<ChannelRole> roles;
    private final Optional<LocalizedStrings> name;
    private final Optional<LocalizedStrings> description;

    ChannelDraft(final String key, final Set<ChannelRole> roles, final Optional<LocalizedStrings> name, final Optional<LocalizedStrings> description) {
        this.key = key;
        this.roles = roles;
        this.name = name;
        this.description = description;
    }

    public static ChannelDraft of(final String key) {
        return new ChannelDraft(key, Collections.emptySet(), Optional.empty(), Optional.empty());
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
    
    public ChannelDraft withRoles(final Set<ChannelRole> roles) {
        return ChannelDraftBuilder.of(this).roles(roles).build();
    }

    public ChannelDraft withRoles(final ChannelRole... roles) {
        return ChannelDraftBuilder.of(this).roles(asSet(roles)).build();
    }
    
    public ChannelDraft withName(final Optional<LocalizedStrings> name) {
        return ChannelDraftBuilder.of(this).name(name).build();
    }  
    
    public ChannelDraft withName(final LocalizedStrings name) {
        return withName(Optional.of(name));
    }    
    
    public ChannelDraft withDescription(final Optional<LocalizedStrings> description) {
        return ChannelDraftBuilder.of(this).description(description).build();
    }  
    
    public ChannelDraft withDescription(final LocalizedStrings description) {
        return withDescription(Optional.of(description));
    }
}
