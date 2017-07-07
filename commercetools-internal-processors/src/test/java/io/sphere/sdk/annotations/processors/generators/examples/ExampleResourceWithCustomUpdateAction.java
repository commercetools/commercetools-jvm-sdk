package io.sphere.sdk.annotations.processors.generators.examples;


import io.sphere.sdk.annotations.*;

public interface ExampleResourceWithCustomUpdateAction {


    @HasUpdateAction(value = "customUpdateAction", fields = {
            @PropertySpec(name = "name", fieldType = String.class),
            @PropertySpec(name = "quantity", fieldType = Long.class),
            @PropertySpec(name = "slug", fieldType = String.class),
            @PropertySpec(name = "taxCategory", fieldType = String.class, useReference = true, isOptional = true)},
            factoryMethods = {
                    @FactoryMethod(parameterNames = {"name", "slug"}),
                    @FactoryMethod(parameterNames = {"name"})}
            )
    String getUserName();


}
