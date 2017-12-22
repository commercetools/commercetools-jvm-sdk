package io.sphere.sdk.extensions;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.HasBuilder;
import io.sphere.sdk.annotations.ResourceValue;

import java.util.List;

@ResourceValue
@JsonDeserialize(as = TriggerImpl.class)
@HasBuilder(factoryMethods = @FactoryMethod(parameterNames = {"resourceTypeId","triggers"}))
public interface Trigger {


    String getResourceTypeId();

    List<TriggerType> getTriggers();

}
