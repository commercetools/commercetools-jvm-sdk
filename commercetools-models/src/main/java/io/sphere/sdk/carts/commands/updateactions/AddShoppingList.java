package io.sphere.sdk.carts.commands.updateactions;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.shoppinglists.ShoppingList;

import javax.annotation.Nullable;

/**
 * Adds a shopping list to a cart.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.carts.commands.CartUpdateCommandIntegrationTest#addShoppingList()}
 */
public final class AddShoppingList extends UpdateActionImpl<Cart> {
    private final Reference<ShoppingList> shoppingList;
    @Nullable
    private final Reference<Channel> supplyChannel;
    @Nullable
    private final Reference<Channel> distributionChannel;

    private AddShoppingList(final Reference<ShoppingList> shoppingList,
                            final @Nullable Reference<Channel> supplyChannel, final @Nullable Reference<Channel> distributionChannel) {
        super("addShoppingList");
        this.shoppingList = shoppingList;
        this.supplyChannel = supplyChannel;
        this.distributionChannel = distributionChannel;
    }

    public Reference<ShoppingList> getShoppingList() {
        return shoppingList;
    }

    @Nullable
    public Reference<Channel> getSupplyChannel() {
        return supplyChannel;
    }

    @Nullable
    public Reference<Channel> getDistributionChannel() {
        return distributionChannel;
    }


    public static AddShoppingList of(final Reference<ShoppingList> shoppingList) {
        return of(shoppingList, null, null);
    }

    public static AddShoppingList of(final Reference<ShoppingList> shoppingList,
                                     final @Nullable Reference<Channel> supplyChannel, final @Nullable Reference<Channel> distributionChannel) {
        return new AddShoppingList(shoppingList, supplyChannel, distributionChannel);
    }
}
