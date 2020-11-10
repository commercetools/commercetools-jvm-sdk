package io.sphere.sdk.stores.error;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.errors.SphereError;


import javax.annotation.Nullable;
import java.util.List;

public final class ProjectNotConfiguredForLanguagesError extends SphereError {
    public static final String CODE = "ProjectNotConfiguredForLanguages";

    private final List<String> languages;

    @JsonCreator
    private ProjectNotConfiguredForLanguagesError(final String message, @Nullable final List<String> languages) {
        super(CODE, message);
        this.languages = languages;
    }

    public static ProjectNotConfiguredForLanguagesError of(final String message, final List<String> languages) {
        return new ProjectNotConfiguredForLanguagesError(message, languages);
    }

    public List<String> getLanguages() {
        return languages;
    }
}
