package io.sphere.sdk.orders;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.sphere.sdk.carts.ItemState;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.taxcategories.TaxCategory;
import io.sphere.sdk.taxcategories.TaxRate;

import javax.money.MonetaryAmount;
import java.util.Optional;
import java.util.Set;

/**
 * @see CustomLineItemImportDraftBuilder
 */
public interface CustomLineItemImportDraft {

    LocalizedStrings getName();

    MonetaryAmount getMoney();

    String getSlug();

    long getQuantity();

    Reference<TaxCategory> getTaxCategory();

    Optional<TaxRate> getTaxRate();

    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    Optional<Set<ItemState>> getState();
}
