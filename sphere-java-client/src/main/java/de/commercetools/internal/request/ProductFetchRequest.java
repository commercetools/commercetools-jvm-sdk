package de.commercetools.internal.request;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import de.commercetools.internal.ProductConversion;
import de.commercetools.sphere.client.FetchRequest;
import de.commercetools.sphere.client.model.products.BackendProduct;
import de.commercetools.sphere.client.shop.CategoryTree;
import de.commercetools.sphere.client.shop.model.Product;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.ExecutionException;

/** Transforms results of type {@link BackendProduct} to {@link Product}. */
public class ProductFetchRequest implements FetchRequest<Product> {
    private FetchRequest<BackendProduct> underlyingRequest;
    private final CategoryTree categoryTree;

    public ProductFetchRequest(@Nonnull FetchRequest<BackendProduct> underlyingRequest, CategoryTree categoryTree) {
        if (underlyingRequest == null) throw new IllegalArgumentException("underlyingRequest can't be null");
        this.underlyingRequest = underlyingRequest;
        this.categoryTree = categoryTree;
    }

    @Override public Optional<Product> fetch() {
        try {
            return fetchAsync().get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
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

    @Override public String getUrl() {
        return underlyingRequest.getUrl();
    }
}
