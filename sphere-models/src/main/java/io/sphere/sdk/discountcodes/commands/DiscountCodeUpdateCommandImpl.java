package io.sphere.sdk.discountcodes.commands;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.commands.UpdateCommandDslBuilder;
import io.sphere.sdk.commands.UpdateCommandDslImpl;
import io.sphere.sdk.discountcodes.DiscountCode;
import io.sphere.sdk.models.Versioned;

import java.util.List;


final class DiscountCodeUpdateCommandImpl extends UpdateCommandDslImpl<DiscountCode, DiscountCodeUpdateCommand> implements DiscountCodeUpdateCommand {
    DiscountCodeUpdateCommandImpl(final Versioned<DiscountCode> versioned, final List<? extends UpdateAction<DiscountCode>> updateActions) {
        super(versioned, updateActions, DiscountCodeEndpoint.ENDPOINT, DiscountCodeUpdateCommandImpl::new);
    }

    DiscountCodeUpdateCommandImpl(final UpdateCommandDslBuilder<DiscountCode, DiscountCodeUpdateCommand> builder) {
        super(builder);
    }
}
