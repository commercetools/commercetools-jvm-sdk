package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.products.Product;

import javax.annotation.Nullable;

/**
 * Sets the SEO attribute description.
 *
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandTest#setMetaDescription()}
 *  <p>Create update actions to set the SEO attributes title, description and keywords altogether:</p>
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandTest#setMetaAttributes()}
 */
public class SetMetaDescription extends UpdateAction<Product> {
    @Nullable
    private final LocalizedStrings metaDescription;

    private SetMetaDescription(final LocalizedStrings metaDescription) {
        super("setMetaDescription");
        this.metaDescription = metaDescription;
    }

    public static SetMetaDescription of(@Nullable final LocalizedStrings metaDescription) {
        return new SetMetaDescription(metaDescription);
    }

    @Nullable
    public LocalizedStrings getMetaDescription() {
        return metaDescription;
    }
}
