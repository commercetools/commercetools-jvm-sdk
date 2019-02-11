package io.sphere.sdk.orderedits.commands.stagedactions;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.neovisionaries.i18n.CountryCode;

import javax.annotation.Nullable;

public final class SetCountry extends OrderEditStagedUpdateActionBase {

    @Nullable
    private final CountryCode country;

    @JsonCreator
    private SetCountry(@Nullable final CountryCode country) {
        super("setCountry");
        this.country = country;
    }

    public static SetCountry of(@Nullable final CountryCode country){
        return new SetCountry(country);
    }

    @Nullable
    public CountryCode getCountry() {
        return country;
    }
}
