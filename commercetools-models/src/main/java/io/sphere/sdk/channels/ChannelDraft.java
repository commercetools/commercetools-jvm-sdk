package io.sphere.sdk.channels;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.models.GeoJSON;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.WithKey;
import io.sphere.sdk.types.CustomDraft;
import io.sphere.sdk.types.CustomFieldsDraft;

import javax.annotation.Nullable;
import java.util.Set;

/**
 * Template to create a new Channel.
 * <p>
 * {@include.example io.sphere.sdk.channels.commands.ChannelCreateCommandIntegrationTest#execution()}
 *
 * @see ChannelDraftBuilder
 * @see ChannelDraftDsl
 */
@JsonDeserialize(as = ChannelDraftDsl.class)
@ResourceDraftValue(
        abstractResourceDraftValueClass = true,
        factoryMethods = @FactoryMethod(parameterNames = {"key"}))
public interface ChannelDraft extends WithKey, CustomDraft {
    String getKey();

    @Nullable
    Set<ChannelRole> getRoles();

    @Nullable
    LocalizedString getName();

    @Nullable
    LocalizedString getDescription();

    @Nullable
    CustomFieldsDraft getCustom();

    @Nullable
    Address getAddress();

    @Nullable
    GeoJSON getGeoLocation();

    static ChannelDraftDsl of(final String key) {
        return ChannelDraftDsl.of(key);
    }
}
