package io.sphere.sdk.discountcodes.commands;

import io.sphere.sdk.commands.DeleteCommand;
import io.sphere.sdk.discountcodes.DiscountCode;
import io.sphere.sdk.discountcodes.expansion.DiscountCodeExpansionModel;
import io.sphere.sdk.expansion.MetaModelReferenceExpansionDsl;
import io.sphere.sdk.models.Versioned;

public interface DiscountCodeDeleteCommand extends MetaModelReferenceExpansionDsl<DiscountCode, DiscountCodeDeleteCommand, DiscountCodeExpansionModel<DiscountCode>>, DeleteCommand<DiscountCode> {
    static DiscountCodeDeleteCommand of(final Versioned<DiscountCode> versioned) {
        return new DiscountCodeDeleteCommandImpl(versioned);
    }
}
