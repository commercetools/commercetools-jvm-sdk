package io.sphere.sdk.selection;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.utils.SphereInternalUtils;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class LocaleSelectionDsl extends Base implements LocaleSelection {
    @Nullable
    private List<String> localeProjection;

    LocaleSelectionDsl(@Nullable List<String> localeProjection) {
        this.localeProjection = localeProjection;
    }

    public static LocaleSelectionDsl of(final String localeSelection) {
        return LocaleSelectionBuilder.of(localeSelection).build();
    }

    public LocaleSelectionDsl withLocaleProjection(@Nullable final List<String> localeProjection) {
        return LocaleSelectionBuilder.of(localeProjection).build();
    }

    public LocaleSelectionDsl withLocaleProjection(@Nullable final String localeProjection) {
        return LocaleSelectionBuilder.of(localeProjection).build();
    }

    public LocaleSelectionDsl plusLocaleProjection(@Nullable final String localeProjection) {
        this.localeProjection =  SphereInternalUtils.listOf(Optional.ofNullable(this.localeProjection).orElseGet(ArrayList::new), localeProjection);
        return this;
    }

    public LocaleSelectionDsl plusLocaleProjection1(@Nullable final String localeProjection) {
        this.localeProjection =  SphereInternalUtils.listOf(Optional.ofNullable(this.localeProjection).orElseGet(ArrayList::new), localeProjection);
        return this;
    }

    public LocaleSelectionDsl plusLocaleProjection(final List<String> localeProjection) {
        this.localeProjection =  SphereInternalUtils.listOf(Optional.ofNullable(this.localeProjection).orElseGet(ArrayList::new), localeProjection);
        return this;
    }

    @Nullable
    @Override
    public List<String> getLocaleProjection() {
        return localeProjection;
    }
}
