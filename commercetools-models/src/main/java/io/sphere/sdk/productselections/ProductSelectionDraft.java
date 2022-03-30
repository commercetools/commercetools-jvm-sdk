package io.sphere.sdk.productselections;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.WithKey;
import io.sphere.sdk.types.CustomDraft;

import javax.annotation.Nullable;


/**
 * @see ProductSelectionDraftBuilder
 * @see ProductSelectionDraftDsl
 */
@JsonDeserialize(as = ProductSelectionDraftDsl.class)
@ResourceDraftValue(
        abstractBuilderClass = true,
        abstractResourceDraftValueClass = true,
        factoryMethods = {@FactoryMethod(parameterNames = {"name"})})
public interface ProductSelectionDraft extends WithKey, CustomDraft {
    @Nullable
    String getKey();

    LocalizedString getName();

    static ProductSelectionDraft ofName(final LocalizedString name) {
        return ProductSelectionDraftDsl.of(name);
    }
}
