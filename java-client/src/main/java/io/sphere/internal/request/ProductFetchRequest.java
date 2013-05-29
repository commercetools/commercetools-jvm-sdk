package io.sphere.internal.request;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import io.sphere.internal.ProductConversion;
import io.sphere.client.FetchRequest;
import io.sphere.client.SearchRequest;
import io.sphere.client.model.products.BackendProduct;
import io.sphere.client.shop.CategoryTree;
import io.sphere.client.shop.model.Product;
import io.sphere.internal.util.Util;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.ExecutionException;

/** Transforms results of type {@link BackendProduct} to {@link Product}. */
public class ProductFetchRequest implements FetchRequest<Product>  {
    private FetchRequest<BackendProduct> underlyingRequest;
    private final CategoryTree categoryTree;

    public ProductFetchRequest(@Nonnull FetchRequest<BackendProduct> underlyingRequest, CategoryTree categoryTree) {
        if (underlyingRequest == null) throw new NullPointerException("underlyingRequest");
        this.underlyingRequest = underlyingRequest;
        this.categoryTree = categoryTree;
    }

    @Override public Optional<Product> fetch() {
        return Util.sync(fetchAsync());
    }

    @Override public ListenableFuture<Optional<Product>> fetchAsync() {
        return Futures.transform(underlyingRequest.fetchAsync(), new Function<Optional<BackendProduct>, Optional<Product>>() {
            @Override public Optional<Product> apply(@Nullable Optional<BackendProduct> backendProduct) {
                assert backendProduct != null;
                if (!backendProduct.isPresent()) return Optional.absent();
                return Optional.of(ProductConversion.fromBackendProduct(backendProduct.get(), categoryTree));
            }
        });
    }

    @Override public ProductFetchRequest expand(String... paths) {
        underlyingRequest = underlyingRequest.expand(paths);
        return this;
    }

    // testing purposes
    public FetchRequest<BackendProduct> getUnderlyingRequest() {
        return underlyingRequest;
    }

    // logging and debugging purposes
    @Override public String toString() {
        return underlyingRequest.toString();
    }
}
