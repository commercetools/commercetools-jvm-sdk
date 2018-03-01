package io.sphere.sdk.carts.commands;

import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;
import io.sphere.sdk.carts.CartLike;
import io.sphere.sdk.models.Reference;

@ResourceDraftValue(factoryMethods = @FactoryMethod(parameterNames = "reference"))
public interface CartReplicationDraft {

    Reference<? extends CartLike> getReference();

}
