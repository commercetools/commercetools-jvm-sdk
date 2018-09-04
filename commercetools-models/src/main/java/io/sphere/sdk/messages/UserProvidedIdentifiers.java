package io.sphere.sdk.messages;


import io.sphere.sdk.annotations.ResourceValue;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.WithKey;

import javax.annotation.Nullable;

@ResourceValue
public interface UserProvidedIdentifiers extends WithKey {

    @Override
    @Nullable
    String getKey();

    @Nullable
    String getExternalId();

    @Nullable
    String getOrderNumber();

    @Nullable
    String getSku();

    @Nullable
    LocalizedString getSlug();

}
