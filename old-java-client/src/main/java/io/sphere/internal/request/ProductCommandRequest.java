package io.sphere.internal.request;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import io.sphere.client.CommandRequest;
import io.sphere.client.SphereResult;
import io.sphere.client.exceptions.SphereBackendException;
import io.sphere.client.exceptions.SphereException;
import io.sphere.client.model.products.BackendProduct;
import io.sphere.client.shop.ApiMode;
import io.sphere.client.shop.CategoryTree;
import io.sphere.client.shop.model.Product;
import io.sphere.internal.ProductConversion;
import io.sphere.internal.util.Util;
import com.google.common.base.Function;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

public class ProductCommandRequest implements CommandRequest<Product> {
    private CommandRequest<BackendProduct> underlyingRequest;
    private final CategoryTree categoryTree;
    private final ApiMode apiMode;
    final @Nullable Function<SphereBackendException, SphereException> transformError;


    public ProductCommandRequest(
            @Nonnull CategoryTree categoryTree, 
            @Nonnull CommandRequest<BackendProduct> underlyingRequest, 
            @Nonnull ApiMode apiMode) {
       this(categoryTree, underlyingRequest, apiMode, null);
    }

    public ProductCommandRequest(
            @Nonnull CategoryTree categoryTree,
            @Nonnull CommandRequest<BackendProduct> underlyingRequest,
            @Nonnull ApiMode apiMode, 
            Function<SphereBackendException, SphereException> transformError) {
        if (underlyingRequest == null) throw new NullPointerException("underlyingRequest");
        this.underlyingRequest = underlyingRequest;
        this.categoryTree = categoryTree;
        this.apiMode = apiMode;
        this.transformError = transformError;
    }

    @Override
    public Product execute() { return Util.syncResult(executeAsync()); }

    @Override
    public ListenableFuture<SphereResult<Product>> executeAsync() {
        CommandRequest<BackendProduct> request;
        if (transformError != null) request = underlyingRequest.withErrorHandling(transformError);
        else request = underlyingRequest;
        
        return Futures.transform(request.executeAsync(), new Function<SphereResult<BackendProduct>, SphereResult<Product>>() {
            @Override
            public SphereResult<Product> apply(SphereResult<BackendProduct> backendProductResult) {
                assert backendProductResult != null;
                return backendProductResult.transform(new Function<BackendProduct, Product>() {
                    @Override
                    public Product apply(BackendProduct backendProduct) {
                        assert backendProduct != null;
                        return ProductConversion.fromBackendProduct(backendProduct, categoryTree, apiMode);
                    }
                });
            }
        });   
    }

    @Override
    public CommandRequest<Product> withErrorHandling(@Nonnull Function<SphereBackendException, SphereException> transformError) {
        return new ProductCommandRequest(categoryTree, underlyingRequest, apiMode, transformError);
    }
}
