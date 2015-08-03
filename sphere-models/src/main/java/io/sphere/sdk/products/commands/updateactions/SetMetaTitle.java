package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.products.Product;

import javax.annotation.Nullable;

/**
 * Sets the SEO attribute title.
 *
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandTest#setMetaTitle()}
 *
 * <p>Create update actions to set the SEO attributes title, description and keywords altogether:</p>
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandTest#setMetaAttributes()}
 */
public class SetMetaTitle extends UpdateActionImpl<Product> {
    @Nullable
    private final LocalizedStrings metaTitle;

    private SetMetaTitle(@Nullable final LocalizedStrings metaTitle) {
        super("setMetaTitle");
        this.metaTitle = metaTitle;
    }

    public static SetMetaTitle of(@Nullable final LocalizedStrings metaTitle) {
        return new SetMetaTitle(metaTitle);
    }

    @Nullable
    public LocalizedStrings getMetaTitle() {
        return metaTitle;
    }
}
