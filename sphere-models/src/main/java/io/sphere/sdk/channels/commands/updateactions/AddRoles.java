package io.sphere.sdk.channels.commands.updateactions;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.channels.ChannelRole;
import io.sphere.sdk.commands.UpdateAction;

import java.util.Set;

/**
 * {@include.example io.sphere.sdk.channels.commands.ChannelUpdateCommandTest#addRoles()}
 */
public class AddRoles extends UpdateAction<Channel> {
    private final Set<ChannelRole> roles;

    private AddRoles(final Set<ChannelRole> roles) {
        super("addRoles");
        this.roles = roles;
    }

    public Set<ChannelRole> getRoles() {
        return roles;
    }

    public static AddRoles of(final Set<ChannelRole> roles) {
        return new AddRoles(roles);
    }
}
