package io.sphere.sdk.selection;

import javax.annotation.Nullable;

public interface LocaleSelectionRequestDsl<T> {
    T withLocaleSelection(@Nullable final LocaleSelection localeSelection);

    @Nullable
    LocaleSelection getLocaleSelection();
}
