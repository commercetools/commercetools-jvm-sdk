package io.sphere.sdk.producttypes;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Optional;
import io.sphere.sdk.models.DefaultModel;
import io.sphere.sdk.models.Reference;

/** Describes common characteristics, most importantly common custom attributes, of many concrete products.

 <p id=operations>Operations:</p>
 <ul>
    <li>Create a product type with {@link io.sphere.sdk.producttypes.commands.ProductTypeCreateCommand}.</li>
    <li>Create a product type test double with {@link io.sphere.sdk.producttypes.ProductTypeBuilder}.</li>
    <li>Query a product type with {@link io.sphere.sdk.producttypes.queries.ProductTypeQuery}.</li>
    <li>Delete a product type with {@link io.sphere.sdk.producttypes.commands.ProductTypeDeleteByIdCommand}.</li>
 </ul>

 */
@JsonDeserialize(as=ProductTypeImpl.class)
public interface ProductType extends DefaultModel<ProductType>, AttributeDefinitionContainer {

    String getName();

    String getDescription();

    @Override
    default Reference<ProductType> toReference() {
        return reference(this);
    }

    public static String typeId(){
        return "product-type";
    }

    public static Reference<ProductType> reference(final ProductType productType) {
        return new Reference<>(typeId(), productType.getId(), Optional.ofNullable(productType));
    }

    public static Optional<Reference<ProductType>> reference(final Optional<ProductType> category) {
        return category.map(ProductType::reference);
    }

    public static Reference<ProductType> reference(final String id) {
        return Reference.of(typeId(), id);
    }

    public static TypeReference<ProductType> typeReference() {
        return new TypeReference<ProductType>(){
            @Override
            public String toString() {
                return "TypeReference<ProductType>";
            }
        };
    }
}
