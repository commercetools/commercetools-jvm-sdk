package io.sphere.sdk.messages;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.ResourceValue;
import io.sphere.sdk.models.ContainerAndKey;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.WithKey;

import javax.annotation.Nullable;

@ResourceValue
@JsonDeserialize(as= UserProvidedIdentifiersImpl.class)
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

    @Nullable
    String getCustomerNumber();

    @Nullable
    ContainerAndKey getContainerAndKey();
}
