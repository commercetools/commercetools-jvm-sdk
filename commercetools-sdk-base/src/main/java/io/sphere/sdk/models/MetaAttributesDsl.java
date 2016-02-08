package io.sphere.sdk.models;

import javax.annotation.Nullable;

public interface MetaAttributesDsl extends MetaAttributes {
    MetaAttributesDsl withTitle(@Nullable final LocalizedString title);

    MetaAttributesDsl withDescription(@Nullable final LocalizedString description);

    MetaAttributesDsl withKeywords(@Nullable final LocalizedString keywords);

    static MetaAttributesDsl of() {
        return new MetaAttributesDslImpl(null, null, null);
    }
}
