package io.sphere.sdk.products.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.ProductProjectionType;
import io.sphere.sdk.products.expansion.ProductExpansionModel;
import io.sphere.sdk.products.search.PriceSelectionRequestDsl;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.queries.*;

import java.util.Locale;

/**
 {@doc.gen summary products}
 */
public interface ProductQuery extends MetaModelQueryDsl<Product, ProductQuery, ProductQueryModel, ProductExpansionModel<Product>>, PriceSelectionRequestDsl<ProductQuery> {
    /**
     * Creates a container which contains the full Java type information to deserialize the query result (NOT this class) from JSON.
     *
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(byte[], TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(String, TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(com.fasterxml.jackson.databind.JsonNode, TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObjectFromResource(String, TypeReference)
     *
     * @return type reference
     */
    static TypeReference<PagedQueryResult<Product>> resultTypeReference() {
        return new TypeReference<PagedQueryResult<Product>>(){
            @Override
            public String toString() {
                return "TypeReference<PagedQueryResult<Product>>";
            }
        };
    }

    static ProductQuery of() {
        return new ProductQueryImpl();
    }

    default ProductQuery bySlug(final ProductProjectionType type, final Locale locale, final String slug) {
        return withPredicates(m -> m.masterData().forProjection(type).slug().lang(locale).is(slug));
    }

    default ProductQuery byProductType(final Referenceable<ProductType> productType) {
        return withPredicates(m -> m.productType().is(productType));
    }

    default ProductQuery bySku(final String sku, final ProductProjectionType type) {
        final QueryPredicate<EmbeddedProductVariantQueryModel> skuPredicate = EmbeddedProductVariantQueryModel.of().sku().is(sku);
        final ProductDataQueryModel<Product> projection = ProductQueryModel.of().masterData().forProjection(type);
        final QueryPredicate<Product> masterVariantSkuPredicate = projection.masterVariant().where(skuPredicate);
        final QueryPredicate<Product> variantsSkuPredicate = projection.variants().where(skuPredicate);
        return withPredicates(masterVariantSkuPredicate.or(variantsSkuPredicate));
    }
}