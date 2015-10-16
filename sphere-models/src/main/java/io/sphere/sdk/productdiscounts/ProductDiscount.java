package io.sphere.sdk.productdiscounts;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Resource;
import io.sphere.sdk.models.Reference;

import javax.annotation.Nullable;
import java.util.List;

@JsonDeserialize(as=ProductDiscountImpl.class)
public interface ProductDiscount extends Resource<ProductDiscount> {

    LocalizedString getName();

    @Nullable
    LocalizedString getDescription();

    ProductDiscountValue getValue();

    String getPredicate();

    String getSortOrder();

    Boolean isActive();

    List<Reference<JsonNode>> getReferences();

    static String referenceTypeId(){
        return "product-discount";
    }

    /**
     *
     * @deprecated use {@link #referenceTypeId()} instead
     * @return referenceTypeId
     */
    @Deprecated
    static String typeId(){
        return "product-discount";
    }

    static TypeReference<ProductDiscount> typeReference(){
        return new TypeReference<ProductDiscount>() {
            @Override
            public String toString() {
                return "TypeReference<ProductDiscount>";
            }
        };
    }
}
