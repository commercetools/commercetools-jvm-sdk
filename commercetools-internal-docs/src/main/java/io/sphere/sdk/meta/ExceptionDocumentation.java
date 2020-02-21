package io.sphere.sdk.meta;

import io.sphere.sdk.models.errors.InvalidJsonInputError;

/**
 * The exception hierarchy documentation.
 *
 * <h3>Exceptions</h3>
 *
 * <img src="{@docRoot}/resources/images/uml/exception-hierarchy.svg" alt="visualization of the exception hierarchy">
 *
 * <p>Open the <a href="{@docRoot}/resources/images/uml/exception-hierarchy.svg" target="_blank">exception hierarchy image in a new tab</a>.</p>
 *
 * The JVM SDK makes use of exceptions of the Java JDK, such as {@link java.lang.IllegalArgumentException}, and provides own exceptions which all inherit from {@link io.sphere.sdk.models.SphereException}.
 *
 * <p>Problems concerning the {@link io.sphere.sdk.http.HttpClient} throw a {@link io.sphere.sdk.http.HttpException}.</p>
 *
 * <p>JSON serializing and deserializing problems throw {@link io.sphere.sdk.json.JsonException}.</p>
 *
 * <p>{@link io.sphere.sdk.client.SphereServiceException} is a base exception for all error responses from the commercetools platform (HTTP status code {@code >= 400}).</p>
 *
 * <p>{@link io.sphere.sdk.client.ClientErrorException} expresses errors which can be recovered by the client side (HTTP status code {@code >= 400 and < 500}).
 * {@link io.sphere.sdk.client.ServerErrorException} is for server errors.</p>
 *
 * <h3>Errors</h3>
 *
 * If a command cannot be performed due to unfulfilled preconditions
 * the platform can return one error response with multiple errors (<a href="https://docs.commercetools.com/http-api-errors.html">listing of error codes</a>).
 * The JVM SDK will then put a {@link io.sphere.sdk.client.ErrorResponseException} into a {@link java.util.concurrent.CompletionStage}.
 *
 * The following example shows how to distinguish errors by error code:
 * {@include.example io.sphere.sdk.customers.commands.CustomerChangePasswordCommandIntegrationTest#demo()}
 *
 * Errors can contain extra information like {@link InvalidJsonInputError} hinting to the problem with the JSON:
 *
 * {@include.example io.sphere.sdk.models.SphereErrorTest#castToConcreteError()}
 *
 *
 * @see io.sphere.sdk.models.SphereException
 */
public final class ExceptionDocumentation {
    private ExceptionDocumentation() {
    }
}
