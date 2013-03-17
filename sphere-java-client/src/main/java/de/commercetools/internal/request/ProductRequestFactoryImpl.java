package de.commercetools.internal.request;

import de.commercetools.sphere.client.FetchRequest;
import de.commercetools.sphere.client.SearchRequest;
import de.commercetools.sphere.client.filters.expressions.FilterExpression;
import de.commercetools.sphere.client.model.QueryResult;
import de.commercetools.sphere.client.model.SearchResult;
import de.commercetools.sphere.client.model.products.BackendProduct;
import de.commercetools.sphere.client.shop.ApiMode;
import de.commercetools.sphere.client.shop.CategoryTree;
import de.commercetools.sphere.client.shop.model.Product;
import org.codehaus.jackson.type.TypeReference;

import javax.annotation.Nonnull;

/** Converts products from the raw {@link de.commercetools.sphere.client.model.products.BackendProduct} to {@link Product}. */
public class ProductRequestFactoryImpl implements ProductRequestFactory {
    private final RequestFactory underlyingRequestFactory;
    private final CategoryTree categoryTree;

    public ProductRequestFactoryImpl(@Nonnull RequestFactory underlyingRequestFactory, @Nonnull CategoryTree categoryTree) {
        if (underlyingRequestFactory == null) throw new IllegalArgumentException("underlyingRequestFactory can't be null");
        if (categoryTree == null) throw new IllegalArgumentException("categoryTree can't be null");
        this.underlyingRequestFactory = underlyingRequestFactory;
        this.categoryTree = categoryTree;
    }

    private static final TypeReference<BackendProduct> productTypeRef = new TypeReference<BackendProduct>() {};
    private static final TypeReference<QueryResult<BackendProduct>> queryProductTypeRef = new TypeReference<QueryResult<BackendProduct>>() {};
    private static final TypeReference<SearchResult<BackendProduct>> searchProductTypeRef = new TypeReference<SearchResult<BackendProduct>>() {};

    @Override public FetchRequest<Product> createFetchRequest(String url) {
        return new ProductFetchRequest(underlyingRequestFactory.createFetchRequest(url, productTypeRef), categoryTree);
    }

    @Override public FetchRequest<Product> createFetchRequestBasedOnQuery(String url) {
        return new ProductFetchRequest(underlyingRequestFactory.createFetchRequestBasedOnQuery(url, queryProductTypeRef), categoryTree);
    }

    @Override public SearchRequest<Product> createSearchRequest(String url, ApiMode apiMode, Iterable<FilterExpression> filters) {
        return new ProductSearchRequest(underlyingRequestFactory.createSearchRequest(url, apiMode, filters, searchProductTypeRef), categoryTree);
    }
}
