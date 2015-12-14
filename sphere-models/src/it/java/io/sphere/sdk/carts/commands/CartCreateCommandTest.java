package io.sphere.sdk.carts.commands;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.carts.*;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.types.CustomFieldsDraft;
import io.sphere.sdk.types.TypeFixtures;
import org.junit.Test;

import java.util.List;

import static io.sphere.sdk.customers.CustomerFixtures.withCustomer;
import static io.sphere.sdk.products.ProductFixtures.withTaxedProduct;
import static io.sphere.sdk.shippingmethods.ShippingMethodFixtures.withShippingMethodForGermany;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static io.sphere.sdk.types.TypeFixtures.withUpdateableType;
import static java.util.Collections.singletonList;
import static java.util.Collections.singletonMap;
import static org.assertj.core.api.Assertions.assertThat;

public class CartCreateCommandTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        final CartDraft cartDraft = CartDraft.of(EUR).withCountry(DE);
        final Cart cart = execute(CartCreateCommand.of(cartDraft));
        assertThat(cart.getTotalPrice().getCurrency().getCurrencyCode()).isEqualTo(EUR.getCurrencyCode());
        assertThat(cart.getCountry()).isEqualTo(DE);
        assertThat(cart.getTotalPrice().isZero()).isTrue();
    }

    @Test
    public void fullExample() throws Exception {
        final Address shippingAddress = Address.of(CountryCode.DE).withAdditionalAddressInfo("shipping");
        final Address billingAddress = Address.of(CountryCode.DE).withAdditionalAddressInfo("billing");

        withCustomer(client(), customer -> {
            final String customerId = customer.getId();
            final String customerEmail = customer.getEmail();
            withUpdateableType(client(), type -> {
                final CustomFieldsDraft customFieldsDraft =
                        CustomFieldsDraft.ofTypeKeyAndObjects(type.getKey(), singletonMap(TypeFixtures.STRING_FIELD_NAME, "foo"));
                withShippingMethodForGermany(client(), shippingMethod -> {
                    withTaxedProduct(client(), product -> {
                        final List<LineItemDraft> lineItems = singletonList(LineItemDraft.of(product, 1, 15));

                        final List<CustomLineItemDraft> customLineItems = singletonList(CustomLineItemDraft.of(randomSlug(), "foo-bar", EURO_5, product.getTaxCategory(), 1L));

                        final CartDraft cartDraft = CartDraft.of(EUR)
                                .withCountry(DE)
                                .withCustomerId(customerId)
                                .withCustomerEmail(customerEmail)
                                .withLineItems(lineItems)
                                .withCustomLineItems(customLineItems)
                                .withBillingAddress(billingAddress)
                                .withShippingAddress(shippingAddress)
                                .withShippingMethod(shippingMethod)
                                .withCustom(customFieldsDraft);
                        final Cart cart = execute(CartCreateCommand.of(cartDraft));

                        softAssert(s -> {
                            s.assertThat(cart.getCountry()).isEqualTo(DE);
                            s.assertThat(cart.getTotalPrice().getCurrency()).isEqualTo(EUR);
                            s.assertThat(cart.getCurrency()).isEqualTo(EUR);
                            s.assertThat(cart.getCustomerId()).isEqualTo(customerId);
                            s.assertThat(cart.getCustomerEmail()).isEqualTo(customerEmail);
                            final LineItem lineItem = cart.getLineItems().get(0);
                            s.assertThat(lineItem.getProductId()).isEqualTo(product.getId());
                            s.assertThat(lineItem.getQuantity()).isEqualTo(15);
                            s.assertThat(cart.getCustomLineItems().get(0).getSlug()).isEqualTo("foo-bar");
                            s.assertThat(cart.getBillingAddress()).isEqualTo(billingAddress);
                            s.assertThat(cart.getShippingAddress()).isEqualTo(shippingAddress);
                            s.assertThat(cart.getCustom().getFieldsJsonMap()).isEqualTo(customFieldsDraft.getFields());
                        });

                        //cleanup
                        execute(CartDeleteCommand.of(cart));
                    });
                });
                return type;
            });
        });

    }

    @Test
    public void inventoryModeNone() {
        testInventoryMode(InventoryMode.NONE);
    }

    @Test
    public void inventoryModeReserveOnOrder() {
        testInventoryMode(InventoryMode.RESERVE_ON_ORDER);
    }

    @Test
    public void inventoryModeTrackOnly() {
        testInventoryMode(InventoryMode.TRACK_ONLY);
    }

    private void testInventoryMode(final InventoryMode inventoryMode) {
        final Cart cart = execute(CartCreateCommand.of(CartDraft.of(EUR).withInventoryMode(inventoryMode)));
        assertThat(cart.getInventoryMode()).isEqualTo(inventoryMode);
        execute(CartDeleteCommand.of(cart));
    }
}