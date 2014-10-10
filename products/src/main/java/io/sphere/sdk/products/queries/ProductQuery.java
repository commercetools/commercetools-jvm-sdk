package io.sphere.sdk.products.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.ProductProjectionType;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.queries.DefaultModelQuery;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.Predicate;
import io.sphere.sdk.queries.QueryDsl;

import java.util.Locale;

public class ProductQuery extends DefaultModelQuery<Product> {
    public ProductQuery(){
        super(ProductsEndpoint.ENDPOINT.endpoint(), resultTypeReference());
    }

    public static TypeReference<PagedQueryResult<Product>> resultTypeReference() {
        return new TypeReference<PagedQueryResult<Product>>(){
            @Override
            public String toString() {
                return "TypeReference<PagedQueryResult<Product>>";
            }
        };
    }

    public static ProductQuery of() {
        return new ProductQuery();
    }

    public QueryDsl<Product> bySlug(final ProductProjectionType type, final Locale locale, final String slug) {
        return withPredicate(model().masterData().forProjection(type).slug().lang(locale).is(slug));
    }

    public QueryDsl<Product> byProductType(final Referenceable<ProductType> productType) {
        return withPredicate(model().productType().is(productType));
    }

    public static ProductExpansionModel<Product> expansionPath() {
        return new ProductExpansionModel<>();
    }

    public static ProductQueryModel model() {
        return ProductQueryModel.get();
    }

    public QueryDsl<Product> bySku(final String sku, final ProductProjectionType type) {
        final Predicate<PartialProductVariantQueryModel> skuPredicate = ProductVariantQueryModel.get().sku().is(sku);
        final ProductDataQueryModel<Product> projection = model().masterData().forProjection(type);
        final Predicate<Product> masterVariantSkuPredicate = projection.masterVariant().where(skuPredicate);
        final Predicate<Product> variantsSkuPredicate = projection.variants().where(skuPredicate);
        return withPredicate(masterVariantSkuPredicate.or(variantsSkuPredicate));
    }
}