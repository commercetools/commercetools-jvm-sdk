package io.sphere.sdk.carts.commands;

import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;
import io.sphere.sdk.carts.CartLike;
import io.sphere.sdk.models.Reference;

import javax.annotation.Nullable;

@ResourceDraftValue(factoryMethods = @FactoryMethod(parameterNames = "reference"))
public interface CartReplicationDraft {

    Reference<? extends CartLike> getReference();

    @Nullable
    String getKey();

}
