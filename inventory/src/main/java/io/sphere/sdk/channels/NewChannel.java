package io.sphere.sdk.channels;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.LocalizedStrings;

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
    private final Optional<LocalizedStrings> name;
    private final Optional<LocalizedStrings> description;

    NewChannel(final String key, final Set<ChannelRoles> roles, final Optional<LocalizedStrings> name, final Optional<LocalizedStrings> description) {
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

    public Optional<LocalizedStrings> getName() {
        return name;
    }

    public Optional<LocalizedStrings> getDescription() {
        return description;
    }
    
    public NewChannel withRoles(final Set<ChannelRoles> roles) {
        return NewChannelBuilder.of(this).roles(roles).build();
    }   
    
    public NewChannel withName(final Optional<LocalizedStrings> name) {
        return NewChannelBuilder.of(this).name(name).build();
    }  
    
    public NewChannel withName(final LocalizedStrings name) {
        return withName(Optional.of(name));
    }    
    
    public NewChannel withDescription(final Optional<LocalizedStrings> description) {
        return NewChannelBuilder.of(this).description(description).build();
    }  
    
    public NewChannel withDescription(final LocalizedStrings description) {
        return withDescription(Optional.of(description));
    }
}
