package io.sphere.sdk.discountcodes.commands;

import io.sphere.sdk.commands.*;
import io.sphere.sdk.discountcodes.DiscountCode;
import io.sphere.sdk.discountcodes.expansion.DiscountCodeExpansionModel;
import io.sphere.sdk.models.Versioned;

import java.util.List;


final class DiscountCodeUpdateCommandImpl extends MetaModelUpdateCommandDslImpl<DiscountCode, DiscountCodeUpdateCommand, DiscountCodeExpansionModel<DiscountCode>> implements DiscountCodeUpdateCommand {
    DiscountCodeUpdateCommandImpl(final Versioned<DiscountCode> versioned, final List<? extends UpdateAction<DiscountCode>> updateActions) {
        super(versioned, updateActions, DiscountCodeEndpoint.ENDPOINT, DiscountCodeUpdateCommandImpl::new, DiscountCodeExpansionModel.of());
    }

    DiscountCodeUpdateCommandImpl(final MetaModelUpdateCommandDslBuilder<DiscountCode, DiscountCodeUpdateCommand, DiscountCodeExpansionModel<DiscountCode>> builder) {
        super(builder);
    }
}
