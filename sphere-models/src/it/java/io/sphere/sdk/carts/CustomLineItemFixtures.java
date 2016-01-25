package io.sphere.sdk.carts;

import io.sphere.sdk.taxcategories.TaxCategory;
import io.sphere.sdk.utils.MoneyImpl;

import javax.money.MonetaryAmount;
import java.math.BigDecimal;

import static io.sphere.sdk.test.SphereTestUtils.EUR;
import static io.sphere.sdk.test.SphereTestUtils.en;

public class CustomLineItemFixtures {

    public static CustomLineItemDraft createCustomLineItemDraft(final TaxCategory taxCategory) {
        final MonetaryAmount money = MoneyImpl.of(new BigDecimal("23.50"), EUR);
        return CustomLineItemDraft.of(en("thing"), "thing-slug", money, taxCategory, 5L, null);
    }
}
