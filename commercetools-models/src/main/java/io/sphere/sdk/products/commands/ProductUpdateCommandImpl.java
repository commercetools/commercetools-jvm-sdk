package io.sphere.sdk.products.commands;

import io.sphere.sdk.commands.MetaModelUpdateCommandDslBuilder;
import io.sphere.sdk.commands.MetaModelUpdateCommandDslImpl;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.http.NameValuePair;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.expansion.ProductExpansionModel;
import io.sphere.sdk.products.search.PriceSelection;

import javax.annotation.Nullable;
import java.util.List;

import static io.sphere.sdk.products.search.PriceSelectionQueryParameters.extractPriceSelectionFromHttpQueryParameters;
import static io.sphere.sdk.products.search.PriceSelectionQueryParameters.getQueryParametersWithPriceSelection;

final class ProductUpdateCommandImpl extends MetaModelUpdateCommandDslImpl<Product, ProductUpdateCommand, ProductExpansionModel<Product>> implements ProductUpdateCommand {
    ProductUpdateCommandImpl(final Versioned<Product> versioned, final List<? extends UpdateAction<Product>> updateActions) {
        super(versioned, updateActions, ProductEndpoint.ENDPOINT, ProductUpdateCommandImpl::new, ProductExpansionModel.of());
    }

    ProductUpdateCommandImpl(final MetaModelUpdateCommandDslBuilder<Product, ProductUpdateCommand, ProductExpansionModel<Product>> builder) {
        super(builder);
    }

    @Override
    public ProductUpdateCommand withPriceSelection(@Nullable final PriceSelection priceSelection) {
        final List<NameValuePair> resultingParameters = getQueryParametersWithPriceSelection(priceSelection, additionalHttpQueryParameters());
        return withAdditionalHttpQueryParameters(resultingParameters);
    }

    @Nullable
    @Override
    public PriceSelection getPriceSelection() {
        return extractPriceSelectionFromHttpQueryParameters(additionalHttpQueryParameters());
    }
}
