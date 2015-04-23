package io.sphere.sdk.carts;

import java.util.Set;

public interface LineItemLike {
    String getId();

    Set<ItemState> getState();

    long getQuantity();
}
