package io.sphere.sdk.meta;

/**
 *
 * <h3>1.0.0-M5</h3>
 *
 * <ul>
 *     <li>Fixed client shutdown problem.</li>
 *     <li>Put {@link io.sphere.sdk.models.MetaAttributes MetaAttributes} in common module and make it an interface.</li>
 *     <li>Add {@link io.sphere.sdk.products.ProductProjection#isPublished()}.</li>
 *     <li>Add {@link io.sphere.sdk.productdiscounts.ProductDiscount ProductDiscount} models.</li>
 *     <li>Add {@link io.sphere.sdk.categories.Category#getExternalId() external id fields and methods} for categories.</li>
 *     <li>Make {@link io.sphere.sdk.products.ProductCatalogData#getCurrent()} return an optional {@link io.sphere.sdk.products.ProductData}, since current should not be accessible if {@link io.sphere.sdk.products.ProductCatalogData#isPublished()} returns false.</li>
 *     <li>Make masterVariant in {@link io.sphere.sdk.products.NewProductBuilder} mandatory.</li>
 * </ul>
 *
 *
 * <h3>1.0.0-M4</h3>
 * <ul>
 *     <li>Replacing joda time library with Java 8 DateTime API.</li>
 *     <li>Removing dependency to Google Guava.</li>
 *     <li>Rename artifact organization to {@code io.sphere.sdk.jvm}.</li>
 *     <li>Rename {@code JsonUtils.readObjectFromJsonFileInClasspath} to {@code JsonUtils.readObjectFromResource}.</li>
 *     <li>Reduced the number of SBT modules to speed up travis builds since the resolving of artifacts for every module is slow. In addition fewer JARs needs to be downloaded.</li>
 *     <li>Introduced {@link io.sphere.sdk.products.ProductProjection}s.</li>
 *     <li>Javadoc does contain a table of content box for h3 headings.</li>
 * </ul>
 *
 * <h3>1.0.0-M3</h3>
 * <ul>
 *     <li>The query model can now be accessed by it's Query class, e.g., {@link io.sphere.sdk.categories.queries.CategoryQuery#model()}.</li>
 *     <li>Added a {@link io.sphere.sdk.meta.GettingStarted Getting Started} page.</li>
 *     <li>Added a {@link io.sphere.sdk.meta.JvmSdkFeatures Features of the SDK} page.</li>
 *     <li>Addad a legacy Play Java client for Play Framework 2.2.x.</li>
 *     <li>Added {@link io.sphere.sdk.products.PriceBuilder}.</li>
 *     <li>Further null checks.</li>
 *     <li>Add a lot of a Javadoc, in general for the packages.</li>
 *     <li>{@link io.sphere.sdk.categories.CategoryTree#of(java.util.List)} instead of CategoryTreeFactory is to be used for creating a category tree.</li>
 *     <li>Move {@link io.sphere.sdk.models.AddressBuilder} out of the {@link io.sphere.sdk.models.Address} class.</li>
 *     <li>Performed a lot of renamings like the {@code requests} package to {@code http}</li>
 *     <li>Moved commands and queries to own packages for easier discovery.</li>
 *     <li>Introduced new predicates for inequality like {@link io.sphere.sdk.queries.StringQueryModel#isGreaterThanOrEquals(String)},
 *     {@link io.sphere.sdk.queries.StringQueryModel#isNot(String)},
 *     {@link io.sphere.sdk.queries.StringQueryModel#isNotIn(String, String...)} or {@link io.sphere.sdk.queries.StringQueryModel#isNotPresent()}.</li>
 *     <li>Introduced an unsafe way to create predicates from strings with {@link io.sphere.sdk.queries.Predicate#of(String)}.</li>
 * </ul>
 *
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
