package io.sphere.sdk.products.queries;

import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.products.*;
import io.sphere.sdk.products.commands.ProductCreateCommand;
import io.sphere.sdk.products.commands.ProductDeleteCommand;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.ProductTypeFixtures;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static io.sphere.sdk.test.SphereTestUtils.en;
import static io.sphere.sdk.test.SphereTestUtils.randomSlug;
import static java.util.stream.Collectors.toList;

public abstract class QueryAllBase extends IntegrationTest {

    protected static final Comparator<Product> BY_ID_COMPARATOR = Comparator.comparing(p -> p.getId());
    protected static List<Product> createdProducts;
    protected static ProductType productType;

    protected static SphereClient sphereClient() {
        return client();
    }

    @BeforeClass
    public static void createProducts() {
        ProductFixtures.deleteProductsAndProductTypes(client());
        productType = ProductTypeFixtures.createProductType(client(), "QueryAllProductsDemo");
        final ProductVariantDraft masterVariant = ProductVariantDraftBuilder.of().build();
        final Stream<Product> productStream = Stream.generate(() -> {
            final ProductDraft productDraft = ProductDraftBuilder.of(productType, en("QueryAllDemoProduct"), randomSlug(), masterVariant).build();
            return client().executeBlocking(ProductCreateCommand.of(productDraft));
        });
        createdProducts = productStream.limit(19).sorted(BY_ID_COMPARATOR).collect(toList());
    }

    @AfterClass
    public static void deleteProducts() {
        ProductFixtures.deleteProductsAndProductTypes(client());
        productType = null;
        createdProducts = null;
    }
}
