package io.sphere.sdk.producttypes;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Optional;
import io.sphere.sdk.models.DefaultModel;
import io.sphere.sdk.models.Reference;

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
