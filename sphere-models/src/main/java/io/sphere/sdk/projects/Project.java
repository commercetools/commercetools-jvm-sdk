package io.sphere.sdk.projects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.CreationTimestamped;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.List;

public final class Project extends Base implements CreationTimestamped {
    private final String key;
    private final String name;
    private final List<CountryCode> countries;
    private final List<String> languages;
    private final Instant createdAt;
    private final ZonedDateTime trialUntil;

    @JsonCreator
    private Project(final String key, final String name, final List<CountryCode> countries, final List<String> languages, final Instant createdAt,  @JsonDeserialize(using=TrialUntilDeserializer.class) final ZonedDateTime trialUntil) {
        this.key = key;
        this.name = name;
        this.countries = countries;
        this.languages = languages;
        this.createdAt = createdAt;
        this.trialUntil = trialUntil;
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
    public Instant getCreatedAt() {
        return createdAt;
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
