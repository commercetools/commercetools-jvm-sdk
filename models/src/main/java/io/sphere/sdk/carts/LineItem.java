package io.sphere.sdk.carts;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.products.Price;
import io.sphere.sdk.products.ProductVariant;
import io.sphere.sdk.taxcategories.TaxRate;

import java.util.List;
import java.util.Optional;

@JsonDeserialize(as=LineItemImpl.class)
public interface LineItem {
    String getId();

    String getProductId();

    LocalizedStrings getName();

    ProductVariant getVariant();

    Price getPrice();

    long getQuantity();

    List<ItemState> getState();

    TaxRate getTaxRate();

    Optional<Channel> getSupplyChannel();
}
