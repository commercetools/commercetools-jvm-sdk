package io.sphere.sdk.models;

import javax.annotation.Nullable;

public interface MetaAttributesDsl extends MetaAttributes {
    MetaAttributesDsl withTitle(@Nullable final LocalizedStrings title);

    MetaAttributesDsl withDescription(@Nullable final LocalizedStrings description);

    MetaAttributesDsl withKeywords(@Nullable final LocalizedStrings keywords);

    static MetaAttributesDsl of() {
        return new MetaAttributesDslImpl(null, null, null);
    }
}
