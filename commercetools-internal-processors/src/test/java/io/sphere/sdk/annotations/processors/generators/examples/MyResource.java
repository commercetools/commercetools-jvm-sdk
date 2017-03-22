package io.sphere.sdk.annotations.processors.generators.examples;

import io.sphere.sdk.annotations.HasUpdateActions;

/**
 * Example of update action for a resource with primitive type.
 */
@HasUpdateActions
public interface MyResource {
    String getName();
}
