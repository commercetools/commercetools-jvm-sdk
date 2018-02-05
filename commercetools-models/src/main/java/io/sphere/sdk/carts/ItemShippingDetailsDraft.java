package io.sphere.sdk.carts;


import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;

import java.util.List;

/**
 * Draft object for {@link ItemShippingDetails}.
 */
@ResourceDraftValue(factoryMethods = @FactoryMethod(parameterNames = {"targets"}))
public interface ItemShippingDetailsDraft  {

    List<ItemShippingTarget> getTargets();

}
