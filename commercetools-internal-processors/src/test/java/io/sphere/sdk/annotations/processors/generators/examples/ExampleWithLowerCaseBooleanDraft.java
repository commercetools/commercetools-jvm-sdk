package io.sphere.sdk.annotations.processors.generators.examples;

import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;

/**
 * Example draft class used in our tests.
 */
@ResourceDraftValue(factoryMethods = {@FactoryMethod(parameterNames = {"lowerCased"}, useLowercaseBooleans = true)})
public interface ExampleWithLowerCaseBooleanDraft {
    Boolean isLowerCased();
}
