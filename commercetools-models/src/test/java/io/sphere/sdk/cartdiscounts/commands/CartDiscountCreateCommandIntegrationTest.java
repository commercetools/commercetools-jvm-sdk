package io.sphere.sdk.cartdiscounts.commands;

import io.sphere.sdk.cartdiscounts.*;
import io.sphere.sdk.cartdiscounts.queries.CartDiscountQuery;
import io.sphere.sdk.cartdiscounts.queries.CartDiscountQueryBuilder;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.products.ByIdVariantIdentifier;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.test.SphereTestUtils;
import io.sphere.sdk.utils.MoneyImpl;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import static io.sphere.sdk.cartdiscounts.CartDiscountFixtures.deleteDiscountCodesAndCartDiscounts;
import static io.sphere.sdk.cartdiscounts.CartDiscountFixtures.newCartDiscountDraftBuilder;
import static io.sphere.sdk.products.ProductFixtures.withProduct;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

public class CartDiscountCreateCommandIntegrationTest extends IntegrationTest {
    private CartDiscount cartDiscount;

    @BeforeClass
    public static void clean() {
        deleteDiscountCodesAndCartDiscounts(client());
        client().executeBlocking(CartDiscountQuery.of().withPredicates(m -> m.name().locale(ENGLISH).is("sample cart discount")))
                .getResults().forEach(discount -> client().executeBlocking(CartDiscountDeleteCommand.of(discount)));
    }

    @Test
    public void execution() throws Exception {
        final ZonedDateTime validFrom = SphereTestUtils.now();
        final ZonedDateTime validUntil = validFrom.plusSeconds(8000);
        final LocalizedString name = en("discount name");
        final LocalizedString description = en("discount descriptions");
        final String predicate = "totalPrice > \"800.00 EUR\"";
        final AbsoluteCartDiscountValue value = CartDiscountValue.ofAbsolute(MoneyImpl.of(10, EUR));
        final LineItemsTarget target = LineItemsTarget.of("1 = 1");
        final String sortOrder = "0.54";
        final boolean requiresDiscountCode = false;
        final CartDiscountDraft discountDraft = CartDiscountDraftBuilder.of(name, CartPredicate.of(predicate),
                value, target, sortOrder, requiresDiscountCode)
                .validFrom(validFrom)
                .validUntil(validUntil)
                .description(description)
                .isActive(false)
                .stackingMode(StackingMode.STOP_AFTER_THIS_DISCOUNT)
                .build();

        cartDiscount = client().executeBlocking(CartDiscountCreateCommand.of(discountDraft));
        assertThat(cartDiscount.getName()).isEqualTo(name);
        assertThat(cartDiscount.getCartPredicate()).isEqualTo(predicate);
        assertThat(cartDiscount.getValue()).isEqualTo(value);
        assertThat(cartDiscount.getTarget()).isEqualTo(target);
        assertThat(cartDiscount.getSortOrder()).isEqualTo(sortOrder);
        assertThat(cartDiscount.isRequiringDiscountCode()).isEqualTo(requiresDiscountCode);
        assertThat(cartDiscount.getValidFrom()).isEqualTo(validFrom);
        assertThat(cartDiscount.getValidUntil()).isEqualTo(validUntil);
        assertThat(cartDiscount.getDescription()).isEqualTo(description);
        assertThat(cartDiscount.getReferences()).isEqualTo(Collections.emptyList());
        assertThat(cartDiscount.getStackingMode()).isEqualTo(StackingMode.STOP_AFTER_THIS_DISCOUNT);
    }

    @After
    public void tearDown() throws Exception {
        Optional.ofNullable(cartDiscount)
                .ifPresent(cartDiscount -> client().executeBlocking(CartDiscountDeleteCommand.of(cartDiscount)));
    }

    @Test
    public void absoluteCartDiscountValue() throws Exception {
        checkCartDiscountValueSerialization(CartDiscountValue.ofAbsolute(MoneyImpl.of(10, EUR)));
    }

    @Test
    public void fixedCartDiscountValue() throws Exception {
        checkCartDiscountValueSerialization(CartDiscountValue.ofFixed(MoneyImpl.of(10, EUR)));
    }

    @Test
    public void relativeCartDiscountValue() throws Exception {
        checkCartDiscountValueSerialization(CartDiscountValue.ofRelative(1234));
    }

    @Test
    public void giftLineItemCartDiscountValue() throws Exception {
        withProduct(client(), product -> {
            final ByIdVariantIdentifier variantId = product.getMasterData().getStaged().getMasterVariant().getIdentifier();
            final GiftLineItemCartDiscountValue value =
                    CartDiscountValue.ofGiftLineItem(variantId,
                            null, null);

            checkCreation(builder -> builder.value(value).target(null),
                    discount -> {
                        assertThat(discount.getValue()).isEqualTo(value);
                        assertThat(((GiftLineItemCartDiscountValue)discount.getValue()).getProduct()).isInstanceOf(ResourceIdentifier.class);
                    }
            );
        });
    }

    @Test
    public void lineItemTarget() throws Exception {
        checkTargetSerialization(LineItemsTarget.of("1 = 1"));
    }

    @Test
    public void shippingCostTarget() throws Exception {
        checkTargetSerialization(ShippingCostTarget.of());
    }

    @Test
    public void customLineItemTarget() throws Exception {
        checkTargetSerialization(CustomLineItemsTarget.of("1 = 1"));
    }

    @Test
    public void multiBuyLineItemsTarget() throws Exception {
        final RelativeCartDiscountValue value = RelativeCartDiscountValue.of(500);
        final MultiBuyLineItemsTarget target = MultiBuyLineItemsTarget.of("1 = 1", 3L, 1L,
                SelectionMode.CHEAPEST, 10L);
        checkTargetSerialization(value, target);
    }

    @Test
    public void multiBuyCustomLineItemsTarget() throws Exception {
        final RelativeCartDiscountValue value = RelativeCartDiscountValue.of(500);
        final MultiBuyCustomLineItemsTarget target = MultiBuyCustomLineItemsTarget.of("1 = 1", 3L, 1L,
                SelectionMode.CHEAPEST, 10L);
        checkTargetSerialization(value, target);
    }

    @Test
    public void createByJson() {
        final CartDiscountDraft cartDiscountDraft = SphereJsonUtils.readObjectFromResource("drafts-tests/cartDiscount.json", CartDiscountDraft.class);

        final CartDiscount cartDiscount = client().executeBlocking(CartDiscountCreateCommand.of(cartDiscountDraft));

        assertThat(cartDiscount.getName().get(Locale.ENGLISH)).isEqualTo("sample cart discount");
        assertThat(cartDiscount.getValue()).isEqualTo(AbsoluteCartDiscountValue.of(MoneyImpl.ofCents(1200, EUR)));
        assertThat(cartDiscount.getSortOrder()).isEqualTo("0.45857448");
        assertThat(cartDiscount.isActive()).isTrue();
        assertThat(cartDiscount.getValidUntil()).isEqualTo(ZonedDateTime.of(2001, 9, 11, 14, 0, 0, 0, ZoneId.of("Z")));

        client().executeBlocking(CartDiscountDeleteCommand.of(cartDiscount));
    }

    @Test
    public void deleteByKey(){
        final CartDiscountDraft draft = newCartDiscountDraftBuilder().build();
        final CartDiscount cartDiscount = client().executeBlocking(CartDiscountCreateCommand.of(draft));
        assertThat(cartDiscount).isNotNull();
        client().executeBlocking(CartDiscountDeleteCommand.of(cartDiscount));
        PagedQueryResult<CartDiscount> result = client().executeBlocking(CartDiscountQueryBuilder.of().plusPredicates(cartQueryModel -> cartQueryModel.id().is(cartDiscount.getId())).build());
        assertThat(result.getResults()).isEmpty();
    }

    private void checkCartDiscountValueSerialization(final CartDiscountValue value) {
        checkCreation(builder -> builder.value(value), discount -> assertThat(discount.getValue()).isEqualTo(value));
    }

    private void checkTargetSerialization(final CartDiscountTarget target) {
        checkCreation(builder -> builder.target(target), discount -> assertThat(discount.getTarget()).isEqualTo(target));
    }


    private void checkTargetSerialization(final CartDiscountValue value, final CartDiscountTarget target) {
        checkCreation(builder -> builder.target(target).value(value), discount -> assertThat(discount.getTarget()).isEqualTo(target));
    }

    private void checkCreation(final Function<CartDiscountDraftBuilder, CartDiscountDraftBuilder> f, final Consumer<CartDiscount> assertions) {
        final CartDiscountDraft draft = f.apply(newCartDiscountDraftBuilder())
                .build();
        final CartDiscount discount = client().executeBlocking(CartDiscountCreateCommand.of(draft));
        client().executeBlocking(CartDiscountDeleteCommand.of(discount));
        assertions.accept(discount);
    }
}
