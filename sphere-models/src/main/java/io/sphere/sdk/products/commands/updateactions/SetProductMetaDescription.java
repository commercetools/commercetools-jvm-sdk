package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.products.ProductUpdateScope;

import java.util.Optional;

/**
 * Sets the SEO attribute description.
 *
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandTest#setMetaDescription()}
 *  <p>Create update actions to set the SEO attributes title, description and keywords altogether:</p>
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandTest#setMetaAttributes()}
 */
public class SetProductMetaDescription extends StageableProductUpdateAction {
    private final Optional<LocalizedStrings> metaDescription;

    private SetProductMetaDescription(final Optional<LocalizedStrings> metaDescription, final ProductUpdateScope productUpdateScope) {
        super("setMetaDescription", productUpdateScope);
        this.metaDescription = metaDescription;
    }

    public static SetProductMetaDescription of(final LocalizedStrings metaDescription, final ProductUpdateScope productUpdateScope) {
        return of(Optional.of(metaDescription), productUpdateScope);
    }

    public static SetProductMetaDescription of(final Optional<LocalizedStrings> metaDescription, final ProductUpdateScope productUpdateScope) {
        return new SetProductMetaDescription(metaDescription, productUpdateScope);
    }

    public Optional<LocalizedStrings> getMetaDescription() {
        return metaDescription;
    }
}
