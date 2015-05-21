package io.sphere.sdk.cartdiscounts.commands;

import io.sphere.sdk.cartdiscounts.*;
import io.sphere.sdk.cartdiscounts.commands.updateactions.ChangeCartPredicate;
import io.sphere.sdk.cartdiscounts.commands.updateactions.ChangeTarget;
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

    @Test
    public void changeCartPredicate() throws Exception {
        withPersistentCartDiscount(client(), cartDiscount -> {
            final String newPredicate = String.format("totalPrice > \"%d.00 EUR\"", randomInt());

            assertThat(cartDiscount.getCartPredicate()).isNotEqualTo(newPredicate);

            final CartDiscount updatedDiscount =
                    execute(CartDiscountUpdateCommand.of(cartDiscount, ChangeCartPredicate.of(newPredicate)));

            assertThat(updatedDiscount.getCartPredicate()).isEqualTo(newPredicate);
        });
    }

    @Test
    public void changeTarget() throws Exception {
        withPersistentCartDiscount(client(), cartDiscount -> {
            final CartDiscountTarget newTarget = cartDiscount.getTarget() instanceof LineItemsTarget ?
                    CustomLineItemsTarget.of("1 = 1") : LineItemsTarget.of("1 = 1");

            assertThat(cartDiscount.getTarget()).isNotEqualTo(newTarget);

            final CartDiscount updatedDiscount =
                    execute(CartDiscountUpdateCommand.of(cartDiscount, ChangeTarget.of(newTarget)));

            assertThat(updatedDiscount.getTarget()).isEqualTo(newTarget);
        });
    }
}