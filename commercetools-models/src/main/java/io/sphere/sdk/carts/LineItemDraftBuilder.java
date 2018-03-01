package io.sphere.sdk.carts;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.products.ByIdVariantIdentifier;
import io.sphere.sdk.products.BySkuVariantIdentifier;
import io.sphere.sdk.taxcategories.ExternalTaxRateDraft;
import io.sphere.sdk.types.CustomFieldsDraft;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;

public final class LineItemDraftBuilder extends LineItemDraftBuilderBase<LineItemDraftBuilder> {

    LineItemDraftBuilder(@Nullable CustomFieldsDraft custom, @Nullable Reference<Channel> distributionChannel, @Nullable MonetaryAmount externalPrice, @Nullable ExternalTaxRateDraft externalTaxRate, @Nullable ExternalLineItemTotalPrice externalTotalPrice, String productId, @Nullable Long quantity,@Nullable final ItemShippingDetailsDraft shippingDetails, @Nullable String sku, @Nullable Reference<Channel> supplyChannel, @Nullable Integer variantId) {
        super(custom, distributionChannel, externalPrice, externalTaxRate, externalTotalPrice, productId, quantity,shippingDetails, sku, supplyChannel, variantId);
    }

    /**
     * Creates a new object initialized with the given values.
     *
     * @param variantIdentifier initial value for the {@link LineItemDraft#getProductId()} and {@link LineItemDraft#getVariantId()} properties
     * @param quantity          initial value for the {@link LineItemDraft#getQuantity()} property
     * @return new object initialized with the given values
     */
    public static LineItemDraftBuilder ofVariantIdentifier(final ByIdVariantIdentifier variantIdentifier, @Nullable final Long quantity) {
        return of(variantIdentifier.getProductId(), variantIdentifier.getVariantId(), quantity, null, null, null, null, null, null);
    }

    /**
     * Creates a new object initialized with the given values.
     *
     * @param skuVariantIdentifier initial value for the {@link LineItemDraft#getSku()} property
     * @param quantity             initial value for the {@link LineItemDraft#getQuantity()} property
     * @return new object initialized with the given values
     */
    public static LineItemDraftBuilder ofSkuVariantIdentifier(final BySkuVariantIdentifier skuVariantIdentifier, @Nullable final Long quantity) {
        return ofSku(skuVariantIdentifier.getSku(), quantity);
    }

}
