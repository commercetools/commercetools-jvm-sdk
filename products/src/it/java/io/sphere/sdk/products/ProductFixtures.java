package io.sphere.sdk.products;

import io.sphere.sdk.client.TestClient;
import io.sphere.sdk.products.commands.ProductCreateCommand;
import io.sphere.sdk.products.commands.ProductDeleteByIdCommand;
import io.sphere.sdk.products.commands.ProductUpdateCommand;
import io.sphere.sdk.products.commands.updateactions.AddPrice;
import io.sphere.sdk.products.commands.updateactions.Publish;
import io.sphere.sdk.products.commands.updateactions.SetTaxCategory;
import io.sphere.sdk.products.commands.updateactions.Unpublish;
import io.sphere.sdk.products.queries.ProductFetchById;
import io.sphere.sdk.products.queries.ProductQuery;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.ProductTypeFixtures;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.suppliers.SimpleCottonTShirtProductDraftSupplier;
import io.sphere.sdk.suppliers.TShirtProductTypeDraftSupplier;
import io.sphere.sdk.taxcategories.TaxCategoryFixtures;
import io.sphere.sdk.utils.SphereInternalLogger;
import org.javamoney.moneta.Money;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static io.sphere.sdk.models.DefaultCurrencyUnits.EUR;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static java.util.Arrays.asList;

public class ProductFixtures {
    public static final SphereInternalLogger PRODUCT_FIXTURES_LOGGER = SphereInternalLogger.getLogger("products.fixtures");
    public static final Price PRICE = Price.of(Money.of(new BigDecimal("12.34"), EUR)).withCountry(DE);
    private static final long MASTER_VARIANT_ID = 1;

    public static void withProduct(final TestClient client, final Consumer<Product> user) {
        withProduct(client, randomString(), user);
    }

    public static void withTaxedProduct(final TestClient client, final Consumer<Product> user) {
        TaxCategoryFixtures.withTransientTaxCategory(client, taxCategory ->
            withProduct(client, randomString(), product -> {
                final Product productWithTaxes = client.execute(new ProductUpdateCommand(product, asList(AddPrice.of(MASTER_VARIANT_ID, PRICE, false), SetTaxCategory.of(taxCategory), Publish.of())));
                user.accept(productWithTaxes);
            })
        );
    }

    public static void withProduct(final TestClient client, final String testName, final Consumer<Product> user) {
        withProductType(client, randomString(), productType -> {
            withProduct(client, new SimpleCottonTShirtProductDraftSupplier(productType, "foo" + testName), user);
        });
    }

    public static void withProduct(final TestClient client, final Supplier<ProductDraft> creator, final Consumer<Product> user) {
        final ProductDraft productDraft = creator.get();
        final String slug = englishSlugOf(productDraft);
        final PagedQueryResult<Product> pagedQueryResult = client.execute(new ProductQuery().bySlug(ProductProjectionType.CURRENT, Locale.ENGLISH, slug));
        delete(client, pagedQueryResult.getResults());
        final Product product = client.execute(new ProductCreateCommand(productDraft));
        PRODUCT_FIXTURES_LOGGER.debug(() -> "created product " + englishSlugOf(product.getMasterData().getCurrent().get()) + " " + product.getId() + " of product type " + product.getProductType().getId());
        user.accept(product);
        delete(client, product);
    }

    public static void withProductType(final TestClient client, final String productTypeName, final Consumer<ProductType> user) {
        ProductTypeFixtures.withProductType(client, new TShirtProductTypeDraftSupplier(productTypeName), user);
    }

    public static void delete(final TestClient client, final List<Product> products) {
        products.forEach(product -> delete(client, product));
    }

    public static void delete(final TestClient client, final Product product) {
        final Optional<Product> freshLoadedProduct = client.execute(new ProductFetchById(product));
        freshLoadedProduct.ifPresent(loadedProduct -> {
            final boolean isPublished = loadedProduct.getMasterData().isPublished();
            PRODUCT_FIXTURES_LOGGER.debug(() -> "product is published " + isPublished);
            final Product unPublishedProduct;
            if (isPublished) {
                unPublishedProduct = client.execute(new ProductUpdateCommand(loadedProduct, asList(Unpublish.of())));
            } else {
                unPublishedProduct = loadedProduct;
            }
            PRODUCT_FIXTURES_LOGGER.debug(() -> "attempt to delete product " + englishSlugOf(product.getMasterData().getCurrent().get()) + " " + product.getId());
            client.execute(new ProductDeleteByIdCommand(unPublishedProduct));
            PRODUCT_FIXTURES_LOGGER.debug(() -> "deleted product " + englishSlugOf(product.getMasterData().getCurrent().get()) + " " + product.getId());
        });
    }

}
