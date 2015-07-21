package io.sphere.sdk.models;

import javax.annotation.Nullable;

class MetaAttributesDslImpl implements MetaAttributesDsl {

    @Nullable
    private final LocalizedStrings metaTitle;
    @Nullable
    private final LocalizedStrings metaDescription;
    @Nullable
    private final LocalizedStrings metaKeywords;

    MetaAttributesDslImpl(@Nullable final LocalizedStrings metaTitle, @Nullable final LocalizedStrings metaDescription, @Nullable final LocalizedStrings metaKeywords) {
        this.metaTitle = metaTitle;
        this.metaDescription = metaDescription;
        this.metaKeywords = metaKeywords;
    }

    @Override
    public LocalizedStrings getMetaTitle() {
        return metaTitle;
    }

    @Override
    public LocalizedStrings getMetaDescription() {
        return metaDescription;
    }

    @Override
    public LocalizedStrings getMetaKeywords() {
        return metaKeywords;
    }

    @Override
    public MetaAttributesDsl withTitle(@Nullable final LocalizedStrings title) {
        return new MetaAttributesDslImpl(title, metaDescription, metaKeywords);
    }

    @Override
    public MetaAttributesDsl withDescription(@Nullable final LocalizedStrings description) {
        return new MetaAttributesDslImpl(metaTitle, description, metaKeywords);
    }

    @Override
    public MetaAttributesDsl withKeywords(@Nullable final LocalizedStrings keywords) {
        return new MetaAttributesDslImpl(metaTitle, metaDescription, keywords);
    }
}