package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.products.ProductUpdateScope;

import java.util.Optional;

/**
 * Sets the SEO attribute keywords.
 *
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandTest#setMetaKeywords()}
 *  <p>Create update actions to set the SEO attributes title, description and keywords altogether:</p>
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandTest#setMetaAttributes()}
 */
public class SetMetaKeywords extends StageableProductUpdateAction {
    private final Optional<LocalizedStrings> metaKeywords;

    private SetMetaKeywords(final Optional<LocalizedStrings> metaKeywords, final ProductUpdateScope productUpdateScope) {
        super("setMetaKeywords", productUpdateScope);
        this.metaKeywords = metaKeywords;
    }

    public static SetMetaKeywords of(final LocalizedStrings metaKeywords, final ProductUpdateScope productUpdateScope) {
        return of(Optional.of(metaKeywords), productUpdateScope);
    }

    public static SetMetaKeywords of(final Optional<LocalizedStrings> metaKeywords, final ProductUpdateScope productUpdateScope) {
        return new SetMetaKeywords(metaKeywords, productUpdateScope);
    }

    public Optional<LocalizedStrings> getMetaKeywords() {
        return metaKeywords;
    }
}
