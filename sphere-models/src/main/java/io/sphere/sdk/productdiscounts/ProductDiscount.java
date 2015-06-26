package io.sphere.sdk.productdiscounts;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.models.DefaultModel;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.models.Reference;

import java.util.List;
import java.util.Optional;

@JsonDeserialize(as=ProductDiscountImpl.class)
public interface ProductDiscount extends DefaultModel<ProductDiscount> {

    LocalizedStrings getName();

    Optional<LocalizedStrings> getDescription();

    ProductDiscountValue getValue();

    String getPredicate();

    String getSortOrder();

    boolean isActive();

    List<Reference<Object>> getReferences();

    static String typeId(){
        return "product-discount";
    }
}
