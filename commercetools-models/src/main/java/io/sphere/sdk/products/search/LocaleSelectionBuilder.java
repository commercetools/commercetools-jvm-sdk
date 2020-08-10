package io.sphere.sdk.products.search;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Builder;

import javax.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public final class LocaleSelectionBuilder extends Base implements Builder<LocaleSelectionDsl> {
    @Nullable
    private List <String> localeProjection = new ArrayList<>();

    LocaleSelectionBuilder localeProjection (@Nullable final List<String> localeProjection) {
        this.localeProjection = localeProjection;
        return this;
    }

    LocaleSelectionBuilder localeProjection(@Nullable final String localeProjection) {
        this.localeProjection = new ArrayList<>();
        this.localeProjection.add(localeProjection);
        return this;
    }

    public LocaleSelectionBuilder plusLocaleProjection(@Nullable final String localeProjection) {
        if (this.localeProjection == null) {
            this.localeProjection = new ArrayList<>();
        }
        this.localeProjection.add(localeProjection);
        return this;
    }

    public static LocaleSelectionBuilder of(@Nullable final String localeProjection) {
        return new LocaleSelectionBuilder();
    }

    @Nullable
    public List<String> getLocaleProjection() {
        return localeProjection;
    }

    @Override
    public LocaleSelectionDsl build() {
        return new LocaleSelectionDsl(localeProjection);
    }
}
