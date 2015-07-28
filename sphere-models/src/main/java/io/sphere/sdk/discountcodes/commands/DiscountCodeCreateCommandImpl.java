package io.sphere.sdk.discountcodes.commands;

import io.sphere.sdk.commands.CreateCommandImpl;
import io.sphere.sdk.discountcodes.DiscountCode;
import io.sphere.sdk.discountcodes.DiscountCodeDraft;

final class DiscountCodeCreateCommandImpl extends CreateCommandImpl<DiscountCode, DiscountCodeDraft> implements DiscountCodeCreateCommand {

    DiscountCodeCreateCommandImpl(final DiscountCodeDraft draft) {
        super(draft, DiscountCodeEndpoint.ENDPOINT);
    }
}
