package io.sphere.sdk.products;

import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static io.sphere.sdk.products.ProductFixtures.withProduct;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

public class GraphQLTest extends IntegrationTest {
    @Test
    public void lightweigthProductsBySku() {
        withProduct(client(), product1 -> {
            withProduct(client(), product2 -> {
                final List<String> skus = asList(product1, product2).stream()
                        .map(product -> product.getMasterData().getStaged().getMasterVariant().getSku())
                        .collect(Collectors.toList());


                final List<LightweightProduct> actual = execute(LightweightProduct.requestOfSkus(skus));
                final List<LightweightProduct> expected = asList(product1, product2).stream()
                        .map(product -> {
                            final String sku = product.getMasterData().getStaged().getMasterVariant().getSku();
                            return new LightweightProduct(product.getId(), product.getVersion(), singletonList(sku));
                        })
                        .collect(Collectors.toList());
                assertThat(actual).isEqualTo(expected);
            });
        });

    }
}
