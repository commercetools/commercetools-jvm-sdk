package io.sphere.sdk.discountcodes.commands;

import io.sphere.sdk.cartdiscounts.CartDiscountPredicate;
import io.sphere.sdk.discountcodes.DiscountCode;
import io.sphere.sdk.discountcodes.DiscountCodeDraft;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.cartdiscounts.CartDiscountFixtures.withPersistentCartDiscount;
import static org.assertj.core.api.Assertions.*;
import static io.sphere.sdk.test.SphereTestUtils.*;

public class DiscountCodeCreateCommandTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        withPersistentCartDiscount(client(), cartDiscount -> {
            final String code = randomKey();
            final DiscountCodeDraft draft = DiscountCodeDraft.of(code, cartDiscount)
                    .withName(en("sample discount code"))
                    .withDescription(en("sample discount code descr."))
                    .withCartPredicate(CartDiscountPredicate.of("1 = 1"))
                    .withIsActive(false)
                    .withMaxApplications(5)
                    .withMaxApplicationsPerCustomer(1);
            final DiscountCode discountCode = execute(DiscountCodeCreateCommand.of(draft));
            assertThat(discountCode.getCode()).isEqualTo(code);
            assertThat(discountCode.getName()).contains(en("sample discount code"));
            assertThat(discountCode.getDescription()).contains(en("sample discount code descr."));
            assertThat(discountCode.getCartDiscounts()).isEqualTo(asList(cartDiscount.toReference()));
            assertThat(discountCode.getCartPredicate()).contains("1 = 1");
            assertThat(discountCode.isActive()).isEqualTo(false);
            assertThat(discountCode.getMaxApplications()).contains(5L);
            assertThat(discountCode.getMaxApplicationsPerCustomer()).contains(1L);
        });
    }
}