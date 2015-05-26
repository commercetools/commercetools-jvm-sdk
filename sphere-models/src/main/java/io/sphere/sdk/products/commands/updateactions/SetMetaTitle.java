package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.products.ProductUpdateScope;

import java.util.Optional;

/**
 * Sets the SEO attribute title.
 *
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandTest#setMetaTitle()}
 *
 * <p>Create update actions to set the SEO attributes title, description and keywords altogether:</p>
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandTest#setMetaAttributes()}
 */
public class SetMetaTitle extends StageableProductUpdateAction {
    private final Optional<LocalizedStrings> metaTitle;

    private SetMetaTitle(final Optional<LocalizedStrings> metaTitle, final ProductUpdateScope productUpdateScope) {
        super("setMetaTitle", productUpdateScope);
        this.metaTitle = metaTitle;
    }

    public static SetMetaTitle of(final LocalizedStrings metaTitle, final ProductUpdateScope productUpdateScope) {
        return of(Optional.of(metaTitle), productUpdateScope);
    }

    public static SetMetaTitle of(final Optional<LocalizedStrings> metaTitle, final ProductUpdateScope productUpdateScope) {
        return new SetMetaTitle(metaTitle, productUpdateScope);
    }

    public Optional<LocalizedStrings> getMetaTitle() {
        return metaTitle;
    }
}
