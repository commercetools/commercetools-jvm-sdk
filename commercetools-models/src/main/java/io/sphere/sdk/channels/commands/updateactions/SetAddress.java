package io.sphere.sdk.channels.commands.updateactions;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.Address;

import javax.annotation.Nullable;

/**
  Sets the address.

 {@doc.gen intro}

 {@include.example io.sphere.sdk.channels.commands.ChannelUpdateCommandIntegrationTest#setAddress()}

 @see Channel#getAddress()
 */
public final class SetAddress extends UpdateActionImpl<Channel> {
    @Nullable
    private final Address address;

    private SetAddress(@Nullable final Address address) {
        super("setAddress");
        this.address = address;
    }

    public static SetAddress of(@Nullable final Address address) {
        return new SetAddress(address);
    }

    @Nullable
    public Address getAddress() {
        return address;
    }
}
