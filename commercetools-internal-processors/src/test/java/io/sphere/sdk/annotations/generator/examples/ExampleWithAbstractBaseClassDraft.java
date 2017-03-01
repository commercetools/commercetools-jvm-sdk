package io.sphere.sdk.annotations.generator.examples;

import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;

/**
 * Example draft class for generating an abstract builder base class.
 */
@ResourceDraftValue(abstractBuilderClass = true, factoryMethods = {@FactoryMethod(parameterNames = {"name"})})
public interface ExampleWithAbstractBaseClassDraft {
    String getName();
}
