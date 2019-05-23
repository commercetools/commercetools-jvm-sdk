package io.sphere.sdk.carts;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.carts.commands.CartUpdateCommand;
import io.sphere.sdk.carts.commands.updateactions.ChangeTaxRoundingMode;
import io.sphere.sdk.carts.queries.CartQuery;
import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.orders.*;
import io.sphere.sdk.orders.commands.OrderDeleteCommand;
import io.sphere.sdk.orders.commands.OrderImportCommand;
import io.sphere.sdk.orders.queries.OrderQuery;
import io.sphere.sdk.products.PriceDraft;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.commands.ProductUpdateCommand;
import io.sphere.sdk.products.commands.updateactions.AddPrice;
import io.sphere.sdk.products.commands.updateactions.Publish;
import io.sphere.sdk.products.commands.updateactions.SetTaxCategory;
import io.sphere.sdk.taxcategories.*;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.utils.MoneyImpl;
import org.junit.Test;

import javax.money.MonetaryAmount;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import static io.sphere.sdk.carts.CartFixtures.withCartDraft;
import static io.sphere.sdk.models.DefaultCurrencyUnits.EUR;
import static io.sphere.sdk.products.ProductFixtures.withProduct;
import static io.sphere.sdk.taxcategories.TaxCategoryFixtures.withTaxCategory;
import static io.sphere.sdk.taxcategories.TaxCategoryFixtures.withTransientTaxCategory;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

public class TaxRoundingModeIntegrationTest extends IntegrationTest {

    @Test
    public void cartTaxRoundingModeWithTaxesIncludedInPrice() {
        final int centAmount = 14;
        final double taxRate = 0.12;
        final boolean isTaxIncluded = true;
        withTaxedCartWithProduct(client(), centAmount, taxRate, isTaxIncluded, cartWithDefaultRounding -> {
            assertThat(cartWithDefaultRounding.getTaxRoundingMode()).isEqualTo(RoundingMode.HALF_EVEN);
            assertThat(extractTaxAmount(cartWithDefaultRounding))
                    .as("Should be rounded half to even")
                    .isEqualTo(0.02);

            final CartUpdateCommand updateToHalfUp = CartUpdateCommand.of(cartWithDefaultRounding, ChangeTaxRoundingMode.of(RoundingMode.HALF_UP));
            final Cart cartWithHalfUpRounding = client().executeBlocking(updateToHalfUp);
            assertThat(cartWithHalfUpRounding.getTaxRoundingMode()).isEqualTo(RoundingMode.HALF_UP);
            assertThat(extractTaxAmount(cartWithHalfUpRounding))
                    .as("Should be rounded half up")
                    .isEqualTo(0.01);

            final CartUpdateCommand updateToHalfDown = CartUpdateCommand.of(cartWithHalfUpRounding, ChangeTaxRoundingMode.of(RoundingMode.HALF_DOWN));
            final Cart cartWithHalfDownRounding = client().executeBlocking(updateToHalfDown);
            assertThat(cartWithHalfDownRounding.getTaxRoundingMode()).isEqualTo(RoundingMode.HALF_DOWN);
            assertThat(extractTaxAmount(cartWithHalfDownRounding))
                    .as("Should be rounded half down")
                    .isEqualTo(0.02);

            return cartWithHalfDownRounding;
        });
    }

    @Test
    public void cartTaxRoundingModeWithTaxesExcludedFromPrice() {
        final int centAmount = 25;
        final double taxRate = 0.14;
        final boolean isTaxIncluded = false;
        withTaxedCartWithProduct(client(), centAmount, taxRate, isTaxIncluded, cartWithDefaultRounding -> {
            assertThat(cartWithDefaultRounding.getTaxRoundingMode()).isEqualTo(RoundingMode.HALF_EVEN);
            assertThat(extractTaxAmount(cartWithDefaultRounding))
                    .as("Should be rounded half to even")
                    .isEqualTo(0.03);

            final CartUpdateCommand updateToHalfUp = CartUpdateCommand.of(cartWithDefaultRounding, ChangeTaxRoundingMode.of(RoundingMode.HALF_UP));
            final Cart cartWithHalfUpRounding = client().executeBlocking(updateToHalfUp);
            assertThat(cartWithHalfUpRounding.getTaxRoundingMode()).isEqualTo(RoundingMode.HALF_UP);
            assertThat(extractTaxAmount(cartWithHalfUpRounding))
                    .as("Should be rounded half up")
                    .isEqualTo(0.04);

            final CartUpdateCommand updateToHalfDown = CartUpdateCommand.of(cartWithHalfUpRounding, ChangeTaxRoundingMode.of(RoundingMode.HALF_DOWN));
            final Cart cartWithHalfDownRounding = client().executeBlocking(updateToHalfDown);
            assertThat(cartWithHalfDownRounding.getTaxRoundingMode()).isEqualTo(RoundingMode.HALF_DOWN);
            assertThat(extractTaxAmount(cartWithHalfDownRounding))
                    .as("Should be rounded half down")
                    .isEqualTo(0.03);

            return cartWithHalfDownRounding;
        });
    }

    @Test
    public void setTaxRoundingModeUpOnOrderImport() throws Exception {
        withFilledImportedOrderDraftBuilder(client(), draftBuilder -> {
            final OrderImportDraft draftWithDefaultRounding = draftBuilder.build();
            withImportedOrder(client(), draftWithDefaultRounding, order -> {
                assertThat(order.getTaxRoundingMode()).isEqualTo(RoundingMode.HALF_EVEN);
                return order;
            });

            final OrderImportDraft draftWithRoundingUp = draftBuilder.taxRoundingMode(RoundingMode.HALF_UP).build();
            withImportedOrder(client(), draftWithRoundingUp, order -> {
                assertThat(order.getTaxRoundingMode()).isEqualTo(RoundingMode.HALF_UP);
                return order;
            });
        });
    }

    @Test
    public void queryCartTaxRoundingMode() {
        final int centAmount = 25;
        final double taxRate = 0.14;
        final boolean isTaxIncluded = false;
        withTaxedCartWithProduct(client(), centAmount, taxRate, isTaxIncluded, cartWithDefaultRounding -> {
            final CartQuery cartQueryHalfUpRounding = CartQuery.of()
                    .plusPredicates(cart -> cart.is(cartWithDefaultRounding))
                    .plusPredicates(cart -> cart.taxRoundingMode().is(RoundingMode.HALF_UP));
            assertThat(client().executeBlocking(cartQueryHalfUpRounding).getResults()).isEmpty();

            final CartUpdateCommand updateToHalfUp = CartUpdateCommand.of(cartWithDefaultRounding, ChangeTaxRoundingMode.of(RoundingMode.HALF_UP));
            final Cart cartWithHalfUpRounding = client().executeBlocking(updateToHalfUp);
            assertThat(client().executeBlocking(cartQueryHalfUpRounding).getResults()).hasSize(1);

            return cartWithHalfUpRounding;
        });
    }

    @Test
    public void queryOrderTaxRoundingMode() throws Exception {
        withFilledImportedOrderDraftBuilder(client(), draftBuilder -> {
            final OrderImportDraft draftWithDefaultRounding = draftBuilder.taxRoundingMode(RoundingMode.HALF_UP).build();
            withImportedOrder(client(), draftWithDefaultRounding, orderWithHalfUpRounding -> {
                final OrderQuery orderQueryHalfDownRounding = OrderQuery.of()
                        .plusPredicates(order -> order.is(orderWithHalfUpRounding))
                        .plusPredicates(order -> order.taxRoundingMode().is(RoundingMode.HALF_DOWN));
                assertThat(client().executeBlocking(orderQueryHalfDownRounding).getResults()).isEmpty();

                final OrderQuery orderQueryHalfUpRounding = OrderQuery.of()
                        .plusPredicates(order -> order.is(orderWithHalfUpRounding))
                        .plusPredicates(order -> order.taxRoundingMode().is(RoundingMode.HALF_UP));
                assertThat(client().executeBlocking(orderQueryHalfUpRounding).getResults()).hasSize(1);

                return orderWithHalfUpRounding;
            });
        });
    }

    private static void withFilledImportedOrderDraftBuilder(final BlockingSphereClient client, final Consumer<OrderImportDraftBuilder> operator) {
        withTransientTaxCategory(client, taxCategory -> {
            final MonetaryAmount lineItemPrice = MoneyImpl.ofCents(randomInt(), EUR);
            final CustomLineItemImportDraft customLineItemImportDraft = CustomLineItemImportDraftBuilder.of(randomSlug(), 1, lineItemPrice, taxCategory).build();
            final OrderImportDraftBuilder builder = OrderImportDraftBuilder.ofCustomLineItems(lineItemPrice, OrderState.OPEN, singletonList(customLineItemImportDraft));
            operator.accept(builder);
        });
    }

    private static void withImportedOrder(final BlockingSphereClient client, final OrderImportDraft draft, final Function<Order, Order> operator) {
        final Order order = client.executeBlocking(OrderImportCommand.of(draft));
        final Order updatedOrder = operator.apply(order);
        client.executeBlocking(OrderDeleteCommand.of(updatedOrder));
    }

    private static double extractTaxAmount(final CartLike<?> cartLike) {
        return cartLike.getTaxedPrice().getTaxPortions().get(0).getAmount().getNumber().doubleValue();
    }

    private static void withTaxedCartWithProduct(final BlockingSphereClient client, final long centAmount,
                                                 final double taxRate, final boolean taxIncluded,
                                                 final Function<Cart, CartLike<?>> operator) {
        withTaxedAndPricedProduct(client, centAmount, taxRate, taxIncluded, product -> {
            final LineItemDraft lineItemDraft = LineItemDraft.of(product, MASTER_VARIANT_ID, 1);
            final CartDraftDsl cartDraft = CartDraft.of(EUR)
                    .withLineItems(singletonList(lineItemDraft))
                    .withShippingAddress(Address.of(CountryCode.DE))
                    .withCountry(CountryCode.DE);
            withCartDraft(client, cartDraft, operator);
        });
    }

    private static void withTaxedAndPricedProduct(final BlockingSphereClient client, final long centAmount,
                                                  final double taxRate, final boolean taxIncluded, final Consumer<Product> operator) {
        final List<TaxRateDraft> taxRateDrafts = singletonList(TaxRateDraftBuilder.of(randomKey(), taxRate, taxIncluded, CountryCode.DE).build());
        final TaxCategoryDraft taxCategoryDraft = TaxCategoryDraft.of(randomString(), taxRateDrafts);
        withTaxCategory(client, taxCategoryDraft, taxCategory ->
                withProduct(client, product -> {
                    final PriceDraft priceDraft = PriceDraft.of(MoneyImpl.ofCents(centAmount, EUR)).withCountry(DE);
                    final ProductUpdateCommand setPricesCmd = ProductUpdateCommand.of(product, asList(
                            AddPrice.of(MASTER_VARIANT_ID, priceDraft),
                            SetTaxCategory.of(taxCategory.toResourceIdentifier()),
                            Publish.of()));
                    final Product productWithPrice = client.executeBlocking(setPricesCmd);
                    operator.accept(productWithPrice);
                })
        );
    }
}
