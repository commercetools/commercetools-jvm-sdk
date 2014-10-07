package io.sphere.sdk.products;

import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.products.commands.ProductUpdateCommand;
import io.sphere.sdk.products.commands.updateactions.AddToCategory;
import io.sphere.sdk.products.queries.FetchProductProjectionById;
import io.sphere.sdk.products.queries.ProductProjectionQuery;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.Query;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.Set;
import java.util.function.BiConsumer;

import static io.sphere.sdk.products.ProductCrudIntegrationTest.withCategory;
import static io.sphere.sdk.products.ProductProjectionType.STAGED;
import static java.util.Arrays.asList;
import static java.util.Locale.ENGLISH;
import static java.util.stream.Collectors.toSet;
import static org.fest.assertions.Assertions.assertThat;

public class ProductProjectionIntegrationTest extends IntegrationTest {

    @Test
    public void getProductProjectionById() throws Exception {
        ProductReferenceExpansionTest.withProduct(client(), "getProductProjectionById", product -> {
            final ProductProjectionType projectionType = STAGED;
            final Identifiable<ProductProjection> identifier = product.toProjection(projectionType).get();
            final ProductProjection productProjection = client().execute(new FetchProductProjectionById(identifier, projectionType)).get();
            final String fetchedProjectionId = productProjection.getId();
            assertThat(fetchedProjectionId).isEqualTo(product.getId());
            assertThat(productProjection.getCategories()).isEqualTo(product.getMasterData().get(projectionType).get().getCategories());
        });
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
        withCategory(cat1 ->
            withCategory(cat2 ->
                with2products("queryByCategory", (p1, p2) -> {
                    final Product productWithCat1 = client().execute(new ProductUpdateCommand(p1, AddToCategory.of(cat1)));
                    final Query<ProductProjection> query = new ProductProjectionQuery(STAGED).withPredicate(ProductProjectionQuery.model().categories().isIn(asList(cat1, cat2)));
                    assertThat(ids(client().execute(query))).containsOnly(productWithCat1.getId());
                })
            )
        );
    }

    private Set<String> ids(final PagedQueryResult<ProductProjection> res) {
        return res.getResults().stream().map(p -> p.getId()).collect(toSet());
    }

    private void with2products(final String testName, final BiConsumer<Product, Product> consumer) {
        ProductReferenceExpansionTest.withProduct(client(), testName + "1", product1 -> {
            ProductReferenceExpansionTest.withProduct(client(), testName + "2", product2 -> {
                consumer.accept(product1, product2);
            });
        });
    }
}
