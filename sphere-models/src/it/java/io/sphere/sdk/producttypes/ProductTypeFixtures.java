package io.sphere.sdk.producttypes;

import io.sphere.sdk.client.TestClient;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.queries.ProductQuery;
import io.sphere.sdk.producttypes.commands.ProductTypeCreateCommand;
import io.sphere.sdk.producttypes.commands.ProductTypeDeleteCommand;
import io.sphere.sdk.producttypes.queries.ProductTypeQuery;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.suppliers.TShirtProductTypeDraftSupplier;
import io.sphere.sdk.utils.SphereInternalLogger;

import java.util.Collections;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static io.sphere.sdk.products.ProductFixtures.delete;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static io.sphere.sdk.utils.SphereInternalLogger.getLogger;

public final class ProductTypeFixtures {
    private ProductTypeFixtures() {
    }

    public static void withEmptyProductType(final TestClient client, final String name, final Consumer<ProductType> user) {
        withProductType(client, () -> ProductTypeDraft.of(name, "desc", Collections.emptyList()), user);
    }

    public static void withProductType(final TestClient client, final String name, final Consumer<ProductType> user) {
        withProductType(client, new TShirtProductTypeDraftSupplier(name), user);
    }

    public static void withProductType(final TestClient client, final Supplier<ProductTypeDraft> creator, final Consumer<ProductType> user) {
        final SphereInternalLogger logger = SphereInternalLogger.getLogger("product-types.fixtures");
        final ProductTypeDraft productTypeDraft = creator.get();
        final String name = productTypeDraft.getName();
        final PagedQueryResult<ProductType> queryResult = client.execute(ProductTypeQuery.of().byName(name));
        queryResult.getResults().forEach(productType -> {
            final PagedQueryResult<Product> pagedQueryResult = client.execute(ProductQuery.of().byProductType(productType));
            delete(client, pagedQueryResult.getResults());
            client.execute(ProductTypeDeleteCommand.of(productType));

        });
        final ProductType productType = client.execute(ProductTypeCreateCommand.of(productTypeDraft));
        logger.debug(() -> "created product type " + productType.getName() + " " + productType.getId());
        user.accept(productType);
        logger.debug(() -> "attempt to delete product type " + productType.getName() + " " + productType.getId());
        try {
            client.execute(ProductTypeDeleteCommand.of(productType));
        } catch (final Exception e) {
            final PagedQueryResult<Product> pagedQueryResult = client.execute(ProductQuery.of().byProductType(productType));
            delete(client, pagedQueryResult.getResults());
            client.execute(ProductTypeDeleteCommand.of(productType));
        }
    }

    public static void deleteProductType(final TestClient client, final ProductType productType) {
        try {
            client.execute(ProductTypeDeleteCommand.of(productType));
        } catch (Exception e) {
            getLogger("test.fixtures").debug(() -> "no product type to delete");
        }
    }

    public static ProductType defaultProductType(final TestClient client) {
        final String name = "referenceable-product-1";
        final ProductTypeDraft productTypeDraft = ProductTypeDraft.of(name, "", asList());
        return client.execute(ProductTypeQuery.of().byName(name)).head()
                .orElseGet(() -> client.execute(ProductTypeCreateCommand.of(productTypeDraft)));
    }
}
