package io.sphere.sdk.cartdiscounts;

import io.sphere.sdk.cartdiscounts.commands.CartDiscountCreateCommand;
import io.sphere.sdk.cartdiscounts.commands.CartDiscountDeleteCommand;
import io.sphere.sdk.cartdiscounts.queries.CartDiscountQuery;
import io.sphere.sdk.cartdiscounts.queries.CartDiscountQueryModel;
import io.sphere.sdk.client.TestClient;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.queries.Query;
import io.sphere.sdk.utils.MoneyImpl;

import java.time.Instant;
import java.util.function.Consumer;
import java.util.function.Function;

import static io.sphere.sdk.test.SphereTestUtils.*;

public class CartDiscountFixtures {
    public static CartDiscountDraftBuilder newCartDiscountDraftBuilder() {
        return newCartDiscountDraftBuilder("totalPrice > \"800.00 EUR\"");
    }

    private static CartDiscountDraftBuilder newCartDiscountDraftBuilder(final String predicate) {
        final Instant validFrom = Instant.now();
        final Instant validUntil = validFrom.plusSeconds(8000);
        final LocalizedStrings name = en("discount name");
        final LocalizedStrings description = en("discount descriptions");
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
        return getCartDiscount(client, CartDiscountFixtures.class.getSimpleName() + "default-4");
    }

    private static CartDiscount getCartDiscount(final TestClient client, final String name) {
        final Query<CartDiscount> query = CartDiscountQuery.of().withPredicate(m -> m.name().lang(ENGLISH).is(name));
        return client.execute(query).head().orElseGet(() -> {
            final CartDiscountDraft draft = newCartDiscountDraftBuilder()
                    .name(LocalizedStrings.ofEnglishLocale(name))
                    .build();
            return client.execute(CartDiscountCreateCommand.of(draft));
        });
    }

    public static void withCartDiscount(final TestClient client, final String name, final Consumer<CartDiscount> consumer) {
        final CartDiscount cartDiscount = getCartDiscount(client, name);
        consumer.accept(cartDiscount);
        client.execute(CartDiscountDeleteCommand.of(cartDiscount));
    }

    public static void withPersistentCartDiscount(final TestClient client, final String name, final Consumer<CartDiscount> consumer) {
        consumer.accept(getCartDiscount(client, name));
    }

    public static void withPersistentCartDiscount(final TestClient client, final Consumer<CartDiscount> consumer) {
        consumer.accept(defaultCartDiscount(client));
    }

    public static void withCartDiscount(final TestClient client, final Function<CartDiscount, CartDiscount> consumer) {
        final CartDiscountDraft draft = newCartDiscountDraftBuilder().build();
        final CartDiscount cartDiscount = client.execute(CartDiscountCreateCommand.of(draft));
        client.execute(CartDiscountDeleteCommand.of(consumer.apply(cartDiscount)));
    }
}
