package io.sphere.sdk.cartdiscounts;

import io.sphere.sdk.cartdiscounts.commands.CartDiscountCreateCommand;
import io.sphere.sdk.cartdiscounts.queries.CartDiscountQuery;
import io.sphere.sdk.client.TestClient;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.queries.Query;
import io.sphere.sdk.utils.MoneyImpl;

import java.time.Instant;
import java.util.function.Consumer;

import static io.sphere.sdk.test.SphereTestUtils.*;

public class CartDiscountFixtures {
    public static CartDiscountDraftBuilder newCartDiscountDraftBuilder() {
        final Instant validFrom = Instant.now();
        final Instant validUntil = validFrom.plusSeconds(8000);
        final LocalizedStrings name = en("discount name");
        final LocalizedStrings description = en("discount descriptions");
        final String predicate = "totalPrice > \"800.00 EUR\"";
        final AbsoluteCartDiscountValue value = CartDiscountValue.ofAbsolute(MoneyImpl.of(10, EUR));
        final LineItemsTarget target = LineItemsTarget.of("1 = 1");
        final String sortOrder = randomSortOrder();
        final boolean requiresDiscountCode = false;
        return CartDiscountDraftBuilder.of(name, CartPredicate.of(predicate),
                value, target, sortOrder, requiresDiscountCode)
                .validFrom(validFrom)
                .validUntil(validUntil)
                .description(description);
    }

    public static CartDiscount defaultCartDiscount(final TestClient client) {
        final String name = CartDiscountFixtures.class.getSimpleName() + "default";
        final Query<CartDiscount> query = CartDiscountQuery.of().withPredicate(CartDiscountQuery.model().name().lang(ENGLISH).is(name));
        return client.execute(query).head().orElseGet(() -> {
            final CartDiscountDraft draft = newCartDiscountDraftBuilder().name(LocalizedStrings.ofEnglishLocale(name)).build();
            return client.execute(CartDiscountCreateCommand.of(draft));
        });
    }

    public static void withPersistentCartDiscount(final TestClient client, final Consumer<CartDiscount> consumer) {
        consumer.accept(defaultCartDiscount(client));
    }
}
