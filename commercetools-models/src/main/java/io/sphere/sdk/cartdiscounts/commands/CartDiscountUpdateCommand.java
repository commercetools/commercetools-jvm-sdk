package io.sphere.sdk.cartdiscounts.commands;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.cartdiscounts.expansion.CartDiscountExpansionModel;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.commands.UpdateCommandDsl;
import io.sphere.sdk.expansion.MetaModelReferenceExpansionDsl;
import io.sphere.sdk.models.Versioned;

import java.util.Collections;
import java.util.List;

/**
 {@doc.gen list actions}
 */
public interface CartDiscountUpdateCommand extends UpdateCommandDsl<CartDiscount, CartDiscountUpdateCommand>, MetaModelReferenceExpansionDsl<CartDiscount, CartDiscountUpdateCommand, CartDiscountExpansionModel<CartDiscount>> {
    static CartDiscountUpdateCommand of(final Versioned<CartDiscount> versioned, final UpdateAction<CartDiscount> updateAction) {
        return of(versioned, Collections.singletonList(updateAction));
    }

    static CartDiscountUpdateCommand of(final Versioned<CartDiscount> versioned, final List<? extends UpdateAction<CartDiscount>> updateActions) {
        return new CartDiscountUpdateCommandImpl(versioned, updateActions);
    }
}
