package io.sphere.sdk.discountcodes.commands;

import io.sphere.sdk.commands.MetaModelByIdDeleteCommandBuilder;
import io.sphere.sdk.commands.MetaModelByIdDeleteCommandImpl;
import io.sphere.sdk.discountcodes.DiscountCode;
import io.sphere.sdk.discountcodes.expansion.DiscountCodeExpansionModel;
import io.sphere.sdk.models.Versioned;

final class DiscountCodeDeleteCommandImpl extends MetaModelByIdDeleteCommandImpl<DiscountCode, DiscountCodeDeleteCommand, DiscountCodeExpansionModel<DiscountCode>> implements DiscountCodeDeleteCommand {
    DiscountCodeDeleteCommandImpl(final Versioned<DiscountCode> versioned) {
        super(versioned, DiscountCodeEndpoint.ENDPOINT, DiscountCodeExpansionModel.of(), DiscountCodeDeleteCommandImpl::new);
    }

    DiscountCodeDeleteCommandImpl(final MetaModelByIdDeleteCommandBuilder<DiscountCode, DiscountCodeDeleteCommand, DiscountCodeExpansionModel<DiscountCode>> builder) {
        super(builder);
    }
}
