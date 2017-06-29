package io.sphere.sdk.annotations.processors.generators.examples;


import io.sphere.sdk.annotations.*;

import java.util.List;
import java.util.Map;

public interface ExampleResourceWithCustomUpdateAction {


    @HasCustomUpdateAction(name = "someShittyAssAction", fields =
//            @PropertySpec(name = "name", fieldType = LocalizedString.class),
//            @PropertySpec(name = "quantity", fieldType = Long.class),
//            @PropertySpec(name = "money", fieldType = MonetaryAmount.class),
//            @PropertySpec(name = "slug", fieldType = String.class),
            @PropertySpec(name = "taxCategory", fieldType = String.class, useReference = true, isOptional = true),
            factoryMethods = {
                    @FactoryMethod(parameterNames = {"name", "slug", "money", "taxCategory", "quantity"}),
                    @FactoryMethod(parameterNames = {"name", "slug", "money", "custom", "quantity"})}
            )
    String getUserName();


}
