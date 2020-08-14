package io.sphere.sdk.selection;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Builder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class LocaleSelectionBuilder extends Base implements Builder<LocaleSelectionDsl> {
    @Nullable
    private List <String> localeProjection;

    LocaleSelectionBuilder(@Nullable final List<String> localeProjection) {
        this.localeProjection = localeProjection;
    }

    public static LocaleSelectionBuilder of(final String localeProjection) {
        return new LocaleSelectionBuilder(Collections.singletonList(localeProjection));
    }

    public static LocaleSelectionBuilder of(final List<String> localeProjection) {
        return new LocaleSelectionBuilder(localeProjection);
    }

    public LocaleSelectionBuilder localeProjection(@Nullable final String localeProjection) {
        this.localeProjection = new ArrayList<>();
        this.localeProjection.add(localeProjection);
        return this;
    }

    public LocaleSelectionBuilder localeProjection(@Nullable final List<String> localeProjection) {
        this.localeProjection = new ArrayList<>();
        this.localeProjection.addAll(localeProjection);
        return this;
    }

    public LocaleSelectionBuilder plusLocaleProjection(@Nullable final String localeProjection) {
        if (this.localeProjection == null) {
            this.localeProjection = new ArrayList<>();
        }
        this.localeProjection.add(localeProjection);
        return this;
    }

    public LocaleSelectionBuilder plusLocaleProjection(@Nullable final List<String> localeProjection) {
        this.localeProjection.addAll(localeProjection);
        return this;
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
