package io.sphere.sdk.channels;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Builder;
import io.sphere.sdk.models.LocalizedStrings;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

public class NewChannelBuilder extends Base implements Builder<NewChannel> {
    private String key;
    private Set<ChannelRoles> roles = Collections.emptySet();
    private Optional<LocalizedStrings> name = Optional.empty();
    private Optional<LocalizedStrings> description = Optional.empty();

    public NewChannelBuilder(final String key) {
        this.key = key;
    }

    public static NewChannelBuilder of(final String key) {
        return new NewChannelBuilder(key);
    }

    public static NewChannelBuilder of(final NewChannel template) {
        return new NewChannelBuilder(template.getKey())
                .roles(template.getRoles())
                .name(template.getName())
                .description(template.getDescription());
    }

    public NewChannelBuilder description(final Optional<LocalizedStrings> description) {
        this.description = description;
        return this;
    }

    public NewChannelBuilder description(final LocalizedStrings description) {
        return description(Optional.ofNullable(description));
    }
    
    public NewChannelBuilder name(final Optional<LocalizedStrings> name) {
        this.name = name;
        return this;
    }

    public NewChannelBuilder name(final LocalizedStrings name) {
        return name(Optional.ofNullable(name));
    }

    public NewChannelBuilder roles(final Set<ChannelRoles> roles) {
        this.roles = roles;
        return this;
    }

    @Override
    public NewChannel build() {
        return new NewChannel(key, roles, name, description);
    }
}
