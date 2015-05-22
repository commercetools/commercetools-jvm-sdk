package io.sphere.sdk.discountcodes;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.cartdiscounts.CartDiscountFixtures;
import io.sphere.sdk.client.TestClient;
import io.sphere.sdk.discountcodes.commands.DiscountCodeCreateCommand;
import io.sphere.sdk.discountcodes.queries.DiscountCodeQuery;

import java.util.Optional;
import java.util.function.Consumer;

import static io.sphere.sdk.cartdiscounts.CartDiscountFixtures.defaultCartDiscount;
import static io.sphere.sdk.test.SphereTestUtils.*;

public class DiscountCodeFixtures {
    public static void withPersistentDiscountCode(final TestClient client, final Consumer<DiscountCode> consumer) {
        final String code = DiscountCodeFixtures.class.getSimpleName() + "-persistent";
        final Optional<DiscountCode> fetchedDiscountCode = client.execute(DiscountCodeQuery.of()
                .withPredicate(DiscountCodeQuery.model().code().is(code))).head();

        final DiscountCode discountCode = fetchedDiscountCode.orElseGet(() -> createDiscountCode(client, code));
        consumer.accept(discountCode);
    }

    private static DiscountCode createDiscountCode(final TestClient client, final String code) {
        final CartDiscount cartDiscount = defaultCartDiscount(client);
        final DiscountCodeDraft draft = DiscountCodeDraft.of(code, cartDiscount)
                .withName(en("sample discount code"))
                .withDescription(en("sample discount code descr."))
                .withIsActive(false)
                .withMaxApplications(5)
                .withMaxApplicationsPerCustomer(1);
        return client.execute(DiscountCodeCreateCommand.of(draft));
    }

}
