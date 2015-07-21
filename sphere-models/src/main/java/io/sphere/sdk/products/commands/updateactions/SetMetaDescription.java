package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.products.ProductUpdateScope;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * Sets the SEO attribute description.
 *
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandTest#setMetaDescription()}
 *  <p>Create update actions to set the SEO attributes title, description and keywords altogether:</p>
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandTest#setMetaAttributes()}
 */
public class SetMetaDescription extends StageableProductUpdateAction {
    @Nullable
    private final LocalizedStrings metaDescription;

    private SetMetaDescription(final LocalizedStrings metaDescription, final ProductUpdateScope productUpdateScope) {
        super("setMetaDescription", productUpdateScope);
        this.metaDescription = metaDescription;
    }

    public static SetMetaDescription of(@Nullable final LocalizedStrings metaDescription, final ProductUpdateScope productUpdateScope) {
        return new SetMetaDescription(metaDescription, productUpdateScope);
    }

    @Nullable
    public LocalizedStrings getMetaDescription() {
        return metaDescription;
    }
}
