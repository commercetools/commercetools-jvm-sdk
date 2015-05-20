package io.sphere.sdk.cartdiscounts;

import io.sphere.sdk.cartdiscounts.commands.CartDiscountCreateCommand;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.utils.MoneyImpl;

import java.time.Instant;
import java.util.Collections;

import static io.sphere.sdk.test.SphereTestUtils.EUR;
import static io.sphere.sdk.test.SphereTestUtils.en;
import static io.sphere.sdk.test.SphereTestUtils.*;

public class CartDiscountFixtures {
    public static CartDiscountDraftBuilder newCartDiscountDraftBuilder() throws Exception {
        final Instant validFrom = Instant.now();
        final Instant validUntil = validFrom.plusSeconds(8000);
        final LocalizedStrings name = en("discount name");
        final LocalizedStrings description = en("discount descriptions");
        final String predicate = "totalPrice > \"800.00 EUR\"";
        final AbsoluteCartDiscountValue value = CartDiscountValue.ofAbsolute(MoneyImpl.of(10, EUR));
        final LineItemsTarget target = LineItemsTarget.of("1 = 1");
        final String sortOrder = "" + randomFloat();
        final boolean requiresDiscountCode = false;
        return CartDiscountDraftBuilder.of(name, CartDiscountPredicate.of(predicate),
                value, target, sortOrder, requiresDiscountCode)
                .validFrom(validFrom)
                .validUntil(validUntil)
                .description(description);
    }
}
