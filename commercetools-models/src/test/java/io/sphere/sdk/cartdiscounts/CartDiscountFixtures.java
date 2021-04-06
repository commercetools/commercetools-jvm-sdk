package io.sphere.sdk.cartdiscounts;

import io.sphere.sdk.cartdiscounts.commands.CartDiscountCreateCommand;
import io.sphere.sdk.cartdiscounts.commands.CartDiscountDeleteCommand;
import io.sphere.sdk.cartdiscounts.queries.CartDiscountQuery;
import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.discountcodes.DiscountCode;
import io.sphere.sdk.discountcodes.commands.DiscountCodeDeleteCommand;
import io.sphere.sdk.discountcodes.queries.DiscountCodeQuery;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.Query;
import io.sphere.sdk.test.SphereTestUtils;
import io.sphere.sdk.utils.MoneyImpl;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import static io.sphere.sdk.test.SphereTestUtils.*;

public class CartDiscountFixtures {


    public static void deleteDiscountCodesAndCartDiscounts(final BlockingSphereClient client){
        PagedQueryResult<DiscountCode> discountCodeResponse;
        PagedQueryResult<CartDiscount> cartDiscountResponse;

        do {
            discountCodeResponse = client.executeBlocking(DiscountCodeQuery.of());
            discountCodeResponse.getResults().forEach(discountCode -> {
                client.executeBlocking(DiscountCodeDeleteCommand.of(discountCode));
            });
        } while (discountCodeResponse.getResults().size() != 0);

        do {
            cartDiscountResponse = client.executeBlocking(CartDiscountQuery.of());
            cartDiscountResponse.getResults().forEach(cartDiscount -> {
                client.executeBlocking(CartDiscountDeleteCommand.of(cartDiscount));
            });
        } while (cartDiscountResponse.getResults().size() != 0);
    }

    public static CartDiscountDraftBuilder newCartDiscountDraftBuilder() {
        return newCartDiscountDraftBuilder("totalPrice > \"800.00 EUR\"");
    }

    private static CartDiscountDraftBuilder newCartDiscountDraftBuilder(final String predicate) {
        final ZonedDateTime validFrom = ZonedDateTime.now().minusHours(1);
        final ZonedDateTime validUntil = validFrom.plusSeconds(8000);
        final LocalizedString name = en("discount name");
        final LocalizedString description = en("discount descriptions");
        final AbsoluteCartDiscountValue value = CartDiscountValue.ofAbsolute(MoneyImpl.of(10, EUR));
        final LineItemsTarget target = LineItemsTarget.of("1 = 1");
        final String sortOrder = randomSortOrder();
        final boolean requiresDiscountCode = false;

        return CartDiscountDraftBuilder.of(name, CartPredicate.of(predicate),
                value, target, sortOrder, requiresDiscountCode)
                .validFrom(validFrom)
                .validUntil(validUntil)
                .description(description)
                .key(SphereTestUtils.randomKey());
    }

    public static CartDiscount defaultCartDiscount(final BlockingSphereClient client) {
        return defaultCartDiscount(client, false);
    }

    public static CartDiscount defaultCartDiscount(final BlockingSphereClient client, final boolean active) {
        return getCartDiscount(client, CartDiscountFixtures.class.getSimpleName() + "default-4", active);
    }

    private static CartDiscount getCartDiscount(final BlockingSphereClient client, final String name) {
        return getCartDiscount(client, name, false);
    }

    private static CartDiscount getCartDiscount(final BlockingSphereClient client, final String name, final boolean active) {
        final Query<CartDiscount> query = CartDiscountQuery.of().withPredicates(m -> m.name().lang(ENGLISH).is(name));
        return client.executeBlocking(query).head().orElseGet(() -> {
            final CartDiscountDraft draft = newCartDiscountDraftBuilder()
                    .requiresDiscountCode(true)
                    .isActive(active)
                    .name(LocalizedString.ofEnglish(name))
                    .build();
            return client.executeBlocking(CartDiscountCreateCommand.of(draft));
        });
    }

    public static void withCartDiscount(final BlockingSphereClient client, final UnaryOperator<CartDiscountDraftBuilder> builderUnaryOperator, final Consumer<CartDiscount> consumer) {
        final CartDiscountDraft draft = builderUnaryOperator.apply(newCartDiscountDraftBuilder()).build();
        final CartDiscount cartDiscount = client.executeBlocking(CartDiscountCreateCommand.of(draft));
        consumer.accept(cartDiscount);
        client.executeBlocking(CartDiscountDeleteCommand.of(cartDiscount));
    }

    public static void withCartDiscount(final BlockingSphereClient client, final UnaryOperator<CartDiscountDraftBuilder> builderUnaryOperator, final UnaryOperator<CartDiscount> update) {
        final CartDiscountDraft draft = builderUnaryOperator.apply(newCartDiscountDraftBuilder()).build();
        final CartDiscount cartDiscount = client.executeBlocking(CartDiscountCreateCommand.of(draft));
        final CartDiscount updatedCartDiscount = update.apply(cartDiscount);
        client.executeBlocking(CartDiscountDeleteCommand.of(updatedCartDiscount));
    }

    public static void withCartDiscount(final BlockingSphereClient client, final String name, final Consumer<CartDiscount> consumer) {
        final CartDiscount cartDiscount = getCartDiscount(client, name);
        consumer.accept(cartDiscount);
        client.executeBlocking(CartDiscountDeleteCommand.of(cartDiscount));
    }

    public static void withPersistentCartDiscount(final BlockingSphereClient client, final String name, final Consumer<CartDiscount> consumer) {
        consumer.accept(getCartDiscount(client, name));
    }

    public static void withPersistentCartDiscount(final BlockingSphereClient client, final Consumer<CartDiscount> consumer) {
        consumer.accept(defaultCartDiscount(client));
    }

    public static void withCartDiscount(final BlockingSphereClient client, final Function<CartDiscount, CartDiscount> consumer) {
        final CartDiscountDraft draft = newCartDiscountDraftBuilder().build();
        final CartDiscount cartDiscount = client.executeBlocking(CartDiscountCreateCommand.of(draft));
        client.executeBlocking(CartDiscountDeleteCommand.of(consumer.apply(cartDiscount)));
    }
}
