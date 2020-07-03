package io.sphere.sdk.stores;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;
import io.sphere.sdk.models.LocalizedString;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

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
    
    static StoreDraft of(final String key, @Nullable final LocalizedString name) {
        return new StoreDraftDsl(key, Collections.emptyList(), name);
    }
    
}
