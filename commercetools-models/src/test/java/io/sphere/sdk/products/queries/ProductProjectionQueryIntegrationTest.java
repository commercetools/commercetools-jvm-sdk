package io.sphere.sdk.products.queries;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.commands.CategoryUpdateCommand;
import io.sphere.sdk.categories.commands.updateactions.ChangeParent;
import io.sphere.sdk.channels.ChannelFixtures;
import io.sphere.sdk.channels.ChannelRole;
import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.http.NameValuePair;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.MetaAttributes;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.products.*;
import io.sphere.sdk.products.attributes.AttributeAccess;
import io.sphere.sdk.products.attributes.NamedAttributeAccess;
import io.sphere.sdk.products.commands.ProductUpdateCommand;
import io.sphere.sdk.products.commands.updateactions.*;
import io.sphere.sdk.selection.LocaleSelection;
import io.sphere.sdk.selection.StoreSelection;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.Query;
import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.stores.StoreFixtures;
import io.sphere.sdk.taxcategories.TaxCategoryFixtures;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.utils.MoneyImpl;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static io.sphere.sdk.categories.CategoryFixtures.withCategory;
import static io.sphere.sdk.customergroups.CustomerGroupFixtures.withCustomerGroup;
import static io.sphere.sdk.models.DefaultCurrencyUnits.EUR;
import static io.sphere.sdk.products.ProductFixtures.*;
import static io.sphere.sdk.products.ProductProjectionType.CURRENT;
import static io.sphere.sdk.products.ProductProjectionType.STAGED;
import static io.sphere.sdk.reviews.ReviewFixtures.withReview;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static java.util.Arrays.asList;
import static java.util.Locale.ENGLISH;
import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductProjectionQueryIntegrationTest extends IntegrationTest {
    public static final int MASTER_VARIANT_ID = 1;

    @Test
    public void expandProductReferencesInProductAttributes() throws Exception {
        withProductWithProductReference(client(), (product, referencedProduct) -> {
            final Query<ProductProjection> query = ProductProjectionQuery.ofStaged()
                    .withPredicates(m -> m.id().is(product.getId()))
                    .withExpansionPaths(m -> m.masterVariant().attributes().value())
                    .toQuery();
            final ProductProjection productProjection = client().executeBlocking(query).head().get();
            final NamedAttributeAccess<Reference<Product>> namedAttributeAccess = AttributeAccess.ofProductReference().ofName("productreference");
            final Reference<Product> productReference = productProjection.getMasterVariant().findAttribute(namedAttributeAccess).get();
            final Product expandedReferencedProduct = productReference.getObj();
            assertThat(expandedReferencedProduct.getId()).isEqualTo(referencedProduct.getId());
        });
    }

    @Test
    public void variantIdentifierIsAvailable() throws Exception {
        withProduct(client(), product -> {
            final Query<ProductProjection> query = ProductProjectionQuery.of(STAGED)
                    .withPredicates(m -> m.id().is(product.getId()));
            final ProductProjection productProjection = client().executeBlocking(query).head().get();
            final ByIdVariantIdentifier identifier = productProjection.getMasterVariant().getIdentifier();
            assertThat(identifier).isEqualTo(ByIdVariantIdentifier.of(product.getId(), 1));
        });
    }

    @Test
    public void differentiateBetweenCurrentAndStaged() throws Exception {
        withUpdateableProduct(client(), product -> {
            final Product publishedProduct = client().executeBlocking(ProductUpdateCommand.of(product, Publish.of()));
            final Product mixedDataProduct = client().executeBlocking(ProductUpdateCommand.of(publishedProduct, ChangeName.of(randomSlug())));
            final LocalizedString nameInCurrent = mixedDataProduct.getMasterData().getCurrent().getName();
            final LocalizedString nameInStaged = mixedDataProduct.getMasterData().getStaged().getName();

            final ProductProjectionQuery stagedQuery = ProductProjectionQuery.of(STAGED).withPredicates(m -> m.id().is(product.getId()));
            assertThat(client().executeBlocking(stagedQuery).head().get().getName()).isEqualTo(nameInStaged);
            final ProductProjectionQuery currentQuery = ProductProjectionQuery.of(CURRENT).withPredicates(m -> m.id().is(product.getId()));
            assertThat(client().executeBlocking(currentQuery).head().get().getName()).isEqualTo(nameInCurrent);

            return mixedDataProduct;
        });
    }

    @Test
    public void expandCustomerGroupInPrice() throws Exception {
        withCustomerGroup(client(), customerGroup -> {
                    withUpdateablePricedProduct(client(), PriceDraft.of(MoneyImpl.of(new BigDecimal("12.34"), EUR)).withCountry(DE).withCustomerGroup(customerGroup), product -> {
                        final Query<ProductProjection> query = ProductProjectionQuery.of(STAGED)
                                .withPredicates(m -> m.id().is(product.getId()))
                                .withExpansionPaths(m -> m.masterVariant().prices().customerGroup());
                        final List<Price> prices = client().executeBlocking(query).head().get().getMasterVariant().getPrices();
                        assertThat(prices
                                .stream()
                                .anyMatch(price -> Optional.ofNullable(price.getCustomerGroup()).map(customerGroupReference -> customerGroupReference.getObj() != null).orElse(false)))
                                .isTrue();
                        return product;
                    });
                }
        );
    }

    @Test
    public void expandChannelInPrice() throws Exception {
        ChannelFixtures.withChannelOfRole(client(), ChannelRole.INVENTORY_SUPPLY, channel -> {
            withUpdateablePricedProduct(client(), PriceDraft.of(MoneyImpl.of(new BigDecimal("12.34"), EUR)).withCountry(DE).withChannel(channel), product -> {
                final Query<ProductProjection> query = ProductProjectionQuery.of(STAGED)
                        .withPredicates(m -> m.id().is(product.getId()))
                        .withExpansionPaths(m -> m.masterVariant().prices().channel());
                final List<Price> prices = client().executeBlocking(query).head().get().getMasterVariant().getPrices();
                assertThat(prices
                        .stream()
                        .anyMatch(price -> Optional.ofNullable(price.getChannel()).map(channelRef -> channelRef.getObj() != null).orElse(false)))
                        .isTrue();
                return product;
            });
        });
    }

    @Test
    public void queryByProductType() throws Exception {
        with2products("queryByProductType", (p1, p2) ->{
            final Query<ProductProjection> query =
                    ProductProjectionQuery.of(STAGED)
                            .byProductType(p1.getProductType())
                            .withExpansionPaths(m -> m.productType());
            final PagedQueryResult<ProductProjection> queryResult = client().executeBlocking(query);
            assertThat(queryResult.head().get().getProductType()).is(expanded());
            assertThat(ids(queryResult)).containsOnly(p1.getId());
        });
    }

    @Test
    public void queryById() throws Exception {
        with2products("queryById", (p1, p2) -> {
            final Query<ProductProjection> query1 = ProductProjectionQuery.of(STAGED).withPredicates(m -> m.id().isIn(asList(p1.getId(), p2.getId())));
            assertThat(ids(client().executeBlocking(query1))).containsOnly(p1.getId(), p2.getId());

            final Query<ProductProjection> query = ProductProjectionQuery.of(STAGED).withPredicates(m -> m.id().is(p1.getId()));
            assertThat(ids(client().executeBlocking(query))).containsOnly(p1.getId());
        });
    }

    @Test
    public void queryByParametrizedId() throws Exception {
        with2products("queryByParametrizedId", (p1, p2) -> {
            final Query<ProductProjection> query1 = ProductProjectionQuery.of(STAGED).withPredicates(QueryPredicate.of("id in (:id1, :id2)")).withQueryParam(NameValuePair.of("id1", p1.getId())).withQueryParam(NameValuePair.of("id2", p2.getId()));

            assertThat(ids(client().executeBlocking(query1))).containsOnly(p1.getId(), p2.getId());

            final Query<ProductProjection> query = ProductProjectionQuery.of(STAGED).withPredicates(m -> m.id().is(p1.getId()));
            assertThat(ids(client().executeBlocking(query))).containsOnly(p1.getId());
        });
    }

    @Test
    public void queryBySlug() throws Exception {
        with2products("queryBySlug", (p1, p2) ->{
            final Query<ProductProjection> query1 = ProductProjectionQuery.of(STAGED).bySlug(ENGLISH, p1.getMasterData().getStaged().getSlug().get(ENGLISH));
            assertThat(ids(client().executeBlocking(query1))).containsOnly(p1.getId());
        });
    }

    @Test
    public void queryByName() throws Exception {
        with2products("queryByName", (p1, p2) ->{
            final Query<ProductProjection> query1 = ProductProjectionQuery.of(STAGED)
                    .withPredicates(m -> m.name().lang(ENGLISH).is(en(p1.getMasterData().getStaged().getName())))
                    .withSort(m -> m.createdAt().sort().desc())
                    .withLimit(1L);
            assertThat(ids(client().executeBlocking(query1))).containsOnly(p1.getId());
        });
    }

    @Test
    public void queryByKey() throws Exception {
        final String key = randomKey();
        ProductFixtures.withProduct(client(), builder -> builder.key(key), product -> {
            final ProductByKeyGet request = ProductByKeyGet.of(key);
            final Product loadedProduct = client().executeBlocking(request);
            assertThat(loadedProduct.getId()).isEqualTo(product.getId());

            final Query<ProductProjection> query1 = ProductProjectionQuery.ofStaged()
                    .withPredicates(m -> m.key().is(loadedProduct.getKey()))
                    .withSort(m -> m.createdAt().sort().desc())
                    .withLimit(1L);
            final PagedQueryResult<ProductProjection> result = client().executeBlocking(query1);

            assertThat(result.getResults().get(0).getKey()).isEqualTo(key);
        });
    }

    @Test
    public void queryByCategory() throws Exception {
        withCategory(client(), cat3 ->
                        withCategory(client(), cat1 ->
                                        withCategory(client(), cat2 ->
                                                        with2products("queryByCategory", (p1, p2) -> {
                                                            final Category cat1WithParent = client().executeBlocking(CategoryUpdateCommand.of(cat1, asList(ChangeParent.of(cat3))));
                                                            final Product productWithCat1 = client().executeBlocking(ProductUpdateCommand.of(p1, AddToCategory.of(cat1WithParent)));
                                                            final Query<ProductProjection> query = ProductProjectionQuery.of(STAGED)
                                                                    .withPredicates(m -> m.categories().isIn(asList(cat1, cat2)))
                                                                    .withExpansionPaths(m -> m.categories().parent());
                                                            final PagedQueryResult<ProductProjection> queryResult = client().executeBlocking(query);
                                                            assertThat(ids(queryResult)).containsOnly(productWithCat1.getId());
                                                            final Reference<Category> cat1Loaded = queryResult.head().get().getCategories().stream().findAny().get();
                                                            assertThat(cat1Loaded).as("cat of product is expanded").is(expanded());
                                                            final Reference<Category> parent = cat1Loaded.getObj().getParent();
                                                            assertThat(parent).as("parent of cat is expanded").is(expanded());
                                                        })
                                        )
                        )
        );
    }

    @Test
    public void queryByHasStagedChanges() throws Exception {
        withProduct(client(), product -> {
            final Product updated = client().executeBlocking(ProductUpdateCommand.of(product, ChangeName.of(randomSlug())));
            final ProductProjectionQuery query = ProductProjectionQuery.of(STAGED)
                    .withPredicates(m -> m.hasStagedChanges().is(true))
                    .withSort(m -> m.createdAt().sort().desc());
            final PagedQueryResult<ProductProjection> pagedQueryResult = client().executeBlocking(query);
            assertThat(ids(pagedQueryResult)).contains(updated.getId());
        });
    }

    private ProductProjectionQueryModel model() {
        return ProductProjectionQueryModel.of();
    }

    @Test
    public void queryBySku() throws Exception {
        withProduct(client(), product -> {
            final String sku = "sku-" + randomString();
            final Product productWithSku = client().executeBlocking(ProductUpdateCommand.of(product, SetSku.of(MASTER_VARIANT_ID, sku)));
            final QueryPredicate<ProductProjection> predicate = model().masterVariant().sku().is(sku);
            checkOneResult(productWithSku, predicate);
        });
    }

    @Test
    public void queryByMetaAttributes() throws Exception {
        withProduct(client(), product -> {
            final MetaAttributes metaAttributes = randomMetaAttributes();
            final Product productWithMetaAttributes = client().executeBlocking(ProductUpdateCommand.of(product, MetaAttributesUpdateActions.of(metaAttributes)));
            checkOneResult(productWithMetaAttributes, model().metaDescription().lang(ENGLISH).is(en(metaAttributes.getMetaDescription())));
            checkOneResult(productWithMetaAttributes, model().metaTitle().lang(ENGLISH).is(en(metaAttributes.getMetaTitle())));
            checkOneResult(productWithMetaAttributes, model().metaKeywords().lang(ENGLISH).is(en(metaAttributes.getMetaKeywords())));
        });
    }

    @Test
    public void expandTaxCategory() throws Exception {
        TaxCategoryFixtures.withTransientTaxCategory(client(), taxCategory ->
                        withProduct(client(), product -> {
                            final Product productWithTaxCategory = client().executeBlocking(ProductUpdateCommand.of(product, SetTaxCategory.of(taxCategory.toResourceIdentifier())));
                            final ProductProjectionQuery query = ProductProjectionQuery.of(STAGED)
                                    .withPredicates(m -> m.id().is(productWithTaxCategory.getId()))
                                    .withExpansionPaths(m -> m.taxCategory());
                            final PagedQueryResult<ProductProjection> pagedQueryResult =
                                    client().executeBlocking(query);
                            assertThat(pagedQueryResult.head().get().getTaxCategory()).is(expanded());
                        })
        );
    }

    @Test
    public void queryByReviewRating() {
        withProduct(client(), product -> {
            withReview(client(), b -> b.target(product).rating(1), review1 -> {
                withReview(client(), b -> b.target(product).rating(3), review2 -> {
                    assertEventually(Duration.ofSeconds(60), Duration.ofMillis(200), () -> {
                        final ProductProjectionQuery query = ProductProjectionQuery.ofStaged()
                                .withPredicates(m -> m.reviewRatingStatistics().averageRating().is(2.0))
                                .plusPredicates(m -> m.reviewRatingStatistics().count().is(2))
                                .plusPredicates(m -> m.reviewRatingStatistics().lowestRating().is(1))
                                .plusPredicates(m -> m.reviewRatingStatistics().highestRating().is(3))
                                .plusPredicates(m -> m.is(product));
                        final List<ProductProjection> results = client().executeBlocking(query).getResults();
                        assertThat(results).hasSize(1);
                        assertThat(results.get(0).getId()).isEqualTo(product.getId());
                    });
                });
            });
        });
    }

    @Test
    public void selectProductByLocaleProjectionInProductProjectionQuery() {
        String localeProjection = "en-EN";
        BlockingSphereClient client = client();
        ProductFixtures.withProduct(client, product -> {
            final ProductProjectionQuery request = ProductProjectionQuery.ofStaged()
                    .withPredicates(m -> m.id().is(product.getId()))
                    .withLocaleSelection(LocaleSelection.of(localeProjection));
            assertThat(request.httpRequestIntent().getPath()).contains("localeProjection=en-EN");
        });
    }

    @Test
    public void selectProductByListOfLocaleProjectionsInProductProjectionQuery() {
        final List<String> localeProjection = Arrays.asList("en-EN", "it-IT");
        BlockingSphereClient client = client();
        ProductFixtures.withProduct(client, product -> {
            final ProductProjectionQuery request = ProductProjectionQuery.ofStaged()
                    .withPredicates(m -> m.id().is(product.getId()))
                    .withLocaleSelection(LocaleSelection.of(localeProjection));
            assertThat(request.httpRequestIntent().getPath()).contains("localeProjection=it-IT");
            assertThat(request.httpRequestIntent().getPath()).contains("localeProjection=en-EN");
        });
    }

    @Test
    public void selectProductByTwoLocaleProjectionsInProductProjectionQuery() {
        final String localeProjectionEN = "en-EN";
        final String localeProjectionDE = "de-DE";
        BlockingSphereClient client = client();
        ProductFixtures.withProduct(client, product -> {
            final ProductProjectionQuery request = ProductProjectionQuery.ofStaged()
                    .withPredicates(m -> m.id().is(product.getId()))
                    .withLocaleSelection(LocaleSelection.of(localeProjectionDE))
                    .plusLocaleSelection(LocaleSelection.of(localeProjectionEN));
            assertThat(request.httpRequestIntent().getPath()).contains("localeProjection=de-DE");
            assertThat(request.httpRequestIntent().getPath()).contains("localeProjection=en-EN");
        });
    }

    @Test
    public void selectProductByLocaleSelectionWithTwoLocaleProjectionsInProductProjectionQuery() {
        final String localeProjectionEN = "en-EN";
        final String localeProjectionDE = "de-DE";
        BlockingSphereClient client = client();
        ProductFixtures.withProduct(client, product -> {
            final ProductProjectionQuery request = ProductProjectionQuery.ofStaged()
                    .withPredicates(m -> m.id().is(product.getId()))
                    .withLocaleSelection(LocaleSelection.of(localeProjectionDE).plusLocaleProjection(localeProjectionEN));
            assertThat(request.httpRequestIntent().getPath()).contains("localeProjection=de-DE");
            assertThat(request.httpRequestIntent().getPath()).contains("localeProjection=en-EN");
        });
    }


    @Test
    public void testLocaleSelectionBuilder() {
        final List<String> localeProjection1 = LocaleSelection.of("ig-NG").withLocaleProjection("en-US").plusLocaleProjection("de-DE").plusLocaleProjection(Arrays.asList("de-AT", "fr-FR")).getLocaleProjection();
        assertThat(localeProjection1).containsOnly("en-US", "de-AT", "de-DE", "fr-FR");

        final List<String> localeProjection2 = LocaleSelection.of("en-US").withLocaleProjection("de-DE").getLocaleProjection();
        assertThat(localeProjection2).containsOnly("de-DE");

        final List<String> localeProjection3 = LocaleSelection.of("ig-NG").withLocaleProjection(Arrays.asList("en-US", "de-DE")).getLocaleProjection();
        assertThat(localeProjection3).containsOnly("de-DE", "en-US");

        final List<String> localeProjection4 = LocaleSelection.of("ig-NG").plusLocaleProjection("de-AT").withLocaleProjection("de-DE").getLocaleProjection();
        assertThat(localeProjection4).containsOnly("de-DE");
    }

    @Test
    public void selectProductByAListOfLocaleProjectionsInProductProjectionQuery() {
        final String localeProjectionEN = "en-EN";
        final List<String> localeProjectionList = asList("de-DE");
        BlockingSphereClient client = client();
        ProductFixtures.withProduct(client, product -> {
            final ProductProjectionQuery request = ProductProjectionQuery.ofStaged()
                    .withPredicates(m -> m.id().is(product.getId()))
                    .withLocaleSelection(LocaleSelection.of(localeProjectionEN))
                    .plusLocaleSelection(LocaleSelection.of(localeProjectionList));
            assertThat(request.httpRequestIntent().getPath()).contains("localeProjection=de-DE");
            assertThat(request.httpRequestIntent().getPath()).contains("localeProjection=en-EN");
        });
    }

    @Test
    public void selectProductByStoreProjectionInProductProjectionQuery() {
        BlockingSphereClient client = client();
        StoreFixtures.withStore(client, store -> {
            withProduct(client, product -> {
                final ProductProjectionQuery request = ProductProjectionQuery.ofStaged()
                        .withPredicates(m -> m.id().is(product.getId()))
                        .withStoreSelection(StoreSelection.of(store.getKey()));
                assertThat(request.httpRequestIntent().getPath()).contains("storeProjection=" + store.getKey());
                final PagedQueryResult<ProductProjection> result = client.executeBlocking(request);
                assertThat(result.getCount()).isEqualTo(1);
            });
        });
    }

    private void checkOneResult(final Product product, final QueryPredicate<ProductProjection> predicate) {
        final PagedQueryResult<ProductProjection> queryResult = client().executeBlocking(ProductProjectionQuery.of(STAGED).withPredicates(predicate));
        assertThat(ids(queryResult)).containsOnly(product.getId());
    }

    private Set<String> ids(final PagedQueryResult<ProductProjection> res) {
        return res.getResults().stream().map(p -> p.getId()).collect(toSet());
    }

    private void with2products(final String testName, final BiConsumer<Product, Product> consumer) {
        final Consumer<Product> user1 = product1 -> {
            final Consumer<Product> user = product2 -> {
                consumer.accept(product1, product2);
            };
            withProduct(client(), testName + "2", user);
        };
        withProduct(client(), testName + "1", user1);
    }
}
