package io.sphere.sdk.projects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.neovisionaries.i18n.CountryCode;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.List;

final class ProjectImpl extends ProjectImplBase {

    @JsonCreator
    public ProjectImpl(List<CountryCode> countries, ZonedDateTime createdAt, List<String> currencies, @Nullable ExternalOAuth externalOAuth, String key, List<String> languages, MessagesConfiguration messages, String name, @Nullable ShippingRateInputType shippingRateInputType, @Nullable ZonedDateTime trialUntil, Long version) {
        super(countries, createdAt, currencies, externalOAuth, key, languages, messages, name, shippingRateInputType, trialUntil, version);
    }
}
