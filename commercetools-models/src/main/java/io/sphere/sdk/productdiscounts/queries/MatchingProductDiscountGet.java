package io.sphere.sdk.productdiscounts.queries;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.sphere.sdk.client.SphereRequest;
import io.sphere.sdk.productdiscounts.ProductDiscount;
import io.sphere.sdk.products.Price;

/**
 * This endpoint can be used to simulate which product discounts would be applied if a specified product variant had a specified price.<br>
 * Given product and variant ids and a price, this endpoint will return the product discount that would have been applied to that price.
 */
public interface MatchingProductDiscountGet extends SphereRequest<ProductDiscount> {

    /**
     * @return UUID, ID of the product
     */
    @JsonProperty
    String getProductId();

    /**
     * @return ID of the variant
     */
    @JsonProperty
    Integer getVariantId();

    /**
     * @return Whether to use the staged version of this variant or the published one
     */
    @JsonProperty
    Boolean getStaged();

    @JsonProperty
    Price getPrice();


    static MatchingProductDiscountGet of(final String productId, final  Integer variantId, final  Boolean staged, final Price price){
        return new MatchingProductDiscountGetImpl(productId,variantId ,staged ,price );
    }
}
