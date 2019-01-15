package io.sphere.sdk.customobjects.queries;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Reference;

public class MyCustomClass extends Base {

    private String id;
    private String description;
    private Reference<Cart> cartReference;

    public MyCustomClass(String id, String description, Reference<Cart> cartReference) {
        this.id = id;
        this.description = description;
        this.cartReference = cartReference;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Reference<Cart> getCartReference() {
        return cartReference;
    }

    public void setCartReference(Reference<Cart> cartReference) {
        this.cartReference = cartReference;
    }
}
