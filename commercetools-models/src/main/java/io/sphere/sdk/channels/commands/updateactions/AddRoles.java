package io.sphere.sdk.channels.commands.updateactions;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.channels.ChannelRole;
import io.sphere.sdk.commands.UpdateActionImpl;

import java.util.Set;

/**
 *  Adds roles to a channel.
 *
 *  {@doc.gen intro}
 *
 *  {@include.example io.sphere.sdk.channels.commands.ChannelUpdateCommandIntegrationTest#addRoles()}
 *
 *  @see Channel#getRoles()
 */
public final class AddRoles extends UpdateActionImpl<Channel> {
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
