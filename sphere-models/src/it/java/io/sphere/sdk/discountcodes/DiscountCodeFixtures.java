package io.sphere.sdk.discountcodes;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.discountcodes.commands.DiscountCodeCreateCommand;
import io.sphere.sdk.discountcodes.commands.DiscountCodeDeleteCommand;
import io.sphere.sdk.discountcodes.queries.DiscountCodeQuery;
import io.sphere.sdk.discountcodes.queries.DiscountCodeQueryModel;

import java.util.Optional;
import java.util.function.Consumer;

import static io.sphere.sdk.cartdiscounts.CartDiscountFixtures.defaultCartDiscount;
import static io.sphere.sdk.test.SphereTestUtils.*;

public class DiscountCodeFixtures {
    public static void withPersistentDiscountCode(final BlockingSphereClient client, final Consumer<DiscountCode> consumer) {
        final String code = DiscountCodeFixtures.class.getSimpleName() + "-persistent-4";
        final Optional<DiscountCode> fetchedDiscountCode = client.executeBlocking(DiscountCodeQuery.of()
                .withPredicates(DiscountCodeQueryModel.of().code().is(code))).head();

        final DiscountCode discountCode = fetchedDiscountCode.orElseGet(() -> createDiscountCode(client, code));
        consumer.accept(discountCode);
    }

    public static DiscountCode createDiscountCode(final BlockingSphereClient client, final String code) {
        final CartDiscount cartDiscount = defaultCartDiscount(client);
        final DiscountCodeDraft draft = DiscountCodeDraft.of(code, cartDiscount)
                .withName(en("sample discount code"))
                .withDescription(en("sample discount code descr."))
                .withIsActive(true)
                .withMaxApplications(5L)
                .withMaxApplicationsPerCustomer(1L);
        return client.executeBlocking(DiscountCodeCreateCommand.of(draft));
    }

    public static void withDiscountCode(final BlockingSphereClient client, final DiscountCodeDraft draft, final Consumer<DiscountCode> consumer) {
        final DiscountCode discountCode = client.executeBlocking(DiscountCodeCreateCommand.of(draft));
        consumer.accept(discountCode);
        client.executeBlocking(DiscountCodeDeleteCommand.of(discountCode));
    }
}
