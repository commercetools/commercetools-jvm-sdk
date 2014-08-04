package io.sphere.sdk.products;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Optional;
import io.sphere.sdk.models.DefaultModel;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.taxcategories.TaxCategory;

/**
 * A sellable good.
 *
 * Use {@link io.sphere.sdk.products.ProductBuilder} to create an instance for unit tests.
 */
@JsonDeserialize(as=ProductImpl.class)
public interface Product extends DefaultModel<Product> {

    Reference<ProductType> getProductType();

    ProductCatalogData getMasterData();

    Optional<Reference<TaxCategory>> getTaxCategory();

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

    public static Reference<Product> reference(final Product category) {
        return new Reference<>(typeId(), category.getId(), Optional.ofNullable(category));
    }

    public static Optional<Reference<Product>> reference(final Optional<Product> category) {
        return category.map(Product::reference);
    }

    public static Reference<Product> reference(final String id) {
        return Reference.of(typeId(), id);
    }
}
