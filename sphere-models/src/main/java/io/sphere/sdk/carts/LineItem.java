package io.sphere.sdk.carts;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.products.Price;
import io.sphere.sdk.products.ProductVariant;
import io.sphere.sdk.taxcategories.TaxRate;
import io.sphere.sdk.types.CustomFields;

import javax.annotation.Nullable;
import java.util.Set;

@JsonDeserialize(as=LineItemImpl.class)
public interface LineItem extends LineItemLike {

    String getProductId();

    LocalizedString getName();

    ProductVariant getVariant();

    Price getPrice();

    Set<ItemState> getState();

    @Nullable
    TaxRate getTaxRate();

    @Nullable
    Reference<Channel> getSupplyChannel();

    @Nullable
    Reference<Channel> getDistributionChannel();

    @Nullable
    LocalizedString getProductSlug();

    @Override
    String getId();

    @Override
    Long getQuantity();

    @Override
    CustomFields getCustom();

    static String resourceTypeId() {
        return "line-item";
    }
}
