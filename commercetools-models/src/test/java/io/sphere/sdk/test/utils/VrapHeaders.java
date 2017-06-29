package io.sphere.sdk.test.utils;

import io.sphere.sdk.http.HttpHeaders;
import io.sphere.sdk.http.NameValuePair;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Provides methods to create vrap specific headers.
 */
public class VrapHeaders {
    private VrapHeaders() {
    }

    /**
     * Creates disable validation headers for the given validation flags.
     *
     * @param validationFlags the validation flags
     * @return the http headers
     */
    public static HttpHeaders disableValidation(final String... validationFlags) {
        final List<NameValuePair> nameValuePairs = Stream.of(validationFlags)
                .map(f -> NameValuePair.of("X-Vrap-Disable-Validation", f))
                .collect(Collectors.toList());
        return HttpHeaders.of(nameValuePairs);
    }
}
