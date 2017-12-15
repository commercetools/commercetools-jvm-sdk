package io.sphere.sdk.carts;

import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;
import io.sphere.sdk.taxcategories.ExternalTaxRateDraft;

import javax.money.MonetaryAmount;

@ResourceDraftValue(
        factoryMethods = @FactoryMethod(parameterNames = { "totalGross", "taxRate" }))
public interface ExternalTaxAmountDraft {
    /**
     * @return The total gross amount of the item (totalNet + taxes).
     */
    MonetaryAmount getTotalGross();

    ExternalTaxRateDraft getTaxRate();
}
