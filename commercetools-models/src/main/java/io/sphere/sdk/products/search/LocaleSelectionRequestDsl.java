package io.sphere.sdk.products.search;

import javax.annotation.Nullable;

public interface LocaleSelectionRequestDsl<T> {
    T withLocaleSelection(@Nullable final LocaleSelection localeSelection);

    @Nullable
    LocaleSelection getLocaleSelection();
}
