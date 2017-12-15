package io.sphere.sdk.annotations.processors.generators.examples;

import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;

@ResourceDraftValue(factoryMethods = @FactoryMethod(parameterNames = {}))
public interface ExampleDraftWithNoAttribute {
}
