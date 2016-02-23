package io.sphere.sdk.channels;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.SphereEnumeration;

/**
 * Role of a channel.
 *
 * For the import and the export of values see also {@link SphereEnumeration}.
 *
 * @see io.sphere.sdk.channels.commands.updateactions.AddRoles
 * @see io.sphere.sdk.channels.commands.updateactions.SetRoles
 * @see io.sphere.sdk.channels.commands.updateactions.RemoveRoles
 * @see Channel#getRoles()
 */
public enum ChannelRole implements SphereEnumeration {
    INVENTORY_SUPPLY, ORDER_EXPORT, ORDER_IMPORT, PRIMARY, PRODUCT_DISTRIBUTION;

    @JsonCreator
    public static ChannelRole ofSphereValue(final String value) {
        return SphereEnumeration.findBySphereName(values(), value).get();
    }
}
