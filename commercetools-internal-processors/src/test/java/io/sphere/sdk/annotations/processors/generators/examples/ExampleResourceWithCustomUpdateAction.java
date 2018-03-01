package io.sphere.sdk.annotations.processors.generators.examples;


import io.sphere.sdk.annotations.*;

public interface ExampleResourceWithCustomUpdateAction {


    @HasUpdateAction(value = "customUpdateAction", fields = {
            @PropertySpec(name = "name", type = String.class),
            @PropertySpec(name = "quantity", type = Long.class),
            @PropertySpec(name = "slug", type = String.class),
            @PropertySpec(name = "ids", type = String[].class),
            @PropertySpec(name = "taxCategory", type = String.class, useReference = true, isOptional = true)},
            factoryMethods = {
                    @FactoryMethod(parameterNames = {"name", "slug"}),
                    @FactoryMethod(parameterNames = {"name"})}
            )
    String getUserName();


}
