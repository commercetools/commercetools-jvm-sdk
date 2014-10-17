package io.sphere.sdk.products;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.commands.CategoryUpdateCommand;
import io.sphere.sdk.categories.commands.updateactions.ChangeParent;
import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.models.MetaAttributes;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.products.commands.ProductUpdateCommand;
import io.sphere.sdk.products.commands.updateactions.*;
import io.sphere.sdk.products.queries.FetchProductProjectionById;
import io.sphere.sdk.products.queries.ProductProjectionQuery;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.Predicate;
import io.sphere.sdk.queries.Query;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static io.sphere.sdk.categories.CategoryFixtures.withCategory;
import static io.sphere.sdk.products.ProductFixtures.withProduct;
import static io.sphere.sdk.products.ProductProjectionType.STAGED;
import static io.sphere.sdk.products.queries.ProductProjectionQuery.expansionPath;
import static io.sphere.sdk.products.queries.ProductProjectionQuery.model;
import static io.sphere.sdk.taxcategories.TaxCategoryFixtures.withTaxCategory;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static java.util.Arrays.asList;
import static java.util.Locale.ENGLISH;
import static java.util.stream.Collectors.toSet;
import static org.fest.assertions.Assertions.assertThat;
import static io.sphere.sdk.test.ReferenceAssert.assertThat;

public class ProductProjectionIntegrationTest extends IntegrationTest {
    public static final int MASTER_VARIANT_ID = 1;

    @Test
    public void getProductProjectionById() throws Exception {
        final Consumer<Product> user = product -> {
            final ProductProjectionType projectionType = STAGED;
            final Identifiable<ProductProjection> identifier = product.toProjection(projectionType).get();
            final ProductProjection productProjection = execute(new FetchProductProjectionById(identifier, projectionType)).get();
            final String fetchedProjectionId = productProjection.getId();
            assertThat(fetchedProjectionId).isEqualTo(product.getId());
            assertThat(productProjection.getCategories()).isEqualTo(product.getMasterData().get(projectionType).get().getCategories());
        };
        withProduct(client(), "getProductProjectionById", user);
    }

    @Test
    public void queryByProductType() throws Exception {
        with2products("queryByProductType", (p1, p2) ->{
            final Query<ProductProjection> query =
                    new ProductProjectionQuery(STAGED)
                            .byProductType(p1.getProductType())
                            .withExpansionPath(expansionPath().productType());
            final PagedQueryResult<ProductProjection> queryResult = execute(query);
            assertThat(queryResult.head().get().getProductType()).isExpanded();
            assertThat(ids(queryResult)).containsOnly(p1.getId());
        });
    }

    @Test
    public void queryById() throws Exception {
        with2products("queryById", (p1, p2) ->{
            final Query<ProductProjection> query1 = new ProductProjectionQuery(STAGED).withPredicate(model().id().isOneOf(p1.getId(), p2.getId()));
            assertThat(ids(execute(query1))).containsOnly(p1.getId(), p2.getId());

            final Query<ProductProjection> query = new ProductProjectionQuery(STAGED).withPredicate(model().id().is(p1.getId()));
            assertThat(ids(execute(query))).containsOnly(p1.getId());
        });
    }

    @Test
    public void queryBySlug() throws Exception {
        with2products("queryBySlug", (p1, p2) ->{
            final Query<ProductProjection> query1 = new ProductProjectionQuery(STAGED).bySlug(ENGLISH, p1.getMasterData().getStaged().getSlug().get(ENGLISH).get());
            assertThat(ids(execute(query1))).containsOnly(p1.getId());
        });
    }

    @Test
    public void queryByName() throws Exception {
        with2products("queryByName", (p1, p2) ->{
            final Query<ProductProjection> query1 = new ProductProjectionQuery(STAGED).withPredicate(model().name().lang(ENGLISH).is(en(p1.getMasterData().getStaged().getDescription())));
            assertThat(ids(execute(query1))).containsOnly(p1.getId());
        });
    }

    @Test
    public void queryByCategory() throws Exception {
        withCategory(client(), cat3 ->
                        withCategory(client(), cat1 ->
                                        withCategory(client(), cat2 ->
                                                        with2products("queryByCategory", (p1, p2) -> {
                                                            final Category cat1WithParent = execute(new CategoryUpdateCommand(cat1, asList(ChangeParent.of(cat3))));
                                                            final Product productWithCat1 = execute(new ProductUpdateCommand(p1, AddToCategory.of(cat1WithParent)));
                                                            final Query<ProductProjection> query = new ProductProjectionQuery(STAGED)
                                                                    .withPredicate(model().categories().isIn(asList(cat1, cat2)))
                                                                    .withExpansionPath(expansionPath().categories().parent());
                                                            final PagedQueryResult<ProductProjection> queryResult = execute(query);
                                                            assertThat(ids(queryResult)).containsOnly(productWithCat1.getId());
                                                            final Reference<Category> cat1Loaded = queryResult.head().get().getCategories().get(0);
                                                            assertThat(cat1Loaded).overridingErrorMessage("cat of product is expanded").isExpanded();
                                                            final Reference<Category> parent = cat1Loaded.getObj().get().getParent().get();
                                                            assertThat(parent).overridingErrorMessage("parent of cat is expanded").isExpanded();
                                                        })
                                        )
                        )
        );
}

    @Test
    public void queryByHasStagedChanges() throws Exception {
        withProduct(client(), product -> {
            final Product updated = execute(new ProductUpdateCommand(product, ChangeName.of(randomSlug(), true)));
            final PagedQueryResult<ProductProjection> pagedQueryResult = execute(new ProductProjectionQuery(STAGED).withPredicate(model().hasStagedChanges().is(true)));
            assertThat(ids(pagedQueryResult)).contains(updated.getId());
        });
    }

    @Test
    public void queryBySku() throws Exception {
        withProduct(client(), product -> {
            final String sku = "sku-" + randomString();
            final Product productWithSku = execute(new ProductUpdateCommand(product, SetSku.of(MASTER_VARIANT_ID, sku)));
            final Predicate<ProductProjection> predicate = model().masterVariant().sku().is(sku);
            checkOneResult(productWithSku, predicate);
        });
    }

    @Test
    public void queryByMetaAttributes() throws Exception {
        withProduct(client(), product -> {
            final MetaAttributes metaAttributes = randomMetaAttributes();
            final Product productWithMetaAttributes = execute(new ProductUpdateCommand(product, SetMetaAttributes.of(metaAttributes)));
            checkOneResult(productWithMetaAttributes, model().metaDescription().lang(ENGLISH).is(en(metaAttributes.getMetaDescription())));
            checkOneResult(productWithMetaAttributes, model().metaTitle().lang(ENGLISH).is(en(metaAttributes.getMetaTitle())));
            checkOneResult(productWithMetaAttributes, model().metaKeywords().lang(ENGLISH).is(en(metaAttributes.getMetaKeywords())));
        });
    }

    @Test
    public void expandTaxCategory() throws Exception {
        withTaxCategory(client(), taxCategory ->
            withProduct(client(), product -> {
                final Product productWithTaxCategory = execute(new ProductUpdateCommand(product, SetTaxCategory.of(taxCategory)));
                final Predicate<ProductProjection> predicate = model().id().is(productWithTaxCategory.getId());
                final PagedQueryResult<ProductProjection> pagedQueryResult =
                        execute(new ProductProjectionQuery(STAGED)
                                .withPredicate(predicate)
                                .withExpansionPath(expansionPath().taxCategory()));
                assertThat(pagedQueryResult.head().get().getTaxCategory().get()).isExpanded();
            })
        );
    }

    private void checkOneResult(final Product product, final Predicate<ProductProjection> predicate) {
        final PagedQueryResult<ProductProjection> queryResult = execute(new ProductProjectionQuery(STAGED).withPredicate(predicate));
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
