package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.products.PriceDraft;
import io.sphere.sdk.products.Product;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Sets the prices of a product variant. The same validation rules as for addPrice apply. All previous price information is lost and even if some prices did not change, all the prices will have new ids.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandIntegrationTest#setPrices()}
 *
 * @see io.sphere.sdk.products.ProductVariant#getPrices()
 */
public final class SetPrices extends UpdateActionImpl<Product> {
    @Nullable
    private final Integer variantId;
    @Nullable
    private final String sku;
    private final List<PriceDraft> prices;

    private SetPrices(@Nullable final Integer variantId, @Nullable final String sku, final List<PriceDraft> prices) {
        super("setPrices");
        this.variantId = variantId;
        this.sku = sku;
        this.prices = prices;
    }

    @Nullable
    public Integer getVariantId() {
        return variantId;
    }

    @Nullable
    public String getSku() {
        return sku;
    }

    public List<PriceDraft> getPrices() {
        return prices;
    }

    public static SetPrices of(final Integer variantId, final List<PriceDraft> prices) {
        return new SetPrices(variantId, null, prices);
    }

    public static SetPrices ofVariantId(final Integer variantId, final List<PriceDraft> prices) {
        return new SetPrices(variantId, null, prices);
    }

    public static SetPrices ofSku(final String sku, final List<PriceDraft> prices) {
        return new SetPrices(null, sku, prices);
    }
}
