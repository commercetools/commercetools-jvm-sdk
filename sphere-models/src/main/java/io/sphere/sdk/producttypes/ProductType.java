package io.sphere.sdk.producttypes;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.models.Resource;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.products.attributes.AttributeDefinition;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

/** Describes common characteristics, most importantly common custom attributes, of many concrete products.

 <p id=operations>Operations:</p>
 <ul>
    <li>Create a product type with {@link io.sphere.sdk.producttypes.commands.ProductTypeCreateCommand}.</li>
    <li>Query a product type with {@link io.sphere.sdk.producttypes.queries.ProductTypeQuery}.</li>
    <li>Delete a product type with {@link io.sphere.sdk.producttypes.commands.ProductTypeDeleteCommand}.</li>
 </ul>

 */
@JsonDeserialize(as=ProductTypeImpl.class)
public interface ProductType extends Resource<ProductType>, AttributeDefinitionContainer {

    String getName();

    String getDescription();

    @Override
    List<AttributeDefinition> getAttributes();

    @Nullable
    default AttributeDefinition getAttribute(final String attributeName) {
        return AttributeDefinitionContainer.super.getAttribute(attributeName);
    }

    @Override
    default Optional<AttributeDefinition> findAttribute(final String attributeName) {
        return AttributeDefinitionContainer.super.findAttribute(attributeName);
    }

    @Override
    default Reference<ProductType> toReference() {
        return reference(this);
    }

    static String referenceTypeId(){
        return "product-type";
    }

    /**
     *
     * @deprecated use {@link #referenceTypeId()} instead
     * @return referenceTypeId
     */
    @Deprecated
    static String typeId(){
        return "product-type";
    }

    static Reference<ProductType> reference(final ProductType productType) {
        return Reference.of(referenceTypeId(), productType.getId(), productType);
    }

    static Reference<ProductType> reference(final String id) {
        return Reference.of(referenceTypeId(), id);
    }

    static TypeReference<ProductType> typeReference() {
        return new TypeReference<ProductType>(){
            @Override
            public String toString() {
                return "TypeReference<ProductType>";
            }
        };
    }
}
