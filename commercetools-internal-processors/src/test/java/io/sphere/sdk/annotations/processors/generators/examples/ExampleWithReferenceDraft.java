package io.sphere.sdk.annotations.processors.generators.examples;

import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;
import io.sphere.sdk.models.Reference;

/**
 * Example draft class that uses a {@link Reference} as field type.
 */
@ResourceDraftValue(factoryMethods = {@FactoryMethod(parameterNames = {"name"})})
public interface ExampleWithReferenceDraft {
    Reference<String> getName();
}
