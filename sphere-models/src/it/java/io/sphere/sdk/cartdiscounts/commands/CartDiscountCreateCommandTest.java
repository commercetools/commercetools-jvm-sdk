package io.sphere.sdk.cartdiscounts.commands;

import io.sphere.sdk.cartdiscounts.*;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.test.SphereTestUtils;
import io.sphere.sdk.utils.MoneyImpl;
import org.junit.After;
import org.junit.Test;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import static io.sphere.sdk.cartdiscounts.CartDiscountFixtures.newCartDiscountDraftBuilder;
import static org.assertj.core.api.Assertions.*;
import static io.sphere.sdk.test.SphereTestUtils.*;

public class CartDiscountCreateCommandTest extends IntegrationTest {
    private CartDiscount cartDiscount;

    @Test
    public void execution() throws Exception {
        final ZonedDateTime validFrom = SphereTestUtils.now();
        final ZonedDateTime validUntil = validFrom.plusSeconds(8000);
        final LocalizedString name = en("discount name");
        final LocalizedString description = en("discount descriptions");
        final String predicate = "totalPrice > \"800.00 EUR\"";
        final AbsoluteCartDiscountValue value = CartDiscountValue.ofAbsolute(MoneyImpl.of(10, EUR));
        final LineItemsTarget target = LineItemsTarget.of("1 = 1");
        final String sortOrder = "0.54";
        final boolean requiresDiscountCode = false;
        final CartDiscountDraft discountDraft = CartDiscountDraftBuilder.of(name, CartDiscountPredicate.of(predicate),
                value, target, sortOrder, requiresDiscountCode)
                .validFrom(validFrom)
                .validUntil(validUntil)
                .description(description)
                .isActive(false)
                .build();

        cartDiscount = client().executeBlocking(CartDiscountCreateCommand.of(discountDraft));
        assertThat(cartDiscount.getName()).isEqualTo(name);
        assertThat(cartDiscount.getCartPredicate()).isEqualTo(predicate);
        assertThat(cartDiscount.getValue()).isEqualTo(value);
        assertThat(cartDiscount.getTarget()).isEqualTo(target);
        assertThat(cartDiscount.getSortOrder()).isEqualTo(sortOrder);
        assertThat(cartDiscount.isRequiringDiscountCode()).isEqualTo(requiresDiscountCode);
        assertThat(cartDiscount.getValidFrom()).isEqualTo(validFrom);
        assertThat(cartDiscount.getValidUntil()).isEqualTo(validUntil);
        assertThat(cartDiscount.getDescription()).isEqualTo(description);
        assertThat(cartDiscount.getReferences()).isEqualTo(Collections.emptyList());
    }

    @After
    public void tearDown() throws Exception {
        Optional.ofNullable(cartDiscount)
                .ifPresent(cartDiscount -> client().executeBlocking(CartDiscountDeleteCommand.of(cartDiscount)));
    }

    @Test
    public void absoluteCartDiscountValue() throws Exception {
        checkCartDiscountValueSerialization(CartDiscountValue.ofAbsolute(MoneyImpl.of(10, EUR)));
    }

    @Test
    public void relativeCartDiscountValue() throws Exception {
        checkCartDiscountValueSerialization(CartDiscountValue.ofRelative(1234));
    }

    @Test
    public void lineItemTarget() throws Exception {
        checkTargetSerialization(LineItemsTarget.of("1 = 1"));
    }

    @Test
    public void ShippingCostTarget() throws Exception {
        checkTargetSerialization(ShippingCostTarget.of());
    }

    @Test
    public void customLineItemTarget() throws Exception {
        checkTargetSerialization(CustomLineItemsTarget.of("1 = 1"));
    }


    private void checkCartDiscountValueSerialization(final CartDiscountValue value) throws Exception {
        checkCreation(builder -> builder.value(value), discount -> assertThat(discount.getValue()).isEqualTo(value));
    }

    private void checkTargetSerialization(final CartDiscountTarget target) throws Exception {
        checkCreation(builder -> builder.target(target), discount -> assertThat(discount.getTarget()).isEqualTo(target));
    }

    private void checkCreation(final Function<CartDiscountDraftBuilder, CartDiscountDraftBuilder> f, final Consumer<CartDiscount> assertions) throws Exception {
        final CartDiscountDraft draft = f.apply(newCartDiscountDraftBuilder())
                .build();
        final CartDiscount discount = client().executeBlocking(CartDiscountCreateCommand.of(draft));
        client().executeBlocking(CartDiscountDeleteCommand.of(discount));
        assertions.accept(discount);
    }
}