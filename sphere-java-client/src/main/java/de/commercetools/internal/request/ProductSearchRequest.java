package de.commercetools.internal.request;

import com.google.common.base.Function;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import de.commercetools.internal.ProductConversion;
import de.commercetools.sphere.client.SearchRequest;
import de.commercetools.sphere.client.SphereException;
import de.commercetools.sphere.client.facets.expressions.FacetExpression;
import de.commercetools.sphere.client.filters.expressions.FilterExpression;
import de.commercetools.sphere.client.model.SearchResult;
import de.commercetools.sphere.client.shop.CategoryTree;
import de.commercetools.sphere.client.shop.model.Product;
import de.commercetools.sphere.client.model.products.BackendProduct;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;

/** Transforms results of type {@link BackendProduct} to {@link Product}. */
public class ProductSearchRequest implements SearchRequest<Product> {
    private SearchRequest<BackendProduct> underlyingRequest;
    private final CategoryTree categoryTree;

    public ProductSearchRequest(@Nonnull SearchRequest<BackendProduct> underlyingRequest, CategoryTree categoryTree) {
        if (underlyingRequest == null) throw new IllegalArgumentException("underlyingRequest can't be null");
        this.underlyingRequest = underlyingRequest;
        this.categoryTree = categoryTree;
    }

    @Override public SearchResult<Product> fetch() throws SphereException {
        return convertResult(underlyingRequest.fetch(), categoryTree);
    }

    @Override
    public ListenableFuture<SearchResult<Product>> fetchAsync() throws SphereException {
        return Futures.transform(underlyingRequest.fetchAsync(), new Function<SearchResult<BackendProduct>, SearchResult<Product>>() {
            @Override public SearchResult<Product> apply(@Nullable SearchResult<BackendProduct> res) {
                return convertResult(res, categoryTree);
            }
        });
    }

    private static SearchResult<Product> convertResult(SearchResult<BackendProduct> res, CategoryTree categoryTree) {
        return new SearchResult<Product>(
                res.getOffset(),
                res.getCount(),
                res.getTotal(),
                ProductConversion.fromBackendProducts(res.getResults(), categoryTree),
                res.getFacetsRaw());
    }

    @Override public SearchRequest<Product> page(int page) {
        underlyingRequest = underlyingRequest.page(page);
        return this;
    }

    @Override public SearchRequest<Product> pageSize(int pageSize) {
        underlyingRequest = underlyingRequest.pageSize(pageSize);
        return this;
    }

// not implemented in the backend yet
//    @Override public SearchRequest<Product> expand(String... paths) {
//        underlyingRequest.expand(paths);
//        return this;
//    }

    @Override public SearchRequest<Product> filtered(FilterExpression filter, FilterExpression... filters) {
        underlyingRequest = underlyingRequest.filtered(filter, filters);
        return this;
    }

    @Override public SearchRequest<Product> filtered(Iterable<FilterExpression> filters) {
        underlyingRequest = underlyingRequest.filtered(filters);
        return this;
    }

    @Override public SearchRequest<Product> faceted(FacetExpression facet, FacetExpression... facets) {
        underlyingRequest = underlyingRequest.faceted(facet, facets);
        return this;
    }

    @Override public SearchRequest<Product> faceted(Collection<FacetExpression> facets) {
        underlyingRequest = underlyingRequest.faceted(facets);
        return this;
    }
}
