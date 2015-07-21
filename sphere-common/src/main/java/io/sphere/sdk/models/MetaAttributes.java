package io.sphere.sdk.models;

import java.util.Locale;
import java.util.Optional;

public interface MetaAttributes {
    Optional<LocalizedStrings> getMetaTitle();

    Optional<LocalizedStrings> getMetaDescription();

    Optional<LocalizedStrings> getMetaKeywords();

    static MetaAttributes metaAttributesOf(final Optional<LocalizedStrings> metaTitle, final Optional<LocalizedStrings> metaDescription, final Optional<LocalizedStrings> metaKeywords) {
        return new MetaAttributesDslImpl(metaTitle, metaDescription, metaKeywords);
    }

    static MetaAttributes metaAttributesOf(final Locale locale, final String metaTitle, final String metaDescription, final String metaKeywords) {
        return MetaAttributesDsl.of()
                .withTitle(LocalizedStrings.of(locale, metaTitle))
                .withDescription(LocalizedStrings.of(locale, metaDescription))
                .withKeywords(LocalizedStrings.of(locale, metaKeywords));
    }
}
