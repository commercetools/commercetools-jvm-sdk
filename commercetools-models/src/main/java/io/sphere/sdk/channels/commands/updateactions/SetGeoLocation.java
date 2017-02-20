package io.sphere.sdk.channels.commands.updateactions;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.GeoJSON;

import javax.annotation.Nullable;

/**
 * Sets/unsets the geo location of the channel.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.channels.commands.ChannelUpdateCommandIntegrationTest#setGeoLocation()}
 */
public final class SetGeoLocation extends UpdateActionImpl<Channel> {
    @Nullable
    private GeoJSON geoLocation;

    private SetGeoLocation(@Nullable final GeoJSON geoLocation) {
        super("setGeoLocation");
        this.geoLocation = geoLocation;
    }

    @Nullable
    public GeoJSON getGeoLocation() {
        return geoLocation;
    }

    public static SetGeoLocation of(@Nullable final GeoJSON geoLocation) {
        return new SetGeoLocation(geoLocation);
    }

    public static SetGeoLocation ofUnset() {
        return of(null);
    }
}
