package io.sphere.sdk.stores;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.models.WithKey;
import io.sphere.sdk.types.CustomDraft;
import io.sphere.sdk.types.CustomFields;
import io.sphere.sdk.types.CustomFieldsDraft;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@JsonDeserialize(as = StoreDraftDsl.class)
@ResourceDraftValue(factoryMethods = {
        @FactoryMethod(parameterNames = {"key"}),
        @FactoryMethod(parameterNames = {"key", "name"}),
        @FactoryMethod(parameterNames = {"key", "name", "languages"})})
public interface StoreDraft extends CustomDraft, WithKey {

    String getKey();

    @Nullable
    LocalizedString getName();

    @Nullable
    List<String> getLanguages();

    @Nullable
    List<ResourceIdentifier<Channel>> getDistributionChannels();

    @Nullable
    List<ResourceIdentifier<Channel>> getSupplyChannels();

    @Nullable
    CustomFieldsDraft getCustom();

    static StoreDraftDsl of(final String key, @Nullable final LocalizedString name) {
        return new StoreDraftDsl(null, null, key, new ArrayList<>(), name, null);
    }

}
