package io.sphere.sdk.annotations.processors.generators.examples;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

/**
 * Example draft class used in our tests.
 */
@ResourceDraftValue(factoryMethods = {
        @FactoryMethod(parameterNames = {"sku", "active"}),
        @FactoryMethod(parameterNames = {"default", "active"}),
        @FactoryMethod(parameterNames = {"default", "optional"})
})
public interface ExampleDraft {
    /**
     * This test a reference type. Our generator only supports reference type.
     */
    String getSku();

    /**
     * This tests a parameterized type.
     */
    List<String> getNames();

    /**
     *This tests plus methods for a set
     */

    Set<String> getValues();

    /**
     * This tests that an {@code active(Boolean)} and {@code isActive(Boolean)} builder method is generated.
     */
    @JsonProperty("isActive")
    Boolean isActive();

    /**
     * This tests that the reserved java keyword {@code default} is handled correctly.
     * The generated builder has a {@code isDefault(Boolean)} builder method and for to stay
     * backward compatible with the previous generator also a {@code _default(Boolean)} builder method.
     */
    Boolean isDefault();

    /**
     * This tests an optional field.
     */
    @Nullable
    String getOptional();

    /**
     * This test a deprecated field.
     * @return
     */
    @Deprecated
    String getDeprecatedField();

    /**
     * This tests that a default method doesn't generate a builder method.
     */
    default String getIgnored() {
        return "default getter methods are ignored";
    }
}
