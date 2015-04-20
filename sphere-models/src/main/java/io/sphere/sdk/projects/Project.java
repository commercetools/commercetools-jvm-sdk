package io.sphere.sdk.projects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.models.Base;

public final class Project extends Base {
    private final String key;

    @JsonCreator
    private Project(final String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
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
