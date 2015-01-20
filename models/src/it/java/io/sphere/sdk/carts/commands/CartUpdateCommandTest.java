package io.sphere.sdk.carts.commands;

import io.sphere.sdk.carts.*;
import io.sphere.sdk.carts.commands.updateactions.*;
import io.sphere.sdk.carts.queries.CartFetchById;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.models.AddressBuilder;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.products.Price;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.commands.ProductUpdateCommand;
import io.sphere.sdk.products.commands.updateactions.ChangePrice;
import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.shippingmethods.ShippingRate;
import io.sphere.sdk.shippingmethods.queries.GetShippingMethodsByCart;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.test.OptionalAssert;
import io.sphere.sdk.utils.MoneyImpl;
import org.junit.Test;

import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static io.sphere.sdk.carts.CartFixtures.*;
import static io.sphere.sdk.carts.CartFixtures.withEmptyCartAndProduct;
import static io.sphere.sdk.carts.CustomLineItemFixtures.createCustomLineItemDraft;
import static io.sphere.sdk.customers.CustomerFixtures.withCustomer;
import static io.sphere.sdk.products.ProductUpdateScope.STAGED_AND_CURRENT;
import static io.sphere.sdk.shippingmethods.ShippingMethodFixtures.withShippingMethod;
import static io.sphere.sdk.taxcategories.TaxCategoryFixtures.withTaxCategory;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static org.fest.assertions.Assertions.assertThat;
import static io.sphere.sdk.test.OptionalAssert.assertThat;

public class CartUpdateCommandTest extends IntegrationTest {
    public static final int MASTER_VARIANT_ID = 1;

    @Test
    public void addLineItem() throws Exception {
        withEmptyCartAndProduct(client(), (cart, product) -> {
            assertThat(cart.getLineItems()).hasSize(0);
            final long quantity = 3;
            final String productId = product.getId();
            final AddLineItem action = AddLineItem.of(productId, MASTER_VARIANT_ID, quantity);

            final Cart updatedCart = execute(CartUpdateCommand.of(cart, action));
            assertThat(updatedCart.getLineItems()).hasSize(1);
            final LineItem lineItem = updatedCart.getLineItems().get(0);
            assertThat(lineItem.getName()).isEqualTo(product.getMasterData().getStaged().getName());
            assertThat(lineItem.getQuantity()).isEqualTo(quantity);
        });
    }

    @Test
    public void removeLineItem() throws Exception {
        withEmptyCartAndProduct(client(), (cart, product) -> {
            assertThat(cart.getLineItems()).hasSize(0);
            final AddLineItem action = AddLineItem.of(product.getId(), MASTER_VARIANT_ID, 3);

            final Cart cartWith3 = execute(CartUpdateCommand.of(cart, action));
            final LineItem lineItem = cartWith3.getLineItems().get(0);
            assertThat(lineItem.getQuantity()).isEqualTo(3);

            final Cart cartWith2 = execute(CartUpdateCommand.of(cartWith3, RemoveLineItem.of(lineItem, 1)));
            assertThat(cartWith2.getLineItems().get(0).getQuantity()).isEqualTo(2);

            final Cart cartWith0 = execute(CartUpdateCommand.of(cartWith2, RemoveLineItem.of(lineItem)));
            assertThat(cartWith0.getLineItems()).hasSize(0);
        });
    }

    @Test
    public void changeLineItemQuantity() throws Exception {
        withEmptyCartAndProduct(client(), (cart, product) -> {
            assertThat(cart.getLineItems()).hasSize(0);
            final AddLineItem action = AddLineItem.of(product.getId(), MASTER_VARIANT_ID, 3);

            final Cart cartWith3 = execute(CartUpdateCommand.of(cart, action));
            final LineItem lineItem = cartWith3.getLineItems().get(0);
            assertThat(lineItem.getQuantity()).isEqualTo(3);

            final Cart cartWith2 = execute(CartUpdateCommand.of(cartWith3, ChangeLineItemQuantity.of(lineItem, 2)));
            assertThat(cartWith2.getLineItems().get(0).getQuantity()).isEqualTo(2);

            final Cart cartWith0 = execute(CartUpdateCommand.of(cartWith2, ChangeLineItemQuantity.of(lineItem, 0)));
            assertThat(cartWith0.getLineItems()).hasSize(0);
        });
    }

    @Test
    public void  addCustomLineItem() throws Exception {
        withTaxCategory(client(), taxCategory -> {
            final Cart cart = createCartWithCountry(client());
            assertThat(cart.getCustomLineItems()).hasSize(0);
            final MonetaryAmount money = MoneyImpl.of(new BigDecimal("23.50"), EUR);
            final String slug = "thing-slug";
            final LocalizedStrings name = en("thing");
            final long quantity = 5;
            final CustomLineItemDraft item = CustomLineItemDraft.of(name, slug, money, taxCategory, quantity);

            final Cart cartWith5 = execute(CartUpdateCommand.of(cart, AddCustomLineItem.of(item)));
            assertThat(cartWith5.getCustomLineItems()).hasSize(1);
            final CustomLineItem customLineItem = cartWith5.getCustomLineItems().get(0);
            assertThat(customLineItem.getMoney()).isEqualTo(money);
            assertThat(customLineItem.getName()).isEqualTo(name);
            assertThat(customLineItem.getQuantity()).isEqualTo(quantity);
            assertThat(customLineItem.getSlug()).isEqualTo(slug);
            assertThat(customLineItem.getState()).isEqualTo(asList(ItemState.of(quantity)));
            assertThat(customLineItem.getTaxCategory()).isEqualTo(taxCategory.toReference());
        });
    }

    @Test
    public void  removeCustomLineItem() throws Exception {
        withTaxCategory(client(), taxCategory -> {
            final Cart cart = createCartWithCountry(client());
            assertThat(cart.getCustomLineItems()).hasSize(0);
            final CustomLineItemDraft draftItem = createCustomLineItemDraft(taxCategory);

            final Cart cartWithCustomLineItem = execute(CartUpdateCommand.of(cart, AddCustomLineItem.of(draftItem)));
            assertThat(cartWithCustomLineItem.getCustomLineItems()).hasSize(1);
            final CustomLineItem customLineItem = cartWithCustomLineItem.getCustomLineItems().get(0);

            final Cart emptyCart = execute(CartUpdateCommand.of(cartWithCustomLineItem, RemoveCustomLineItem.of(customLineItem)));
            assertThat(emptyCart.getCustomLineItems()).hasSize(0);
        });
    }

    @Test
    public void setCustomerEmail() throws Exception {
        final Cart cart = createCartWithCountry(client());
        OptionalAssert.assertThat(cart.getCustomerEmail()).isAbsent();
        final String email = "info@commercetools.de";
        final Cart cartWithEmail = execute(CartUpdateCommand.of(cart, SetCustomerEmail.of(email)));
        OptionalAssert.assertThat(cartWithEmail.getCustomerEmail()).isPresentAs(email);
    }

    @Test
    public void setShippingAddress() throws Exception {
        final Cart cart = createCartWithCountry(client());
        OptionalAssert.assertThat(cart.getShippingAddress()).isAbsent();
        final Address address = AddressBuilder.of(DE).build();
        final Cart cartWithAddress = execute(CartUpdateCommand.of(cart, SetShippingAddress.of(address)));
        OptionalAssert.assertThat(cartWithAddress.getShippingAddress()).isPresentAs(address);
        final Cart cartWithoutAddress = execute(CartUpdateCommand.of(cartWithAddress, SetShippingAddress.of(Optional.empty())));
        OptionalAssert.assertThat(cartWithoutAddress.getShippingAddress()).isAbsent();
    }

    @Test
    public void setBillingAddress() throws Exception {
        final Cart cart = createCartWithCountry(client());
        OptionalAssert.assertThat(cart.getBillingAddress()).isAbsent();
        final Address address = AddressBuilder.of(DE).build();
        final Cart cartWithAddress = execute(CartUpdateCommand.of(cart, SetBillingAddress.of(address)));
        OptionalAssert.assertThat(cartWithAddress.getBillingAddress()).isPresentAs(address);
        final Cart cartWithoutAddress = execute(CartUpdateCommand.of(cartWithAddress, SetBillingAddress.of(Optional.empty())));
        OptionalAssert.assertThat(cartWithoutAddress.getBillingAddress()).isAbsent();
    }

    @Test
    public void setCountry() throws Exception {
        final Cart cart = createCartWithoutCountry(client());
        OptionalAssert.assertThat(cart.getCountry()).isAbsent();
        final Cart cartWithCountry = execute(CartUpdateCommand.of(cart, SetCountry.of(DE)));
        OptionalAssert.assertThat(cartWithCountry.getCountry()).isPresentAs(DE);
        final Cart cartWithoutCountry = execute(CartUpdateCommand.of(cartWithCountry, SetCountry.of(Optional.empty())));
        OptionalAssert.assertThat(cartWithoutCountry.getCountry()).isAbsent();
    }

    @Test
    public void setCustomShippingMethod() throws Exception {
        withTaxCategory(client(), taxCategory -> {
            final Cart cart = createCartWithShippingAddress(client());
            OptionalAssert.assertThat(cart.getShippingInfo()).isAbsent();
            final MonetaryAmount price = MoneyImpl.of(new BigDecimal("23.50"), EUR);
            final ShippingRate shippingRate = ShippingRate.of(price);
            final String shippingMethodName = "custom-shipping";
            final SetCustomShippingMethod action = SetCustomShippingMethod.of(shippingMethodName, shippingRate, taxCategory);
            final Cart cartWithShippingMethod = execute(CartUpdateCommand.of(cart, action));
            final CartShippingInfo shippingInfo = cartWithShippingMethod.getShippingInfo().get();
            assertThat(shippingInfo.getPrice()).isEqualTo(price);
            OptionalAssert.assertThat(shippingInfo.getShippingMethod()).isAbsent();
            assertThat(shippingInfo.getShippingMethodName()).isEqualTo(shippingMethodName);
            assertThat(shippingInfo.getShippingRate()).isEqualTo(shippingRate);
            assertThat(shippingInfo.getTaxCategory()).isEqualTo(taxCategory.toReference());
            assertThat(shippingInfo.getTaxRate()).isNotNull();
        });
    }

    @Test
    public void setShippingMethod() throws Exception {
        final Cart cart = createCartWithShippingAddress(client());
        assertThat(cart.getShippingInfo()).isAbsent();
        final ShippingMethod shippingMethod = execute(GetShippingMethodsByCart.of(cart)).get(0);
        final Cart updatedCart = execute(CartUpdateCommand.of(cart, SetShippingMethod.of(shippingMethod)));
        assertThat(updatedCart.getShippingInfo().get().getShippingMethod()).isPresentAs(shippingMethod.toReference());
    }

    @Test
    public void setCustomerId() throws Exception {
        withCustomer(client(), customer -> {
            final Cart cart = createCartWithCountry(client());
            OptionalAssert.assertThat(cart.getCustomerId()).isAbsent();
            final Cart cartWithCustomerId = execute(CartUpdateCommand.of(cart, SetCustomerId.of(customer)));
            OptionalAssert.assertThat(cartWithCustomerId.getCustomerId()).isPresentAs(customer.getId());
            final Cart cartWithoutCustomerId = execute(CartUpdateCommand.of(cartWithCustomerId, SetCustomerId.of(Optional.empty())));
            OptionalAssert.assertThat(cartWithoutCustomerId.getCustomerId()).isAbsent();
        });
    }

    @Test
    public void recalculate() throws Exception {
        withEmptyCartAndProduct(client(), (emptyCart, product) -> {
            final AddLineItem action = AddLineItem.of(product.getId(), MASTER_VARIANT_ID, 1);

            final Cart cartWithLineItem = execute(CartUpdateCommand.of(emptyCart, action));
            final Price oldPrice = cartWithLineItem.getLineItems().get(0).getPrice();
            final Price newPrice = oldPrice.withValue(oldPrice.getValue().multiply(2));
            final Product productWithChangedPrice =
                    execute(ProductUpdateCommand.of(product, asList(ChangePrice.of(MASTER_VARIANT_ID, newPrice, STAGED_AND_CURRENT))));

            final List<Price> prices = productWithChangedPrice.getMasterData().getCurrent().get().getMasterVariant().getPrices();
            assertThat(prices)
                    .overridingErrorMessage("we updated the price of the product")
                    .containsExactly(newPrice);

            final LineItem lineItemOfTheChangedProduct =
                    execute(CartFetchById.of(cartWithLineItem.getId())).get().getLineItems().get(0);
            assertThat(lineItemOfTheChangedProduct.getPrice())
                    .overridingErrorMessage("the new product price is not automatically propagated to the line item in the cart")
                    .isEqualTo(oldPrice).isNotEqualTo(newPrice);

            final Cart recalculatedCart = execute(CartUpdateCommand.of(cartWithLineItem, Recalculate.of()));

            assertThat(recalculatedCart.getLineItems().get(0).getPrice())
                    .overridingErrorMessage("recalculate updated the price of the line item in the cart")
                    .isEqualTo(newPrice);
            assertThat(recalculatedCart.getTotalPrice())
                    .overridingErrorMessage("recalculate also updated the total price of the cart")
                    .isEqualTo(newPrice.getValue()).isNotEqualTo(cartWithLineItem.getTotalPrice());
        });
    }
}