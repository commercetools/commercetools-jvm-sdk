package io.sphere.sdk.models;

import java.util.Optional;

public interface MetaAttributes {
    public Optional<LocalizedString> getMetaTitle();

    public Optional<LocalizedString> getMetaDescription();

    public Optional<LocalizedString> getMetaKeywords();

    public static MetaAttributes of(final Optional<LocalizedString> metaTitle, final Optional<LocalizedString> metaDescription, final Optional<LocalizedString> metaKeywords) {
        return MetaAttributesImpl.of(metaTitle, metaDescription, metaKeywords);
    }
}
