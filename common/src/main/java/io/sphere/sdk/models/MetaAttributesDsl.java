package io.sphere.sdk.models;

import java.util.Optional;

public interface MetaAttributesDsl extends MetaAttributes {
    public MetaAttributesDsl withTitle(final Optional<LocalizedString> title);

    public default MetaAttributesDsl withTitle(final LocalizedString title) {
        return withTitle(Optional.of(title));
    }

    public MetaAttributesDsl withDescription(final Optional<LocalizedString> description);

    public default MetaAttributesDsl withDescription(final LocalizedString description) {
        return withDescription(Optional.of(description));
    }

    public MetaAttributesDsl withKeywords(final Optional<LocalizedString> keywords);

    public default MetaAttributesDsl withKeywords(final LocalizedString keywords) {
        return withKeywords(Optional.of(keywords));
    }

    public static MetaAttributesDsl of() {
        return new MetaAttributesDslImpl(Optional.empty(), Optional.empty(), Optional.empty());
    }
}
