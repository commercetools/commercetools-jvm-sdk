package io.sphere.sdk.orders;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.carts.*;
import io.sphere.sdk.carts.commands.CartUpdateCommand;
import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.orders.commands.OrderUpdateCommand;
import io.sphere.sdk.orders.commands.updateactions.*;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.test.SphereTestUtils;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static io.sphere.sdk.carts.CartFixtures.withFilledCart;
import static io.sphere.sdk.customers.CustomerFixtures.withCustomer;
import static io.sphere.sdk.orders.OrderFixtures.withOrder;
import static io.sphere.sdk.orders.OrderFixtures.withOrderOfCustomLineItems;
import static org.assertj.core.api.Assertions.assertThat;

public class OrderMultipleShippingAddressesIntegrationTest extends IntegrationTest {

    @Test
    public void testAddAddressesToCartUpdateAction() {

        final List<Address> addressList = createAddressArray();
        withFilledCart(client(), cart -> {
            withCustomer(client(),customer -> {
                withOrder(client(),customer,cart,order -> {
                    assertThat(order.getItemShippingAddresses()).isEmpty();
                    List<UpdateAction<Order>> addAddresses = addressList.stream().map(AddItemShippingAddress::of).collect(Collectors.toList());
                    Order updatedOrder = client().executeBlocking(OrderUpdateCommand.of(order,addAddresses ));
                    assertThat(updatedOrder.getItemShippingAddresses()).hasSameSizeAs(addressList);
                    return updatedOrder;
                });
            });
        });


    }

    @Test
    public void testRemoveAddressesToOrderUpdateAction() {


        withFilledCartAndMultipleAddresses(client(), cart -> {
            withCustomer(client(),customer -> {
                withOrder(client(),customer,cart,order -> {
                    final List<Address> addresses = order.getItemShippingAddresses();
                    final String addressKey = addresses.get(0).getKey();
                    final Order updatedOrder = client().executeBlocking(OrderUpdateCommand.of(order, RemoveItemShippingAddress.of(addressKey)));
                    assertThat(updatedOrder.getItemShippingAddresses()).hasSize(addresses.size() - 1);
                    assertThat(updatedOrder.getItemShippingAddresses().stream().filter(address -> addressKey.equals(address.getKey())).collect(Collectors.toList())).isEmpty();
                    return updatedOrder;
                });
            });
        });
    }

    @Test
    public void testUpdateAddressesToOrderUpdateAction() {

        withFilledCartAndMultipleAddresses(client(), cart -> {
            withCustomer(client(),customer -> {
                withOrder(client(),customer,cart,order -> {
                    final List<Address> addresses = order.getItemShippingAddresses();
                    final Address firstAddress = addresses.get(0);
                    assertThat(firstAddress.getCountry()).isNotEqualByComparingTo(CountryCode.CA);
                    final String firstAddressKey = firstAddress.getKey();
                    final Address updatedAddress =  Address.of(CountryCode.CA).withKey(firstAddressKey);
                    final Order updatedOrder= client().executeBlocking(OrderUpdateCommand.of(order, UpdateItemShippingAddress.of(updatedAddress)));

                    List<Address> newAddresses = updatedOrder.getItemShippingAddresses().stream().filter(address -> address.getKey().equals(firstAddressKey)).collect(Collectors.toList());
                    assertThat(newAddresses).hasSize(1);
                    assertThat(newAddresses.get(0).getCountry()).isEqualByComparingTo(CountryCode.CA);
                    return updatedOrder;
                });
            });
        });
    }

    @Test
    public void testSetLineItemShippingDetails() {

        withFilledCartAndMultipleAddresses(client(), cart -> {
            withCustomer(client(),customer -> {
                withOrder(client(),customer,cart,order -> {
                    final LineItem firstLineItem = order.getLineItems().get(0);
                    final Long quantity = firstLineItem.getQuantity();
                    final List<Address> addresses = order.getItemShippingAddresses();
                    final Address firstAddress = addresses.get(0);
                    final String firstAddressKey = firstAddress.getKey();
                    final ItemShippingDetailsDraft itemShippingDetailsDraft = ItemShippingDetailsDraftBuilder.of(Arrays.asList(ItemShippingTargetBuilder.of(firstAddressKey, firstLineItem.getQuantity()).build())).build();
                    final Order updatedOrder = client().executeBlocking(OrderUpdateCommand.of(order, SetLineItemShippingDetails.of(firstLineItem.getId(),itemShippingDetailsDraft)));
                    final LineItem updatedLineItem = updatedOrder.getLineItems().stream().filter(lineItem -> lineItem.getId().equals(firstLineItem.getId())).findAny().get();
                    assertThat(updatedLineItem.getShippingDetails().getTargets()).hasSize(1);
                    assertThat(updatedLineItem.getShippingDetails().getTargets().get(0).getAddressKey()).isEqualTo(firstAddressKey);
                    assertThat(updatedLineItem.getShippingDetails().getTargets().get(0).getQuantity()).isEqualTo(quantity);
                    return updatedOrder;
                });
            });
        });
    }

    @Test
    public void testSetCustomLineItemShippingDetails() {


        final List<Address> addressList = createAddressArray();
        
        withOrderOfCustomLineItems(client(),order -> {

            assertThat(order.getItemShippingAddresses()).isEmpty();
            List<UpdateAction<Order>> addAddresses = addressList.stream().map(AddItemShippingAddress::of).collect(Collectors.toList());
            Order updatedOrder = client().executeBlocking(OrderUpdateCommand.of(order,addAddresses ));
            assertThat(updatedOrder.getItemShippingAddresses()).hasSameSizeAs(addressList);
            assertThat(order.getCustomLineItems()).isNotEmpty();
            final CustomLineItem firstLineItem = updatedOrder.getCustomLineItems().get(0);
            final Long quantity = firstLineItem.getQuantity();
            final List<Address> addresses = updatedOrder.getItemShippingAddresses();
            final Address firstAddress = addresses.get(0);
            final String firstAddressKey = firstAddress.getKey();
            final ItemShippingDetailsDraft itemShippingDetailsDraft = ItemShippingDetailsDraftBuilder.of(Arrays.asList(ItemShippingTargetBuilder.of(firstAddressKey, firstLineItem.getQuantity()).build())).build();
            final Order updatedOrder2 = client().executeBlocking(OrderUpdateCommand.of(updatedOrder, SetCustomLineItemShippingDetails.of(firstLineItem.getId(),itemShippingDetailsDraft)));
            final CustomLineItem updatedLineItem = updatedOrder2.getCustomLineItems().stream().filter(lineItem -> lineItem.getId().equals(firstLineItem.getId())).findAny().get();
            assertThat(updatedLineItem.getShippingDetails().getTargets()).hasSize(1);
            assertThat(updatedLineItem.getShippingDetails().getTargets().get(0).getAddressKey()).isEqualTo(firstAddressKey);
            assertThat(updatedLineItem.getShippingDetails().getTargets().get(0).getQuantity()).isEqualTo(quantity);

        } );
    }

    public void withFilledCartAndMultipleAddresses(final BlockingSphereClient client, final Consumer<Cart> f) {

        final List<Address> addressList = createAddressArray();
        withFilledCart(client(), cart -> {
            List<UpdateAction<Cart>> addAddresses = addressList.stream().map(io.sphere.sdk.carts.commands.updateactions.AddItemShippingAddress::of).collect(Collectors.toList());
            Cart updatedCart = client().executeBlocking(CartUpdateCommand.of(cart,addAddresses ));
            f.accept(updatedCart);
        });
    }

    public final static List<Address> createAddressArray() {
        final Address address1 = Address.of(CountryCode.DE).withKey(SphereTestUtils.randomKey());
        final Address address2 = Address.of(CountryCode.FR).withKey(SphereTestUtils.randomKey());
        final Address address3 = Address.of(CountryCode.MA).withKey(SphereTestUtils.randomKey());
        final Address address4 = Address.of(CountryCode.IT).withKey(SphereTestUtils.randomKey());
        return Arrays.asList(address1, address2, address3, address4);
    }

}
