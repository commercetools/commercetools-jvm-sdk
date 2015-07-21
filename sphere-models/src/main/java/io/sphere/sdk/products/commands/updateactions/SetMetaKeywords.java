package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.products.ProductUpdateScope;

import javax.annotation.Nullable;

/**
 * Sets the SEO attribute keywords.
 *
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandTest#setMetaKeywords()}
 *  <p>Create update actions to set the SEO attributes title, description and keywords altogether:</p>
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandTest#setMetaAttributes()}
 */
public class SetMetaKeywords extends StageableProductUpdateAction {
    @Nullable
    private final LocalizedStrings metaKeywords;

    private SetMetaKeywords(@Nullable final LocalizedStrings metaKeywords, final ProductUpdateScope productUpdateScope) {
        super("setMetaKeywords", productUpdateScope);
        this.metaKeywords = metaKeywords;
    }

    public static SetMetaKeywords of(@Nullable final LocalizedStrings metaKeywords, final ProductUpdateScope productUpdateScope) {
        return new SetMetaKeywords(metaKeywords, productUpdateScope);
    }

    public LocalizedStrings getMetaKeywords() {
        return metaKeywords;
    }
}
