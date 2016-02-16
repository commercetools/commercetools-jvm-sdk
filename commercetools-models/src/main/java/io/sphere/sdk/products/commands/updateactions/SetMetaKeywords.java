package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.products.Product;

import javax.annotation.Nullable;

/**
 * Sets the SEO attribute keywords.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandIntegrationTest#setMetaKeywords()}
 *  <p>Create update actions to set the SEO attributes title, description and keywords altogether:</p>
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandIntegrationTest#setMetaAttributes()}
 */
public final class SetMetaKeywords extends UpdateActionImpl<Product> {
    @Nullable
    private final LocalizedString metaKeywords;

    private SetMetaKeywords(@Nullable final LocalizedString metaKeywords) {
        super("setMetaKeywords");
        this.metaKeywords = metaKeywords;
    }

    public static SetMetaKeywords of(@Nullable final LocalizedString metaKeywords) {
        return new SetMetaKeywords(metaKeywords);
    }

    @Nullable
    public LocalizedString getMetaKeywords() {
        return metaKeywords;
    }
}
