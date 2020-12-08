package io.sphere.sdk.products.commands;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.commands.UpdateCommandDsl;
import io.sphere.sdk.expansion.MetaModelReferenceExpansionDsl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.expansion.ProductExpansionModel;
import io.sphere.sdk.products.search.PriceSelectionRequestDsl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static io.sphere.sdk.client.SphereRequestUtils.urlEncode;

/**

 {@doc.gen list actions}

 */
public interface ProductUpdateCommand extends UpdateCommandDsl<Product, ProductUpdateCommand>, MetaModelReferenceExpansionDsl<Product, ProductUpdateCommand, ProductExpansionModel<Product>>, PriceSelectionRequestDsl<ProductUpdateCommand> {

    static ProductUpdateCommand of(final Versioned<Product> versioned, final UpdateAction<Product> updateAction, final UpdateAction<Product>... updateActions) {
        final List<UpdateAction<Product>> actions = new ArrayList<>();
        actions.add(updateAction);
        actions.addAll(Arrays.asList(updateActions));
        return of(versioned, actions);
    }

    static ProductUpdateCommand of(final Versioned<Product> versioned, final List<? extends UpdateAction<Product>> updateActions) {
        return new ProductUpdateCommandImpl(versioned, updateActions);
    }

    static ProductUpdateCommand ofKey(final String key, final Long version, final List<? extends UpdateAction<Product>> updateActions) {
        final Versioned<Product> versioned = Versioned.of("key=" + urlEncode(key), version);//hack for simple reuse
        return of(versioned, updateActions);
    }

    static ProductUpdateCommand ofKey(final String key, final Long version, final UpdateAction<Product> updateAction) {
        return ofKey(key, version, Collections.singletonList(updateAction));
    }
}
