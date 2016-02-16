package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.products.Product;

import javax.annotation.Nullable;

/**
 * Sets the SEO attribute title.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandIntegrationTest#setMetaTitle()}
 *
 * <p>Create update actions to set the SEO attributes title, description and keywords altogether:</p>
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandIntegrationTest#setMetaAttributes()}
 */
public final class SetMetaTitle extends UpdateActionImpl<Product> {
    @Nullable
    private final LocalizedString metaTitle;

    private SetMetaTitle(@Nullable final LocalizedString metaTitle) {
        super("setMetaTitle");
        this.metaTitle = metaTitle;
    }

    public static SetMetaTitle of(@Nullable final LocalizedString metaTitle) {
        return new SetMetaTitle(metaTitle);
    }

    @Nullable
    public LocalizedString getMetaTitle() {
        return metaTitle;
    }
}
