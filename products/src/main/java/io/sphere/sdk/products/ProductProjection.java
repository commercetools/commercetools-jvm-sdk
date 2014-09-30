package io.sphere.sdk.products;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;

/**
  A projected representation of a product shows the product with its current or staged data.

  {@link io.sphere.sdk.products.ProductProjection}s are views of {@link io.sphere.sdk.products.Product}s.

 <p>To create, update or delete {@link io.sphere.sdk.products.ProductProjection}s you have to use the
 {@link io.sphere.sdk.products.Product} lifecycle classes.</p>

 <p id=operations>Operations:</p>
 <ul>
    <li>Fetch by ID with {@link io.sphere.sdk.products.queries.FetchProductProjectionById}</li>
 </ul>

 If you need {@link io.sphere.sdk.products.ProductProjection} for a method but you have a product,
 you can transform a product into a product projection:

 {@include.example io.sphere.sdk.products.ProductProjectionTest#transformProductIntoProductProjection()}

 {@link ProductProjection#toReference()} returns not are reference to a {@link io.sphere.sdk.products.ProductProjection} but to a {@link io.sphere.sdk.products.Product}.

 */
@JsonDeserialize(as=ProductProjectionImpl.class)
public interface ProductProjection extends ProductLike<ProductProjection>, ProductDataLike, Referenceable<Product> {

    public boolean hasStagedChanges();

    public boolean isPublished();

    @Override
    default Reference<Product> toReference() {
        return Product.reference(getId());
    }

    public static TypeReference<ProductProjection> typeReference(){
        return new TypeReference<ProductProjection>() {
            @Override
            public String toString() {
                return "TypeReference<ProductProjection>";
            }
        };
    }
}
