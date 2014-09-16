package io.sphere.sdk.models;

import java.util.Optional;

class MetaAttributesDslImpl implements MetaAttributesDsl {

    private final Optional<LocalizedString> metaTitle;
    private final Optional<LocalizedString> metaDescription;
    private final Optional<LocalizedString> metaKeywords;

    MetaAttributesDslImpl(final Optional<LocalizedString> metaTitle, final Optional<LocalizedString> metaDescription, final Optional<LocalizedString> metaKeywords) {
        this.metaTitle = metaTitle;
        this.metaDescription = metaDescription;
        this.metaKeywords = metaKeywords;
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

    @Override
    public MetaAttributesDsl withTitle(final Optional<LocalizedString> title) {
        return new MetaAttributesDslImpl(title, metaDescription, metaKeywords);
    }

    @Override
    public MetaAttributesDsl withDescription(final Optional<LocalizedString> description) {
        return new MetaAttributesDslImpl(metaTitle, description, metaKeywords);
    }

    @Override
    public MetaAttributesDsl withKeywords(final Optional<LocalizedString> keywords) {
        return new MetaAttributesDslImpl(metaTitle, metaDescription, keywords);
    }
}