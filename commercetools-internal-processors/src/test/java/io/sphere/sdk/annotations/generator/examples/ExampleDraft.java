package io.sphere.sdk.annotations.generator.examples;

import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Example draft class used in our tests.
 */
@ResourceDraftValue(factoryMethods = {@FactoryMethod(parameterNames = {"sku", "active"})})
public interface ExampleDraft {
    String getSku();

    List<String> getPrices();

    List<String> getAttributes();

    List<String> getImages();

    Boolean isActive();

    @Nullable
    String getKey();

    @Nullable
    List<String> getAssets();

    default String getIgnored() {
        return "default getter methods are ignored";
    }
}
