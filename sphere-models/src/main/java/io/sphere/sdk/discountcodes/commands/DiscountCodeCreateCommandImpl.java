package io.sphere.sdk.discountcodes.commands;

import io.sphere.sdk.commands.CreateCommandImpl;
import io.sphere.sdk.discountcodes.DiscountCode;
import io.sphere.sdk.discountcodes.DiscountCodeDraft;

/**
 * {@include.example io.sphere.sdk.discountcodes.commands.DiscountCodeCreateCommandTest#execution()}
 */
public class DiscountCodeCreateCommandImpl extends CreateCommandImpl<DiscountCode, DiscountCodeDraft> {

    private DiscountCodeCreateCommandImpl(final DiscountCodeDraft draft) {
        super(draft, DiscountCodeEndpoint.ENDPOINT);
    }

    public static DiscountCodeCreateCommandImpl of(final DiscountCodeDraft draft) {
        return new DiscountCodeCreateCommandImpl(draft);
    }
}
