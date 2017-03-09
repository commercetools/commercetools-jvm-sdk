package io.sphere.sdk.annotations.processors.generators.examples;

import io.sphere.sdk.annotations.ResourceValue;

/**
 * Example for a resource with an abstract base calss.
 */
@ResourceValue(abstractResourceClass = true)
public interface ExampleResourceWithAbstractBaseClass {
    String getName();
}
