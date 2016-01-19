package io.sphere.sdk.projects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.CreationTimestamped;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Locale;

import static java.util.stream.Collectors.toList;

public final class Project extends Base implements CreationTimestamped {
    private final String key;
    private final String name;
    private final List<CountryCode> countries;
    private final List<String> languages;
    private final List<String> currencies;
    private final ZonedDateTime createdAt;
    private final ZonedDateTime trialUntil;

    @JsonCreator
    private Project(final String key, final String name, final List<CountryCode> countries, final List<String> languages, final List<String> currencies, final ZonedDateTime createdAt, @JsonDeserialize(using = TrialUntilDeserializer.class) final ZonedDateTime trialUntil) {
        this.key = key;
        this.name = name;
        this.countries = countries;
        this.languages = languages;
        this.currencies = currencies;
        this.createdAt = createdAt;
        this.trialUntil = trialUntil;
    }

    /**
     * The unique key of the project.
     * @return key
     */
    public String getKey() {
        return key;
    }

    /**
     * The name of the project.
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Enabled countries.
     * @return countries
     */
    public List<CountryCode> getCountries() {
        return countries;
    }

    /**
     * A two-digit language code as per ISO 3166-1 alpha-2 String.
     * @see #getLanguageLocales()
     * @return language
     */
    public List<String> getLanguages() {
        return languages;
    }

    /**
     * The languages as list of {@link Locale}s of this project.
     * @see #getLanguages()
     * @return languages
     */
    public List<Locale> getLanguageLocales() {
        return getLanguages().stream()
                .map(Locale::forLanguageTag)
                .collect(toList());
    }

    public ZonedDateTime getTrialUntil() {
        return trialUntil;
    }

    @Override
    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * A three-digit currency code as per ISO 4217.
     * @see #getCurrencyUnits()
     * @return currency codes
     */
    public List<String> getCurrencies() {
        return currencies;
    }

    /**
     * Currencies assigned to this project as {@link CurrencyUnit}.
     * @see #getCurrencies()
     * @return currencies
     */
    public List<CurrencyUnit> getCurrencyUnits() {
        return getCurrencies().stream()
                .map(Monetary::getCurrency)
                .collect(toList());
    }

    public static TypeReference<Project> typeReference() {
        return new TypeReference<Project>(){
            @Override
            public String toString() {
                return "TypeReference<Project>";
            }
        };
    }
}
