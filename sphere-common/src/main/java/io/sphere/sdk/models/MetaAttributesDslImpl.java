package io.sphere.sdk.models;

import java.util.Optional;

class MetaAttributesDslImpl implements MetaAttributesDsl {

    private final Optional<LocalizedStrings> metaTitle;
    private final Optional<LocalizedStrings> metaDescription;
    private final Optional<LocalizedStrings> metaKeywords;

    MetaAttributesDslImpl(final Optional<LocalizedStrings> metaTitle, final Optional<LocalizedStrings> metaDescription, final Optional<LocalizedStrings> metaKeywords) {
        this.metaTitle = metaTitle;
        this.metaDescription = metaDescription;
        this.metaKeywords = metaKeywords;
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

    @Override
    public MetaAttributesDsl withTitle(final Optional<LocalizedStrings> title) {
        return new MetaAttributesDslImpl(title, metaDescription, metaKeywords);
    }

    @Override
    public MetaAttributesDsl withDescription(final Optional<LocalizedStrings> description) {
        return new MetaAttributesDslImpl(metaTitle, description, metaKeywords);
    }

    @Override
    public MetaAttributesDsl withKeywords(final Optional<LocalizedStrings> keywords) {
        return new MetaAttributesDslImpl(metaTitle, metaDescription, keywords);
    }
}