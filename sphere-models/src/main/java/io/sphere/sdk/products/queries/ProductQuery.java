package io.sphere.sdk.products.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.ProductProjectionType;
import io.sphere.sdk.products.expansion.ProductExpansionModel;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.queries.*;

import java.util.Locale;

/**
 {@doc.gen summary products}
 */
public interface ProductQuery extends MetaModelQueryDsl<Product, ProductQuery, ProductQueryModel, ProductExpansionModel<Product>> {

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
        return withPredicate(ProductQueryModel.of().masterData().forProjection(type).slug().lang(locale).is(slug));
    }

    default ProductQuery byProductType(final Referenceable<ProductType> productType) {
        return withPredicate(ProductQueryModel.of().productType().is(productType));
    }

    default ProductQuery bySku(final String sku, final ProductProjectionType type) {
        final QueryPredicate<PartialProductVariantQueryModel> skuPredicate = ProductVariantQueryModel.get().sku().is(sku);
        final ProductDataQueryModel<Product> projection = ProductQueryModel.of().masterData().forProjection(type);
        final QueryPredicate<Product> masterVariantSkuPredicate = projection.masterVariant().where(skuPredicate);
        final QueryPredicate<Product> variantsSkuPredicate = projection.variants().where(skuPredicate);
        return withPredicate(masterVariantSkuPredicate.or(variantsSkuPredicate));
    }
}