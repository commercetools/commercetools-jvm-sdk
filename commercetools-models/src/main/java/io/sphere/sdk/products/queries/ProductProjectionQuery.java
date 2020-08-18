package io.sphere.sdk.products.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.ProductProjectionType;
import io.sphere.sdk.products.ProductVariant;
import io.sphere.sdk.products.expansion.ProductProjectionExpansionModel;
import io.sphere.sdk.products.search.*;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.queries.MetaModelQueryDsl;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.queries.QuerySort;
import io.sphere.sdk.selection.*;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;

/**
 {@doc.gen summary product projections}
 */
public interface ProductProjectionQuery extends MetaModelQueryDsl<ProductProjection, ProductProjectionQuery, ProductProjectionQueryModel, ProductProjectionExpansionModel<ProductProjection>>, PriceSelectionRequestDsl<ProductProjectionQuery>, LocaleSelectionRequestDsl<ProductProjectionQuery>, StoreSelectionRequestDsl<ProductProjectionQuery> {
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
    static TypeReference<PagedQueryResult<ProductProjection>> resultTypeReference() {
        return new TypeReference<PagedQueryResult<ProductProjection>>(){
            @Override
            public String toString() {
                return "TypeReference<PagedQueryResult<ProductProjection>>";
            }
        };
    }

    default ProductProjectionQuery byProductType(final Referenceable<ProductType> productType) {
        return withPredicates(m -> m.productType().is(productType));
    }

    default ProductProjectionQuery bySlug(final Locale locale, final String slug) {
        return withPredicates(m -> m.slug().lang(locale).is(slug));
    }

    default ProductProjectionQuery bySku(final String sku) {
        return withPredicates(m -> m.allVariants().where(n -> n.sku().is(sku)));
    }

    default ProductProjectionQuery bySku(final List<String> skus) {
        return withPredicates(m -> m.allVariants().where(n -> n.sku().isIn(skus)));
    }

    static ProductProjectionQuery ofStaged() {
        return of(ProductProjectionType.STAGED);
    }

    static ProductProjectionQuery ofCurrent() {
        return of(ProductProjectionType.CURRENT);
    }

    static ProductProjectionQuery of(final ProductProjectionType productProjectionType) {
        return new ProductProjectionQueryImpl(productProjectionType);
    }

    @Override
    ProductProjectionQuery withLimit(final Long limit);

    @Override
    ProductProjectionQuery withOffset(final Long offset);

    @Override
    ProductProjectionQuery withPredicates(final List<QueryPredicate<ProductProjection>> queryPredicates);

    @Override
    ProductProjectionQuery withPredicates(final QueryPredicate<ProductProjection> queryPredicate);

    @Override
    ProductProjectionQuery withPredicates(final Function<ProductProjectionQueryModel, QueryPredicate<ProductProjection>> predicateFunction);

    @Override
    ProductProjectionQuery plusPredicates(final List<QueryPredicate<ProductProjection>> queryPredicates);

    @Override
    ProductProjectionQuery plusPredicates(final QueryPredicate<ProductProjection> queryPredicate);

    @Override
    ProductProjectionQuery plusPredicates(final Function<ProductProjectionQueryModel, QueryPredicate<ProductProjection>> m);

    @Override
    ProductProjectionQuery withSort(final Function<ProductProjectionQueryModel, QuerySort<ProductProjection>> m);

    @Override
    ProductProjectionQuery withSort(final List<QuerySort<ProductProjection>> sort);

    @Override
    ProductProjectionQuery withSort(final QuerySort<ProductProjection> sort);

    @Override
    ProductProjectionQuery withSortMulti(final Function<ProductProjectionQueryModel, List<QuerySort<ProductProjection>>> m);

    /**
     * Uses the cart price selection logic to retrieve the prices for product variants.
     *
     * <p>Example for selecting just a currency</p>
     *
     * {@include.example io.sphere.sdk.products.search.PriceSelectionIntegrationTest#selectAPriceByCurrencyInProductProjectionQuery()}
     *
     * @param priceSelection parameters for the price selection, using null deletes the values
     * @return request with new parameters
     * @see ProductVariant#getPrice()
     */
    @Override
    ProductProjectionQuery withPriceSelection(@Nullable final PriceSelection priceSelection);

    @Override
    ProductProjectionQuery withLocaleSelection(@Nullable final LocaleSelection localeSelection);

    ProductProjectionQuery plusLocaleSelection(@Nullable final LocaleSelection localeSelection);

    @Override
    ProductProjectionQuery withStoreSelection(@Nullable final StoreSelection storeSelection);
}