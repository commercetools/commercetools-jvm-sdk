package io.sphere.sdk.annotations.processors.generators.examples;

import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * This interface contains multiple nested types annotated with {@link com.fasterxml.jackson.annotation.JsonTypeName}.
 */
public interface ExampleTypes {
    @JsonTypeName("type1")
    interface Type1 {}

    @JsonTypeName("type2")
    interface Type2 {}
}
