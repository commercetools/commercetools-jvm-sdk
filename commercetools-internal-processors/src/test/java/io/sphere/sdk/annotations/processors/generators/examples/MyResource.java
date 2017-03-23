package io.sphere.sdk.annotations.processors.generators.examples;

import io.sphere.sdk.annotations.HasUpdateActions;

import javax.annotation.Nullable;

/**
 * Example of update action for a resource with primitive types.
 */
@HasUpdateActions
public interface MyResource {
    String getName();

    @Nullable
    String getDescription();

    Integer getQuantity();
}
