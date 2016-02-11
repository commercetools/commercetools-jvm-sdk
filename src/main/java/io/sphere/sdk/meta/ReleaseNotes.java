package io.sphere.sdk.meta;

import io.sphere.sdk.carts.CartLike;
import io.sphere.sdk.carts.CustomLineItem;
import io.sphere.sdk.carts.LineItem;
import io.sphere.sdk.carts.commands.updateactions.SetShippingMethod;
import io.sphere.sdk.carts.expansion.CartExpansionModel;
import io.sphere.sdk.carts.queries.CartByIdGet;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.channels.queries.ChannelByIdGet;
import io.sphere.sdk.client.SphereApiConfig;
import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.client.SphereRequest;
import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.commands.UpdateCommand;
import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.customobjects.commands.CustomObjectDeleteCommand;
import io.sphere.sdk.customobjects.queries.CustomObjectByKeyGet;
import io.sphere.sdk.customobjects.queries.CustomObjectQuery;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.http.*;
import io.sphere.sdk.models.*;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.expansion.OrderExpansionModel;
import io.sphere.sdk.payments.Transaction;
import io.sphere.sdk.productdiscounts.queries.ProductDiscountByIdGet;
import io.sphere.sdk.products.*;
import io.sphere.sdk.products.attributes.Attribute;
import io.sphere.sdk.products.attributes.AttributeAccess;
import io.sphere.sdk.products.attributes.AttributeDefinition;
import io.sphere.sdk.products.attributes.LocalizedToStringProductAttributeConverter;
import io.sphere.sdk.products.commands.ProductCreateCommand;
import io.sphere.sdk.products.commands.updateactions.SetMetaDescription;
import io.sphere.sdk.products.commands.updateactions.SetMetaKeywords;
import io.sphere.sdk.products.commands.updateactions.SetMetaTitle;
import io.sphere.sdk.products.expansion.ProductProjectionExpansionModel;
import io.sphere.sdk.products.queries.*;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.ProductTypeDraft;
import io.sphere.sdk.projects.Project;
import io.sphere.sdk.queries.Get;
import io.sphere.sdk.queries.QueryDsl;
import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.search.SearchKeywords;
import io.sphere.sdk.taxcategories.TaxRate;
import io.sphere.sdk.types.CustomFields;
import io.sphere.sdk.types.FieldType;

import javax.money.CurrencyUnit;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 <div class=release-notes>
 <h3>Legend</h3>
 <ul>
 <li class=removed-in-release>removed functionality</li>
 <li class=new-in-release>added functionality</li>
 <li class=change-in-release>breaking change</li>
 <li class=fixed-in-release>bugfix, can include a breaking change</li>
 </ul>

 <!--
 <ul>
 <li class=removed-in-release></li>
 <li class=new-in-release></li>
 <li class=change-in-release></li>
 <li class=fixed-in-release></li>
 </ul>
 -->

 <h3 class=released-version id="v1_0_0_RC1">1.0.0-RC1 (12.02.2016)</h3>
 <a class="theme-btn expand-all">Expand all</a>

 <br>
 <br>
 <ul>
 <li class=new-in-release>use a new groupId as well as artifactId for the JVM SDK, have a look at the <a href="https://github.com/sphereio/sphere-jvm-sdk#sphereio-jvm-sdk">GitHub readme</a></li>
 <li class=new-in-release>in commercetools-convenience module: {@link io.sphere.sdk.client.metrics.SimpleMetricsSphereClient} makes durations for requests observable</li>
 <li class=new-in-release>add {@link io.sphere.sdk.jsonnodes.queries.JsonNodeQuery} to use the query endpoints and get directly {@link com.fasterxml.jackson.databind.JsonNode} instances</li>
 <li class=new-in-release>add draft getters to create commands</li>
 <li class=new-in-release>a lot of new documentation in the classes</li>
 <li class=new-in-release>cross reference expansion for a lot of endpoints, so for example expand of a review its current state and of this state the possible transition states</li>
 <li class=new-in-release>add {@link java.time.Duration} API for {@link io.sphere.sdk.client.SphereClientUtils#blockingWait(CompletionStage, Duration)} and {@link io.sphere.sdk.client.BlockingSphereClient#of(SphereClient, Duration)}  </li>

 <li class=new-in-release>add {@link LocalizedString#ofStringToStringMap(Map)} </li>
 <li class=new-in-release>add {@link io.sphere.sdk.client.TokensFacade} to conveniently just get an access token
 <li class=new-in-release>add {@link io.sphere.sdk.messages.queries.MessageQuery#forMessageTypes(List)}</li>
 <li class=new-in-release>use {@code io.sphere.sdk.http.HttpClient} as logger name with debug level to log the http method and the path (currently works only for the default HTTP client)</li>

 <li class=change-in-release>deprecate {@code LocalizedString#ofEnglishLocale(String)}, use  {@link LocalizedString#ofEnglish(String)} instead</li>
 <li class=change-in-release>{@code VariantIdentifier} became {@link ByIdVariantIdentifier} and use {@link VariantIdentifier} as super interface for {@link ByIdVariantIdentifier} and {@link BySkuVariantIdentifier}</li>
 <li class=change-in-release>drafts are interfaces</li>
 <li class=change-in-release>{@link io.sphere.sdk.products.search.ProductProjectionSearch} uses POST instead of GET</li>
 <li class=change-in-release>set the default HTTP client to Async Http Client to 2.0.0-RC9</li>
 <li class=change-in-release>deprecate {@code io.sphere.sdk.client.SphereAccessTokenSupplierFactory}, use {@link io.sphere.sdk.client.SphereAccessTokenSupplier} methods instead</li>

 <li class=removed-in-release>class {@code SphereRequestBase}, use {@link io.sphere.sdk.client.SphereRequestUtils} instead</li>
 <li class=removed-in-release>{@code ExperimentalReactiveStreamUtils} is not experimental anymore and has been moved out of commercetools-convenience to a separate repository: <a href="https://github.com/sphereio/commercetools-jvm-sdk-reactive-streams-add-ons" target="_blank">https://github.com/sphereio/commercetools-jvm-sdk-reactive-streams-add-ons</a></li>
 <li class=removed-in-release>some internal util classes (StringUtils, IterableUtils, MapUtils, SetUtils, TriFunction, PatternMatcher, UrlUtils)</li>
 <li class=removed-in-release>{@code CustomObjectDraft#withVersion(String, String, JsonNode)}</li>
 <li class=removed-in-release>{@code HttpQueryParameter} class</li>
 <li class=removed-in-release>{@code InvalidQueryOffsetException}</li>

 </ul>




 <h3 class=released-version id="v1_0_0_M26">1.0.0-M26 (20.01.2016)</h3>
 <a class="theme-btn expand-all">Expand all</a>

 <br>
 <br>
 <ul>
 <li class=new-in-release>Added {@link io.sphere.sdk.reviews.Review}s.</li>

 <li class=new-in-release>Builders for query and search requests. So for example
 {@link io.sphere.sdk.products.queries.ProductProjectionQueryBuilder}
 to build {@link io.sphere.sdk.products.queries.ProductProjectionQuery} and
 {@link io.sphere.sdk.products.search.ProductProjectionSearchBuilder}
 to build {@link io.sphere.sdk.products.search.ProductProjectionSearch}.


 <div class="rn-hidden">
 <p>The new way with builders (code with side effects)</p>
 {@include.example io.sphere.sdk.meta.M26Demo#createSearchQueryWithBuilder}
 <p>The classic way (immutable objects, reassigning)</p>
 {@include.example io.sphere.sdk.meta.M26Demo#createSearchQueryClassicWay()}
 </div>

 </li>

 <li class=new-in-release>Added customer update actions
 {@link io.sphere.sdk.customers.commands.updateactions.SetTitle},
 {@link io.sphere.sdk.customers.commands.updateactions.SetFirstName},
 {@link io.sphere.sdk.customers.commands.updateactions.SetMiddleName} and
 {@link io.sphere.sdk.customers.commands.updateactions.SetLastName}.</li>
 <li class=new-in-release>{@link CustomObjectQuery} supports now predicates containing key.
 <div class="rn-hidden">
 {@include.example io.sphere.sdk.meta.M26Demo#queryCustomObjectsByKeyExample()}
 </div>
 </li>
 <li class=new-in-release>Added {@link Project#getCurrencies()} and {@link Project#getCurrencyUnits()} so you can get your enabled currencies for the commercetools project.</li>

 <li class=new-in-release>Added some short hand methods for the work with product attributes like {@link Attribute#getValueAsLong()} and {@link Attribute#getValueAsString()}. For all see {@link Attribute}.</li>
 <li class=new-in-release>Added {@link LocalizedToStringProductAttributeConverter} which provides some defaults to present product attribute (including monetary amounts and date) values as String. The behaviour can be changed through subclasses.</li>
 </ul>


 <h3 class=released-version id="v1_0_0_M25">1.0.0-M25 (08.01.2016)</h3>
 <ul>
 <li class=fixed-in-release>Support full locales for search endpoint. See <a href="https://github.com/sphereio/sphere-jvm-sdk/issues/922" target="_blank">#922</a></li>
 </ul>

 <h3 class=released-version id="v1_0_0_M24">1.0.0-M24 (05.01.2016)</h3>

 <ul>
 <li class=new-in-release>Commercetools responses are gzipped which should result in less traffic and faster responses. {@link HttpResponse#getResponseBody()} returns the unpacked body.</li>
 <li class=new-in-release>Added {@link io.sphere.sdk.client.BlockingSphereClient} which does not execute asynchronous: {@include.example io.sphere.sdk.meta.BlockingClientValueGetDemo}</li>
 <li class=new-in-release>Added {@link ProductProjectionExpansionModel#allVariants()} for expanding objects in masterVariant and the other variants with one code expression. {@include.example io.sphere.sdk.products.expansion.ProductProjectionExpansionModelTest#allVariants()}</li>
 <li class=new-in-release>{@link io.sphere.sdk.meta.GraphQLDocumentation GraphQL Example}</li>
 <li class=new-in-release>InputHint for types is optional, so added {@link io.sphere.sdk.types.FieldDefinition#of(FieldType, String, LocalizedString, Boolean)}.</li>
 <li class=new-in-release>Ease initialising clients which need a bearer token instead of the client secret:
 {@link io.sphere.sdk.client.SphereClientFactory#createClientOfApiConfigAndAccessToken(SphereApiConfig, String)} and
 {@link io.sphere.sdk.client.SphereClientFactory#createClientOfApiConfigAndAccessToken(SphereApiConfig, String, HttpClient)}
 </li>
 <li class=new-in-release>{@link ProductDraftBuilder#plusVariants(List)} and {@link ProductDraftBuilder#plusVariants(ProductVariantDraft)}</li>
 <li class=new-in-release>Improve error reporting for failed attempts fetching OAuth tokens.</li>
 <li class=new-in-release>Added missing transaction query fields.</li>
 <li class=new-in-release>{@link io.sphere.sdk.client.SphereClientUtils#blockingWait(CompletionStage, long, TimeUnit)} for waiting for sphere requests</li>
 <li class=new-in-release>{@link CartLike#getCurrency()} which gets the currency for a cart or an order</li>
 <li class=new-in-release>Added category order hints to search model.</li>
 <li class=new-in-release>Added {@link io.sphere.sdk.types.ResourceTypeIdsSetBuilder} which helps to find the resource type ids for creation {@link io.sphere.sdk.types.Type}s.</li>
 <li class=new-in-release>{@link ResourceIdentifier}, for example for creating products with the product type key: {@link ProductDraftBuilder#of(ResourceIdentifiable, LocalizedString, LocalizedString, ProductVariantDraft)} </li>
 <li class=new-in-release>Added {@link ProductVariant#isMatchingVariant()}.</li>
 <li class=new-in-release>Several documentation improvements.</li>
 <li class=change-in-release>Renamed methods like "ofLocalizable*" to "ofLocalized*". (the old methods are deprecated)</li>
 <li class=change-in-release>Renamed duplicated classes like "*Type" to "*AttributeType" and "*FieldType". (direct change, no deprecation possible)</li>
 <li class=change-in-release>Use {@link io.sphere.sdk.client.TestDoubleSphereClientFactory#createHttpTestDouble(Function)}
 and {@link io.sphere.sdk.client.TestDoubleSphereClientFactory#createObjectTestDouble(Function)} instead of methods from {@link io.sphere.sdk.client.SphereClientFactory} for test doubles.</li>
 <li class=fixed-in-release>In the properties config the default auth url is wrong. See <a href="https://github.com/sphereio/sphere-jvm-sdk/issues/889">889</a>.</li>
 <li class=removed-in-release>The review classes are removed.</li>
 </ul>

 <h3 class=released-version id="v1_0_0_M23">1.0.0-M23 (09.12.2015)</h3>

 <ul>
 <li class=new-in-release>Create a cart including shipping and billing address as well as with items. See {@link io.sphere.sdk.carts.CartDraftBuilder}</li>
 <li class=new-in-release>Improved Order Query, query by (custom) line items, billing and shipping address, discount codes and custom fields.</li>
 <li class=new-in-release>Query {@link io.sphere.sdk.types.Type}s by resourceTypeIds and fieldDefinitions.</li>
 <li class=new-in-release>Query {@link Order} by empty or not empty syncInfo.</li>
 <li class=new-in-release>Added {@link QueryDsl#plusSort(List)}.</li>
 <li class=new-in-release>Added query predicates for createdAt and lastModifiedAt.</li>
 <li class=new-in-release>Added isIn() query for enum constants like {@link io.sphere.sdk.states.StateType}.</li>
 <li class=new-in-release>Added {@link ProductProjectionQuery#bySku(List)}.</li>
 <li class=new-in-release>Added {@link io.sphere.sdk.taxcategories.queries.TaxCategoryByIdGet}.</li>
 <li class=new-in-release>{@link io.sphere.sdk.client.SphereClientFactory} also auto discovers the Apache HTTP client SDK module if this is in the classpath and not the Async HTTP Client module.</li>
 <li class=new-in-release>Added {@link CustomFields#getFieldsJsonMap()}.</li>
 <li class=change-in-release>Primitive scalar values are now as wrapper classes defined (int to Long, long to Long)</li>
 <li class=change-in-release>{@link io.sphere.sdk.cartdiscounts.DiscountedLineItemPriceForQuantity} and {@link io.sphere.sdk.carts.commands.updateactions.RemoveLineItem} uses long for quantity. </li>
 <li class=change-in-release>{@link Versioned#getVersion()} is of type {@link Long} instead of long.</li>
 <li class=change-in-release>Http core: {@link io.sphere.sdk.http.FormUrlEncodedHttpRequestBody} did not respect the order of elements and possible duplicates.
 Use  {@link io.sphere.sdk.http.FormUrlEncodedHttpRequestBody#of(List)} for construction and {@link FormUrlEncodedHttpRequestBody#getParameters()} for getting the data.
 {@link FormUrlEncodedHttpRequestBody#getData()} is deprecated.</li>
 <li class=new-in-release>Added {@link io.sphere.sdk.client.SphereClientConfig#ofProperties(Properties, String)} to get the commercetools credentials form a properties file.</li>
 <li class=new-in-release>For SDK contributors: <a href="https://github.com/sphereio/sphere-jvm-sdk#executing-integration-tests" target="_blank">integration test credentials can be set via a properties file</a></li>
 </ul>

 <h3 class=released-version id="v1_0_0_M22">1.0.0-M22 (01.12.2015)</h3>
 <ul>
 <li class=change-in-release>{@link AttributeDefinition#getIsRequired()} and {@link AttributeDefinition#getIsSearchable()} ()} have been
 deprecated in favor of {@link AttributeDefinition#isRequired()} and {@link AttributeDefinition#isSearchable}.
 </li>
 <li class=new-in-release>Added new Payment actions:
 {@link io.sphere.sdk.payments.commands.updateactions.ChangeTransactionTimestamp},
 {@link io.sphere.sdk.payments.commands.updateactions.ChangeTransactionInteractionId} and
 {@link io.sphere.sdk.payments.commands.updateactions.ChangeTransactionState}
 </li>
 <li class=new-in-release>Added {@link Transaction#getId()} and {@link Transaction#getState()}</li>
 <li class=change-in-release>{@link Transaction} has been split to {@link io.sphere.sdk.payments.TransactionDraft} as input object
 and {@link Transaction} as output object of the API.</li>
 <li class=change-in-release>Fixed method typo {@link ProductProjection#findVariantBySky(String)}, use {@link ProductProjection#findVariantBySku(String)}</li>
 </ul>

 <h3 class=released-version id="v1_0_0_M21">1.0.0-M21 (25.11.2015)</h3>
 <ul>
 <li class=new-in-release>Introduced the possibility to perform faceted search on products, for more information see the {@link io.sphere.sdk.meta.SearchDocumentation search documentation}, section Faceted Search.</li>
 <li class=new-in-release>Added getters {@link io.sphere.sdk.search.SearchExpression#value()} and {@link io.sphere.sdk.search.FacetExpression#alias()} for the Search API.</li>
 <li class=new-in-release>Added helper method {@link io.sphere.sdk.search.model.RangeTermFacetSearchModel#allRanges()} to request a range facet for all values.</li>
 <li class=fixed-in-release>Validates the provided parameters in filter and facet expressions in order to avoid generating wrong requests.</li>
 <li class=change-in-release>Deprecated {@code io.sphere.sdk.search.model.RangeFacetBaseSearchModel#onlyRangeAsString(java.lang.String, java.lang.String)} in favour of a more generic {@code io.sphere.sdk.search.model.RangeFacetBaseSearchModel#onlyRangeAsString(FacetRange)}.</li>
 <li class=new-in-release>Added static constructors to all facet/filter/sort search model classes, to easily build customized search expressions.</li>
 <li class=change-in-release>Since {@link io.sphere.sdk.search.model.SortSearchModel} implementation classes have now static factory constructors, these class constructors have been removed from the public API.</li>
 <li class=change-in-release>Renamed {@code Range} filter/facet search models to {@code RangeTerm} to emphasize that you can build both range and term oriented search expressions with them.</li>
 <li class=new-in-release>ExpansionPath for shippingInfo fields: {@link CartExpansionModel#shippingInfo()} and {@link OrderExpansionModel#shippingInfo()}</li>
 <li class=new-in-release>{@link ProductType#getKey()}</li>
 <li class=change-in-release>{@link ProductTypeDraft#of(String, String, List)} has been deprecated in favor of {@link ProductTypeDraft#of(String, String, String, List)} since it is a very good practice to create a {@link ProductType} with a key.</li>
 <li class=new-in-release>{@link SetShippingMethod#ofRemove()} to be able to remove a {@link io.sphere.sdk.shippingmethods.ShippingMethod} from a {@link io.sphere.sdk.carts.Cart}</li>
 <li class=new-in-release>{@link io.sphere.sdk.orders.commands.OrderFromCartCreateCommand} contains an example</li>
 <li class=new-in-release>Support for currencies like Yen.</li>
 <li class=new-in-release>create {@link javax.money.MonetaryAmount} with centAmount: {@link io.sphere.sdk.utils.MoneyImpl#ofCents(long, CurrencyUnit)}</li>
 <li class=new-in-release>Add {@code state} field to QueryModel for Products, Reviews and Orders.</li>
 <li class=new-in-release>Custom fields for prices: {@link Price#getCustom()}</li>
 <li class=change-in-release>For custom fields in prices the HTTP API and hence the SDK differentiate now between {@link Price} and {@link io.sphere.sdk.products.PriceDraft}.
 <p>Places where {@link io.sphere.sdk.products.PriceDraft} has to be used:</p>
 <ul>
   <li>{@link io.sphere.sdk.products.commands.updateactions.AddPrice}</li>
   <li>{@link io.sphere.sdk.products.commands.updateactions.ChangePrice}</li>
   <li>{@link io.sphere.sdk.products.ProductVariantDraftBuilder#price(PriceDraft)}</li>
   <li>{@link io.sphere.sdk.products.ProductVariantDraftBuilder#prices(List)}</li>
   <li>{@link io.sphere.sdk.products.commands.updateactions.AddVariant}</li>
 </ul>
 <p>To roughly compare a {@link Price} with a {@link io.sphere.sdk.products.PriceDraft}, use {@link PriceDraft#of(Price)} to create a draft out of the price and then compare the drafts.</p>
 </li>
 </ul>


 <h3 class=released-version id="v1_0_0_M20">1.0.0-M20 (02.11.2015)</h3>
 <ul>
  <li class=change-in-release>{@code query.withSort(m -> m.createdAt().sort(DESC));} is deprecated, use it with {@code query.withSort(m -> m.createdAt().sort().desc());}</li>
  <li class=fixed-in-release>Duplicates with {@code io.sphere.sdk.queries.ExperimentalReactiveStreamUtils#publisherOf(QueryDsl, SphereClient)} on multiple parallel request(n) calls. See <a href="https://github.com/sphereio/sphere-jvm-sdk/issues/809">809</a>.</li>
  <li class=fixed-in-release>Logger name shows query string. See <a href="https://github.com/sphereio/sphere-jvm-sdk/issues/802">802</a>.</li>
  <li class=fixed-in-release>Missing Content-Length header in Async HTTP Client. See <a href="https://github.com/sphereio/sphere-jvm-sdk/issues/799">799</a>.</li>
  <li class=fixed-in-release>variantIdentifier in {@link LineItem} is null. See <a href="https://github.com/sphereio/sphere-jvm-sdk/issues/771">771</a>.</li>
 </ul>


 <h3 class=released-version id="v1_0_0_M19">1.0.0-M19 (16.10.2015)</h3>

 <a class="theme-btn expand-all">Expand all</a>
 <br>
 <br>
 <ul>
 <li class=new-in-release>The Search API now allows multi-sort by accepting a list of {@link io.sphere.sdk.search.SortExpression} instead of a single value.</li>
 <li class=new-in-release>Introduced explicit Set product custom attribute models to build search expressions (i.e. facets, filters and sort expressions).</li>
 <li class=new-in-release>Introduced methods {@link io.sphere.sdk.search.PagedSearchResult#getTermFacetResult}, {@link io.sphere.sdk.search.PagedSearchResult#getRangeFacetResult} and {@link io.sphere.sdk.search.PagedSearchResult#getFilteredFacetResult} that accept the facet path.</li>
 <li class=change-in-release>Introduced search filter methods {@link io.sphere.sdk.search.model.TermFilterSearchModel#byAny} and {@link io.sphere.sdk.search.model.TermFilterSearchModel#byAll} (OR and AND semantics, respectively), along with the counterpart for range filters{@link io.sphere.sdk.search.model.RangeTermFilterSearchModel#byAnyRange} and {@link io.sphere.sdk.search.model.RangeTermFilterSearchModel#byAllRanges}), change that affected the signature of all filter methods which now return a list of {@link io.sphere.sdk.search.FilterExpression} instead of a single value.</li>
 <li class=change-in-release>The Search Sort Model offers now the methods {@link io.sphere.sdk.search.model.SortSearchModel#byAsc} and {@link io.sphere.sdk.search.model.SortSearchModel#byDesc}, as well as {@link io.sphere.sdk.search.model.MultiValueSortSearchModel#byAscWithMax} and {@link io.sphere.sdk.search.model.MultiValueSortSearchModel#byDescWithMin} for multi-valued attributes.</li>
 <li class=change-in-release>Removed {@code io.sphere.sdk.search.UntypedSearchModel} class and instead provide methods to give filter and facet parameters as simple strings.
 <div class="rn-hidden">
 {@include.example io.sphere.sdk.products.search.ProductProjectionSearchFiltersIntegrationTest#filterByValueAsString}
 </div>
 </li>
 <li class=change-in-release>While before the Search Model was built using the structure {@code attribute + action + parameters}, now it changed to {@code action + attribute + parameters}, in order to split the model into 3 independent models: Search Filter Model, Search Facet Model and Search Sort Model.
 <div class="rn-hidden">
 Before it looked like:
 <pre>{@code
 ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
 .plusQueryFilters(product -> product.name().locale(ENGLISH).filtered().by("shoes"));
 PagedSearchResult<ProductProjection> result = client.execute(search);
 }</pre>

 Now it is built as follows:
 <pre>{@code
 ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
 .plusQueryFilters(filter -> filter.name().locale(ENGLISH).by("shoes"));
 PagedSearchResult<ProductProjection> result = client.execute(search);
 }</pre>
 </div>
 </li>
 <li class=change-in-release>Renamed {@code SearchSort} to {@link io.sphere.sdk.search.SortExpression}, which now shares the same properties as {@link io.sphere.sdk.search.FacetExpression} and {@link io.sphere.sdk.search.FilterExpression} under the {@link io.sphere.sdk.search.SearchExpression} interface.</li>
 <li class=change-in-release>The static factory method to directly build unsafe {@link io.sphere.sdk.search.FacetExpression} is now located in {@link io.sphere.sdk.search.TermFacetExpression}, {@link io.sphere.sdk.search.RangeFacetExpression} and {@link io.sphere.sdk.search.FilteredFacetExpression}, so that a facet expression for the corresponding type is obtained.</li>
 <li class=change-in-release>Moved related Search Model classes from {@code io.sphere.sdk.search} to {@code io.sphere.sdk.search.model} package, to clearly separate the Search Model from the Search API classes.</li>
 <li class=removed-in-release>Removed type parameters from {@link io.sphere.sdk.search.TermFacetResult}, {@link io.sphere.sdk.search.RangeFacetResult} and {@link io.sphere.sdk.search.FilteredFacetResult}, which now return simple strings as they are received from the platform.</li>
 <li class=new-in-release>Added {@link ProductProjection#getCategoryOrderHints()} and {@link ProductProjectionQueryModel#categoryOrderHints()}. It can be used for search as shown in the following example, but the meta model comes in a later release.
  <div class="rn-hidden">{@include.example io.sphere.sdk.products.ProductCategoryOrderHintTest#searchForCategoryAndSort()}</div>
 </li>
 <li class=new-in-release>Added states and update actions for Orders, Reviews and Products: {@link Order#getState()}, {@link io.sphere.sdk.products.Product#getState()}, {@code io.sphere.sdk.reviews.Review#getState()}</li>
 <li class=new-in-release>Added {@link LineItem#getTotalPrice()}, {@link LineItem#getDiscountedPricePerQuantity()}, {@link CustomLineItem#getTotalPrice()} and {@link CustomLineItem#getDiscountedPricePerQuantity()}</li>
 <li class=change-in-release>Deprecated {@link LineItem#getDiscountedPrice()} and {@link CustomLineItem#getDiscountedPrice()} since they are deprecated in the HTTP API</li>
 <li class=new-in-release>Improved documentation of {@link io.sphere.sdk.models.Versioned}.</li>
 <li class=new-in-release>Added {@link io.sphere.sdk.payments.Payment}.</li>
 <li class=change-in-release>Deprecated typo method {@code io.sphere.sdk.carts.CartDraft#witCustom(CustomFieldsDraft)}, use {@code io.sphere.sdk.carts.CartDraft#withCustom(CustomFieldsDraft)} instead.</li>
 <li class=change-in-release>Deprecated {@link io.sphere.sdk.customers.queries.CustomerByTokenGet}, use {@link io.sphere.sdk.customers.queries.CustomerByPasswordTokenGet} instead.</li>
 <li class=new-in-release>Added {@link io.sphere.sdk.customobjects.queries.CustomObjectByIdGet} and {@link CustomObjectDeleteCommand#of(String, Long, Class)} which can delete a {@link CustomObject} by ID.</li>
 <li class=change-in-release>Simplified CustomObject endpoints. Instead of a {@link com.fasterxml.jackson.core.type.TypeReference} for the endpoint result only the class of the value of the custom object needs to be provided.
 <div class="rn-hidden">
 <p>Before:</p>
 <pre>{@code final CustomObjectQuery<Pojo> query = CustomObjectQuery.of(new TypeReference<PagedQueryResult<CustomObject<Pojo>>>(){});}</pre>
 <p>After:</p>
 <pre>{@code final CustomObjectQuery<Pojo> query = CustomObjectQuery.of(Pojo.class);}</pre>

 <p>This change applies for other endpoints, too.</p>
 </div>
 </li>
 <li class=change-in-release>
 Deprecations in custom object endpoints:
 <dl>
 <dt>{@link CustomObjectDeleteCommand#of(io.sphere.sdk.customobjects.CustomObject)}</dt>
 <dd>use instead {@link CustomObjectDeleteCommand#ofJsonNode(CustomObject)}</dd>

 <dt>{@link CustomObjectDeleteCommand#of(java.lang.String, java.lang.String)}</dt>
 <dd>use instead  {@link CustomObjectDeleteCommand#ofJsonNode(java.lang.String, java.lang.String)}</dd>
 <dt>{@link CustomObjectByKeyGet#of(java.lang.String, java.lang.String)}</dt>
 <dd>use instead {@link CustomObjectByKeyGet#ofJsonNode(java.lang.String, java.lang.String)}</dd>
 <dt>{@link CustomObjectQuery#of()}</dt>
 <dd>use instead {@link CustomObjectQuery#ofJsonNode()}</dd>

 </dl>
 </li>
 <li class=fixed-in-release>Don't include customer password in logs. See <a href="https://github.com/sphereio/sphere-jvm-sdk/issues/767">767</a>.</li>
 </ul>



 <h3 class=released-version id="v1_0_0_M18">1.0.0-M18 (01.10.2015)</h3>

 <ul>
 <li class=new-in-release>{@link io.sphere.sdk.products.search.ProductProjectionSearch#withFuzzy(Boolean)}</li>
 <li class=new-in-release>documentation of the customer flow in {@link io.sphere.sdk.customers.Customer}</li>
 <li class=new-in-release>{@link io.sphere.sdk.types.Custom custom fields}</li>
 <li class=new-in-release>Improved error reporting for status code 413 and 414.</li>

 <li class=change-in-release>{@link io.sphere.sdk.search.TermStats} contains not anymore "T" and "F" but "true" and "false".</li>
 <li class=change-in-release>deprecate {@link io.sphere.sdk.customers.commands.CustomerCreateTokenCommand}, use {@link io.sphere.sdk.customers.commands.CustomerCreatePasswordTokenCommand} instead</li>
 <li class=fixed-in-release>DiscountedLineItemPrice money field is called value not money. See <a href="https://github.com/sphereio/sphere-jvm-sdk/issues/711">711</a>.</li>
 </ul>


 <h3 class=released-version id="v1_0_0_M17">1.0.0-M17 (28.08.2015)</h3>

 <ul>
 <li class=new-in-release>Support of full locales like "de_DE"</li>
 <li class=new-in-release>{@link io.sphere.sdk.products.ProductProjection} is also a {@link io.sphere.sdk.models.Versioned} for {@link io.sphere.sdk.products.Product}</li>
 <li class=new-in-release>{@link AttributeAccess#ofInteger()}, {@link AttributeAccess#ofIntegerSet()}, {@link AttributeAccess#ofLong()}, {@link AttributeAccess#ofLongSet()}</li>
 <li class=new-in-release>{@link io.sphere.sdk.taxcategories.commands.TaxCategoryUpdateCommand}</li>
 <li class=new-in-release>{@link io.sphere.sdk.products.ProductDraftBuilder#taxCategory(Referenceable)}, {@link io.sphere.sdk.products.ProductDraftBuilder#searchKeywords(SearchKeywords)}</li>
 <li class=new-in-release>{@code Reference<Void>} and {@code Reference<Object>} is now {@code Reference<JsonNode>}</li>
 <li class=new-in-release>{@link UpdateCommand#getUpdateActions()}</li>
 <li class=new-in-release>{@link io.sphere.sdk.products.messages.ProductPublishedMessage}, {@link io.sphere.sdk.products.messages.ProductUnpublishedMessage}</li>
 <li class=new-in-release>{@link io.sphere.sdk.models.Address#equalsIgnoreId(Address)}, {@link io.sphere.sdk.products.Price#equalsIgnoreId(Price)}, {@link io.sphere.sdk.taxcategories.TaxRate#equalsIgnoreId(TaxRate)}</li>
 </ul>

 <h3 class=released-version id="v1_0_0_M16">1.0.0-M16 (06.08.2015)</h3>
 <p>This release is intended to be the last release before 1.0.0-RC1.</p>

 <ul>
    <li class=new-in-release>{@link io.sphere.sdk.productdiscounts.commands.ProductDiscountCreateCommand},
 {@link io.sphere.sdk.productdiscounts.commands.ProductDiscountUpdateCommand},
 {@link io.sphere.sdk.productdiscounts.commands.ProductDiscountDeleteCommand},
 {@link ProductDiscountByIdGet},
 {@link io.sphere.sdk.productdiscounts.queries.ProductDiscountQuery}
 </li>
 <li class=new-in-release>More reference expansion for product fields.</li>
 <li class=new-in-release>Added a {@link ProductAttributeDocumentation product attribute tutorial}.</li>
 <li class=new-in-release>Added a  {@link CategoryDocumentation category tutorial}.</li>
 <li class=new-in-release>Added the {@link io.sphere.sdk.messages.queries.MessageQuery message endpoint}.</li>
 <li class=new-in-release>Added query and expansion meta models for carts.</li>
 <li class=new-in-release>{@link io.sphere.sdk.utils.MoneyImpl#ofCents(long, CurrencyUnit)} can be used to create monetary values</li>
 <li class=new-in-release>Customer can be created with attributes dateOfBirth, companyName, vatId, isEmailVerified, customerGroup and addresses.</li>
 <li class=new-in-release>External images can be included in the product creation.</li>
 <li class=new-in-release>Added {@link io.sphere.sdk.inventory.commands.updateactions.ChangeQuantity} to update {@link io.sphere.sdk.inventory.InventoryEntry}s.</li>
 <li class=new-in-release>Added {@link io.sphere.sdk.channels.ChannelRole#PRODUCT_DISTRIBUTION}.</li>
 <li class=new-in-release>Added {@link LineItem#getDistributionChannel()}.</li>
 <li class=new-in-release>Meta models can be used in the product search, too. See {@link io.sphere.sdk.products.search.ProductProjectionSearch#plusFacetFilters(Function)}.</li>
 <li class=new-in-release>Added documentation how to query for large offsets in {@link QueryDocumentation}.</li>
 <li class=new-in-release>Added hints how to format date and monetary data in {@link FormattingDocumentation}.</li>
 <li class=new-in-release>Added {@link io.sphere.sdk.producttypes.commands.ProductTypeUpdateCommand}.</li>
 <li class=new-in-release>To imrove the query speed the calculation of the total amount of items in commercetools platform can be deactivated with {@link io.sphere.sdk.products.queries.ProductQuery#withFetchTotal(boolean)}.</li>
 <li class=new-in-release>{@link QueryPredicate}s can be negated with {@link QueryPredicate#negate()}</li>
 <li class=new-in-release>Added {@link io.sphere.sdk.carts.CartState#ORDERED} and {@link Order#getCart()}.</li>
 <li class=new-in-release>Added {@link io.sphere.sdk.products.ProductProjection#findVariantBySky(String)}.</li>
 <li class=new-in-release>Reference expansion for the product projection search.</li>
 <li class=change-in-release>The default behaviour of queries changed, things are not sorted by ID by default for performance reasons. The impact is that performing the exact query again may yields different results.</li>
 <li class=change-in-release>{@link java.util.Optional} as return type for optional values has been removed, now the annotation {@link javax.annotation.Nullable} is used to mark objects as not mandatory. This change was necessary to provide a stable API, to provide later serializable Objects and sparse representations for objects.</li>
 <li class=change-in-release>For API read objects primitives have been replaced by wrapper classed to implement later sparse representations. This may affect type conversions which won't work anymore.</li>
 <li class=change-in-release>The instantiation of the {@link io.sphere.sdk.client.SphereClient} has been changed, see {@link GettingStarted} and {@link io.sphere.sdk.client.SphereClientFactory}.</li>
 <li class=change-in-release>Product type creation has been refactored. Have a look at {@link ProductAttributeDocumentation product attribute tutorial}.</li>
 <li class=change-in-release>Product prices have an ID and price updates need to be performed via the price ID: {@link io.sphere.sdk.products.commands.updateactions.ChangePrice}, {@link io.sphere.sdk.products.commands.updateactions.RemovePrice}. Keep in mind that {@link io.sphere.sdk.products.Price#equals(Object)} includes the ID.</li>
 <li class=change-in-release>The naming conventions in the commercetools platform have been changed:
   <ul>
   <li>PlainEnumValue is now EnumValue</li>
   <li>LocalizedStrings is now LocalizedString</li>
   <li>LocalizedStringsEntry is now LocalizedStringEntry</li>
   <li>Fetch is now Get (e.g. {@code ProductProjectionByIdFetch} is now {@link ProductProjectionByIdGet}</li>
   </ul>
 </li>

 <li class=change-in-release>{@code JsonUtils} have been renamed to {@link io.sphere.sdk.json.SphereJsonUtils}, this is an internal utility class working for the commercetools platform context, it is not intended to build apps or libs on it.</li>
 <li class=change-in-release>{@code ChannelRoles} have been renamed to {@link io.sphere.sdk.channels.ChannelRole}.</li>
 <li class=change-in-release>{@code SearchText} is now {@link io.sphere.sdk.models.LocalizedStringEntry}</li>
 <li class=removed-in-release>Removed {@code ProductUpdateScope}, so all product update actions update only staged.</li>
 <li class=removed-in-release>Builders for resources (read objects) have been removed. See {@link TestingDocumentation} to build the objects anyway.</li>
 <li class=removed-in-release>QueryToFetch adapter has been removed.</li>

 <li class=fixed-in-release>{@link io.sphere.sdk.queries.StringQueryModel#isGreaterThan(Object)} and other methods do not quote strings correctly. See <a href="https://github.com/sphereio/sphere-jvm-sdk/issues/558">558</a>.</li>
 <li class=fixed-in-release>Product variant expansion does not work. See <a href="https://github.com/sphereio/sphere-jvm-sdk/issues/631">631</a>.</li>

 </ul>


 <h3 class=released-version id="v1_0_0_M15">1.0.0-M15 (23.06.2015)</h3>

 <a class="theme-btn expand-all">Expand all</a>

 <br>
 <br>

 <ul>
 <li class=new-in-release>{@code io.sphere.sdk.products.search.ExperimentalProductProjectionSearchModel#productType()} enables you to build search expressions with the Product Type reference of products</li>
 <li class=new-in-release>{@link ProductVariant#getIdentifier()} enables to get the product id and the variant id from the variant. This is nice, since this data is often needed and on the product variant level the product ID is not available.</li>
 <li class=new-in-release>Error reporting has been improved. Especially if JSON mappings do not fit.</li>
 <li class=new-in-release>{@link LineItem#getProductSlug()}</li>
 <li class=new-in-release>Send User-Agent head for oauth requests.</li>
 <li class=new-in-release>A lot of more reference expansions.</li>
 <li class=new-in-release>Reference expansion for single elements of a list, example: {@link io.sphere.sdk.categories.expansion.CategoryExpansionModel#ancestors(int)}.</li>
 <li class=new-in-release>Reference expansion for fetch endpoints, e.g., {@link ProductProjectionByIdGet#withExpansionPaths(ExpansionPath)}.</li>
 <li class=new-in-release>Set the reference expansion, query predicate and sort expression more convenient (less imports, less class search) with lambdas.
 <div class="rn-hidden">
 Migration pains: {@code QueryDsl<Category>} will be {@code CategoryQuery}

 <p>Benefits of the new API:</p>

 {@include.example example.QueryMetaDslDemo#demo1()}

 instead of

 {@include.example example.QueryMetaDslDemo#demo2()}

 In Scala (with add-ons) it will look like <pre><code>val query = ProductProjectionQuery.ofCurrent()
 .withPredicateScala(_.productType.id.is("product-type-id-1"))
 .withSortScala(_.name.lang(ENGLISH).sort.asc)
 .withExpansionPathsScala(_.productType)</code></pre>
 </div>
 </li>
 <li class=new-in-release>
 DSL for expansion of references in a product attribute.
 <div class="rn-hidden">{@include.example io.sphere.sdk.products.expansion.ProductVariantExpansionModelTest#productAttributeReferenceExpansion()}</div>
 </li>
 <li class=new-in-release>{@code io.sphere.sdk.reviews.commands.ReviewDeleteCommand}</li>
 <li class=new-in-release>{@link io.sphere.sdk.client.SphereClientConfig#ofEnvironmentVariables(String)} to get the </li>
 <li class=change-in-release>The product attributes have been refactored, look at the {@link ProductAttributeDocumentation} how it works now.</li>
 <li class=change-in-release>{@link io.sphere.sdk.client.SphereClient} implements {@link AutoCloseable} instead of {@link java.io.Closeable}.</li>
 <li class=change-in-release>For timestamps we moved from {@link java.time.Instant} to {@link java.time.ZonedDateTime} since the latter also contains a timezone which better reflects SPHERE.IOs date time data.</li>
 <li class=change-in-release>Getting the child categories of a category is not in category anymore but in {@link io.sphere.sdk.categories.CategoryTree#findChildren(Identifiable)}.</li>
 <li class=fixed-in-release>Sphere client does not shutdown actors properly.  See <a target="_blank" href="https://github.com/sphereio/sphere-jvm-sdk/issues/491">#491</a>.</li>
 <li class=removed-in-release>{@code Category#getPathInTree()}</li>
 <li class=removed-in-release>{@code ExperimentalProductImageUploadCommand}, but you can find a similar command here: <a href="https://github.com/sphereio/sphere-jvm-sdk-experimental-java-add-ons">https://github.com/sphereio/sphere-jvm-sdk-experimental-java-add-ons</a></li>
 </ul>




 <h3 class=released-version id=v1_0_0_M14>1.0.0-M14 (27.05.2015)</h3>
 <ul>
 <li class=new-in-release>New fields in {@link io.sphere.sdk.products.Price}: {@link io.sphere.sdk.products.Price#validFrom} and {@link io.sphere.sdk.products.Price#validUntil}.</li>
 <li class=new-in-release>Use {@link io.sphere.sdk.products.queries.ProductProjectionQueryModel#allVariants()} to formulate a predicate for all variants. In SPHERE.IO the json fields masterVariant (object) and variants (array of objects) together contain all variants.</li>
 <li class=new-in-release>Using {@link ProductProjectionQuery#ofCurrent()} and {@link ProductProjectionQuery#ofStaged()} saves you the import of {@link ProductProjectionType}.</li>
 <li class=new-in-release>{@link CompletionStage} does not support by default timeouts which are quite important in a reactive application so you can decorate the {@link io.sphere.sdk.client.SphereClient} with {@link io.sphere.sdk.client.TimeoutSphereClientDecorator} to get a {@link java.util.concurrent.TimeoutException} after a certain amount of time. But this does NOT cancel the request to SPHERE.IO.</li>
 <li class=new-in-release>The {@code io.sphere.sdk.reviews.Review} endpoints and models are implemented, but we suggest to not use it, since {@code io.sphere.sdk.reviews.Review}s cannot be deleted or marked as hidden.</li>
 <li class=new-in-release>New endpoint: Use {@link io.sphere.sdk.projects.queries.ProjectGet} to get the currencies, countries and languages of the SPHERE.IO project.</li>
 <li class=new-in-release>Categories with SEO meta attributes {@link Category#getMetaTitle()}, {@link Category#getMetaDescription()} and {@link Category#getMetaKeywords()} and
 update actions {@link io.sphere.sdk.categories.commands.updateactions.SetMetaTitle}, {@link io.sphere.sdk.categories.commands.updateactions.SetMetaDescription} and {@link io.sphere.sdk.categories.commands.updateactions.SetMetaKeywords}.</li>
 <li class=new-in-release>Cart discounts: {@link io.sphere.sdk.cartdiscounts.commands.CartDiscountCreateCommand}.</li>
 <li class=new-in-release>Discount codes: {@link io.sphere.sdk.discountcodes.commands.DiscountCodeCreateCommand}.</li>
 <li class=change-in-release>{@link io.sphere.sdk.client.SphereApiConfig}, {@link io.sphere.sdk.client.SphereAuthConfig}, {@link io.sphere.sdk.client.SphereClientConfig} validates the input, so for example you cannot enter null or whitespace for the project key.</li>
 <li class=change-in-release>Date time attributes in {@code ProductProjectionSearchModel} are using <a href="https://docs.oracle.com/javase/8/docs/api/java/time/ZonedDateTime.html">ZonedDateTime</a> instead of <a href="https://docs.oracle.com/javase/8/docs/api/java/time/LocalDateTime.html">LocalDateTime</a>.</li>
 <li class=change-in-release>The {@code ProductProjectionSearchModel} has been improved with better naming and better documentation.</li>
 <li class=change-in-release>Sort related classes for the Query API have been renamed with a "Query" prefix, to distinguish them from the Search API sort classes.</li>
 <li class=change-in-release>{@code io.sphere.sdk.queries.Predicate} has been renamed to {@link io.sphere.sdk.queries.QueryPredicate}.</li>
 <li class=change-in-release>The JVM SDK itself uses for tests the <a href="http://joel-costigliola.github.io/assertj/">assertj</a> assertion methods instead of fest assertions.</li>
 <li class=change-in-release>{@code io.sphere.sdk.products.commands.updateactions.SetMetaAttributes} has been removed since it is deprecated in SPHERE.IO.
 Use {@link SetMetaTitle},
 {@link SetMetaDescription},
 {@link SetMetaKeywords} or {@link io.sphere.sdk.products.commands.updateactions.MetaAttributesUpdateActions} for all together.
 </li>
 <li class=change-in-release>Update of the Java Money library to version 1.0 which has some breaking changes in comparison to the release candidates.</li>
 <li class=change-in-release>Some predicate methods have been renamed: "isOneOf" to "isIn", "isAnyOf" to "isIn", "isLessThanOrEquals" to "isLessThanOrEqualTo" (analog for greaterThan).</li>
 <li class=fixed-in-release>The exception for a failing {@link io.sphere.sdk.customers.commands.CustomerSignInCommand} shows more problem details. See <a href="https://github.com/sphereio/sphere-jvm-sdk/issues/397">#397</a>.</li>
 <li class=fixed-in-release>There has been a thread leak if {@code io.sphere.sdk.client.SphereClientFactory#createClient(io.sphere.sdk.client.SphereClientConfig)} was used.
 It created twice a http client instance and closed just one.</li>
 </ul>

 <h3 class=released-version id=v1_0_0_M13>1.0.0-M13 (21.04.2015)</h3>
 <ul>
     <li class=new-in-release>{@link LocalizedString#mapValue(BiFunction)} and {@link LocalizedString#stream()}
     can be used transform {@link LocalizedString}, for example for creating slugs or formatting.</li>
     <li class=new-in-release>Experimental {@link io.sphere.sdk.utils.CompletableFutureUtils} to work with Java 8 Futures.</li>
     <li class=new-in-release>{@link AsyncDocumentation} documents how to work with {@link java.util.concurrent.CompletableFuture} and {@link java.util.concurrent.CompletionStage}.</li>

     <li class=new-in-release>{@link io.sphere.sdk.models.SphereException}s may give hint to developers how to recover from errors. For example on typical elasticsearch related problems it suggests to reindex the product index.</li>
     <li class=new-in-release>The JVM SDK is available for Ning Async HTTP Client 1.8 and 1.9 (incompatible to 1.8).</li>
     <li class=new-in-release>State update actions and {@code io.sphere.sdk.states.StateBuilder} contributed by <a href="https://github.com/abrauner">Ansgar Brauner</a></li>
     <li class=new-in-release>Embedded predicates (as used in 'where'-clauses) now support lambda syntax: {@code ProductVariantQueryModel#where(java.util.function.Function)}.</li>
     <li class=new-in-release>{@link io.sphere.sdk.orders.queries.OrderQuery#byCustomerId(java.lang.String)} and {@link io.sphere.sdk.orders.queries.OrderQuery#byCustomerEmail(java.lang.String)}</li>
     <li class=new-in-release>{@link io.sphere.sdk.channels.commands.ChannelUpdateCommand}</li>
     <li class=new-in-release>{@link ChannelByIdGet}</li>
     <li class=new-in-release>{@link io.sphere.sdk.customers.queries.CustomerQuery#byEmail(java.lang.String)}</li>
     <li class=change-in-release>{@link io.sphere.sdk.client.SphereRequest} has been refactored:<ul>
         <li>{@code Function<HttpResponse, T> resultMapper()} is now {@link io.sphere.sdk.client.SphereRequest#deserialize(HttpResponse)}</li>
         <li>{@code boolean canHandleResponse(final HttpResponse response)} is now {@link io.sphere.sdk.client.SphereRequest#canDeserialize(HttpResponse)}</li>
       </ul>
     </li>
     <li class=change-in-release>Artifact IDs start now with "sphere-".</li>
     <li class=change-in-release> {@link java.util.concurrent.CompletableFuture} has been replaced with {@link java.util.concurrent.CompletionStage}.

       You can convert from {@link java.util.concurrent.CompletionStage} to {@link java.util.concurrent.CompletableFuture} with {@link CompletionStage#toCompletableFuture()}.

       The opposite direction can be achieved with assigning {@link java.util.concurrent.CompletableFuture} (implementation) to {@link java.util.concurrent.CompletionStage} (interface).</li>
     <li class=change-in-release>Refactoring of {@link io.sphere.sdk.client.SphereClient}, {@code <T> CompletionStage<T> execute(final SphereRequest<T> sphereRequest)}
     is now {@link io.sphere.sdk.client.SphereClient#execute(SphereRequest)} which returns {@link java.util.concurrent.CompletionStage} instead of {@link java.util.concurrent.CompletableFuture}.
     </li>
     <li class=change-in-release>{@link  io.sphere.sdk.carts.LineItem#getTaxRate()} is optional.</li>
     <li class=change-in-release>{@link io.sphere.sdk.carts.LineItem#getState()} now returns a set instead of a list.</li>
     <li class=change-in-release>{@link io.sphere.sdk.products.ProductProjection#getCategories()} now returns a set instead of a list.</li>

    <li class=fixed-in-release>URL encoding of parameters. See <a href="https://github.com/sphereio/sphere-jvm-sdk/issues/240">#240</a>.</li>
 </ul>

 <h3 class=released-version id=v1_0_0_M12>1.0.0-M12 (19.03.2015)</h3>
 <ul>
 <li class=new-in-release>Added the {@link io.sphere.sdk.orders.commands.OrderImportCommand}.</li>
 <li class=new-in-release>Added the nested attributes: {@code io.sphere.sdk.attributes.AttributeAccess#ofNested()} + {@code io.sphere.sdk.attributes.AttributeAccess#ofNestedSet()}.</li>
 <li class=new-in-release>The error JSON body from SPHERE.IO responses can be directly extracted as JSON with {@link io.sphere.sdk.client.SphereServiceException#getJsonBody()}.</li>
 <li class=new-in-release>{@link io.sphere.sdk.http.HttpResponse} also contains {@link io.sphere.sdk.http.HttpHeaders}.</li>
 <li class=new-in-release>Experimental search filter/facet/sort expression model {@code ProductProjectionSearchModel}. See also {@link io.sphere.sdk.meta.SearchDocumentation}.</li>
 <li class=change-in-release>The {@link io.sphere.sdk.producttypes.ProductType} creation has been simplified (TextAttributeDefinition, LocalizedStringsAttributeDefinition, ... are just AttributeDefinition), see {@link io.sphere.sdk.producttypes.commands.ProductTypeCreateCommand} how to create them.</li>
 <li class=change-in-release>{@link io.sphere.sdk.search.TermFacetResult} and
 {@link io.sphere.sdk.search.RangeFacetResult} are using generics.
 {@link io.sphere.sdk.search.TermFacetResult} uses long instead of int for some methods like {@link io.sphere.sdk.search.TermFacetResult#getMissing()}.</li>
 <li class=change-in-release>Methods in {@link io.sphere.sdk.search.SearchDsl} have been renamed.</li>
 <li class=change-in-release>{@code io.sphere.sdk.search.RangeStats#getMean()} now returns a double.</li>
 <li class=change-in-release>{@link io.sphere.sdk.http.HttpHeaders} allows reoccurring headers.</li>
 <li class=change-in-release>Use of a new toString style, from <pre>io.sphere.sdk.client.HttpRequestIntent@7308a939[httpMethod=POST,path=/categories,headers={},body=Optional[io.sphere.sdk.http.StringHttpRequestBody@216ec9be[body={invalidJson :)]]]</pre> to <pre>HttpRequestIntent[httpMethod=POST,path=/categories,headers={},body=Optional[StringHttpRequestBody[body={invalidJson :)]]]</pre></li>
 <li class=fixed-in-release>{@link io.sphere.sdk.client.ErrorResponseException#getMessage()} now returns also the project debug info.</li>
 <li class=fixed-in-release><a href="https://github.com/sphereio/sphere-jvm-sdk/issues/312">Incompatibility with Jackson 2.5.1</a> has been fixed. A cause message was "No suitable constructor found for type [simple type, class io.sphere.sdk.models.ImageImpl]: can not instantiate from JSON object (missing default constructor or creator, or perhaps need to add/enable type information?)"</li>
 </ul>

 <h3 class=released-version id=v1_0_0_M11>1.0.0-M11 (03.03.2015)</h3>
 <h4>Overall</h4>
 <ul>
    <li class=new-in-release>Code examples contain the links to the GitHub source code.</li>
    <li class=new-in-release>The {@link io.sphere.sdk.client.SphereClient} architecture has been refactored, so it is now possible to inject access token suppliers and custom HTTP clients.
        <ul>
            <li>{@link AsyncHttpClientAdapter} enables to use a custom underlying Ning HTTP client for settings like proxies or max connections per host.</li>
            <li>The new module {@code java-client-apache-async} contains an {@link ApacheHttpClientAdapter adapter} to use the Apache HTTP client instead of the current default client Ning.</li>
            <li>The {@link io.sphere.sdk.client.QueueSphereClientDecorator} enables to limit the amount of concurrent requests to SPHERE.IO with a task queue.</li>
            <li>{@link io.sphere.sdk.client.SphereAccessTokenSupplierFactory} is a starting point to create custom access token suppliers for one token (either fetched from SPHERE.IO or as String) or auto refreshing for online shops.</li>
        </ul>
    </li>
    <li class=new-in-release>Added {@link io.sphere.sdk.client.SphereRequestDecorator} to decorate {@link io.sphere.sdk.client.SphereRequest}s.</li>
    <li class=change-in-release>{@link io.sphere.sdk.meta.ExceptionDocumentation Exception hierarchy}, relocated some exceptions and deleted some.
        <ul>
            <li>Removed SphereBackendException, SphereClientException, JavaConcurrentUtils, Requestable</li>
            <li>Removed ReferenceExistsException, usage as {@code SphereError} from a {@link io.sphere.sdk.client.ErrorResponseException}</li>
            <li>JsonParseException is now {@link io.sphere.sdk.json.JsonException}.</li>
            <li>InvalidQueryOffsetException is replaced with {@link java.lang.IllegalArgumentException}.</li>
        </ul>
    </li>
    <li class=change-in-release>For SDK devs: {@link io.sphere.sdk.http.HttpRequest} has changed tasks and structure, now it contains the full information for a HTTP request whereas now {@link io.sphere.sdk.client.HttpRequestIntent} is an element to describe an endpoint of sphere project independent.</li>
    <li class=change-in-release>For SDK devs: {@link io.sphere.sdk.client.JsonEndpoint} moved to the client package</li>
    <li class=change-in-release>{@link Get} class names end now with Fetch for consistency, so for example {@code CartFetchById} is now {@link CartByIdGet}.</li>
    <li class=change-in-release>{@link io.sphere.sdk.commands.DeleteCommand} implementations don't have {@code ById} or {@code ByKey} in the class name and the {@code of} factory method returns the interface, not the implementation, example {@link io.sphere.sdk.categories.commands.CategoryDeleteCommand#of(io.sphere.sdk.models.Versioned)}.</li>
    <li class=change-in-release>LocalizedText* classes have been renamed to LocalizedString.</li>
    <li class=fixed-in-release>Fixed: UnknownCurrencyException <a href="https://github.com/sphereio/sphere-jvm-sdk/issues/264">#264</a>.</li>
 </ul>

 <h4>States</h4>
 <ul>
    <li class=new-in-release>{@link io.sphere.sdk.states.State} models, creation and deletion contributed by <a href="https://github.com/abrauner">Ansgar Brauner</a></li>
 </ul>

 <h4>Products</h4>
 <ul>
 <li class=new-in-release>Added update actions: {@link io.sphere.sdk.products.commands.updateactions.AddVariant}, {@link io.sphere.sdk.products.commands.updateactions.RemoveFromCategory}, {@link io.sphere.sdk.products.commands.updateactions.RemoveVariant}, {@link io.sphere.sdk.products.commands.updateactions.RevertStagedChanges}, {@link io.sphere.sdk.products.commands.updateactions.SetAttribute}, {@link io.sphere.sdk.products.commands.updateactions.SetAttributeInAllVariants}, {@link io.sphere.sdk.products.commands.updateactions.SetSearchKeywords}</li>
 </ul>

 <h3 class=released-version id=v1_0_0_M10>1.0.0-M10 (26.01.2015)</h3>
 <ul>
 <li class=new-in-release>Added {@link io.sphere.sdk.customobjects.CustomObject} models and endpoints. There is also a {@link io.sphere.sdk.meta.CustomObjectDocumentation tutorial for custom objects}.</li>
 <li class=new-in-release>Added the {@link io.sphere.sdk.zones.Zone} models and endpoints.</li>
 <li class=new-in-release>Added the {@link io.sphere.sdk.shippingmethods.ShippingMethod} models and endpoints.</li>
 <li class=new-in-release>Added Typesafe Activator files, so you can edit the SDK on UNIX with {@code ./activator ui} or on Windows with {@code activator ui}.</li>
 <li class=new-in-release>Added io.sphere.sdk.client.ConcurrentModificationException which is thrown when an {@link io.sphere.sdk.commands.UpdateCommand} fails because of concurrent usage.</li>
 <li class=new-in-release>Added io.sphere.sdk.client.ReferenceExistsException which is thrown when executing a {@link io.sphere.sdk.commands.DeleteCommand} and the resource is referenced by another resource and cannot be deleted before deleting the other resource.</li>
 <li class=new-in-release>Added io.sphere.sdk.queries.InvalidQueryOffsetException which is thrown if offset is
 not between {@value io.sphere.sdk.queries.Query#MIN_OFFSET} and {@value io.sphere.sdk.queries.Query#MAX_OFFSET}..</li>
 <li class=new-in-release>Improved on different location the structure of interfaces so important methods are highlighted bold in the IDE.</li>
 <li class=new-in-release>JSON mapping errors are better logged.</li>
 <li class=new-in-release>Added update actions for cart: {@link io.sphere.sdk.carts.commands.updateactions.SetShippingMethod} and {@link io.sphere.sdk.carts.commands.updateactions.SetCustomerId}.</li>
 <li class=new-in-release>Added update actions for customer: {@link io.sphere.sdk.carts.commands.updateactions.SetCustomerId}.</li>
 <li class=new-in-release>Added {@link io.sphere.sdk.models.Referenceable#hasSameIdAs(io.sphere.sdk.models.Referenceable)} to check if a similar object has the same ID.</li>
 <li class=new-in-release>Added {@code AttributeAccess#ofName(String)} as alias to {@code io.sphere.sdk.attributes.AttributeAccess#getterSetter(String)}.</li>

 <li class=new-in-release>Update action list in update commands do not have the type {@literal List<UpdateAction<T>>} {@literal  List<? extends UpdateAction<T>>}, so you can pass a list of a subclass of {@link UpdateActionImpl}.
 Example: {@literal List<ChangeName>} can be assigned where {@literal ChangeName} extends {@link UpdateActionImpl}.</li>

 <li class=new-in-release>Added {@link io.sphere.sdk.models.Address#of(com.neovisionaries.i18n.CountryCode)}.</li>
 <li class=new-in-release>Added {@code io.sphere.sdk.carts.Cart#getLineItem(String)} and {@code io.sphere.sdk.carts.Cart#getCustomLineItem(String)} to find items in a cart.</li>
 <li class=new-in-release>Added {@link io.sphere.sdk.products.ProductProjection#getAllVariants()} to receive master variant and all other variants in one call. {@link io.sphere.sdk.products.ProductProjection#getVariants()} just returns all variants except the master variant.</li>
 <li class=new-in-release>Added {@link io.sphere.sdk.products.ProductProjection#getVariant(int)} and {@link io.sphere.sdk.products.ProductProjection#getVariantOrMaster(int)} to find a product variant by id.</li>
 <li class=new-in-release>Added {@code VariantIdentifier} to have a container to address product variants which needs a product ID and a variant ID.</li>
 <li class=new-in-release>added {@link io.sphere.sdk.customers.commands.CustomerDeleteCommand} to delete customers.</li>
 <li class=new-in-release>Added {@link io.sphere.sdk.products.commands.updateactions.AddExternalImage} to connect products with images not hosted by SPHERE.IO.</li>
 <li class=new-in-release>Added {@link io.sphere.sdk.products.commands.updateactions.RemoveImage} to disconnect images from a product (external images and SPHERE.IO hosted).</li>
 <li class=new-in-release>Added {@link io.sphere.sdk.client.SphereAccessTokenSupplier} as authentication method in the {@link io.sphere.sdk.client.SphereClient}.
 It is possible to automatically refresh a token or just pass a token to the client, see {@link io.sphere.sdk.client.SphereClientFactory#createClient(io.sphere.sdk.client.SphereApiConfig, io.sphere.sdk.client.SphereAccessTokenSupplier)} and {@link io.sphere.sdk.client.SphereAccessTokenSupplier#ofConstantToken(String)}.</li>


 <li class=change-in-release>Product variants are all of type int, was int and long before.</li>
 <li class=change-in-release>{@link io.sphere.sdk.models.Reference} is not instantiated with new.</li>
 <li class=change-in-release>{@link io.sphere.sdk.http.UrlQueryBuilder} is not instantiated with new.</li>
 <li class=change-in-release>io.sphere.sdk.client.SphereErrorResponse is not instantiated with new.</li>
 <li class=change-in-release>{@code ClientRequest} has been renamed to {@link io.sphere.sdk.client.SphereRequest} and therefore {@code ClientRequestBase} to {@code io.sphere.sdk.client.SphereRequestBase}. </li>
 <li class=change-in-release>{@code ClientRequest} has been renamed to {@link io.sphere.sdk.client.SphereRequest} and therefore {@code ClientRequestBase} to {@code io.sphere.sdk.client.SphereRequestBase}. </li>
 <li class=change-in-release>{@code JavaClient} has been renamed to {@link io.sphere.sdk.client.SphereClient} and uses the {@link io.sphere.sdk.client.SphereClientFactory} to initialized a client, {@code JavaClientIml} has been removed, see {@link io.sphere.sdk.meta.GettingStarted}.
 The typesafe config library is not used anymore. The class {@code HttpClientTestDouble} has been removed, use {@link io.sphere.sdk.client.SphereClientFactory#createHttpTestDouble(java.util.function.Function)} instead.
 {@code SphereRequestExecutor} and {@code SphereRequestExecutorTestDouble} have been removed, use {@link io.sphere.sdk.client.SphereClientFactory#createObjectTestDouble(java.util.function.Function)} instead.
 </li>


 <li class=fixed-in-release>Money portions in the taxed price is not null. The method name is now {@link io.sphere.sdk.carts.TaxPortion#getAmount()} instead of {@code getMoney()}.</li>
 <li class=fixed-in-release>Fixed JSON serialization and deserialization of {@code ImageDimensions} which caused adding external images to a product to fail.</li>
 </ul>

 <h3>1.0.0-M9</h3>
 <ul>
 <li>Added Known Issues page.</li>
 <li>Added experimental support for uploading product images in variants. See {@code io.sphere.sdk.products.commands.ExperimentalProductImageUploadCommand}.</li>
 <li>Added factory methods for {@code Image}.</li>
 <li>{@code Image} contains directly getters for width {@code Image#getWidth()}
 and height {@code Image#getHeight()}.</li>
 <li>{@link io.sphere.sdk.queries.PagedQueryResult} is constructable for empty results. Before this, the SDK throwed an Exception.</li>
 <li>Fields called {@code quantity} are now of type long instead of int.</li>
 <li>Added a documentation page {@link io.sphere.sdk.meta.ConstructionDocumentation how to construct objects}.</li>
 </ul>

 <h3>1.0.0-M8</h3>

 <ul>
 <li>Query models contain id, createdAt and lastModifiedAt for predicates and sorting.</li>
 <li>Introduced endpoints and models for {@link io.sphere.sdk.carts.Cart}s, {@link io.sphere.sdk.customers.Customer}s, {@link io.sphere.sdk.customergroups.CustomerGroup}s and {@link io.sphere.sdk.orders.Order}s.</li>
 <li>Quantity fields are now of type long.</li>
 <li>Classes like {@link ProductByIdGet} take now a string parameter for the ID and not an {@link io.sphere.sdk.models.Identifiable}.</li>
 <li>Queries, Fetches, Commands and Searches are only instantiable with an static of method like {@link io.sphere.sdk.categories.commands.CategoryCreateCommand#of(io.sphere.sdk.categories.CategoryDraft)}. The instantiation by constructor is not supported anymore.</li>
 <li>Enum constant names are only in upper case.</li>
 </ul>

 <h3>1.0.0-M7</h3>

 <ul>
 <li>Incompatible change: Classes to create templates for new entries in SPHERE.IO like {@code NewCategory} have been renamed to {@link io.sphere.sdk.categories.CategoryDraft}. </li>
 <li>Incompatible change: {@link ProductTypeDraft} has now only
 factory methods with an explicit parameter for the attribute declarations to prevent to use
 the getter {@link ProductTypeDraft#getAttributes()} and list add operations. </li>
 <li>Incompatible change: {@code LocalizedString} has been renamed to {@link LocalizedString}, since it is not a container for one string and a locale, but for multiple strings of different locals. It is like a map.</li>
 <li>Incompatible change: The {@link Get} classes have been renamed. From FetchRESOURCEByWhatever to RESOURCEFetchByWhatever</li>
 <li>Moved Scala and Play clients out of the Git repository to <a href="https://github.com/sphereio/sphere-jvm-sdk-scala-add-ons">https://github.com/sphereio/sphere-jvm-sdk-scala-add-ons</a>. The artifact ID changed.</li>
 <li>{@link io.sphere.sdk.meta.SphereResources} contains now also a listing of queries and commands for the resources.</li>
 <li>Added {@link io.sphere.sdk.products.search.ProductProjectionSearch} for full-text, filtered and faceted search.</li>
 <li>Incompatible change: {@code io.sphere.sdk.products.ProductUpdateScope} makes it more visible that product update operations can be for only staged or for current and staged. The product update actions will be affected by that.</li>
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
 <li>The query model can now be accessed by it's Query class, e.g., {@code io.sphere.sdk.categories.queries.CategoryQuery#model()}.</li>
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
 <li>Introduced new predicates for inequality like {@link io.sphere.sdk.queries.StringQueryModel#isGreaterThanOrEqualTo(String)},
 {@link io.sphere.sdk.queries.StringQueryModel#isNot(String)},
 {@code io.sphere.sdk.queries.StringQueryModel#isNotIn(String, String...)} or {@link io.sphere.sdk.queries.StringQueryModel#isNotPresent()}.</li>
 <li>Introduced an unsafe way to create predicates from strings with {@link QueryPredicate#of(String)}.</li>
 </ul>

 <h3>1.0.0-M2</h3>
 <ul>
 <li>With the new reference expansion dsl it is possible to discover and create reference expansion paths for a query.</li>
 <li>All artifacts have the ivy organization {@code io.sphere.jvmsdk}.</li>
 <li>Migration from Google Guavas com.google.common.util.concurrent.ListenableFuture to Java 8 java.util.concurrent.CompletableFuture.</li>
 <li>Removed all Google Guava classes from the public API (internally still used).</li>
 <li>The logger is more fine granular controllable, for example the logger {@code sphere.products.responses.queries} logs only the responses of the queries for products. The trace level logs the JSON of responses and requests as pretty printed JSON.</li>
 <li>Introduced the class {@code io.sphere.sdk.models.Referenceable} which enables to use a model or a reference to a model as parameter, so no direct call of {@code io.sphere.sdk.models.DefaultModel#toReference()} is needed anymore for model classes.</li>
 <li>It is possible to overwrite the error messages of {@code io.sphere.sdk.test.DefaultModelAssert}, {@code io.sphere.sdk.test.LocalizedStringAssert} and {@code io.sphere.sdk.test.ReferenceAssert}.</li>
 <li>{@link io.sphere.sdk.models.Versioned} contains a type parameter to prevent copy and paste errors.</li>
 <li>Sorting query model methods have better support in the IDE, important methods are bold.</li>
 <li>Queries and commands for models are in there own package now and less coupled to the model.</li>
 <li>The query classes have been refactored.</li>
 </ul>




 </div>
 */
public final class ReleaseNotes {
    private ReleaseNotes() {
    }
}
