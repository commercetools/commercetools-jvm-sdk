package test;

import io.sphere.sdk.client.TestClient;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.products.*;
import io.sphere.sdk.producttypes.*;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.Query;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.Locale;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static io.sphere.sdk.test.ReferenceAssert.assertThat;
import static io.sphere.sdk.test.SphereTestUtils.englishSlugOf;

public class ProductReferenceExpansionTest extends IntegrationTest {
    @Test
    public void productType() throws Exception {
        withProduct("productType", product -> {
            final Query<Product> query = new ProductQuery().
                    bySlug(ProductProjectionType.CURRENT, Locale.ENGLISH, englishSlugOf(product.getMasterData().getStaged())).
                    withExpansionPath(ProductQuery.expansionPath().productType()).
                    toQuery();
            final PagedQueryResult<Product> queryResult = client().execute(query);
            final Reference<ProductType> productTypeReference = queryResult.head().get().getProductType();
            assertThat(productTypeReference).isExpanded();
        });
    }

    public void withProduct(final String testName, final Consumer<Product> user) {
        withProductType(client(), ProductReferenceExpansionTest.class.getName() + "." + testName, productType -> {
            withProduct(client(), new SimpleCottonTShirtNewProductSupplier(productType.toReference(), "foo"), user);
        });
    }

    public static void withProduct(final TestClient client, final Supplier<NewProduct> creator, final Consumer<Product> user) {
        final NewProduct newProduct = creator.get();
        final String slug = englishSlugOf(newProduct);
        final PagedQueryResult<Product> pagedQueryResult = client.execute(new ProductQuery().bySlug(ProductProjectionType.STAGED, Locale.ENGLISH, slug));
        pagedQueryResult.getResults().forEach(product -> client.execute(new ProductDeleteByIdCommand(product)));
        final Product product = client.execute(new ProductCreateCommand(newProduct));
        try {
            user.accept(product);
        } finally {
            client.execute(new ProductDeleteByIdCommand(product));
        }
    }

    public static void withProductType(final TestClient client, final Supplier<NewProductType> creator, final Consumer<ProductType> user) {
        final NewProductType newProductType = creator.get();
        final String name = newProductType.getName();
        final PagedQueryResult<ProductType> queryResult = client.execute(new ProductTypeQuery().byName(name));
        queryResult.getResults().forEach(productType -> client.execute(new ProductTypeDeleteByIdCommand(productType)));
        final ProductType productType = client.execute(new ProductTypeCreateCommand(newProductType));
        try {
            user.accept(productType);
        } finally {
            client.execute(new ProductTypeDeleteByIdCommand(productType));
        }
    }

    public static void withProductType(final TestClient client, final String productTypeName, final Consumer<ProductType> user) {
        withProductType(client, new TShirtNewProductTypeSupplier(productTypeName), user);
    }
}
