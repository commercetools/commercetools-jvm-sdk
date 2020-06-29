package io.sphere.sdk.stores;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;
import io.sphere.sdk.models.LocalizedString;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Locale;

@JsonDeserialize(as = StoreDraftDsl.class)
@ResourceDraftValue(factoryMethods = {
        @FactoryMethod(parameterNames = {"key"}),
        @FactoryMethod(parameterNames = {"key", "name", "languages"})})
public interface StoreDraft {
    
    String getKey();
    
    @Nullable
    LocalizedString getName();

    @Nullable
    List<Locale> getLanguages();
    
    static StoreDraft of(final String key, @Nullable final LocalizedString name, @Nullable final List<Locale> languages) {
        return new StoreDraftDsl(key, languages, name);
    }
    
}
