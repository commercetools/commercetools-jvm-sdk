package io.sphere.sdk.carts.commands.updateactions;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.CustomLineItem;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.commands.UpdateActionImpl;

import javax.money.MonetaryAmount;

/**
 Sets the monetary amount of the given custom line item.

 {@doc.gen intro}

 {@include.example io.sphere.sdk.carts.commands.CartUpdateCommandIntegrationTest#changeCustomLineItemQuantity()}

 @see CustomLineItem#getMoney()
 */
public final class ChangeCustomLineItemMoney extends UpdateActionImpl<Cart> {
    private final String customLineItemId;
    private final MonetaryAmount money;

    private ChangeCustomLineItemMoney(final String customLineItemId, final MonetaryAmount money) {
        super("changeCustomLineItemMoney");
        this.customLineItemId = customLineItemId;
        this.money = money;
    }

    public String getCustomLineItemId() {
        return customLineItemId;
    }

    public MonetaryAmount getMoney() {
        return money;
    }

    public static ChangeCustomLineItemMoney of(final String lineItemId, final MonetaryAmount money) {
        return new ChangeCustomLineItemMoney(lineItemId, money);
    }

    public static UpdateAction<Cart> of(final CustomLineItem lineItem, final MonetaryAmount money) {
        return of(lineItem.getId(), money);
    }
}
