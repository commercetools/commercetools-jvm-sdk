package io.sphere.sdk.carts;

import io.sphere.sdk.carts.commands.CartCreateCommand;
import io.sphere.sdk.carts.commands.CartUpdateCommand;
import io.sphere.sdk.carts.commands.updateactions.*;
import io.sphere.sdk.client.ErrorResponseException;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.products.PriceDraft;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.shippingmethods.ShippingRate;
import io.sphere.sdk.taxcategories.ExternalTaxRateDraft;
import io.sphere.sdk.taxcategories.ExternalTaxRateDraftBuilder;
import io.sphere.sdk.taxcategories.SubRate;
import io.sphere.sdk.taxcategories.TaxRate;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.utils.MoneyImpl;
import org.junit.Test;

import javax.money.MonetaryAmount;
import java.util.Collections;
import java.util.List;

import static io.sphere.sdk.carts.CartFixtures.withCartDraft;
import static io.sphere.sdk.products.ProductFixtures.withProductOfPrices;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class ExternalTaxRatesIntegrationTest extends IntegrationTest {

    @Test
    public void createEmptyCart() {
        final CartDraftDsl cartDraft = CartDraft.of(EUR)
                .withTaxMode(TaxMode.EXTERNAL);//important

        final Cart cart = client().executeBlocking(CartCreateCommand.of(cartDraft));

        assertThat(cart.getTaxMode()).isEqualTo(TaxMode.EXTERNAL);
    }

    @Test
    public void changeTaxMode() {
        final CartDraftDsl cartDraft = CartDraft.of(EUR)
                .withTaxMode(TaxMode.EXTERNAL);//important

        final Cart externalTaxModeCart = client().executeBlocking(CartCreateCommand.of(cartDraft));

        assertThat(externalTaxModeCart.getTaxMode()).isEqualTo(TaxMode.EXTERNAL);

        final Cart platformTaxModeCart =
                client().executeBlocking(CartUpdateCommand.of(externalTaxModeCart, ChangeTaxMode.of(TaxMode.PLATFORM)));

        assertThat(platformTaxModeCart.getTaxMode()).isEqualTo(TaxMode.PLATFORM);
    }

    @Test
    public void addLineItem() {
        withProductOfPrices(client(), singletonList(PriceDraft.of(EURO_10)), (Product product) -> {
            final CartDraft draft = CartDraft.of(EUR).withTaxMode(TaxMode.EXTERNAL);
            withCartDraft(client(), draft, (Cart cart) -> {
                final int quantity = 3;
                final int variantId = product.getMasterData().getStaged().getMasterVariant().getId();
                final String taxRateName = "special tax";
                final double rate = 0.20;
                final List<SubRate> subRates = asList(SubRate.of("foo", 0.11), SubRate.of("bar", 0.09));
                final ExternalTaxRateDraft externalTaxRate =
                        ExternalTaxRateDraftBuilder.ofAmount(rate, taxRateName, DE)
                                .subRates(subRates)
                                .build();
                final AddLineItem updateAction = AddLineItem.of(product, variantId, quantity)
                        .withExternalTaxRate(externalTaxRate);
                final Cart cartWithLineItem = client().executeBlocking(CartUpdateCommand.of(cart, updateAction));

                final LineItem lineItem = cartWithLineItem.getLineItems().get(0);
                final TaxRate taxRate = lineItem.getTaxRate();
                assertThat(taxRate.getSubRates()).isEqualTo(subRates);
                assertThat(taxRate.getCountry()).isEqualTo(DE);
                assertThat(taxRate.getId()).isNull();
                assertThat(taxRate.getName()).isEqualTo(taxRateName);
                assertThat(taxRate.getAmount()).isEqualTo(0.2);
                assertThat(taxRate.isIncludedInPrice()).isFalse();
                assertThat(lineItem.getTotalPrice()).isEqualTo(MoneyImpl.ofCents(3000, "EUR"));
                assertThat(lineItem.getTaxedPrice().getTotalNet()).isEqualTo(MoneyImpl.ofCents(3000, "EUR"));
                assertThat(lineItem.getTaxedPrice().getTotalGross()).isEqualTo(MoneyImpl.ofCents(3600, "EUR"));

                return cartWithLineItem;
            });
        });
    }

    @Test
    public void errorMovingFromExternalToPlatformTaxMode() {
        withProductOfPrices(client(), singletonList(PriceDraft.of(EURO_10)), (Product product) -> {
            final CartDraft draft = CartDraft.of(EUR).withTaxMode(TaxMode.EXTERNAL);
            withCartDraft(client(), draft, (Cart cart) -> {
                final int quantity = 3;
                final int variantId = product.getMasterData().getStaged().getMasterVariant().getId();
                final String taxRateName = "special tax";
                final double rate = 0.20;
                final List<SubRate> subRates = asList(SubRate.of("foo", 0.11), SubRate.of("bar", 0.09));
                final ExternalTaxRateDraft externalTaxRate =
                        ExternalTaxRateDraftBuilder.ofAmount(rate, taxRateName, DE)
                                .subRates(subRates)
                                .build();
                final AddLineItem updateAction = AddLineItem.of(product, variantId, quantity)
                        .withExternalTaxRate(externalTaxRate);
                final Cart cartWithLineItem = client().executeBlocking(CartUpdateCommand.of(cart, updateAction));

                final LineItem lineItem = cartWithLineItem.getLineItems().get(0);
                final TaxRate taxRate = lineItem.getTaxRate();
                assertThat(taxRate.getName()).isEqualTo(taxRateName);

                final CartUpdateCommand setTaxModeCmd =
                        CartUpdateCommand.of(cartWithLineItem, ChangeTaxMode.of(TaxMode.PLATFORM));
                final Throwable throwable = catchThrowable(() -> client().executeBlocking(setTaxModeCmd));

                assertThat(throwable)
                        .as("it is not possible to change the cart to platform tax mode, " +
                                "if the product does not have a tax category")
                        .isInstanceOf(ErrorResponseException.class);

                return cartWithLineItem;
            });
        });
    }

    @Test
    public void setLineItemTaxRate() {
        withProductOfPrices(client(), singletonList(PriceDraft.of(EURO_10)), (Product product) -> {
            final CartDraft draft = CartDraft.of(EUR).withTaxMode(TaxMode.EXTERNAL);
            withCartDraft(client(), draft, (Cart cart) -> {
                final int quantity = 3;
                final int variantId = product.getMasterData().getStaged().getMasterVariant().getId();
                final AddLineItem updateAction = AddLineItem.of(product, variantId, quantity);
                final Cart cartWithLineItem = client().executeBlocking(CartUpdateCommand.of(cart, updateAction));

                final LineItem lineItem = cartWithLineItem.getLineItems().get(0);
                assertThat(lineItem.getTaxRate()).isNull();
                assertThat(lineItem.getTotalPrice()).isEqualTo(EURO_30);
                assertThat(lineItem.getTaxedPrice()).isNull();

                //now add the taxes
                final String taxRateName = "special tax";
                final double taxRate = 0.20;
                final ExternalTaxRateDraft externalTaxRate =
                        ExternalTaxRateDraftBuilder.ofAmount(taxRate, taxRateName, DE).includedInPrice(false).build();

                final SetLineItemTaxRate setLineItemTaxRate = SetLineItemTaxRate.of(lineItem.getId(), externalTaxRate);
                final Cart cartWithTaxedLineItem =
                        client().executeBlocking(CartUpdateCommand.of(cartWithLineItem, setLineItemTaxRate));

                final LineItem taxedLineItem = cartWithTaxedLineItem.getLineItems().get(0);
                assertThat(taxedLineItem.getTaxRate().getName()).isEqualTo(taxRateName);
                assertThat(taxedLineItem.getTaxRate().isIncludedInPrice()).isEqualTo(false);
                assertThat(taxedLineItem.getTotalPrice()).isEqualTo(EURO_30);
                assertThat(taxedLineItem.getTaxedPrice().getTotalGross()).isEqualTo(EURO_36);
                assertThat(taxedLineItem.getTaxedPrice().getTotalNet()).isEqualTo(EURO_30);

                return cartWithTaxedLineItem;
            });
        });
    }

    @Test
    public void addCustomLineItem() {
        final CartDraft draft = CartDraft.of(EUR)
                .withTaxMode(TaxMode.EXTERNAL)
                .withShippingAddress(Address.of(DE));
        withCartDraft(client(), draft, (Cart cart) -> {
            final MonetaryAmount money = MoneyImpl.of("23.50", EUR);
            final String slug = "thing-slug";
            final LocalizedString name = en("thing");
            final long quantity = 5;
            final String taxRateName = "special tax";
            final double taxRate = 0.20;
            final ExternalTaxRateDraft externalTaxRate =
                    ExternalTaxRateDraftBuilder.ofAmount(taxRate, taxRateName, DE).build();
            final CustomLineItemDraft item = CustomLineItemDraft.ofExternalTaxCalculation(name, slug, money, externalTaxRate, quantity);

            final Cart cartWithCustomLineItems = client().executeBlocking(CartUpdateCommand.of(cart, AddCustomLineItem.of(item)));
            assertThat(cartWithCustomLineItems.getCustomLineItems()).hasSize(1);
            final CustomLineItem customLineItem = cartWithCustomLineItems.getCustomLineItems().get(0);

            assertThat(customLineItem.getTaxCategory()).isNull();
            assertThat(customLineItem.getTaxedPrice().getTotalNet()).isEqualTo(MoneyImpl.of("117.50", EUR));
            assertThat(customLineItem.getTaxedPrice().getTotalGross()).isEqualTo(MoneyImpl.of("141.00", EUR));
            assertThat(customLineItem.getTaxRate().getName()).isEqualTo(taxRateName);

            return cartWithCustomLineItems;
        });
    }

    @Test
    public void setCustomLineItemTaxRate() {
        final CartDraft draft = CartDraft.of(EUR)
                .withTaxMode(TaxMode.EXTERNAL)
                .withShippingAddress(Address.of(DE));
        withCartDraft(client(), draft, (Cart cart) -> {
            final MonetaryAmount money = MoneyImpl.of("23.50", EUR);
            final String slug = "thing-slug";
            final LocalizedString name = en("thing");
            final long quantity = 5;
            final CustomLineItemDraft item = CustomLineItemDraft.ofExternalTaxCalculation(name, slug, money, quantity);

            final Cart cartWithCustomLineItem = client().executeBlocking(CartUpdateCommand.of(cart, AddCustomLineItem.of(item)));
            assertThat(cartWithCustomLineItem.getCustomLineItems()).hasSize(1);
            final CustomLineItem customLineItem = cartWithCustomLineItem.getCustomLineItems().get(0);

            assertThat(customLineItem.getTaxCategory()).isNull();
            assertThat(customLineItem.getTaxedPrice()).isNull();
            assertThat(customLineItem.getTaxRate())
                    .as("custom line item does not have tax information yet")
                    .isNull();

            final String taxRateName = "special tax";
            final double taxRate = 0.20;
            final ExternalTaxRateDraft externalTaxRate =
                    ExternalTaxRateDraftBuilder.ofAmount(taxRate, taxRateName, DE).build();

            final SetCustomLineItemTaxRate updateAction = SetCustomLineItemTaxRate.of(customLineItem.getId(), externalTaxRate);
            final Cart cartWithTaxedLineItem = client().executeBlocking(CartUpdateCommand.of(cartWithCustomLineItem, updateAction));

            final CustomLineItem taxedCustomLineItem = cartWithTaxedLineItem.getCustomLineItems().get(0);
            assertThat(taxedCustomLineItem.getTaxCategory()).isNull();
            assertThat(taxedCustomLineItem.getTaxedPrice().getTotalNet()).isEqualTo(MoneyImpl.of("117.50", EUR));
            assertThat(taxedCustomLineItem.getTaxedPrice().getTotalGross()).isEqualTo(MoneyImpl.of("141.00", EUR));
            assertThat(taxedCustomLineItem.getTaxRate().getName()).isEqualTo(taxRateName);

            return cartWithTaxedLineItem;
        });
    }

    @Test
    public void addCustomShippingMethod() {
        final CartDraft draft = CartDraft.of(EUR)
                .withTaxMode(TaxMode.EXTERNAL)
                .withShippingAddress(Address.of(DE));
        withCartDraft(client(), draft, (Cart cart) -> {
            final String taxRateName = "special tax";
            final double taxRate = 0.20;
            final ExternalTaxRateDraft externalTaxRate =
                    ExternalTaxRateDraftBuilder.ofAmount(taxRate, taxRateName, DE).build();
            final ShippingRate shippingRate = ShippingRate.of(EURO_10,null, Collections.emptyList());
            final SetCustomShippingMethod action =
                    SetCustomShippingMethod.ofExternalTaxCalculation("name", shippingRate, externalTaxRate);

            final Cart cartWithShippingMethod = client().executeBlocking(CartUpdateCommand.of(cart, action));

            final CartShippingInfo shippingInfo = cartWithShippingMethod.getShippingInfo();
            assertThat(shippingInfo).isNotNull();
            assertThat(shippingInfo.getShippingRate()).isEqualTo(shippingRate);
            assertThat(shippingInfo.getTaxCategory()).isNull();
            assertThat(shippingInfo.getTaxedPrice().getTotalNet()).isEqualTo(EURO_10);
            assertThat(shippingInfo.getTaxedPrice().getTotalGross()).isEqualTo(EURO_12);
            assertThat(shippingInfo.getTaxRate().getName()).isEqualTo(taxRateName);

            return cartWithShippingMethod;
        });
    }

    @Test
    public void setShippingMethodTaxRate() {
        final CartDraft draft = CartDraft.of(EUR)
                .withTaxMode(TaxMode.EXTERNAL)
                .withShippingAddress(Address.of(DE));
        withCartDraft(client(), draft, (Cart cart) -> {
            final ShippingRate shippingRate = ShippingRate.of(EURO_10,null, Collections.emptyList());
            final SetCustomShippingMethod action =
                    SetCustomShippingMethod.ofExternalTaxCalculation("name", shippingRate);

            final Cart cartWithShippingMethod = client().executeBlocking(CartUpdateCommand.of(cart, action));

            final CartShippingInfo shippingInfo = cartWithShippingMethod.getShippingInfo();
            assertThat(shippingInfo).isNotNull();
            assertThat(shippingInfo.getShippingRate()).isEqualTo(shippingRate);
            assertThat(shippingInfo.getTaxCategory()).isNull();
            assertThat(shippingInfo.getTaxedPrice()).isNull();
            assertThat(shippingInfo.getTaxRate()).isNull();


            final String taxRateName = "special tax";
            final double taxRate = 0.20;
            final ExternalTaxRateDraft externalTaxRate =
                    ExternalTaxRateDraftBuilder.ofAmount(taxRate, taxRateName, DE).build();

            final CartUpdateCommand cmd =
                    CartUpdateCommand.of(cartWithShippingMethod, SetShippingMethodTaxRate.of(externalTaxRate));
            final Cart cartWithTaxedShippingMethod = client().executeBlocking(cmd);

            final CartShippingInfo taxedShippingInfo = cartWithTaxedShippingMethod.getShippingInfo();
            assertThat(taxedShippingInfo).isNotNull();
            assertThat(taxedShippingInfo.getShippingRate()).isEqualTo(shippingRate);
            assertThat(taxedShippingInfo.getTaxCategory()).isNull();
            assertThat(taxedShippingInfo.getTaxedPrice().getTotalNet()).isEqualTo(EURO_10);
            assertThat(taxedShippingInfo.getTaxedPrice().getTotalGross()).isEqualTo(EURO_12);
            assertThat(taxedShippingInfo.getTaxRate().getName()).isEqualTo(taxRateName);

            return cartWithTaxedShippingMethod;
        });
    }

    @Test
    public void addLineItemOnPlatformCart() {
        withProductOfPrices(client(), singletonList(PriceDraft.of(EURO_10)), (Product product) -> {
            final CartDraft draft = CartDraft.of(EUR);//uses by default platform!
            withCartDraft(client(), draft, (Cart cart) -> {
                final int quantity = 3;
                final int variantId = product.getMasterData().getStaged().getMasterVariant().getId();
                final String taxRateName = "special tax";
                final double taxRate = 0.20;
                final List<SubRate> subRates = asList(SubRate.of("foo", 0.11), SubRate.of("bar", 0.09));
                final ExternalTaxRateDraft externalTaxRate =
                        ExternalTaxRateDraftBuilder.ofAmount(taxRate, taxRateName, DE)
                                .subRates(subRates)
                                .build();
                final AddLineItem updateAction = AddLineItem.of(product, variantId, quantity)
                        .withExternalTaxRate(externalTaxRate);

                final Throwable throwable = catchThrowable(() -> client().executeBlocking(CartUpdateCommand.of(cart, updateAction)));

                //the error message is sth. like "Cannot set an external tax rate on a cart with taxMode 'Platform'."
                assertThat(throwable).isInstanceOf(ErrorResponseException.class);

                return cart;
            });
        });
    }

    @Test
    public void createCartWithALineItemWithAnExternalTaxRate() {
        withProductOfPrices(client(), singletonList(PriceDraft.of(EURO_10)), (Product product) -> {
            final String taxRateName = "special tax";
            final double rate = 0.20;
            final ExternalTaxRateDraft externalTaxRate =
                    ExternalTaxRateDraftBuilder.ofAmount(rate, taxRateName, DE).build();
            final int quantity = 22;
            final int variantId = 1;
            final LineItemDraft itemDraft = LineItemDraft.of(product, variantId, quantity)
                    .withExternalTaxRate(externalTaxRate);
            final CartDraftDsl cartDraft = CartDraft.of(EUR)
                    .withTaxMode(TaxMode.EXTERNAL)//important
                    .withCountry(DE)
                    .withShippingAddress(Address.of(DE))
                    .withLineItems(singletonList(itemDraft));
            final CartCreateCommand cmd = CartCreateCommand.of(cartDraft);

            final Cart cart = client().executeBlocking(cmd);

            assertThat(cart.getTaxMode()).isEqualTo(TaxMode.EXTERNAL);
            final LineItem lineItem = cart.getLineItems().get(0);

            softAssert(s -> {
                final TaxRate taxRate = lineItem.getTaxRate();
                s.assertThat(taxRate.getName()).isEqualTo(taxRateName);
                s.assertThat(taxRate.getAmount()).isEqualTo(rate);
                s.assertThat(taxRate.getCountry()).isEqualTo(DE);
                s.assertThat(taxRate.getId()).as("tax rate id").isNull();
                s.assertThat(taxRate.getSubRates()).as("sub rates").isEmpty();
                s.assertThat(lineItem.getTotalPrice()).as("lineItem totalPrice").isEqualTo(MoneyImpl.of(220, EUR));
                s.assertThat(cart.getTotalPrice()).as("cart totalPrice").isEqualTo(MoneyImpl.of(220, EUR));
                final TaxedPrice taxedPrice = cart.getTaxedPrice();
                s.assertThat(taxedPrice.getTotalGross()).as("gross").isEqualTo(MoneyImpl.of(264, EUR));
                s.assertThat(taxedPrice.getTotalNet()).as("net").isEqualTo(MoneyImpl.of(220, EUR));
            });
        });
    }
}
