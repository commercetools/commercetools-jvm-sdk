package io.sphere.sdk.producttypes.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.producttypes.ProductType;

import java.util.List;

public final class ChangeAttributeOrderByName extends UpdateActionImpl<ProductType> {
    private final List<String> attributeNames;

    private ChangeAttributeOrderByName(final List<String> attributeNames) {
        super("changeAttributeOrderByName");
        this.attributeNames = attributeNames;
    }

    public static ChangeAttributeOrderByName of(final List<String> attributeNames){
        return new ChangeAttributeOrderByName(attributeNames);
    }

    public List<String> getAttributeNames() {
        return attributeNames;
    }
}
