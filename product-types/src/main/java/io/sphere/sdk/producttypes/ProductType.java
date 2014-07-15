package io.sphere.sdk.producttypes;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Optional;
import io.sphere.sdk.models.DefaultModel;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.producttypes.attributes.AttributeDefinition;

import java.util.List;

@JsonDeserialize(as=ProductTypeImpl.class)
public interface ProductType extends DefaultModel<ProductType> {

    String getName();

    String getDescription();

    List<AttributeDefinition> getAttributes();

    @Override
    default Reference<ProductType> toReference() {
        return reference(this);
    }

    public static String typeId(){
        return "product-type";
    }

    public static ProductTypeQuery query() {
        return new ProductTypeQuery();
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
}
