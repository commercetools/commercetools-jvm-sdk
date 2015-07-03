package io.sphere.sdk.channels.commands.updateactions;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.channels.ChannelRole;
import io.sphere.sdk.commands.UpdateAction;

import java.util.Set;

/**
 * {@include.example io.sphere.sdk.channels.commands.ChannelUpdateCommandTest#setRoles()}
 */
public class SetRoles extends UpdateAction<Channel> {
    private final Set<ChannelRole> roles;

    private SetRoles(final Set<ChannelRole> roles) {
        super("setRoles");
        this.roles = roles;
    }

    public Set<ChannelRole> getRoles() {
        return roles;
    }

    public static SetRoles of(final Set<ChannelRole> roles) {
        return new SetRoles(roles);
    }
}
