package io.sphere.sdk.meta;

/**
 <h3>Legend</h3>
 <ul><li class=removed-in-release>removed functionality</li>
 <li class=new-in-release>added functionality</li>
 <li class=change-in-release>breaking change</li>
 <li class=fixed-in-release>bugfix, can include a breaking change</li>
 </ul>

 <h3 class=released-version>1.0.0-M10</h3>
 <h4 id=products-1.0.0-M10>Products</h4>
 <ul>
    <li class=new-in-release>Added update actions:
     <ul>
        <li>{@link io.sphere.sdk.products.commands.updateactions.AddVariant}</li>
        <li>{@link io.sphere.sdk.products.commands.updateactions.RemoveVariant}</li>
        <li>{@link io.sphere.sdk.products.commands.updateactions.SetAttribute}</li>
        <li>{@link io.sphere.sdk.products.commands.updateactions.SetAttributeInAllVariants}</li>
        <li>{@link io.sphere.sdk.products.commands.updateactions.RemoveFromCategory}</li>
        <li>{@link io.sphere.sdk.products.commands.updateactions.SetSearchKeywords}</li>
        <li>{@link io.sphere.sdk.products.commands.updateactions.RevertStagedChanges}</li>
     </ul>
    </li>
 </ul>



 <h3 class=released-version>1.0.0-M10</h3>
 <ul>
     <li class=new-in-release>Added {@link io.sphere.sdk.customobjects.CustomObject} models and endpoints. There is also a {@link io.sphere.sdk.meta.CustomObjectDocumentation tutorial for custom objects}.</li>
     <li class=new-in-release>Added the {@link io.sphere.sdk.zones.Zone} models and endpoints.</li>
     <li class=new-in-release>Added the {@link io.sphere.sdk.shippingmethods.ShippingMethod} models and endpoints.</li>
     <li class=new-in-release>Added Typesafe Activator files, so you can edit the SDK on UNIX with {@code ./activator ui} or on Windows with {@code activator ui}.</li>
     <li class=new-in-release>Added {@link io.sphere.sdk.client.ConcurrentModificationException} which is thrown when an {@link io.sphere.sdk.commands.UpdateCommand} fails because of concurrent usage.</li>
     <li class=new-in-release>Added {@link io.sphere.sdk.client.ReferenceExistsException} which is thrown when executing a {@link io.sphere.sdk.commands.DeleteCommand} and the resource is referenced by another resource and cannot be deleted before deleting the other resource.</li>
     <li class=new-in-release>Added {@link io.sphere.sdk.queries.InvalidQueryOffsetException} which is thrown if offset is
     not between {@value io.sphere.sdk.queries.Query#MIN_OFFSET} and {@value io.sphere.sdk.queries.Query#MAX_OFFSET}..</li>
     <li class=new-in-release>Improved on different location the structure of interfaces so important methods are highlighted bold in the IDE.</li>
     <li class=new-in-release>JSON mapping errors are better logged.</li>
     <li class=new-in-release>Added update actions for cart: {@link io.sphere.sdk.carts.commands.updateactions.SetShippingMethod} and {@link io.sphere.sdk.carts.commands.updateactions.SetCustomerId}.</li>
     <li class=new-in-release>Added update actions for customer: {@link io.sphere.sdk.carts.commands.updateactions.SetCustomerId}.</li>
     <li class=new-in-release>Added {@link io.sphere.sdk.models.Referenceable#hasSameIdAs(io.sphere.sdk.models.Referenceable)} to check if a similar object has the same ID.</li>
        <li class=new-in-release>Added {@link io.sphere.sdk.attributes.AttributeAccess#ofName(String)} as alias to {@link io.sphere.sdk.attributes.AttributeAccess#getterSetter(String)}.</li>

 <li class=new-in-release>Update action list in update commands do not have the type {@literal List<UpdateAction<T>>} {@literal  List<? extends UpdateAction<T>>}, so you can pass a list of a subclass of {@link io.sphere.sdk.commands.UpdateAction}.
 Example: {@literal List<ChangeName>} can be assigned where {@literal ChangeName} extends {@link io.sphere.sdk.commands.UpdateAction}.</li>

 <li class=new-in-release>Added {@link io.sphere.sdk.models.Address#of(com.neovisionaries.i18n.CountryCode)}.</li>
 <li class=new-in-release>Added {@link io.sphere.sdk.carts.Cart#getLineItem(String)} and {@link io.sphere.sdk.carts.Cart#getCustomLineItem(String)} to find items in a cart.</li>
 <li class=new-in-release>Added {@link io.sphere.sdk.products.ProductProjection#getAllVariants()} to receive master variant and all other variants in one call. {@link io.sphere.sdk.products.ProductProjection#getVariants()} just returns all variants except the master variant.</li>
 <li class=new-in-release>Added {@link io.sphere.sdk.products.ProductProjection#getVariant(int)} and {@link io.sphere.sdk.products.ProductProjection#getVariantOrMaster(int)} to find a product variant by id.</li>
 <li class=new-in-release>Added {@link io.sphere.sdk.products.VariantIdentifier} to have a container to address product variants which needs a product ID and a variant ID.</li>
 <li class=new-in-release>added {@link io.sphere.sdk.customers.commands.CustomerDeleteByIdCommand} to delete customers.</li>
 <li class=new-in-release>Added {@link io.sphere.sdk.products.commands.updateactions.AddExternalImage} to connect products with images not hosted by SPHERE.IO.</li>
 <li class=new-in-release>Added {@link io.sphere.sdk.products.commands.updateactions.RemoveImage} to disconnect images from a product (external images and SPHERE.IO hosted).</li>
 <li class=new-in-release>Added {@link io.sphere.sdk.client.SphereAccessTokenSupplier} as authentication method in the {@link io.sphere.sdk.client.SphereClient}.
 It is possible to automatically refresh a token or just pass a token to the client, see {@link io.sphere.sdk.client.SphereClientFactory#createClient(io.sphere.sdk.client.SphereApiConfig, io.sphere.sdk.client.SphereAccessTokenSupplier)} and {@link io.sphere.sdk.client.SphereAccessTokenSupplier#ofFixedToken(String)}.</li>


     <li class=change-in-release>Product variants are all of type int, was int and long before.</li>
     <li class=change-in-release>{@link io.sphere.sdk.models.Reference} is not instantiated with new.</li>
     <li class=change-in-release>{@link io.sphere.sdk.utils.UrlQueryBuilder} is not instantiated with new.</li>
     <li class=change-in-release>{@link io.sphere.sdk.client.SphereErrorResponse} is not instantiated with new.</li>
     <li class=change-in-release>{@code ClientRequest} has been renamed to {@link io.sphere.sdk.client.SphereRequest} and therefore {@code ClientRequestBase} to {@link io.sphere.sdk.client.SphereRequestBase}. </li>
     <li class=change-in-release>{@code ClientRequest} has been renamed to {@link io.sphere.sdk.client.SphereRequest} and therefore {@code ClientRequestBase} to {@link io.sphere.sdk.client.SphereRequestBase}. </li>
     <li class=change-in-release>{@code JavaClient} has been renamed to {@link io.sphere.sdk.client.SphereClient} and uses the {@link io.sphere.sdk.client.SphereClientFactory} to initialized a client, {@code JavaClientIml} has been removed, see {@link io.sphere.sdk.meta.GettingStarted}.
    The typesafe config library is not used anymore. The class {@code HttpClientTestDouble} has been removed, use {@link io.sphere.sdk.client.SphereClientFactory#createHttpTestDouble(java.util.function.Function)} instead.
 {@code SphereRequestExecutor} and {@code SphereRequestExecutorTestDouble} have been removed, use {@link io.sphere.sdk.client.SphereClientFactory#createObjectTestDouble(java.util.function.Function)} instead.
 </li>


        <li class=fixed-in-release>Money portions in the taxed price is not null. The method name is now {@link io.sphere.sdk.carts.TaxPortion#getAmount()} instead of {@code getMoney()}.</li>
 <li class=fixed-in-release>Fixed JSON serialization and deserialization of {@link io.sphere.sdk.models.ImageDimensions} which caused adding external images to a product to fail.</li>
 </ul>

 <h3>1.0.0-M9</h3>
 <ul>
    <li>Added {@link io.sphere.sdk.meta.KnownIssues Known Issues} page.</li>
    <li>Added experimental support for uploading product images in variants. See {@link io.sphere.sdk.products.commands.ExperimentalProductImageUploadCommand}.</li>
    <li>Added factory methods for {@link io.sphere.sdk.models.Image}.</li>
    <li>{@link io.sphere.sdk.models.Image} contains directly getters for width {@link io.sphere.sdk.models.Image#getWidth()} 
    and height {@link io.sphere.sdk.models.Image#getHeight()}.</li>
    <li>{@link io.sphere.sdk.queries.PagedQueryResult} is constructable for empty results. Before this, the SDK throwed an Exception.</li>
    <li>Fields called {@code quantity} are now of type long instead of int.</li>
    <li>Added a documentation page {@link io.sphere.sdk.meta.ConstructionDocumentation how to construct objects}.</li>
 </ul>

 <h3>1.0.0-M8</h3>

 <ul>
    <li>Query models contain id, createdAt and lastModifiedAt for predicates and sorting.</li>
    <li>Introduced endpoints and models for {@link io.sphere.sdk.carts.Cart}s, {@link io.sphere.sdk.customers.Customer}s, {@link io.sphere.sdk.customergroups.CustomerGroup}s and {@link io.sphere.sdk.orders.Order}s.</li>
    <li>Quantity fields are now of type long.</li>
    <li>Classes like {@link io.sphere.sdk.products.queries.ProductFetchById} take now a string parameter for the ID and not an {@link io.sphere.sdk.models.Identifiable}.</li>
    <li>Queries, Fetches, Commands and Searches are only instantiable with an static of method like {@link io.sphere.sdk.categories.commands.CategoryCreateCommand#of(io.sphere.sdk.categories.CategoryDraft)}. The instantiation by constructor is not supported anymore.</li>
    <li>Enum constant names are only in upper case.</li>
 </ul>

  <h3>1.0.0-M7</h3>

 <ul>
    <li>Incompatible change: Classes to create templates for new entries in SPHERE.IO like {@code NewCategory} have been renamed to {@link io.sphere.sdk.categories.CategoryDraft}. </li>
    <li>Incompatible change: {@link io.sphere.sdk.producttypes.ProductTypeDraft} has now only
 factory methods with an explicit parameter for the attribute declarations to prevent to use
 the getter {@link io.sphere.sdk.producttypes.ProductTypeDraft#getAttributes()} and list add operations. </li>
    <li>Incompatible change: {@code LocalizedString} has been renamed to {@link io.sphere.sdk.models.LocalizedStrings}, since it is not a container for one string and a locale, but for multiple strings of different locals. It is like a map.</li>
    <li>Incompatible change: The {@link io.sphere.sdk.queries.Fetch} classes have been renamed. From FetchRESOURCEByWhatever to RESOURCEFetchByWhatever</li>
    <li>Moved Scala and Play clients out of the Git repository to <a href="https://github.com/sphereio/sphere-jvm-sdk-scala-add-ons">https://github.com/sphereio/sphere-jvm-sdk-scala-add-ons</a>. The artifact ID changed.</li>
    <li>{@link io.sphere.sdk.meta.SphereResources} contains now also a listing of queries and commands for the resources.</li>
    <li>Added {@link io.sphere.sdk.products.search.ProductProjectionSearch} for full-text, filtered and faceted search.</li>
    <li>Incompatible change: {@link io.sphere.sdk.products.ProductUpdateScope} makes it more visible that product update operations can be for only staged or for current and staged. The product update actions will be affected by that.</li>
    <li>Implemented anonymous carts.</li>
 </ul>

  <h3>1.0.0-M6</h3>

  <ul>
      <li>Usage of <a href="http://javamoney.java.net/">Java money</a> instead of custom implementation.</li>
      <li>Introduce {@link io.sphere.sdk.products.queries.ProductProjectionQuery}.</li>
      <li>Introduce {@link io.sphere.sdk.meta.QueryDocumentation} to document the query API.</li>
  </ul>

  <h3>1.0.0-M5</h3>

  <ul>
      <li>Fixed client shutdown problem.</li>
      <li>Put {@link io.sphere.sdk.models.MetaAttributes MetaAttributes} in common module and make it an interface.</li>
      <li>Add {@link io.sphere.sdk.products.ProductProjection#isPublished()}.</li>
      <li>Add {@link io.sphere.sdk.productdiscounts.ProductDiscount ProductDiscount} models.</li>
      <li>Add {@link io.sphere.sdk.categories.Category#getExternalId() external id fields and methods} for categories.</li>
      <li>Make {@link io.sphere.sdk.products.ProductCatalogData#getCurrent()} return an optional {@link io.sphere.sdk.products.ProductData}, since current should not be accessible if {@link io.sphere.sdk.products.ProductCatalogData#isPublished()} returns false.</li>
      <li>Make masterVariant in {@link io.sphere.sdk.products.ProductDraftBuilder} mandatory.</li>
  </ul>


  <h3>1.0.0-M4</h3>
  <ul>
      <li>Replacing joda time library with Java 8 DateTime API.</li>
      <li>Removing dependency to Google Guava.</li>
      <li>Rename artifact organization to {@code io.sphere.sdk.jvm}.</li>
      <li>Rename {@code JsonUtils.readObjectFromJsonFileInClasspath} to {@code JsonUtils.readObjectFromResource}.</li>
      <li>Reduced the number of SBT modules to speed up travis builds since the resolving of artifacts for every module is slow. In addition fewer JARs needs to be downloaded.</li>
      <li>Introduced {@link io.sphere.sdk.products.ProductProjection}s.</li>
      <li>Javadoc does contain a table of content box for h3 headings.</li>
  </ul>

  <h3>1.0.0-M3</h3>
  <ul>
      <li>The query model can now be accessed by it's Query class, e.g., {@link io.sphere.sdk.categories.queries.CategoryQuery#model()}.</li>
      <li>Added a {@link io.sphere.sdk.meta.GettingStarted Getting Started} page.</li>
      <li>Added a {@link io.sphere.sdk.meta.JvmSdkFeatures Features of the SDK} page.</li>
      <li>Addad a legacy Play Java client for Play Framework 2.2.x.</li>
      <li>Added {@link io.sphere.sdk.products.PriceBuilder}.</li>
      <li>Further null checks.</li>
      <li>Add a lot of a Javadoc, in general for the packages.</li>
      <li>{@link io.sphere.sdk.categories.CategoryTree#of(java.util.List)} instead of CategoryTreeFactory is to be used for creating a category tree.</li>
      <li>Move {@link io.sphere.sdk.models.AddressBuilder} out of the {@link io.sphere.sdk.models.Address} class.</li>
      <li>Performed a lot of renamings like the {@code requests} package to {@code http}</li>
      <li>Moved commands and queries to own packages for easier discovery.</li>
      <li>Introduced new predicates for inequality like {@link io.sphere.sdk.queries.StringQueryModel#isGreaterThanOrEquals(String)},
      {@link io.sphere.sdk.queries.StringQueryModel#isNot(String)},
      {@link io.sphere.sdk.queries.StringQueryModel#isNotIn(String, String...)} or {@link io.sphere.sdk.queries.StringQueryModel#isNotPresent()}.</li>
      <li>Introduced an unsafe way to create predicates from strings with {@link io.sphere.sdk.queries.Predicate#of(String)}.</li>
  </ul>

  <h3>1.0.0-M2</h3>
  <ul>
      <li>With the new reference expansion dsl it is possible to discover and create reference expansion paths for a query.</li>
      <li>All artifacts have the ivy organization {@code io.sphere.jvmsdk}.</li>
      <li>Migration from Google Guavas com.google.common.util.concurrent.ListenableFuture to Java 8 {@link java.util.concurrent.CompletableFuture}.</li>
      <li>Removed all Google Guava classes from the public API (internally still used).</li>
      <li>The logger is more fine granular controllable, for example the logger {@code sphere.products.responses.queries} logs only the responses of the queries for products. The trace level logs the JSON of responses and requests as pretty printed JSON.</li>
      <li>Introduced the class {@link io.sphere.sdk.models.Referenceable} which enables to use a model or a reference to a model as parameter, so no direct call of {@link io.sphere.sdk.models.DefaultModel#toReference()} is needed anymore for model classes.</li>
      <li>It is possible to overwrite the error messages of {@link io.sphere.sdk.test.DefaultModelAssert}, {@link io.sphere.sdk.test.LocalizedStringsAssert} and {@link io.sphere.sdk.test.ReferenceAssert}.</li>
      <li>{@link io.sphere.sdk.models.Versioned} contains a type parameter to prevent copy and paste errors.</li>
      <li>Sorting query model methods have better support in the IDE, important methods are bold.</li>
      <li>Queries and commands for models are in there own package now and less coupled to the model.</li>
      <li>The query classes have been refactored.</li>
  </ul>
 */
public final class ReleaseNotes {
    private ReleaseNotes() {
    }
}
