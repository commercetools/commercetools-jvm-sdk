package io.sphere.sdk.carts;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.taxcategories.ExternalTaxRateDraft;
import io.sphere.sdk.types.CustomFieldsDraft;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;
import java.time.ZonedDateTime;
import java.util.Optional;

public final class LineItemDraftDsl extends LineItemDraftDslBase<LineItemDraftDsl>{

    LineItemDraftDsl(@Nullable ZonedDateTime addedAt, @Nullable CustomFieldsDraft custom, @Nullable ResourceIdentifier<Channel> distributionChannel, @Nullable MonetaryAmount externalPrice, @Nullable ExternalTaxRateDraft externalTaxRate, @Nullable ExternalLineItemTotalPrice externalTotalPrice, String productId, @Nullable Long quantity, @Nullable ItemShippingDetailsDraft shippingDetails, String sku, @Nullable ResourceIdentifier<Channel> supplyChannel, Integer variantId) {
        super(addedAt, custom, distributionChannel, externalPrice, externalTaxRate, externalTotalPrice, productId, quantity, shippingDetails, sku, supplyChannel, variantId);
    }

    public LineItemDraftDsl withSupplyChannel(@Nullable Referenceable<Channel> supplyChannel) {
        return super.withSupplyChannel(Optional.ofNullable(supplyChannel).map(Referenceable::toResourceIdentifier).orElse(null));
    }

    public LineItemDraftDsl withDistributionChannel(@Nullable Referenceable<Channel> distributionChannel) {
        return super.withDistributionChannel(Optional.ofNullable(distributionChannel).map(Referenceable::toResourceIdentifier).orElse(null));
    }
}
