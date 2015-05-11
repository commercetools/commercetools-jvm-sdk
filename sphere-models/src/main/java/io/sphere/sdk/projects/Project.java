package io.sphere.sdk.projects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.models.Base;

import java.util.List;

public final class Project extends Base {
    private final String key;
    private final String name;
    private final List<CountryCode> countries;

    @JsonCreator
    private Project(final String key, final String name, final List<CountryCode> countries) {
        this.key = key;
        this.name = name;
        this.countries = countries;
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

    public static TypeReference<Project> typeReference() {
        return new TypeReference<Project>(){
            @Override
            public String toString() {
                return "TypeReference<Project>";
            }
        };
    }
}
