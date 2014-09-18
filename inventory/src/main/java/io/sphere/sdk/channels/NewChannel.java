package io.sphere.sdk.channels;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.LocalizedString;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

/**
 * Template to create a new Channel.
 *
 * @see io.sphere.sdk.channels.NewChannelBuilder
 */
public class NewChannel extends Base {
    private final String key;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final Set<ChannelRoles> roles;
    private final Optional<LocalizedString> name;
    private final Optional<LocalizedString> description;

    NewChannel(final String key, final Set<ChannelRoles> roles, final Optional<LocalizedString> name, final Optional<LocalizedString> description) {
        this.key = key;
        this.roles = roles;
        this.name = name;
        this.description = description;
    }

    public static NewChannel of(final String key) {
        return new NewChannel(key, Collections.emptySet(), Optional.empty(), Optional.empty());
    }

    public String getKey() {
        return key;
    }

    public Set<ChannelRoles> getRoles() {
        return roles;
    }

    public Optional<LocalizedString> getName() {
        return name;
    }

    public Optional<LocalizedString> getDescription() {
        return description;
    }
    
    public NewChannel withRoles(final Set<ChannelRoles> roles) {
        return NewChannelBuilder.of(this).roles(roles).build();
    }   
    
    public NewChannel withName(final Optional<LocalizedString> name) {
        return NewChannelBuilder.of(this).name(name).build();
    }  
    
    public NewChannel withName(final LocalizedString name) {
        return withName(Optional.of(name));
    }    
    
    public NewChannel withDescription(final Optional<LocalizedString> description) {
        return NewChannelBuilder.of(this).description(description).build();
    }  
    
    public NewChannel withDescription(final LocalizedString description) {
        return withDescription(Optional.of(description));
    }
}
