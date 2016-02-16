package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.products.Product;

import javax.annotation.Nullable;

/**
 * Sets the SEO attribute description.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandIntegrationTest#setMetaDescription()}
 *  <p>Create update actions to set the SEO attributes title, description and keywords altogether:</p>
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandIntegrationTest#setMetaAttributes()}
 */
public final class SetMetaDescription extends UpdateActionImpl<Product> {
    @Nullable
    private final LocalizedString metaDescription;

    private SetMetaDescription(@Nullable final LocalizedString metaDescription) {
        super("setMetaDescription");
        this.metaDescription = metaDescription;
    }

    public static SetMetaDescription of(@Nullable final LocalizedString metaDescription) {
        return new SetMetaDescription(metaDescription);
    }

    @Nullable
    public LocalizedString getMetaDescription() {
        return metaDescription;
    }
}
