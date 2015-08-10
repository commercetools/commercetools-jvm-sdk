package io.sphere.sdk.discountcodes.commands;

import io.sphere.sdk.commands.ReferenceExpandeableCreateCommandBuilder;
import io.sphere.sdk.commands.ReferenceExpandeableCreateCommandImpl;
import io.sphere.sdk.discountcodes.DiscountCode;
import io.sphere.sdk.discountcodes.DiscountCodeDraft;
import io.sphere.sdk.discountcodes.expansion.DiscountCodeExpansionModel;

final class DiscountCodeCreateCommandImpl extends ReferenceExpandeableCreateCommandImpl<DiscountCode, DiscountCodeCreateCommand, DiscountCodeDraft, DiscountCodeExpansionModel<DiscountCode>> implements DiscountCodeCreateCommand {
    DiscountCodeCreateCommandImpl(final ReferenceExpandeableCreateCommandBuilder<DiscountCode, DiscountCodeCreateCommand, DiscountCodeDraft, DiscountCodeExpansionModel<DiscountCode>> builder) {
        super(builder);
    }

    DiscountCodeCreateCommandImpl(final DiscountCodeDraft draft) {
        super(draft, DiscountCodeEndpoint.ENDPOINT, DiscountCodeExpansionModel.of(), DiscountCodeCreateCommandImpl::new);
    }
}
