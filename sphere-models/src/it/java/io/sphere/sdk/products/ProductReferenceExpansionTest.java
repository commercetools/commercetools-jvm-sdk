package io.sphere.sdk.products;

import io.sphere.sdk.models.Reference;
import io.sphere.sdk.products.commands.ProductUpdateCommand;
import io.sphere.sdk.products.commands.updateactions.SetTaxCategory;
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
import static io.sphere.sdk.test.ReferenceAssert.assertThat;
import static io.sphere.sdk.test.SphereTestUtils.*;

public class ProductReferenceExpansionTest extends IntegrationTest {

    @Test
    public void productType() throws Exception {
        final Consumer<Product> user = product -> {
            final Query<Product> query = ProductQuery.of().
                    bySlug(ProductProjectionType.CURRENT, Locale.ENGLISH, englishSlugOf(product.getMasterData().getStaged())).
                    withExpansionPath(ProductQuery.expansionPath().productType()).
                    toQuery();
            final PagedQueryResult<Product> queryResult = execute(query);
            final Reference<ProductType> productTypeReference = queryResult.head().get().getProductType();
            assertThat(productTypeReference).isExpanded();
        };
        withProduct(client(), "productTypeReferenceExpansion", user);
    }

    @Test
    public void taxCategory() throws Exception {
        TaxCategoryFixtures.withTransientTaxCategory(client(), taxCategory ->
            withProduct(client(), product -> {
                final Product productWithTaxCategory = execute(ProductUpdateCommand.of(product, SetTaxCategory.of(taxCategory)));
                assertThat(productWithTaxCategory.getTaxCategory()).isPresent();
                final Query<Product> query = ProductQuery.of().
                        bySlug(ProductProjectionType.CURRENT, Locale.ENGLISH, englishSlugOf(product.getMasterData().getStaged())).
                        withExpansionPath(ProductQuery.expansionPath().taxCategory()).
                        toQuery();
                final PagedQueryResult<Product> queryResult = execute(query);
                final Reference<TaxCategory> productTypeReference = firstOf(queryResult).getTaxCategory().get();
                assertThat(productTypeReference).isExpanded();
            })
        );
    }
}
