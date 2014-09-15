package io.sphere.sdk.models;

import java.util.Optional;

class MetaAttributesImpl extends Base implements MetaAttributes {

    private final Optional<LocalizedString> metaTitle;
    private final Optional<LocalizedString> metaDescription;
    private final Optional<LocalizedString> metaKeywords;

    private MetaAttributesImpl(final Optional<LocalizedString> metaTitle, final Optional<LocalizedString> metaDescription, final Optional<LocalizedString> metaKeywords) {
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

    public static MetaAttributes of(final Optional<LocalizedString> metaTitle, final Optional<LocalizedString> metaDescription, final Optional<LocalizedString> metaKeywords) {
        return new MetaAttributesImpl(metaTitle, metaDescription, metaKeywords);
    }
}
