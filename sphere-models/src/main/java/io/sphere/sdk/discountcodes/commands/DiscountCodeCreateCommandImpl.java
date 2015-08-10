package io.sphere.sdk.discountcodes.commands;

import io.sphere.sdk.commands.MetaModelCreateCommandBuilder;
import io.sphere.sdk.commands.MetaModelCreateCommandImpl;
import io.sphere.sdk.discountcodes.DiscountCode;
import io.sphere.sdk.discountcodes.DiscountCodeDraft;
import io.sphere.sdk.discountcodes.expansion.DiscountCodeExpansionModel;

final class DiscountCodeCreateCommandImpl extends MetaModelCreateCommandImpl<DiscountCode, DiscountCodeCreateCommand, DiscountCodeDraft, DiscountCodeExpansionModel<DiscountCode>> implements DiscountCodeCreateCommand {
    DiscountCodeCreateCommandImpl(final MetaModelCreateCommandBuilder<DiscountCode, DiscountCodeCreateCommand, DiscountCodeDraft, DiscountCodeExpansionModel<DiscountCode>> builder) {
        super(builder);
    }

    DiscountCodeCreateCommandImpl(final DiscountCodeDraft draft) {
        super(draft, DiscountCodeEndpoint.ENDPOINT, DiscountCodeExpansionModel.of(), DiscountCodeCreateCommandImpl::new);
    }
}
