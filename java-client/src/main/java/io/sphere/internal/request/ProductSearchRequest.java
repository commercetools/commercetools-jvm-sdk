package io.sphere.internal.request;

import com.google.common.base.Function;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import io.sphere.internal.ProductConversion;
import io.sphere.internal.util.SearchResultUtil;
import io.sphere.client.ProductSort;
import io.sphere.client.SearchRequest;
import io.sphere.client.facets.expressions.FacetExpression;
import io.sphere.client.filters.expressions.FilterExpression;
import io.sphere.client.model.SearchResult;
import io.sphere.client.shop.CategoryTree;
import io.sphere.client.shop.model.Product;
import io.sphere.client.model.products.BackendProduct;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** Transforms results from {@link BackendProduct} to {@link Product}. */
public class ProductSearchRequest implements SearchRequest<Product> {
    private SearchRequest<BackendProduct> underlyingRequest;
    private final CategoryTree categoryTree;

    public ProductSearchRequest(@Nonnull SearchRequest<BackendProduct> underlyingRequest, CategoryTree categoryTree) {
        if (underlyingRequest == null) throw new NullPointerException("underlyingRequest");
        this.underlyingRequest = underlyingRequest;
        this.categoryTree = categoryTree;
    }

    @Override public SearchResult<Product> fetch() {
        return convertProducts(underlyingRequest.fetch(), categoryTree);
    }

    @Override public ListenableFuture<SearchResult<Product>> fetchAsync() {
        return Futures.transform(underlyingRequest.fetchAsync(), new Function<SearchResult<BackendProduct>, SearchResult<Product>>() {
            @Override public SearchResult<Product> apply(@Nullable SearchResult<BackendProduct> res) {
                return convertProducts(res, categoryTree);
            }
        });
    }

    private static SearchResult<Product> convertProducts(SearchResult<BackendProduct> res, CategoryTree categoryTree) {
        return SearchResultUtil.transform(
                res,
                ProductConversion.fromBackendProducts(res.getResults(), categoryTree));
    }

    @Override public SearchRequest<Product> page(int page) {
        underlyingRequest = underlyingRequest.page(page);
        return this;
    }

    @Override public SearchRequest<Product> pageSize(int pageSize) {
        underlyingRequest = underlyingRequest.pageSize(pageSize);
        return this;
    }

    @Override public SearchRequest<Product> filter(FilterExpression filter, FilterExpression... filters) {
        underlyingRequest = underlyingRequest.filter(filter, filters);
        return this;
    }

    @Override public SearchRequest<Product> filter(Iterable<FilterExpression> filters) {
        underlyingRequest = underlyingRequest.filter(filters);
        return this;
    }

    @Override public SearchRequest<Product> facet(FacetExpression facet, FacetExpression... facets) {
        underlyingRequest = underlyingRequest.facet(facet, facets);
        return this;
    }

    @Override public SearchRequest<Product> facet(Iterable<FacetExpression> facets) {
        underlyingRequest = underlyingRequest.facet(facets);
        return this;
    }

    @Override public SearchRequest<Product> sort(ProductSort sort) {
        underlyingRequest = underlyingRequest.sort(sort);
        return this;
    }

    // testing purposes
    public SearchRequest<BackendProduct> getUnderlyingRequest() {
        return underlyingRequest;
    }

    // logging and debugging purposes
    @Override public String toString() {
        return underlyingRequest.toString();
    }
}
