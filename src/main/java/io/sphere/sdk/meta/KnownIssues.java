package io.sphere.sdk.meta;

import io.sphere.sdk.models.LocalizedString;

/**
<h2><a href=https://github.com/sphereio/sphere-jvm-sdk/issues/183>#183</a> {@link LocalizedString} currently supports only the language in the {@link java.util.Locale}</h2>

 {@link java.util.Locale} containing a country can currently produce undesired behaviour while
 initializing a {@link LocalizedString} or a query.

 <p>Workaround: pass locales with language only, like:</p>

 {@include.example io.sphere.sdk.meta.KnownIssuesTest#localeWithCountry()}

 Sample error response if not taken care of the problem:

 <pre>{@code
 underlying http response: status=400 {
    "statusCode" : 400,
    "message" : "Malformed parameter: where: `(' expected but `-' found",
    "errors" : [ {
      "code" : "InvalidInput",
      "message" : "Malformed parameter: where: `(' expected but `-' found"
    } ]
}
 }</pre>

 */

public final class KnownIssues {
    private KnownIssues() {
    }
}
