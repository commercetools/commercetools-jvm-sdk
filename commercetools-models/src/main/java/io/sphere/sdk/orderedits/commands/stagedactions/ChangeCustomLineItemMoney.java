package io.sphere.sdk.orderedits.commands.stagedactions;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.money.MonetaryAmount;

public final class ChangeCustomLineItemMoney extends OrderEditStagedUpdateActionBase {

    private final String customLineItemId;
    private final MonetaryAmount money;

    @JsonCreator
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

    public static ChangeCustomLineItemMoney of(final String customLineItemId, final MonetaryAmount money) {
        return new ChangeCustomLineItemMoney(customLineItemId, money);
    }
}
