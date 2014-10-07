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
import static io.sphere.sdk.taxcategories.TaxCategoryFixtures.withTaxCategory;
import static io.sphere.sdk.test.ReferenceAssert.assertThat;
import static io.sphere.sdk.test.OptionalAssert.assertThat;
import static io.sphere.sdk.test.SphereTestUtils.*;

public class ProductReferenceExpansionTest extends IntegrationTest {

    @Test
    public void productType() throws Exception {
        final Consumer<Product> user = product -> {
            final Query<Product> query = new ProductQuery().
                    bySlug(ProductProjectionType.CURRENT, Locale.ENGLISH, englishSlugOf(product.getMasterData().getStaged())).
                    withExpansionPaths(ProductQuery.expansionPath().productType()).
                    toQuery();
            final PagedQueryResult<Product> queryResult = client().execute(query);
            final Reference<ProductType> productTypeReference = queryResult.head().get().getProductType();
            assertThat(productTypeReference).isExpanded();
        };
        withProduct(client(), "productTypeReferenceExpansion", user);
    }

    @Test
    public void taxCategory() throws Exception {
        final Consumer<TaxCategory> user1 = taxCategory -> {
            final Consumer<Product> user = product -> {
                final Product productWithTaxCategory = client().execute(new ProductUpdateCommand(product, SetTaxCategory.of(taxCategory)));
                assertThat(productWithTaxCategory.getTaxCategory()).isPresent();
                final Query<Product> query = new ProductQuery().
                        bySlug(ProductProjectionType.CURRENT, Locale.ENGLISH, englishSlugOf(product.getMasterData().getStaged())).
                        withExpansionPaths(ProductQuery.expansionPath().taxCategory()).
                        toQuery();
                final PagedQueryResult<Product> queryResult = client().execute(query);
                final Reference<TaxCategory> productTypeReference = firstOf(queryResult).getTaxCategory().get();
                assertThat(productTypeReference).isExpanded();
            };
            withProduct(client(), "taxCategoryReferenceExpansion", user);
        };
        withTaxCategory(client(), ProductReferenceExpansionTest.class.toString() + ".taxCategory", user1);
    }
}
