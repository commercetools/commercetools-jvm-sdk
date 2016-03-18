package io.sphere.sdk.producttypes;

import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.products.attributes.AttributeDefinition;
import io.sphere.sdk.products.attributes.AttributeDefinitionBuilder;
import io.sphere.sdk.products.attributes.ReferenceAttributeType;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.attributes.StringAttributeType;
import io.sphere.sdk.products.queries.ProductQuery;
import io.sphere.sdk.producttypes.commands.ProductTypeCreateCommand;
import io.sphere.sdk.producttypes.commands.ProductTypeDeleteCommand;
import io.sphere.sdk.producttypes.queries.ProductTypeByKeyGet;
import io.sphere.sdk.producttypes.queries.ProductTypeQuery;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.suppliers.TShirtProductTypeDraftSupplier;
import io.sphere.sdk.utils.SphereInternalLogger;

import java.util.Collections;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import static io.sphere.sdk.products.ProductFixtures.delete;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static io.sphere.sdk.utils.SphereInternalLogger.getLogger;

public final class ProductTypeFixtures {
    private static final String productReferenceProductTypeName = "productreference";

    private ProductTypeFixtures() {
    }

    public static void withEmptyProductType(final BlockingSphereClient client, final Consumer<ProductType> user) {
        withProductType(client, () -> newEmptyProductDraft(), user);
    }

    public static void withAttributesProductType(final BlockingSphereClient client, final Consumer<ProductType> user) {
        withProductType(client, () -> {
            final AttributeDefinition stringAttribute = AttributeDefinitionBuilder
                    .of("stringfield", randomSlug(), StringAttributeType.of())
                    .build();
            return ProductTypeDraft.of(randomKey(), randomKey(), "desc", Collections.singletonList(stringAttribute));
        }, user);
    }

    public static ProductTypeDraft newEmptyProductDraft() {
        return ProductTypeDraft.of(randomKey(), randomKey(), "desc", Collections.emptyList());
    }

    public static void withEmptyProductType(final BlockingSphereClient client, final String name, final Consumer<ProductType> user) {
        withProductType(client, () -> ProductTypeDraft.of(randomKey(), name, "desc", Collections.emptyList()), user);
    }

    public static void withProductType(final BlockingSphereClient client, final Consumer<ProductType> user) {
        withProductType(client, randomKey(), user);
    }

    public static void withProductType(final BlockingSphereClient client, final String name, final Consumer<ProductType> user) {
        withProductType(client, new TShirtProductTypeDraftSupplier(name), user);
    }

    public static void withProductType(final BlockingSphereClient client, final Supplier<ProductTypeDraft> creator, final Consumer<ProductType> user) {
        withUpdateableProductType(client, creator, productType -> {
            user.accept(productType);
            return productType;
        });
    }

    public static void withUpdateableProductType(final BlockingSphereClient client, final UnaryOperator<ProductType> user) {
        withUpdateableProductType(client, new TShirtProductTypeDraftSupplier(randomKey()), user);
    }

    public static void withUpdateableProductType(final BlockingSphereClient client, final Supplier<ProductTypeDraft> creator, final UnaryOperator<ProductType> user) {
        final SphereInternalLogger logger = SphereInternalLogger.getLogger("product-types.fixtures");
        final ProductTypeDraft productTypeDraft = creator.get();
        final String name = productTypeDraft.getName();
        final PagedQueryResult<ProductType> queryResult = client.executeBlocking(ProductTypeQuery.of().byName(name));
        queryResult.getResults().forEach(productType -> {
            final PagedQueryResult<Product> pagedQueryResult = client.executeBlocking(ProductQuery.of().byProductType(productType));
            delete(client, pagedQueryResult.getResults());
            client.executeBlocking(ProductTypeDeleteCommand.of(productType));

        });
        final ProductType productType = client.executeBlocking(ProductTypeCreateCommand.of(productTypeDraft));
        logger.debug(() -> "created product type " + productType.getName() + " " + productType.getId());
        final ProductType updated = user.apply(productType);
        logger.debug(() -> "attempt to delete product type " + productType.getName() + " " + productType.getId());
        try {
            client.executeBlocking(ProductTypeDeleteCommand.of(updated));
        } catch (final Exception e) {
            final PagedQueryResult<Product> pagedQueryResult = client.executeBlocking(ProductQuery.of().byProductType(productType));
            delete(client, pagedQueryResult.getResults());
            client.executeBlocking(ProductTypeDeleteCommand.of(productType));
        }
    }

    public static void deleteProductType(final BlockingSphereClient client, final ProductType productType) {
        try {
            client.executeBlocking(ProductTypeDeleteCommand.of(productType));
        } catch (Exception e) {
            getLogger("test.fixtures").debug(() -> "no product type to delete");
        }
    }

    public static ProductType productReferenceProductType(final BlockingSphereClient client) {
        final AttributeDefinition productReferenceDefinition = AttributeDefinitionBuilder
                .of("productreference", en("suggested product"), ReferenceAttributeType.ofProduct())
                .required(true)
                .build();
        final ProductTypeDraft productTypeDraft = ProductTypeDraft.of(randomKey(), productReferenceProductTypeName, "has an attribute which is reference to a product", asList(productReferenceDefinition));
        final Optional<ProductType> productTypeOptional = client.executeBlocking(ProductTypeQuery.of().byName(productReferenceProductTypeName)).head();
        return productTypeOptional.orElseGet(() -> client.executeBlocking(ProductTypeCreateCommand.of(productTypeDraft)));
    }

    public static ProductType defaultProductType(final BlockingSphereClient client) {
        final String name = "referenceable-product-1";
        return createProductType(client, name);
    }

    public static ProductType createProductType(final BlockingSphereClient client, final String name) {
        final ProductTypeDraft productTypeDraft = ProductTypeDraft.of(randomKey(), name, "", asList());
        return client.executeBlocking(ProductTypeQuery.of().byName(name)).head()
                .orElseGet(() -> client.executeBlocking(ProductTypeCreateCommand.of(productTypeDraft)));
    }

    public static void removeProductTypeByKey(final BlockingSphereClient client, final String productTypeKey) {
        final ProductType productType = client.executeBlocking(ProductTypeByKeyGet.of(productTypeKey));
        if (productType != null) {
            client.executeBlocking(ProductTypeDeleteCommand.of(productType));
        }
    }
}
