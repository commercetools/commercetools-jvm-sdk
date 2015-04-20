package io.sphere.sdk.models;

import java.util.Locale;
import java.util.Optional;

public interface MetaAttributes {
    public Optional<LocalizedStrings> getMetaTitle();

    public Optional<LocalizedStrings> getMetaDescription();

    public Optional<LocalizedStrings> getMetaKeywords();

    public static MetaAttributes metaAttributesOf(final Optional<LocalizedStrings> metaTitle, final Optional<LocalizedStrings> metaDescription, final Optional<LocalizedStrings> metaKeywords) {
        return new MetaAttributesDslImpl(metaTitle, metaDescription, metaKeywords);
    }

    public static MetaAttributes metaAttributesOf(final Locale locale, final String metaTitle, final String metaDescription, final String metaKeywords) {
        return MetaAttributesDsl.of()
                .withTitle(LocalizedStrings.of(locale, metaTitle))
                .withDescription(LocalizedStrings.of(locale, metaDescription))
                .withKeywords(LocalizedStrings.of(locale, metaKeywords));
    }
}
