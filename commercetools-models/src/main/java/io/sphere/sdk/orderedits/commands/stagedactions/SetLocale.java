package io.sphere.sdk.orderedits.commands.stagedactions;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.annotation.Nullable;
import java.util.Locale;

public final class SetLocale extends OrderEditStagedUpdateActionBase {

    @Nullable
    private final Locale locale;

    @JsonCreator
    private SetLocale(@Nullable final Locale locale) {
        super("setLocale");
        this.locale = locale;
    }

    public static SetLocale of(@Nullable final Locale locale) {
        return new SetLocale(locale);
    }

    @Nullable
    public Locale getLocale() {
        return locale;
    }
}
