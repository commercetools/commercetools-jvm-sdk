package io.sphere.sdk.selection;

import javax.annotation.Nullable;
import java.util.List;

public interface LocaleSelection {
    @Nullable
    List<String> getLocaleProjection();

    static LocaleSelectionDsl of(final String localeProjection)
    {
        return LocaleSelectionBuilder.of(localeProjection).build();
    }

    static LocaleSelectionDsl of(final List<String> localeProjection)
    {
        return LocaleSelectionBuilder.of(localeProjection).build();
    }
}
