package io.sphere.sdk.cartdiscounts.commands;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.cartdiscounts.CartDiscountValue;
import io.sphere.sdk.cartdiscounts.commands.updateactions.ChangeValue;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.utils.MoneyImpl;
import org.junit.Test;

import static io.sphere.sdk.cartdiscounts.CartDiscountFixtures.withPersistentCartDiscount;
import static org.assertj.core.api.Assertions.*;
import static io.sphere.sdk.test.SphereTestUtils.*;

public class CartDiscountUpdateCommandTest extends IntegrationTest {
    @Test
    public void changeValue() throws Exception {
        withPersistentCartDiscount(client(), cartDiscount -> {
            final CartDiscountValue newValue = CartDiscountValue.ofAbsolute(MoneyImpl.of(randomInt(), EUR));

            assertThat(cartDiscount.getValue()).isNotEqualTo(newValue);

            final CartDiscount updatedDiscount =
                    execute(CartDiscountUpdateCommand.of(cartDiscount, ChangeValue.of(newValue)));

            assertThat(updatedDiscount.getValue()).isEqualTo(newValue);
        });
    }
}