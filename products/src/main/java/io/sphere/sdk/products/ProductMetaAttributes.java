package io.sphere.sdk.products;

import com.google.common.base.Optional;
import io.sphere.sdk.models.LocalizedString;

public class ProductMetaAttributes {
    private final Optional<LocalizedString> metaTitle;
    private final Optional<LocalizedString> metaDescription;
    private final Optional<LocalizedString> metaKeywords;

    public ProductMetaAttributes(final Optional<LocalizedString> metaTitle, final Optional<LocalizedString> metaDescription, final Optional<LocalizedString> metaKeywords) {
        this.metaTitle = metaTitle;
        this.metaDescription = metaDescription;
        this.metaKeywords = metaKeywords;
    }

    public Optional<LocalizedString> getMetaTitle() {
        return metaTitle;
    }

    public Optional<LocalizedString> getMetaDescription() {
        return metaDescription;
    }

    public Optional<LocalizedString> getMetaKeywords() {
        return metaKeywords;
    }
}
