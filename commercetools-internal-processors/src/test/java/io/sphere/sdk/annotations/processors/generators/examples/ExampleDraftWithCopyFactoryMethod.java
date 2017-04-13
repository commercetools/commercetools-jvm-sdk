package io.sphere.sdk.annotations.processors.generators.examples;

import io.sphere.sdk.annotations.CopyFactoryMethod;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;

/**
 * Example draft class used in our tests.
 */
@ResourceDraftValue(copyFactoryMethods = @CopyFactoryMethod(ExampleResource.class), factoryMethods = @FactoryMethod(parameterNames = {}))
public interface ExampleDraftWithCopyFactoryMethod {
    String getName();
}
