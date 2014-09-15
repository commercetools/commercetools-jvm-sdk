package io.sphere.sdk.productdiscounts;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.models.DefaultModel;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;

import java.util.List;
import java.util.Optional;

@JsonDeserialize(as=ProductDiscountImpl.class)
public interface ProductDiscount extends DefaultModel<ProductDiscount> {

    public LocalizedString getName();

    public Optional<LocalizedString> getDescription();

    public ProductDiscountValue getValue();

    public String getPredicate();

    public String getSortOrder();

    public boolean isActive();

    public List<Reference<Object>> getReferences();

    public static String typeId(){
        return "product-discount";
    }
}
