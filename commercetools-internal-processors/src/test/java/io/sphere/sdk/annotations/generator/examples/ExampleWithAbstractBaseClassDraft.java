package io.sphere.sdk.annotations.generator.examples;

import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Example draft class for generating an abstract builder base class.
 */
@ResourceDraftValue(abstractBaseClass = true, factoryMethods = {@FactoryMethod(parameterNames = {"sku", "active"})})
public interface ExampleWithAbstractBaseClassDraft {
    String getSku();

    List<String> getPrices();

    List<String> getAttributes();

    List<String> getImages();

    Boolean isActive();

    @Nullable
    String getKey();

    @Nullable
    List<String> getAssets();
}
