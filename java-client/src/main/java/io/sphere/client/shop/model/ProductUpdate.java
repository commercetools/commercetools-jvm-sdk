package io.sphere.client.shop.model;

import io.sphere.internal.command.ProductCommands;
import io.sphere.internal.command.Update;

/** 
 * ProductUpdate is used to update a product in the backend. 
 * Every action can update either the staged product or both current and staged.
 * */
public class ProductUpdate extends Update<ProductCommands.ProductUpdateAction> {
    
    /** Sets the attribute in the given variant. */
    public ProductUpdate setAttribute(int variantId, Attribute attribute, boolean staged) {
        add(new ProductCommands.SetAttribute(variantId, attribute.getName(), attribute.getValue(), staged));
        return this;
    }

    /** Removes the attribute from the given variant. */
    public ProductUpdate removeAttribute(int variantId, String attributeName, boolean staged) {
        add(new ProductCommands.SetAttribute(variantId, attributeName, null, staged));
        return this;
    }
    
}
