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
                    .withName(en(DiscountCodeCreateCommandTest.class.getName()))
                    .withDescription(en("sample discount code descr."))
                    .withCartPredicate(CartDiscountPredicate.of("1 = 1"))
                    .withIsActive(false)
                    .withMaxApplications(5L)
                    .withMaxApplicationsPerCustomer(1L);
            final DiscountCode discountCode = client().executeBlocking(DiscountCodeCreateCommand.of(draft));
            assertThat(discountCode.getCode()).isEqualTo(code);
            assertThat(discountCode.getName()).isEqualTo(en(DiscountCodeCreateCommandTest.class.getName()));
            assertThat(discountCode.getDescription()).isEqualTo(en("sample discount code descr."));
            assertThat(discountCode.getCartDiscounts()).isEqualTo(asList(cartDiscount.toReference()));
            assertThat(discountCode.getCartPredicate()).contains("1 = 1");
            assertThat(discountCode.isActive()).isEqualTo(false);
            assertThat(discountCode.getMaxApplications()).isEqualTo(5L);
            assertThat(discountCode.getMaxApplicationsPerCustomer()).isEqualTo(1L);
            //clean up
            client().executeBlocking(DiscountCodeDeleteCommand.of(discountCode));
        });
    }
}