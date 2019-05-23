package io.sphere.sdk.products;

import io.sphere.sdk.models.Reference;
import io.sphere.sdk.products.commands.ProductUpdateCommand;
import io.sphere.sdk.products.commands.updateactions.SetTaxCategory;
import io.sphere.sdk.products.expansion.ProductExpansionModel;
import io.sphere.sdk.products.queries.ProductQuery;
import io.sphere.sdk.producttypes.*;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.Query;
import io.sphere.sdk.taxcategories.*;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.Locale;
import java.util.function.Consumer;

import static io.sphere.sdk.products.ProductFixtures.withProduct;
import static org.assertj.core.api.Assertions.assertThat;
import static io.sphere.sdk.test.SphereTestUtils.*;

public class ProductReferenceExpansionIntegrationTest extends IntegrationTest {

    @Test
    public void productType() throws Exception {
        final Consumer<Product> user = product -> {
            final Query<Product> query = ProductQuery.of().
                    bySlug(ProductProjectionType.CURRENT, Locale.ENGLISH, englishSlugOf(product.getMasterData().getStaged())).
                    withExpansionPaths(ProductExpansionModel.of().productType()).
                    toQuery();
            final PagedQueryResult<Product> queryResult = client().executeBlocking(query);
            final Reference<ProductType> productTypeReference = queryResult.head().get().getProductType();
            assertThat(productTypeReference).is(expanded());
        };
        withProduct(client(), "productTypeReferenceExpansion", user);
    }

    @Test
    public void taxCategory() throws Exception {
        TaxCategoryFixtures.withTransientTaxCategory(client(), taxCategory ->
            withProduct(client(), product -> {
                final Product productWithTaxCategory = client().executeBlocking(ProductUpdateCommand.of(product, SetTaxCategory.of(taxCategory.toResourceIdentifier())));
                assertThat(productWithTaxCategory.getTaxCategory()).isNotNull();
                final Query<Product> query = ProductQuery.of().
                        bySlug(ProductProjectionType.CURRENT, Locale.ENGLISH, englishSlugOf(product.getMasterData().getStaged())).
                        withExpansionPaths(ProductExpansionModel.of().taxCategory()).
                        toQuery();
                final PagedQueryResult<Product> queryResult = client().executeBlocking(query);
                final Reference<TaxCategory> productTypeReference = firstOf(queryResult).getTaxCategory();
                assertThat(productTypeReference).is(expanded());
            })
        );
    }
}
