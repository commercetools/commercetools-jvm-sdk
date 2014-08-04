package test;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.client.TestClient;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.products.*;
import io.sphere.sdk.products.commands.ProductUpdateCommand;
import io.sphere.sdk.products.commands.Publish;
import io.sphere.sdk.products.commands.SetTaxCategory;
import io.sphere.sdk.products.commands.UnPublish;
import io.sphere.sdk.producttypes.*;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.Query;
import io.sphere.sdk.taxcategories.*;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.utils.SphereInternalLogger;
import org.junit.Test;

import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static io.sphere.sdk.test.ReferenceAssert.assertThat;
import static io.sphere.sdk.test.OptionalAssert.assertThat;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static java.util.Arrays.asList;

public class ProductReferenceExpansionTest extends IntegrationTest {
    @Test
    public void productType() throws Exception {
        withProduct("productTypeReferenceExpansion", product -> {
            final Query<Product> query = new ProductQuery().
                    bySlug(ProductProjectionType.CURRENT, Locale.ENGLISH, englishSlugOf(product.getMasterData().getStaged())).
                    withExpansionPaths(ProductQuery.expansionPath().productType()).
                    toQuery();
            final PagedQueryResult<Product> queryResult = client().execute(query);
            final Reference<ProductType> productTypeReference = queryResult.head().get().getProductType();
            assertThat(productTypeReference).isExpanded();
        });
    }

    @Test
    public void taxCategory() throws Exception {
        withTaxCategory(client(), ProductReferenceExpansionTest.class.toString() + ".taxCategory", taxCategory -> {
            withProduct("taxCategoryReferenceExpansion", product -> {
                final Product productWithTaxCategory = client().execute(new ProductUpdateCommand(product, asList(SetTaxCategory.of(taxCategory.toReference()), Publish.of())));
                assertThat(productWithTaxCategory.getTaxCategory()).isPresent();
                final Query<Product> query = new ProductQuery().
                        bySlug(ProductProjectionType.CURRENT, Locale.ENGLISH, englishSlugOf(product.getMasterData().getStaged())).
                        withExpansionPaths(ProductQuery.expansionPath().taxCategory()).
                        toQuery();
                final PagedQueryResult<Product> queryResult = client().execute(query);
                final Reference<TaxCategory> productTypeReference = firstOf(queryResult).getTaxCategory().get();
                assertThat(productTypeReference).isExpanded();
            });
        });
    }

    public void withProduct(final String testName, final Consumer<Product> user) {
        withProductType(client(), ProductReferenceExpansionTest.class.getName() + "." + testName, productType -> {
            withProduct(client(), new SimpleCottonTShirtNewProductSupplier(productType.toReference(), "foo" + testName), user);
        });
    }

    public static void withProduct(final TestClient client, final Supplier<NewProduct> creator, final Consumer<Product> user) {
        final SphereInternalLogger logger = SphereInternalLogger.getLogger("products.fixtures");
        final NewProduct newProduct = creator.get();
        final String slug = englishSlugOf(newProduct);
        final PagedQueryResult<Product> pagedQueryResult = client.execute(new ProductQuery().bySlug(ProductProjectionType.CURRENT, Locale.ENGLISH, slug));
        delete(client, pagedQueryResult.getResults());
        final Product product = client.execute(new ProductCreateCommand(newProduct));
        logger.debug(() -> "created product " + englishSlugOf(product.getMasterData().getCurrent()) + " " + product.getId() + " of product type " + product.getProductType().getId());
        user.accept(product);
        logger.debug(() -> "attempt to delete product " + englishSlugOf(product.getMasterData().getCurrent()) + " " + product.getId());
        //TODO remove code duplication

        final Product possiblyUpdatedProduct = client.execute(new ProductQuery().bySlug(ProductProjectionType.CURRENT, Locale.ENGLISH, slug)).head().get();

        final Product unPublishedProduct = possiblyUpdatedProduct.getMasterData().isPublished() ? client().execute(new ProductUpdateCommand(product, asList(UnPublish.of()))) : possiblyUpdatedProduct;
        client.execute(new ProductDeleteByIdCommand(unPublishedProduct));
        logger.debug(() -> "deleted product " + englishSlugOf(product.getMasterData().getCurrent()) + " " + product.getId());
    }

    public static void delete(final TestClient client, final List<Product> products) {
        products.forEach(product -> {
            final Product unPublishedProduct = product.getMasterData().isPublished() ? client().execute(new ProductUpdateCommand(product, asList(UnPublish.of()))) : product;
            client.execute(new ProductDeleteByIdCommand(unPublishedProduct));
        });
    }

    public static void withProductType(final TestClient client, final Supplier<NewProductType> creator, final Consumer<ProductType> user) {
        final SphereInternalLogger logger = SphereInternalLogger.getLogger("product-types.fixtures");
        final NewProductType newProductType = creator.get();
        final String name = newProductType.getName();
        final PagedQueryResult<ProductType> queryResult = client.execute(new ProductTypeQuery().byName(name));
        queryResult.getResults().forEach(productType -> {
            final PagedQueryResult<Product> pagedQueryResult = client.execute(new ProductQuery().byProductType(productType.toReference()));
            delete(client, pagedQueryResult.getResults());
            client.execute(new ProductTypeDeleteByIdCommand(productType));

        });
        final ProductType productType = client.execute(new ProductTypeCreateCommand(newProductType));
        logger.debug(() -> "created product type " + productType.getName() + " " + productType.getId());
        user.accept(productType);
        logger.debug(() -> "attempt to delete product type " + productType.getName() + " " + productType.getId());
        try {
            client.execute(new ProductTypeDeleteByIdCommand(productType));
        } catch (final Exception e) {
            final PagedQueryResult<Product> pagedQueryResult = client.execute(new ProductQuery().byProductType(productType.toReference()));
            delete(client, pagedQueryResult.getResults());
            client.execute(new ProductTypeDeleteByIdCommand(productType));
        }
    }

    public static void withProductType(final TestClient client, final String productTypeName, final Consumer<ProductType> user) {
        withProductType(client, new TShirtNewProductTypeSupplier(productTypeName), user);
    }

    public static void withTaxCategory(final TestClient client, final String name, final Consumer<TaxCategory> user) {
        final NewTaxCategory de19 = NewTaxCategory.of(name, asList(TaxRateBuilder.of("de19", 0.19, true, CountryCode.DE).build()));
        final PagedQueryResult<TaxCategory> results = client.execute(new TaxCategoryQuery().byName(name));
        results.getResults().forEach(tc -> client.execute(new TaxCategoryDeleteByIdCommand(tc)));
        final TaxCategory taxCategory = client.execute(new TaxCategoryCreateCommand(de19));
        user.accept(taxCategory);
        client.execute(new TaxCategoryDeleteByIdCommand(taxCategory));
    }
}
