package io.sphere.sdk.discountcodes.commands;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.commands.UpdateCommandDsl;
import io.sphere.sdk.discountcodes.DiscountCode;
import io.sphere.sdk.models.Versioned;

import java.util.List;

import static java.util.Arrays.asList;

/**
 {@doc.gen list actions}
 */
public interface DiscountCodeUpdateCommand extends UpdateCommandDsl<DiscountCode, DiscountCodeUpdateCommand> {
    static DiscountCodeUpdateCommand of(final Versioned<DiscountCode> versioned, final UpdateAction<DiscountCode> updateAction) {
        return of(versioned, asList(updateAction));
    }

    static DiscountCodeUpdateCommand of(final Versioned<DiscountCode> versioned, final List<? extends UpdateAction<DiscountCode>> updateActions) {
        return new DiscountCodeUpdateCommandImpl(versioned, updateActions);
    }
}
