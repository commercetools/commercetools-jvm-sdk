package io.sphere.sdk.carts;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;
import io.sphere.sdk.taxcategories.ExternalTaxRateDraft;

import javax.money.MonetaryAmount;

@ResourceDraftValue(
        factoryMethods = @FactoryMethod(parameterNames = { "totalGross", "taxRate" }))
@JsonDeserialize(as = ExternalTaxAmountDraftDsl.class)
public interface ExternalTaxAmountDraft {
    /**
     * @return The total gross amount of the item (totalNet + taxes).
     */
    MonetaryAmount getTotalGross();

    ExternalTaxRateDraft getTaxRate();
}
