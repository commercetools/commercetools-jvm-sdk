package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.MetaAttributes;

import java.util.Optional;

/**
 * Sets the SEO attributes title, description and key words.
 *
 * {@include.example products.ProductCrudIntegrationTest#setMetaAttributesUpdateAction()}
 */
public class SetMetaAttributes extends StageableProductUpdateAction implements MetaAttributes {
    private final Optional<LocalizedString> metaTitle;

    private final Optional<LocalizedString> metaDescription;

    private final Optional<LocalizedString> metaKeywords;

    private SetMetaAttributes(final MetaAttributes metaAttributes, final boolean staged) {
        super("setMetaAttributes", staged);
        metaTitle = metaAttributes.getMetaTitle();
        metaDescription = metaAttributes.getMetaDescription();
        metaKeywords = metaAttributes.getMetaKeywords();
    }

    @Override
    public Optional<LocalizedString> getMetaTitle() {
        return metaTitle;
    }

    @Override
    public Optional<LocalizedString> getMetaDescription() {
        return metaDescription;
    }

    @Override
    public Optional<LocalizedString> getMetaKeywords() {
        return metaKeywords;
    }

    public static SetMetaAttributes of(final MetaAttributes metaAttributes, final boolean staged) {
        return new SetMetaAttributes(metaAttributes, staged);
    }

    public static SetMetaAttributes of(final MetaAttributes metaAttributes) {
        return of(metaAttributes, true);
    }
}
