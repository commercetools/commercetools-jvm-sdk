package io.sphere.sdk.channels.commands.updateactions;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.channels.ChannelRoles;
import io.sphere.sdk.commands.UpdateAction;

import java.util.Set;

/**
 * {@include.example io.sphere.sdk.channels.commands.ChannelUpdateCommandTest#setRoles()}
 */
public class SetRoles extends UpdateAction<Channel> {
    private final Set<ChannelRoles> roles;

    private SetRoles(final Set<ChannelRoles> roles) {
        super("setRoles");
        this.roles = roles;
    }

    public Set<ChannelRoles> getRoles() {
        return roles;
    }

    public static SetRoles of(final Set<ChannelRoles> roles) {
        return new SetRoles(roles);
    }
}
