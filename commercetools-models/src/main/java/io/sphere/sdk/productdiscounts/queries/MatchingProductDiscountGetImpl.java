package io.sphere.sdk.productdiscounts.queries;

import io.sphere.sdk.client.HttpRequestIntent;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.productdiscounts.ProductDiscount;
import io.sphere.sdk.products.Price;

import static io.sphere.sdk.http.HttpMethod.POST;

final class MatchingProductDiscountGetImpl extends Base implements MatchingProductDiscountGet {

    private final String productId;
    private final Integer variantId;
    private final Boolean staged;
    private final Price price;

    MatchingProductDiscountGetImpl(final String productId, final  Integer variantId, final  Boolean staged, final Price price) {
        this.productId = productId;
        this.variantId = variantId;
        this.staged = staged;
        this.price = price;
    }

    @Override
    public ProductDiscount deserialize(final HttpResponse httpResponse) {
        return SphereJsonUtils.readObject(httpResponse.getResponseBody(), ProductDiscount.typeReference());
    }

    @Override
    public HttpRequestIntent httpRequestIntent() {
        return HttpRequestIntent.of(POST, "/product-discounts/matching",SphereJsonUtils.toJsonString(this));
    }


    @Override
    public String getProductId() {
        return productId;
    }

    @Override
    public Integer getVariantId() {
        return variantId;
    }

    @Override
    public Boolean getStaged() {
        return staged;
    }

    @Override
    public Price getPrice() {
        return price;
    }
}
