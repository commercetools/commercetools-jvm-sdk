package io.sphere.sdk.orders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.carts.ItemShippingDetailsDraft;
import io.sphere.sdk.carts.ItemState;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.taxcategories.TaxCategory;
import io.sphere.sdk.taxcategories.TaxRate;
import io.sphere.sdk.types.CustomDraft;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;
import java.util.Set;

/**
 * @see CustomLineItemImportDraftBuilder
 */
@JsonDeserialize(as = CustomLineItemImportDraftImpl.class)
public interface CustomLineItemImportDraft extends CustomDraft {

    LocalizedString getName();

    MonetaryAmount getMoney();

    String getSlug();

    Long getQuantity();

    Reference<TaxCategory> getTaxCategory();

    @Nullable
    TaxRate getTaxRate();

    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    @Nullable
    Set<ItemState> getState();

    /**
     *  Container for the sub-quantity of the line item quantity for the specific address when multiple shipping addresses are required
     * @return ItemShippingDetailsDraft
     */
    @Nullable
    ItemShippingDetailsDraft getShippingDetails();
}
