package io.sphere.sdk.discountcodes;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.discountcodes.commands.DiscountCodeCreateCommand;
import io.sphere.sdk.discountcodes.commands.DiscountCodeDeleteCommand;
import io.sphere.sdk.discountcodes.queries.DiscountCodeQuery;
import io.sphere.sdk.discountcodes.queries.DiscountCodeQueryModel;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import static io.sphere.sdk.cartdiscounts.CartDiscountFixtures.defaultCartDiscount;
import static io.sphere.sdk.test.SphereTestUtils.en;

public class DiscountCodeFixtures {
    public static void withPersistentDiscountCode(final BlockingSphereClient client, final Consumer<DiscountCode> consumer) {
        final String code = DiscountCodeFixtures.class.getSimpleName() + "-persistent-4";
        final Optional<DiscountCode> fetchedDiscountCode = client.executeBlocking(DiscountCodeQuery.of()
                .withPredicates(DiscountCodeQueryModel.of().code().is(code))).head();

        final DiscountCode discountCode = fetchedDiscountCode.orElseGet(() -> createDiscountCode(client, code));
        consumer.accept(discountCode);
    }

    public static DiscountCode createDiscountCode(final BlockingSphereClient client, final String code) {
        final DiscountCodeDraft draft = discountCodeDraftBuilder(client, code).build();
        return client.executeBlocking(DiscountCodeCreateCommand.of(draft));
    }

    public static DiscountCodeDraftBuilder discountCodeDraftBuilder(BlockingSphereClient client, String code) {
        final CartDiscount cartDiscount = defaultCartDiscount(client, true);
        return DiscountCodeDraftBuilder.of(code, cartDiscount)
                .name(en("sample discount code"))
                .description(en("sample discount code descr."))
                .isActive(true)
                .maxApplications(1000L)
                .maxApplicationsPerCustomer(1L);
    }

    public static void withDiscountCode(final BlockingSphereClient client, final DiscountCodeDraft draft, final Consumer<DiscountCode> consumer) {
        final DiscountCode discountCode = client.executeBlocking(DiscountCodeCreateCommand.of(draft));
        consumer.accept(discountCode);
        client.executeBlocking(DiscountCodeDeleteCommand.of(discountCode));
    }

    public static void withUpdateableDiscountCode(final BlockingSphereClient client, final DiscountCodeDraft draft, final Function<DiscountCode, DiscountCode> f) {
        final DiscountCode discountCode = client.executeBlocking(DiscountCodeCreateCommand.of(draft));
        final DiscountCode updatedDiscountCode = f.apply(discountCode);
        client.executeBlocking(DiscountCodeDeleteCommand.of(updatedDiscountCode));
    }
}
