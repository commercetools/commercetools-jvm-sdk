package io.sphere.sdk.annotations.processors.generators.examples;

import com.fasterxml.jackson.annotation.JsonTypeName;
import io.sphere.sdk.annotations.ResourceValue;

/**
 * This is an example resource with a type variable.
 */
@JsonTypeName("generic-resource")
@ResourceValue
public class ExampleGenericResource<T> {
}
