package io.sphere.sdk.carts;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.products.ByIdVariantIdentifier;
import io.sphere.sdk.taxcategories.ExternalTaxRateDraft;
import io.sphere.sdk.types.CustomFieldsDraft;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;

public class LineItemDraftBuilder extends LineItemDraftBuilderBase<LineItemDraftBuilder> {

    LineItemDraftBuilder(@Nullable CustomFieldsDraft custom, @Nullable Reference<Channel> distributionChannel, @Nullable MonetaryAmount externalPrice, @Nullable ExternalTaxRateDraft externalTaxRate, @Nullable ExternalLineItemTotalPrice externalTotalPrice, String productId, @Nullable Long quantity, @Nullable String sku, @Nullable Reference<Channel> supplyChannel, @Nullable Integer variantId) {
        super(custom, distributionChannel, externalPrice, externalTaxRate, externalTotalPrice, productId, quantity, sku, supplyChannel, variantId);
    }

    /**
     * Creates a new object initialized with the given values.
     *
     * @param variantIdentifier   initial value for the {@link LineItemDraft#getProductId()} and {@link LineItemDraft#getVariantId()} properties
     * @param quantity            initial value for the {@link LineItemDraft#getQuantity()} property
     * @param supplyChannel       initial value for the {@link LineItemDraft#getSupplyChannel()} property
     * @param distributionChannel initial value for the {@link LineItemDraft#getDistributionChannel()} property
     * @param custom              initial value for the {@link LineItemDraft#getCustom()} property
     * @param externalTaxRate     initial value for the {@link LineItemDraft#getExternalTaxRate()} property
     * @param externalPrice       initial value for the {@link LineItemDraft#getExternalPrice()} property
     * @param externalTotalPrice  initial value for the {@link LineItemDraft#getExternalTotalPrice()} property
     * @return new object initialized with the given values
     */
    public static LineItemDraftBuilder ofVariantIdentifier(final ByIdVariantIdentifier variantIdentifier,
                                                           @Nullable final Long quantity,
                                                           @Nullable final Reference<Channel> supplyChannel,
                                                           @Nullable final Reference<Channel> distributionChannel,
                                                           @Nullable final CustomFieldsDraft custom,
                                                           @Nullable final ExternalTaxRateDraft externalTaxRate,
                                                           @Nullable final MonetaryAmount externalPrice,
                                                           @Nullable final ExternalLineItemTotalPrice externalTotalPrice) {
        return of(variantIdentifier.getProductId(), variantIdentifier.getVariantId(), quantity, supplyChannel, distributionChannel, custom, externalTaxRate, externalPrice, externalTotalPrice);
    }
}
