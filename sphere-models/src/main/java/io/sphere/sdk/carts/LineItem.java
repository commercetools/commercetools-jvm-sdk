package io.sphere.sdk.carts;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.products.Price;
import io.sphere.sdk.products.ProductVariant;
import io.sphere.sdk.taxcategories.TaxRate;

import java.util.List;
import java.util.Optional;

@JsonDeserialize(as=LineItemImpl.class)
public interface LineItem extends LineItemLike {

    String getProductId();

    LocalizedStrings getName();

    ProductVariant getVariant();

    Price getPrice();

    List<ItemState> getState();

    Optional<TaxRate> getTaxRate();

    Optional<Reference<Channel>> getSupplyChannel();

    @Override
    String getId();

    @Override
    long getQuantity();
}
