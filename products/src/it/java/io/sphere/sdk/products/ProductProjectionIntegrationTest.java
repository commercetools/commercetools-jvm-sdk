package io.sphere.sdk.products;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.products.commands.ProductUpdateCommand;
import io.sphere.sdk.products.commands.updateactions.AddToCategory;
import io.sphere.sdk.products.commands.updateactions.ChangeName;
import io.sphere.sdk.products.queries.FetchProductProjectionById;
import io.sphere.sdk.products.queries.ProductProjectionQuery;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.Query;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static io.sphere.sdk.categories.CategoryFixtures.withCategory;
import static io.sphere.sdk.products.ProductFixtures.withProduct;
import static io.sphere.sdk.products.ProductProjectionType.STAGED;
import static io.sphere.sdk.test.SphereTestUtils.randomSlug;
import static java.util.Arrays.asList;
import static java.util.Locale.ENGLISH;
import static java.util.stream.Collectors.toSet;
import static org.fest.assertions.Assertions.assertThat;

public class ProductProjectionIntegrationTest extends IntegrationTest {

    @Test
    public void getProductProjectionById() throws Exception {
        final Consumer<Product> user = product -> {
            final ProductProjectionType projectionType = STAGED;
            final Identifiable<ProductProjection> identifier = product.toProjection(projectionType).get();
            final ProductProjection productProjection = client().execute(new FetchProductProjectionById(identifier, projectionType)).get();
            final String fetchedProjectionId = productProjection.getId();
            assertThat(fetchedProjectionId).isEqualTo(product.getId());
            assertThat(productProjection.getCategories()).isEqualTo(product.getMasterData().get(projectionType).get().getCategories());
        };
        withProduct(client(), "getProductProjectionById", user);
    }

    @Test
    public void queryByProductType() throws Exception {
        with2products("queryByProductType", (p1, p2) ->{
            final Query<ProductProjection> query = new ProductProjectionQuery(STAGED).byProductType(p1.getProductType());
            assertThat(ids(client().execute(query))).containsOnly(p1.getId());
        });
    }

    @Test
    public void queryById() throws Exception {
        with2products("queryById", (p1, p2) ->{
            final Query<ProductProjection> query1 = new ProductProjectionQuery(STAGED).withPredicate(ProductProjectionQuery.model().id().isOneOf(p1.getId(), p2.getId()));
            assertThat(ids(client().execute(query1))).containsOnly(p1.getId(), p2.getId());

            final Query<ProductProjection> query = new ProductProjectionQuery(STAGED).withPredicate(ProductProjectionQuery.model().id().is(p1.getId()));
            assertThat(ids(client().execute(query))).containsOnly(p1.getId());
        });
    }

    @Test
    public void queryBySlug() throws Exception {
        with2products("queryBySlug", (p1, p2) ->{
            final Query<ProductProjection> query1 = new ProductProjectionQuery(STAGED).bySlug(ENGLISH, p1.getMasterData().getStaged().getSlug().get(ENGLISH).get());
            assertThat(ids(client().execute(query1))).containsOnly(p1.getId());
        });
    }

    @Test
    public void queryByName() throws Exception {
        with2products("queryByName", (p1, p2) ->{
            final Query<ProductProjection> query1 = new ProductProjectionQuery(STAGED).withPredicate(ProductProjectionQuery.model().name().lang(ENGLISH).is(p1.getMasterData().getStaged().getDescription().get().get(ENGLISH).get()));
            assertThat(ids(client().execute(query1))).containsOnly(p1.getId());
        });
    }

    @Test
    public void queryByCategory() throws Exception {
        final Consumer<Category> consumer1 = cat1 -> {
            final Consumer<Category> consumer = cat2 ->
                            with2products("queryByCategory", (p1, p2) -> {
                                final Product productWithCat1 = client().execute(new ProductUpdateCommand(p1, AddToCategory.of(cat1)));
                                final Query<ProductProjection> query = new ProductProjectionQuery(STAGED).withPredicate(ProductProjectionQuery.model().categories().isIn(asList(cat1, cat2)));
                                assertThat(ids(client().execute(query))).containsOnly(productWithCat1.getId());
                            });
            withCategory(client(), consumer);
        };
        withCategory(client(), consumer1);
    }

    @Test
    public void queryByHasStagedChanges() throws Exception {
        withProduct(client(), product -> {
            final Product updated = client().execute(new ProductUpdateCommand(product, ChangeName.of(randomSlug(), true)));
            final PagedQueryResult<ProductProjection> pagedQueryResult = client().execute(new ProductProjectionQuery(STAGED).withPredicate(ProductProjectionQuery.model().hasStagedChanges().is(true)));
            assertThat(ids(pagedQueryResult)).contains(updated.getId());
        });
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
