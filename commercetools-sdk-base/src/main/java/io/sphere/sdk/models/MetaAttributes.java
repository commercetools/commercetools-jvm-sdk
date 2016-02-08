package io.sphere.sdk.models;

import javax.annotation.Nullable;
import java.util.Locale;

public interface MetaAttributes {
    @Nullable
    LocalizedString getMetaTitle();

    @Nullable
    LocalizedString getMetaDescription();

    @Nullable
    LocalizedString getMetaKeywords();

    static MetaAttributesDsl metaAttributesOf(@Nullable final LocalizedString metaTitle, @Nullable final LocalizedString metaDescription, @Nullable final LocalizedString metaKeywords) {
        return new MetaAttributesDslImpl(metaTitle, metaDescription, metaKeywords);
    }

    static MetaAttributesDsl metaAttributesOf(final Locale locale, final String metaTitle, final String metaDescription, final String metaKeywords) {
        return MetaAttributesDsl.of()
                .withTitle(LocalizedString.of(locale, metaTitle))
                .withDescription(LocalizedString.of(locale, metaDescription))
                .withKeywords(LocalizedString.of(locale, metaKeywords));
    }
}
