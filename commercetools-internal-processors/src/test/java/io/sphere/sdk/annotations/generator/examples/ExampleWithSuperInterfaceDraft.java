package io.sphere.sdk.annotations.generator.examples;

import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;

/**
 * Example draft class for generating a builder for a draft class wich extends an interface.
 */
@ResourceDraftValue(factoryMethods = {@FactoryMethod(parameterNames = {"name", "sku", "active"})})
public interface ExampleWithSuperInterfaceDraft extends ExampleDraft {
    String getName();
}
