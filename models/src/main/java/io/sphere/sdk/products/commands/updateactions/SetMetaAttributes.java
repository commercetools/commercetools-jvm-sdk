package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.models.MetaAttributes;
import io.sphere.sdk.products.ProductUpdateScope;

import java.util.Optional;

/**
 * Sets the SEO attributes title, description and key words.
 *
 * {@include.example io.sphere.sdk.products.ProductCrudIntegrationTest#setMetaAttributesUpdateAction()}
 */
public class SetMetaAttributes extends StageableProductUpdateAction implements MetaAttributes {
    private final Optional<LocalizedStrings> metaTitle;

    private final Optional<LocalizedStrings> metaDescription;

    private final Optional<LocalizedStrings> metaKeywords;

    private SetMetaAttributes(final MetaAttributes metaAttributes, final ProductUpdateScope productUpdateScope) {
        super("setMetaAttributes", productUpdateScope);
        metaTitle = metaAttributes.getMetaTitle();
        metaDescription = metaAttributes.getMetaDescription();
        metaKeywords = metaAttributes.getMetaKeywords();
    }

    @Override
    public Optional<LocalizedStrings> getMetaTitle() {
        return metaTitle;
    }

    @Override
    public Optional<LocalizedStrings> getMetaDescription() {
        return metaDescription;
    }

    @Override
    public Optional<LocalizedStrings> getMetaKeywords() {
        return metaKeywords;
    }

    public static SetMetaAttributes of(final MetaAttributes metaAttributes, final ProductUpdateScope productUpdateScope) {
        return new SetMetaAttributes(metaAttributes, productUpdateScope);
    }
}
