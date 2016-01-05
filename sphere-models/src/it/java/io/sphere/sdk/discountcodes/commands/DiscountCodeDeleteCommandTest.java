package io.sphere.sdk.discountcodes.commands;

import io.sphere.sdk.discountcodes.DiscountCode;
import io.sphere.sdk.discountcodes.queries.DiscountCodeByIdGet;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.discountcodes.DiscountCodeFixtures.createDiscountCode;
import static org.assertj.core.api.Assertions.*;

public class DiscountCodeDeleteCommandTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        final String code = DiscountCodeDeleteCommandTest.class.getSimpleName();
        final DiscountCode discountCode = createDiscountCode(client(), code);
        client().executeBlocking(DiscountCodeDeleteCommand.of(discountCode));
        assertThat(client().executeBlocking(DiscountCodeByIdGet.of(discountCode))).isNull();
    }
}