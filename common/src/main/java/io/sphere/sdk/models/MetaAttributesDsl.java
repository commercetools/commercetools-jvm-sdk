package io.sphere.sdk.models;

import java.util.Optional;

public interface MetaAttributesDsl extends MetaAttributes {
    public MetaAttributesDsl withTitle(final Optional<LocalizedStrings> title);

    public default MetaAttributesDsl withTitle(final LocalizedStrings title) {
        return withTitle(Optional.of(title));
    }

    public MetaAttributesDsl withDescription(final Optional<LocalizedStrings> description);

    public default MetaAttributesDsl withDescription(final LocalizedStrings description) {
        return withDescription(Optional.of(description));
    }

    public MetaAttributesDsl withKeywords(final Optional<LocalizedStrings> keywords);

    public default MetaAttributesDsl withKeywords(final LocalizedStrings keywords) {
        return withKeywords(Optional.of(keywords));
    }

    public static MetaAttributesDsl of() {
        return new MetaAttributesDslImpl(Optional.empty(), Optional.empty(), Optional.empty());
    }
}
