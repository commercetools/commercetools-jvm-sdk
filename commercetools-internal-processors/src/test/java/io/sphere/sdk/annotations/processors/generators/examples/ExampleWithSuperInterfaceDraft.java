package io.sphere.sdk.annotations.processors.generators.examples;

import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;

/**
 * Example draft class for generating a builder for a draft class which extends another interface.
 */
@ResourceDraftValue(factoryMethods = {@FactoryMethod(parameterNames = {"name", "sku"})})
public interface ExampleWithSuperInterfaceDraft extends ExampleDraftSuperInterface {
    String getName();
}
