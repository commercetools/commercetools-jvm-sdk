package io.sphere.sdk.models;

import javax.annotation.Nullable;
import java.util.Locale;

public interface MetaAttributes {
    @Nullable
    LocalizedStrings getMetaTitle();

    @Nullable
    LocalizedStrings getMetaDescription();

    @Nullable
    LocalizedStrings getMetaKeywords();

    static MetaAttributes metaAttributesOf(@Nullable final LocalizedStrings metaTitle, @Nullable final LocalizedStrings metaDescription, @Nullable final LocalizedStrings metaKeywords) {
        return new MetaAttributesDslImpl(metaTitle, metaDescription, metaKeywords);
    }

    static MetaAttributes metaAttributesOf(final Locale locale, final String metaTitle, final String metaDescription, final String metaKeywords) {
        return MetaAttributesDsl.of()
                .withTitle(LocalizedStrings.of(locale, metaTitle))
                .withDescription(LocalizedStrings.of(locale, metaDescription))
                .withKeywords(LocalizedStrings.of(locale, metaKeywords));
    }
}
