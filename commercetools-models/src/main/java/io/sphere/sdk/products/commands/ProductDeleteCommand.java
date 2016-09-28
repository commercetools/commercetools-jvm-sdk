package io.sphere.sdk.products.commands;

import io.sphere.sdk.commands.DeleteCommand;
import io.sphere.sdk.expansion.MetaModelReferenceExpansionDsl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.expansion.ProductExpansionModel;

/**
 {@include.example io.sphere.sdk.products.ProductDeleteExample#delete()}
 */
public interface ProductDeleteCommand extends MetaModelReferenceExpansionDsl<Product, ProductDeleteCommand, ProductExpansionModel<Product>>, DeleteCommand<Product> {

    static ProductDeleteCommand of(final Versioned<Product> versioned) {
        return new ProductDeleteCommandImpl(versioned);
    }

    static ProductDeleteCommand ofKey(final String key, final Long version) {
        final Versioned<Product> versioned = Versioned.of("key=" + key, version);//hack for simple reuse
        return of(versioned);
    }
}
