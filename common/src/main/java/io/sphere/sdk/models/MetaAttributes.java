package io.sphere.sdk.models;

import java.util.Locale;
import java.util.Optional;

public interface MetaAttributes {
    public Optional<LocalizedString> getMetaTitle();

    public Optional<LocalizedString> getMetaDescription();

    public Optional<LocalizedString> getMetaKeywords();

    public static MetaAttributes of(final Optional<LocalizedString> metaTitle, final Optional<LocalizedString> metaDescription, final Optional<LocalizedString> metaKeywords) {
        return new MetaAttributesDslImpl(metaTitle, metaDescription, metaKeywords);
    }

    public static MetaAttributes of(final Locale locale, final String metaTitle, final String metaDescription, final String metaKeywords) {
        return MetaAttributesDsl.of()
                .withTitle(LocalizedString.of(locale, metaTitle))
                .withDescription(LocalizedString.of(locale, metaDescription))
                .withKeywords(LocalizedString.of(locale, metaKeywords));
    }
}
