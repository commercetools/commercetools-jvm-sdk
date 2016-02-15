package io.sphere.sdk.discountcodes.queries;

import io.sphere.sdk.discountcodes.DiscountCode;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.discountcodes.DiscountCodeFixtures.withPersistentDiscountCode;
import static org.assertj.core.api.Assertions.*;

public class DiscountCodeByIdGetIntegrationTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        withPersistentDiscountCode(client(), discountCode -> {
            final DiscountCode fetchedCode = client().executeBlocking(DiscountCodeByIdGet.of(discountCode));
            assertThat(fetchedCode.getId()).isEqualTo(discountCode.getId());
        });
    }
}