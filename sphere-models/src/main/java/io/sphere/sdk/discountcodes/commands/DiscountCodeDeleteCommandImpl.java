package io.sphere.sdk.discountcodes.commands;

import io.sphere.sdk.commands.ByIdDeleteCommandImpl;
import io.sphere.sdk.discountcodes.DiscountCode;
import io.sphere.sdk.models.Versioned;

final class DiscountCodeDeleteCommandImpl extends ByIdDeleteCommandImpl<DiscountCode> implements DiscountCodeDeleteCommand {
    DiscountCodeDeleteCommandImpl(final Versioned<DiscountCode> versioned) {
        super(versioned, DiscountCodeEndpoint.ENDPOINT);
    }
}
