package io.sphere.sdk.productdiscounts.queries;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.sphere.sdk.client.HttpRequestIntent;
import io.sphere.sdk.client.SphereRequest;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.productdiscounts.ProductDiscount;
import io.sphere.sdk.products.Price;

import static io.sphere.sdk.http.HttpMethod.POST;

public final class MatchingProductDiscountGet extends Base implements SphereRequest<ProductDiscount> {

    private final String productId;
    private final Integer variantId;
    private final Boolean staged;
    private final Price price;

    private MatchingProductDiscountGet(final String productId, final  Integer variantId, final  Boolean staged, final Price price) {
        this.productId = productId;
        this.variantId = variantId;
        this.staged = staged;
        this.price = price;
    }

    public static MatchingProductDiscountGet of(final String productId, final  Integer variantId, final  Boolean staged, final Price price){
        return new MatchingProductDiscountGet(productId,variantId ,staged ,price );
    }

    @Override
    public ProductDiscount deserialize(final HttpResponse httpResponse) {
        return SphereJsonUtils.readObject(httpResponse.getResponseBody(), ProductDiscount.typeReference());
    }

    @Override
    public HttpRequestIntent httpRequestIntent() {
        return HttpRequestIntent.of(POST, "/product-discounts/matching",SphereJsonUtils.toJsonString(MatchingProductDiscountGet.this));
    }


    @JsonProperty
    public String getProductId() {
        return productId;
    }

    @JsonProperty
    public Integer getVariantId() {
        return variantId;
    }

    @JsonProperty
    public Boolean getStaged() {
        return staged;
    }

    @JsonProperty
    public Price getPrice() {
        return price;
    }
}
