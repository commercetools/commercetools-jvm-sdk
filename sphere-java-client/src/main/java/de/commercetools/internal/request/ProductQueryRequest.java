package de.commercetools.internal.request;

import com.google.common.base.Function;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import de.commercetools.internal.ProductConverter;
import de.commercetools.sphere.client.QueryRequest;
import de.commercetools.sphere.client.model.products.BackendProduct;
import de.commercetools.sphere.client.shop.CategoryTree;
import de.commercetools.sphere.client.shop.model.Product;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** Transforms results of type {@link BackendProduct} to {@link Product}. */
public class ProductQueryRequest implements QueryRequest<Product> {
    private QueryRequest<BackendProduct> underlyingRequest;
    private final CategoryTree categoryTree;

    public ProductQueryRequest(@Nonnull QueryRequest<BackendProduct> underlyingRequest, CategoryTree categoryTree) {
        if (underlyingRequest == null) throw new IllegalArgumentException("underlyingRequest can't be null");
        this.underlyingRequest = underlyingRequest;
        this.categoryTree = categoryTree;
    }

    @Override public Product fetch() {
        return ProductConverter.convertProduct(underlyingRequest.fetch(), categoryTree);
    }

    @Override public ListenableFuture<Product> fetchAsync() {
        return Futures.transform(underlyingRequest.fetchAsync(), new Function<BackendProduct, Product>() {
            @Override public Product apply(@Nullable BackendProduct product) {
                return ProductConverter.convertProduct(product, categoryTree);
            }
        });
    }

    @Override public QueryRequest<Product> page(int page) {
        underlyingRequest = underlyingRequest.page(page);
        return this;
    }

    @Override public QueryRequest<Product> pageSize(int pageSize) {
        underlyingRequest = underlyingRequest.pageSize(pageSize);
        return this;
    }

    @Override public QueryRequest<Product> expand(String... paths) {
        underlyingRequest = underlyingRequest.expand(paths);
        return this;
    }
}
