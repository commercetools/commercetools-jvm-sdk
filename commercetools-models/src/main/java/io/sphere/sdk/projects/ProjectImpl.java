package io.sphere.sdk.projects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.models.Base;

import java.time.ZonedDateTime;
import java.util.List;

final class ProjectImpl extends Base implements Project {
    private final String key;
    private final String name;
    private final List<CountryCode> countries;
    private final List<String> languages;
    private final List<String> currencies;
    private final ZonedDateTime createdAt;
    private final ZonedDateTime trialUntil;
    private final MessagesConfiguration messages;

    @JsonCreator
    ProjectImpl(final String key, final String name, final List<CountryCode> countries, final List<String> languages, final List<String> currencies, final ZonedDateTime createdAt, @JsonDeserialize(using = TrialUntilDeserializer.class) final ZonedDateTime trialUntil, final MessagesConfiguration messages) {
        this.key = key;
        this.name = name;
        this.countries = countries;
        this.languages = languages;
        this.currencies = currencies;
        this.createdAt = createdAt;
        this.trialUntil = trialUntil;
        this.messages = messages;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public List<CountryCode> getCountries() {
        return countries;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public ZonedDateTime getTrialUntil() {
        return trialUntil;
    }

    @Override
    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public List<String> getCurrencies() {
        return currencies;
    }

    @Override
    public MessagesConfiguration getMessages() {
        return messages;
    }
}
