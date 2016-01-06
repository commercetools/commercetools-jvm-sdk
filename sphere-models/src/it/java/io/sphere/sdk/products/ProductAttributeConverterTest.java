package io.sphere.sdk.products;

import io.sphere.sdk.test.IntegrationTest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ProductAttributeConverterTest extends IntegrationTest {

    private static ProductsScenario1Fixtures.Data data;

    @BeforeClass
    public static void setupScenario() {
        data = ProductsScenario1Fixtures.createScenario(client());
    }

    @AfterClass
    public static void cleanupScenario() {
        ProductsScenario1Fixtures.destroy(client(), data);
        data = null;
    }

    @Test
    public void useDefaultConverter() {


    }
}
