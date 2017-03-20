package io.sphere.sdk.annotations.processors.generators.examples;

import io.sphere.sdk.annotations.ResourceValue;
import io.sphere.sdk.models.Base;

/**
 * Example for a resource that uses a custom base class.
 */
@ResourceValue(baseClass = ExampleResourceWithBaseClass.CustomBaseClass.class)
public interface ExampleResourceWithBaseClass {

    class CustomBaseClass extends Base {

    }
}
