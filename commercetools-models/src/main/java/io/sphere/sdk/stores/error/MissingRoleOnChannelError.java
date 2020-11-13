package io.sphere.sdk.stores.error;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.channels.ChannelRole;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.models.errors.SphereError;

import javax.annotation.Nullable;


public final class MissingRoleOnChannelError  extends SphereError {
    public static final String CODE = "MissingRoleOnChannel";

    @Nullable
    private final ResourceIdentifier<Channel> channel;
    private final ChannelRole missingRole;

    @JsonCreator
    private MissingRoleOnChannelError(final String message, @Nullable ResourceIdentifier<Channel> channel, ChannelRole missingRole) {
        super(CODE, message);
        this.channel = channel;
        this.missingRole = missingRole;
    }

    public static MissingRoleOnChannelError of(final String message, @Nullable ResourceIdentifier<Channel> channel, ChannelRole missingRole) {
        return new MissingRoleOnChannelError(message, channel, missingRole);
    }

    @Nullable
    public ResourceIdentifier<Channel> getChannel() {
        return channel;
    }

    public ChannelRole getMissingRole() {
        return missingRole;
    }
}
