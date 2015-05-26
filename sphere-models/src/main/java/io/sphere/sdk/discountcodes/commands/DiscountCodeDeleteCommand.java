package io.sphere.sdk.discountcodes.commands;

import io.sphere.sdk.commands.ByIdDeleteCommandImpl;
import io.sphere.sdk.commands.DeleteCommand;
import io.sphere.sdk.discountcodes.DiscountCode;
import io.sphere.sdk.models.Versioned;

public class DiscountCodeDeleteCommand extends ByIdDeleteCommandImpl<DiscountCode> {
    private DiscountCodeDeleteCommand(final Versioned<DiscountCode> versioned) {
        super(versioned, DiscountCodeEndpoint.ENDPOINT);
    }

    public static DeleteCommand<DiscountCode> of(final Versioned<DiscountCode> versioned) {
        return new DiscountCodeDeleteCommand(versioned);
    }
}
