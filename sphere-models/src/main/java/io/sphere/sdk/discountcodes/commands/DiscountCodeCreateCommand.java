package io.sphere.sdk.discountcodes.commands;

import io.sphere.sdk.commands.CreateCommandImpl;
import io.sphere.sdk.discountcodes.DiscountCode;
import io.sphere.sdk.discountcodes.DiscountCodeDraft;

/**
 * {@include.example io.sphere.sdk.discountcodes.commands.DiscountCodeCreateCommandTest#execution()}
 */
public class DiscountCodeCreateCommand extends CreateCommandImpl<DiscountCode, DiscountCodeDraft> {

    private DiscountCodeCreateCommand(final DiscountCodeDraft draft) {
        super(draft, DiscountCodeEndpoint.ENDPOINT);
    }

    public static DiscountCodeCreateCommand of(final DiscountCodeDraft draft) {
        return new DiscountCodeCreateCommand(draft);
    }
}
