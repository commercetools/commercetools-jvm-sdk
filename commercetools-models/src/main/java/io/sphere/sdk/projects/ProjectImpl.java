package io.sphere.sdk.projects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.neovisionaries.i18n.CountryCode;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.List;

final class ProjectImpl extends ProjectImplBase {

    @JsonCreator
    ProjectImpl(final List<CountryCode> countries, final ZonedDateTime createdAt, final List<String> currencies, final String key, final List<String> languages,
                final MessagesConfiguration messages, final String name,@Nullable final ShippingRateInputType shippingRateInputType, @Nullable  @JsonDeserialize(using = TrialUntilDeserializer.class) final ZonedDateTime trialUntil, final Long version) {

        super(countries, createdAt, currencies, key, languages, messages, name,shippingRateInputType, trialUntil, version);

    }
}
