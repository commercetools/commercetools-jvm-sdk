package io.sphere.sdk.models;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Hint for guis if a String input field should be of a line or multiline.
 *
 * For the import and the export of values see also {@link SphereEnumeration}.
 *
 */
public enum TextInputHint implements SphereEnumeration {
    SINGLE_LINE, MULTI_LINE;

    @JsonCreator
    public static TextInputHint ofSphereValue(final String value) {
        return SphereEnumeration.findBySphereName(values(), value).get();
    }
}
