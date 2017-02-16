package io.sphere.sdk.carts.commands.updateactions;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.shoppinglists.ShoppingList;

import javax.annotation.Nullable;

/**
 * Adds all line items of a shopping list to the cart. If a channel is provided, it will be set for all line items that are added to the cart.
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
                            @Nullable final Reference<Channel> supplyChannel, @Nullable final Reference<Channel> distributionChannel) {
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
        return new AddShoppingList(shoppingList, null, null);
    }

    public AddShoppingList withSupplyChannel(@Nullable final Reference<Channel> supplyChannel) {
        return new AddShoppingList(getShoppingList(), supplyChannel, getDistributionChannel());
    }


    public AddShoppingList withDistributionChannel(@Nullable final Reference<Channel> distributionChannel) {
        return new AddShoppingList(getShoppingList(), getSupplyChannel(), distributionChannel);
    }
}
