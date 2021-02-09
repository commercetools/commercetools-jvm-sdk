package io.sphere.sdk.producttypes.errors;

import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.ProductTypeFixtures;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

public class ProductTypeErrorIntegrationTest extends IntegrationTest {

    @Test
    public void attributeDefinitionAlreadyExists() {
        ProductType productType1 = ProductTypeFixtures.productReferenceProductType(client());
        ProductType productType2 = ProductTypeFixtures.productReferenceProductType(client());
        ass
    }
}
