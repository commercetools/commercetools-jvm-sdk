package io.sphere.sdk.extensions;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.HasBuilder;
import io.sphere.sdk.annotations.ResourceValue;

import java.util.List;

@ResourceValue
@JsonDeserialize(as = TriggerImpl.class)
@HasBuilder(factoryMethods = @FactoryMethod(parameterNames = {"resourceTypeId","actions"}))
public interface Trigger {


    ExtensionResourceType getResourceTypeId();

    List<TriggerType> getActions();

}
