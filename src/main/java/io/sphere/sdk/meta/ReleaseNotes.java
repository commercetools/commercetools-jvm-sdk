package io.sphere.sdk.meta;

/**
 * <h3>1.0.0-M2</h3>
 * <ul>
 *     <li>With the new reference expansion dsl it is possible to discover and create reference expansion paths for a query.</li>
 *     <li>All artifacts have the ivy organization {@code io.sphere.jvmsdk}.</li>
 *     <li>Migration from Google Guavas {@link com.google.common.util.concurrent.ListenableFuture} to Java 8 {@link java.util.concurrent.CompletableFuture}.</li>
 *     <li>Removed all Google Guava classes from the public API (internally still used).</li>
 *     <li>The logger is more fine granular controllable, for example the logger {@code sphere.products.responses.queries} logs only the responses of the queries for products. The trace level logs the JSON of responses and requests as pretty printed JSON.</li>
 *     <li>Introduced the class {@link io.sphere.sdk.models.Referenceable} which enables to use a model or a reference to a model as parameter, so no direct call of {@link io.sphere.sdk.models.DefaultModel#toReference()} is needed anymore for model classes.</li>
 *     <li>It is possible to overwrite the error messages of {@link io.sphere.sdk.test.DefaultModelAssert}, {@link io.sphere.sdk.test.LocalizedStringAssert} and {@link io.sphere.sdk.test.ReferenceAssert}.</li>
 *     <li>{@link io.sphere.sdk.models.Versioned} contains a type parameter to prevent copy and paste errors.</li>
 *     <li>Sorting query model methods have better support in the IDE, important methods are bold.</li>
 *     <li>Queries and commands for models are in there own package now and less coupled to the model.</li>
 *     <li>The query classes have been refactored.</li>
 * </ul>
 */
public final class ReleaseNotes {
    private ReleaseNotes() {
    }
}
