package io.sphere.sdk.products;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Optional;
import io.sphere.sdk.models.DefaultModel;
import io.sphere.sdk.models.Reference;

/**

 <p>A sellable good.</p>

 <ul>
    <li>Create a product in SPHERE.IO with {@link io.sphere.sdk.products.commands.ProductCreateCommand}.</li>
    <li>Create a product test double with {@link io.sphere.sdk.products.ProductBuilder}.</li>
    <li>Query a product with {@link io.sphere.sdk.products.queries.ProductQuery}.</li>
    <li>Update a product with {@link io.sphere.sdk.products.commands.ProductUpdateCommand}.</li>
    <li>Delete a product with {@link io.sphere.sdk.products.commands.ProductDeleteByIdCommand}.</li>
 </ul>

 @see io.sphere.sdk.products.ProductProjection
 @see io.sphere.sdk.categories.Category
 */
@JsonDeserialize(as=ProductImpl.class)
public interface Product extends ProductLike<Product>, DefaultModel<Product> {

    ProductCatalogData getMasterData();

    public static TypeReference<Product> typeReference(){
        return new TypeReference<Product>() {
            @Override
            public String toString() {
                return "TypeReference<Product>";
            }
        };
    }

    @Override
    default Reference<Product> toReference() {
        return reference(this);
    }

    public static String typeId(){
        return "product";
    }

    public static Reference<Product> reference(final Product product) {
        return new Reference<>(typeId(), product.getId(), Optional.ofNullable(product));
    }

    public static Optional<Reference<Product>> reference(final Optional<Product> category) {
        return category.map(Product::reference);
    }

    public static Reference<Product> reference(final String id) {
        return Reference.of(typeId(), id);
    }

    default Optional<ProductProjection> toProjection(final ProductProjectionType productProjectionType) {
        return getMasterData().get(productProjectionType)
                .map(productData -> new ProductToProductProjectionWrapper(this, productProjectionType));
    }
}
