package io.sphere.sdk.channels;

import io.sphere.sdk.models.Address;
import io.sphere.sdk.models.GeoJSON;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.types.CustomFieldsDraft;

import javax.annotation.Nullable;
import java.util.Set;

public final class ChannelDraftDsl extends ChannelDraftDslBase<ChannelDraftDsl> {

    ChannelDraftDsl(@Nullable final Address address, @Nullable final CustomFieldsDraft custom, @Nullable final LocalizedString description, @Nullable final GeoJSON geoLocation, final String key, @Nullable final LocalizedString name, @Nullable final Set<ChannelRole> roles) {
        super(address, custom, description, geoLocation, key, name, roles);
    }

    public ChannelDraftDsl withRoles(final ChannelRole... roles) {
        return newBuilder().roles(io.sphere.sdk.utils.SphereInternalUtils.asSet(roles)).build();
    }

}
