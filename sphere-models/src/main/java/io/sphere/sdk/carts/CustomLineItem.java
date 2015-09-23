package io.sphere.sdk.carts;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.taxcategories.TaxCategory;
import io.sphere.sdk.taxcategories.TaxRate;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;
import java.util.Set;

@JsonDeserialize(as=CustomLineItemImpl.class)
public interface CustomLineItem extends LineItemLike {
    LocalizedString getName();

    MonetaryAmount getMoney();

    String getSlug();

    Set<ItemState> getState();

    Reference<TaxCategory> getTaxCategory();

    @Nullable
    TaxRate getTaxRate();

    @Override
    String getId();

    @Override
    Long getQuantity();

    static String resourceTypeId() {
        return "custom-line-item";
    }
}
