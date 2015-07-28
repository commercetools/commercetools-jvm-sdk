package io.sphere.sdk.producttypes.commands;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.commands.UpdateCommandDslBuilder;
import io.sphere.sdk.commands.UpdateCommandDslImpl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.producttypes.ProductType;

import java.util.List;

final class ProductTypeUpdateCommandImpl extends UpdateCommandDslImpl<ProductType, ProductTypeUpdateCommand> implements ProductTypeUpdateCommand {
    ProductTypeUpdateCommandImpl(final Versioned<ProductType> versioned, final List<? extends UpdateAction<ProductType>> updateActions) {
        super(versioned, updateActions, ProductTypeEndpoint.ENDPOINT, ProductTypeUpdateCommandImpl::new);
    }

    ProductTypeUpdateCommandImpl(final UpdateCommandDslBuilder<ProductType, ProductTypeUpdateCommand> builder) {
        super(builder);
    }
}
