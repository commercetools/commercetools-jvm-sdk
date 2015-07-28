package io.sphere.sdk.discountcodes.commands;

import io.sphere.sdk.commands.CreateCommand;
import io.sphere.sdk.discountcodes.DiscountCode;
import io.sphere.sdk.discountcodes.DiscountCodeDraft;

/**
 * {@include.example io.sphere.sdk.discountcodes.commands.DiscountCodeCreateCommandTest#execution()}
 */
public interface DiscountCodeCreateCommand extends CreateCommand<DiscountCode> {

    static DiscountCodeCreateCommand of(final DiscountCodeDraft draft) {
        return new DiscountCodeCreateCommandImpl(draft);
    }
}
