package io.sphere.sdk.attributestutorial;

import io.sphere.sdk.attributes.*;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.products.*;
import io.sphere.sdk.products.commands.ProductCreateCommand;
import io.sphere.sdk.products.commands.ProductDeleteCommand;
import io.sphere.sdk.products.queries.ProductProjectionQuery;
import io.sphere.sdk.products.queries.ProductQuery;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.ProductTypeDraft;
import io.sphere.sdk.producttypes.commands.ProductTypeCreateCommand;
import io.sphere.sdk.producttypes.commands.ProductTypeDeleteCommand;
import io.sphere.sdk.producttypes.queries.ProductTypeQuery;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.CompletionException;

import static io.sphere.sdk.test.SphereTestUtils.*;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

public class ProductTypeCreationDemoTest extends IntegrationTest {

    private static final String PRODUCT_TYPE_NAME = "tshirt-product-attribute-tutorial";

    public void demoCheckingIfProductTypeExist() {
        final ProductTypeQuery query = ProductTypeQuery.of().byName(PRODUCT_TYPE_NAME);
        final boolean productTypeAlreadyExists = client().execute(query).head().isPresent();
    }

    @Test
    public void createProductType() throws Exception {
        final ProductTypeQuery query = ProductTypeQuery.of().byName(PRODUCT_TYPE_NAME);
        final boolean productTypeAlreadyExists = client().execute(query).head().isPresent();
        if(productTypeAlreadyExists)
            throw new RuntimeException("ProductType with name " + PRODUCT_TYPE_NAME + " already exist.");

        final ProductTypeDraft productTypeDraft = ProductTypeDraft.of(PRODUCT_TYPE_NAME, "a 'T' shaped cloth", asList());
        final ProductType productType = client().execute(ProductTypeCreateCommand.of(productTypeDraft));
    }

    @AfterClass
    @BeforeClass
    public static void deleteProductsAndProductType() {
        final List<ProductType> productTypes = execute(ProductTypeQuery.of().byName(PRODUCT_TYPE_NAME)).getResults();
        if (!productTypes.isEmpty()) {
            final ProductQuery productQuery = ProductQuery.of()
                    .withPredicate(m -> m.productType().isIn(productTypes))
                    .withLimit(500);
            execute(productQuery).getResults().forEach(p -> execute(ProductDeleteCommand.of(p)));
            productTypes.forEach(p -> execute(ProductTypeDeleteCommand.of(p)));
        }
    }
}
