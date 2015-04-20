package io.sphere.sdk.channels.commands.updateactions;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.channels.ChannelRoles;
import io.sphere.sdk.commands.UpdateAction;

import java.util.Set;

/**
 * {@include.example io.sphere.sdk.channels.commands.ChannelUpdateCommandTest#addRoles()}
 */
public class AddRoles extends UpdateAction<Channel> {
    private final Set<ChannelRoles> roles;

    private AddRoles(final Set<ChannelRoles> roles) {
        super("addRoles");
        this.roles = roles;
    }

    public Set<ChannelRoles> getRoles() {
        return roles;
    }

    public static AddRoles of(final Set<ChannelRoles> roles) {
        return new AddRoles(roles);
    }
}
