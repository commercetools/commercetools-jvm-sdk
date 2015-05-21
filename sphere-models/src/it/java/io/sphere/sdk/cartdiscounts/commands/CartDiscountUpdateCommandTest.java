package io.sphere.sdk.cartdiscounts.commands;

import io.sphere.sdk.cartdiscounts.*;
import io.sphere.sdk.cartdiscounts.commands.updateactions.*;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.utils.MoneyImpl;
import org.junit.Test;

import java.util.Optional;

import static io.sphere.sdk.cartdiscounts.CartDiscountFixtures.withPersistentCartDiscount;
import static java.lang.String.format;
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
            final String newPredicate = format("totalPrice > \"%d.00 EUR\"", randomInt());

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

    @Test
    public void changeIsActive() throws Exception {
        withPersistentCartDiscount(client(), cartDiscount -> {
            final boolean newIsActice = !cartDiscount.isActive();

            assertThat(cartDiscount.isActive()).isNotEqualTo(newIsActice);

            final CartDiscount updatedDiscount =
                    execute(CartDiscountUpdateCommand.of(cartDiscount, ChangeIsActive.of(newIsActice)));

            assertThat(updatedDiscount.isActive()).isEqualTo(newIsActice);
        });
    }

    @Test
    public void changeName() throws Exception {
        withPersistentCartDiscount(client(), cartDiscount -> {
            final LocalizedStrings newName = randomSlug();

            assertThat(cartDiscount.getName()).isNotEqualTo(newName);

            final CartDiscount updatedDiscount =
                    execute(CartDiscountUpdateCommand.of(cartDiscount, ChangeName.of(newName)));

            assertThat(updatedDiscount.getName()).isEqualTo(newName);
        });
    }

    @Test
    public void setDescription() throws Exception {
        withPersistentCartDiscount(client(), cartDiscount -> {
            final LocalizedStrings newDescription = randomSlug();

            assertThat(cartDiscount.getDescription()).isNotEqualTo(Optional.of(newDescription));

            final CartDiscount updatedDiscount =
                    execute(CartDiscountUpdateCommand.of(cartDiscount, SetDescription.of(newDescription)));

            assertThat(updatedDiscount.getDescription()).contains(newDescription);
        });
    }

    @Test
    public void changeSortOrder() throws Exception {
        withPersistentCartDiscount(client(), cartDiscount -> {
            final String newSortOrder = format("0.%d", randomInt());

            assertThat(cartDiscount.getSortOrder()).isNotEqualTo(newSortOrder);

            final CartDiscount updatedDiscount =
                    execute(CartDiscountUpdateCommand.of(cartDiscount, ChangeSortOrder.of(newSortOrder)));

            assertThat(updatedDiscount.getSortOrder()).isEqualTo(newSortOrder);
        });
    }
}