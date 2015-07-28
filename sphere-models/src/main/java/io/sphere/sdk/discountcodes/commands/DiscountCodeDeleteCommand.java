package io.sphere.sdk.discountcodes.commands;

import io.sphere.sdk.commands.ByIdDeleteCommand;
import io.sphere.sdk.commands.DeleteCommand;
import io.sphere.sdk.discountcodes.DiscountCode;
import io.sphere.sdk.models.Versioned;

public interface DiscountCodeDeleteCommand extends ByIdDeleteCommand<DiscountCode> {
    static DeleteCommand<DiscountCode> of(final Versioned<DiscountCode> versioned) {
        return new DiscountCodeDeleteCommandImpl(versioned);
    }
}
