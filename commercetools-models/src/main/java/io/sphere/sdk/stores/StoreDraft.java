package io.sphere.sdk.stores;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;
import io.sphere.sdk.models.LocalizedString;

import javax.annotation.Nullable;

@JsonDeserialize(as = StoreDraftDsl.class)
@ResourceDraftValue(factoryMethods = {
        @FactoryMethod(parameterNames = {"key"}),
        @FactoryMethod(parameterNames = {"key", "name"})})
public interface StoreDraft {
    
    String getKey();
    
    @Nullable
    LocalizedString getName();
    
    static StoreDraft of(final String key, @Nullable final LocalizedString name) {
        return new StoreDraftDsl(key, name);
    }
    
}
