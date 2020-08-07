package io.sphere.sdk.products.search;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Parameters to select prices in {@link ProductProjectionSearch}.
 *
 * Use {@link LocaleSelectionBuilder} or {@link LocaleSelectionDsl} to create an instance.
 *
 *
 */
public interface LocaleSelection {
    @Nullable
    List<String> getLocaleProjection();

    static LocaleSelectionDsl of(final String localeProjection)
    {
        return LocaleSelectionBuilder.of(localeProjection).build();
    }
}
