package test;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.client.TestClient;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.products.*;
import io.sphere.sdk.products.commands.ProductCreateCommand;
import io.sphere.sdk.products.commands.ProductDeleteByIdCommand;
import io.sphere.sdk.products.commands.ProductUpdateCommand;
import io.sphere.sdk.products.commands.updateactions.SetTaxCategory;
import io.sphere.sdk.products.commands.updateactions.Unpublish;
import io.sphere.sdk.products.queries.FetchProductById;
import io.sphere.sdk.products.queries.ProductQuery;
import io.sphere.sdk.producttypes.*;
import io.sphere.sdk.producttypes.commands.ProductTypeCreateCommand;
import io.sphere.sdk.producttypes.commands.ProductTypeDeleteByIdCommand;
import io.sphere.sdk.producttypes.queries.ProductTypeQuery;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.Query;
import io.sphere.sdk.taxcategories.*;
import io.sphere.sdk.taxcategories.commands.TaxCategoryCreateCommand;
import io.sphere.sdk.taxcategories.commands.TaxCategoryDeleteByIdCommand;
import io.sphere.sdk.taxcategories.queries.TaxCategoryQuery;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.utils.SphereInternalLogger;
import org.junit.Test;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static io.sphere.sdk.test.ReferenceAssert.assertThat;
import static io.sphere.sdk.test.OptionalAssert.assertThat;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static java.util.Arrays.asList;

public class ProductReferenceExpansionTest extends IntegrationTest {

    public static final SphereInternalLogger PRODUCT_FIXTURES_LOGGER = SphereInternalLogger.getLogger("products.fixtures");

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
                final Product productWithTaxCategory = client().execute(new ProductUpdateCommand(product, SetTaxCategory.of(taxCategory)));
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
        withProduct(client(), testName, user);
    }

    public static void withProduct(final TestClient client, final String testName, final Consumer<Product> user) {
        withProductType(client, ProductReferenceExpansionTest.class.getName() + "." + testName, productType -> {
            withProduct(client, new SimpleCottonTShirtNewProductSupplier(productType, "foo" + testName), user);
        });
    }

    public static void withProduct(final TestClient client, final Supplier<NewProduct> creator, final Consumer<Product> user) {
        final NewProduct newProduct = creator.get();
        final String slug = englishSlugOf(newProduct);
        final PagedQueryResult<Product> pagedQueryResult = client.execute(new ProductQuery().bySlug(ProductProjectionType.CURRENT, Locale.ENGLISH, slug));
        delete(client, pagedQueryResult.getResults());
        final Product product = client.execute(new ProductCreateCommand(newProduct));
        PRODUCT_FIXTURES_LOGGER.debug(() -> "created product " + englishSlugOf(product.getMasterData().getCurrent().get()) + " " + product.getId() + " of product type " + product.getProductType().getId());
        user.accept(product);
        delete(client(), product);
    }

    public static void delete(final TestClient client, final Product product) {
        final Optional<Product> freshLoadedProduct = client.execute(new FetchProductById(product));
        freshLoadedProduct.ifPresent(loadedProduct -> {
            final boolean isPublished = loadedProduct.getMasterData().isPublished();
            PRODUCT_FIXTURES_LOGGER.debug(() -> "product is published " + isPublished);
            final Product unPublishedProduct;
            if (isPublished) {
                unPublishedProduct = client().execute(new ProductUpdateCommand(loadedProduct, asList(Unpublish.of())));
            } else {
                unPublishedProduct = loadedProduct;
            }
            PRODUCT_FIXTURES_LOGGER.debug(() -> "attempt to delete product " + englishSlugOf(product.getMasterData().getCurrent().get()) + " " + product.getId());
            client.execute(new ProductDeleteByIdCommand(unPublishedProduct));
            PRODUCT_FIXTURES_LOGGER.debug(() -> "deleted product " + englishSlugOf(product.getMasterData().getCurrent().get()) + " " + product.getId());
        });
    }

    public static void delete(final TestClient client, final List<Product> products) {
        products.forEach(product -> delete(client, product));
    }

    public static void withProductType(final TestClient client, final Supplier<NewProductType> creator, final Consumer<ProductType> user) {
        final SphereInternalLogger logger = SphereInternalLogger.getLogger("product-types.fixtures");
        final NewProductType newProductType = creator.get();
        final String name = newProductType.getName();
        final PagedQueryResult<ProductType> queryResult = client.execute(new ProductTypeQuery().byName(name));
        queryResult.getResults().forEach(productType -> {
            final PagedQueryResult<Product> pagedQueryResult = client.execute(new ProductQuery().byProductType(productType));
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
            final PagedQueryResult<Product> pagedQueryResult = client.execute(new ProductQuery().byProductType(productType));
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
