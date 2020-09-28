package io.sphere.sdk.stores;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.ResourceIdentifier;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

@JsonDeserialize(as = StoreDraftDsl.class)
@ResourceDraftValue(factoryMethods = {
        @FactoryMethod(parameterNames = {"key"}),
        @FactoryMethod(parameterNames = {"key", "name"}),
        @FactoryMethod(parameterNames = {"key", "name", "languages"})})
public interface StoreDraft {

    String getKey();

    @Nullable
    LocalizedString getName();

    @Nullable
    List<String> getLanguages();

    @Nullable
    List<ResourceIdentifier<Channel>> getDistributionChannels();

    static StoreDraftDsl of(final String key, @Nullable final LocalizedString name) {
        return new StoreDraftDsl(null, key, new ArrayList<>(), name);
    }

}
