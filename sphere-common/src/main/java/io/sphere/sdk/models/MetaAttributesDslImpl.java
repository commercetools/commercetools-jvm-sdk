package io.sphere.sdk.models;

import javax.annotation.Nullable;

class MetaAttributesDslImpl implements MetaAttributesDsl {

    @Nullable
    private final LocalizedString metaTitle;
    @Nullable
    private final LocalizedString metaDescription;
    @Nullable
    private final LocalizedString metaKeywords;

    MetaAttributesDslImpl(@Nullable final LocalizedString metaTitle, @Nullable final LocalizedString metaDescription, @Nullable final LocalizedString metaKeywords) {
        this.metaTitle = metaTitle;
        this.metaDescription = metaDescription;
        this.metaKeywords = metaKeywords;
    }

    @Nullable
    @Override
    public LocalizedString getMetaTitle() {
        return metaTitle;
    }

    @Nullable
    @Override
    public LocalizedString getMetaDescription() {
        return metaDescription;
    }

    @Nullable
    @Override
    public LocalizedString getMetaKeywords() {
        return metaKeywords;
    }

    @Override
    public MetaAttributesDsl withTitle(@Nullable final LocalizedString title) {
        return new MetaAttributesDslImpl(title, metaDescription, metaKeywords);
    }

    @Override
    public MetaAttributesDsl withDescription(@Nullable final LocalizedString description) {
        return new MetaAttributesDslImpl(metaTitle, description, metaKeywords);
    }

    @Override
    public MetaAttributesDsl withKeywords(@Nullable final LocalizedString keywords) {
        return new MetaAttributesDslImpl(metaTitle, metaDescription, keywords);
    }
}