package io.sphere.sdk.discountcodes.commands;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.commands.UpdateCommandDslImpl;
import io.sphere.sdk.discountcodes.DiscountCode;
import io.sphere.sdk.models.Versioned;

import java.util.List;

import static java.util.Arrays.asList;

/**
 {@doc.gen list actions}
 */
public class DiscountCodeUpdateCommand extends UpdateCommandDslImpl<DiscountCode> {
    private DiscountCodeUpdateCommand(final Versioned<DiscountCode> versioned, final List<? extends UpdateAction<DiscountCode>> updateActions) {
        super(versioned, updateActions, DiscountCodeEndpoint.ENDPOINT);
    }

    public static DiscountCodeUpdateCommand of(final Versioned<DiscountCode> versioned, final UpdateAction<DiscountCode> updateAction) {
        return of(versioned, asList(updateAction));
    }

    public static DiscountCodeUpdateCommand of(final Versioned<DiscountCode> versioned, final List<? extends UpdateAction<DiscountCode>> updateActions) {
        return new DiscountCodeUpdateCommand(versioned, updateActions);
    }
}
