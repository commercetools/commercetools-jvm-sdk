package io.sphere.sdk.carts;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.carts.commands.CartUpdateCommand;
import io.sphere.sdk.carts.commands.updateactions.*;
import io.sphere.sdk.carts.queries.CartQuery;
import io.sphere.sdk.carts.queries.CartQueryModel;
import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.models.DefaultCurrencyUnits;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.test.SphereTestUtils;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static io.sphere.sdk.carts.CartFixtures.withCartDraft;
import static io.sphere.sdk.carts.CartFixtures.withFilledCart;
import static io.sphere.sdk.products.ProductFixtures.withTaxedProduct;
import static org.assertj.core.api.Assertions.assertThat;

public class CartMultipleShippingAddressesIntegrationTest extends IntegrationTest {

    @Test
    public void testDraftWithMultipleAddresses() {

        final List<Address> itemShippingAddresses = createAddressArray();

        final CartDraft draft = CartDraftBuilder.of(DefaultCurrencyUnits.EUR)
                .country(CountryCode.DE)
                .itemShippingAddresses(itemShippingAddresses)
                .build();

        withTaxedProduct(client(), product -> {
            withCartDraft(client(), draft, cart -> {
                Assertions.assertThat(cart.getItemShippingAddresses()).containsAll(itemShippingAddresses);
                final String addressKey = cart.getItemShippingAddresses().get(0).getKey();
                final ItemShippingDetailsDraft itemShippingDetailsDraft = ItemShippingDetailsDraftBuilder.of(Arrays.asList(ItemShippingTargetBuilder.of(addressKey, 2L).build())).build();
                final int master_variant_id = product.getMasterData().getStaged().getMasterVariant().getId();
                final LineItemDraft lineItemDraft = LineItemDraft.of(product, master_variant_id, 25).withShippingDetails(itemShippingDetailsDraft);
                final Cart updatedCart = client().executeBlocking(CartUpdateCommand.of(cart, AddLineItem.of(lineItemDraft)));

                PagedQueryResult<Cart> result = client().executeBlocking(CartQuery.of().plusPredicates(CartQueryModel.of().itemShippingAddresses().address().country().is(CountryCode.DE)));
                Assertions.assertThat(result.getResults()).isNotEmpty();

                return updatedCart;
            });
        });

    }


    @Test
    public void testAddAddressesToCartUpdateAction() {

        final List<Address> addressList = createAddressArray();
        withFilledCart(client(), cart -> {
            assertThat(cart.getItemShippingAddresses()).isEmpty();
            List<UpdateAction<Cart>> addAddresses = addressList.stream().map(AddItemShippingAddress::of).collect(Collectors.toList());
            Cart updatedCart = client().executeBlocking(CartUpdateCommand.of(cart,addAddresses ));
            assertThat(updatedCart.getItemShippingAddresses()).hasSameSizeAs(addressList);
        });
    }


    @Test
    public void testRemoveAddressesToCartUpdateAction() {

        withFilledCartAndMultipleAddresses(client(), cart -> {
            final List<Address> addresses = cart.getItemShippingAddresses();
            final String addressKey = addresses.get(0).getKey();
            final Cart updatedCart = client().executeBlocking(CartUpdateCommand.of(cart, RemoveItemShippingAddress.of(addressKey)));
            assertThat(updatedCart.getItemShippingAddresses()).hasSize(addresses.size() - 1);
            assertThat(updatedCart.getItemShippingAddresses().stream().filter(address -> addressKey.equals(address.getKey())).collect(Collectors.toList())).isEmpty();
        });
    }

    @Test
    public void testUpdateAddressesToCartUpdateAction() {

        withFilledCartAndMultipleAddresses(client(), cart -> {
            final List<Address> addresses = cart.getItemShippingAddresses();
            final Address firstAddress = addresses.get(0);
            assertThat(firstAddress.getCountry()).isNotEqualByComparingTo(CountryCode.CA);
            final String firstAddressKey = firstAddress.getKey();
            final Address updatedAddress =  Address.of(CountryCode.CA).withKey(firstAddressKey);
            final Cart updatedCart = client().executeBlocking(CartUpdateCommand.of(cart, UpdateItemShippingAddress.of(updatedAddress)));

            List<Address> newAddresses = updatedCart.getItemShippingAddresses().stream().filter(address -> address.getKey().equals(firstAddressKey)).collect(Collectors.toList());
            assertThat(newAddresses).hasSize(1);
            assertThat(newAddresses.get(0).getCountry()).isEqualByComparingTo(CountryCode.CA);
        });
    }

    @Test
    public void testSetLineItemShippingDetails() {

        withFilledCartAndMultipleAddresses(client(), cart -> {
            final LineItem firstLineItem = cart.getLineItems().get(0);
            final List<Address> addresses = cart.getItemShippingAddresses();
            final Address firstAddress = addresses.get(0);
            final String firstAddressKey = firstAddress.getKey();
            final ItemShippingDetailsDraft itemShippingDetailsDraft = ItemShippingDetailsDraftBuilder.of(Arrays.asList(ItemShippingTargetBuilder.of(firstAddressKey, 2L).build())).build();
            final Cart updatedCart = client().executeBlocking(CartUpdateCommand.of(cart, SetLineItemShippingDetails.of(firstLineItem.getId(),itemShippingDetailsDraft)));
            final LineItem updatedLineItem = updatedCart.getLineItems().stream().filter(lineItem -> lineItem.getId().equals(firstLineItem.getId())).findAny().get();
            assertThat(updatedLineItem.getShippingDetails().getTargets()).hasSize(1);
            assertThat(updatedLineItem.getShippingDetails().getTargets().get(0).getAddressKey()).isEqualTo(firstAddressKey);
            assertThat(updatedLineItem.getShippingDetails().getTargets().get(0).getQuantity()).isEqualTo(2);

        });
    }

    @Test
    public void testApplyDeltaToLineItemShippingDetailsTargets() {

        withFilledCartAndMultipleAddresses(client(), cart -> {
            final LineItem firstLineItem = cart.getLineItems().get(0);
            final List<Address> addresses = cart.getItemShippingAddresses();
            final Address firstAddress = addresses.get(0);
            final String firstAddressKey = firstAddress.getKey();
            final Cart updatedCart = client().executeBlocking(CartUpdateCommand.of(cart, ApplyDeltaToLineItemShippingDetailsTargets.of(firstLineItem.getId(), Arrays.asList(ItemShippingTargetBuilder.of(firstAddressKey,6L ).build()))));
            final LineItem updatedLineItem = updatedCart.getLineItems().stream().filter(lineItem -> lineItem.getId().equals(firstLineItem.getId())).findAny().get();
            assertThat(updatedLineItem.getShippingDetails().getTargets()).hasSize(1);
            assertThat(updatedLineItem.getShippingDetails().getTargets().get(0).getAddressKey()).isEqualTo(firstAddressKey);
            assertThat(updatedLineItem.getShippingDetails().getTargets().get(0).getQuantity()).isEqualTo(6);

        });
    }


    @Test
    public void testItemShippingDetailsConvenienceMethod(){
        withFilledCartAndMultipleAddresses(client(), cart -> {
            final LineItem firstLineItem = cart.getLineItems().get(0);
            final List<Address> addresses = cart.getItemShippingAddresses();
            final Address firstAddress = addresses.get(0);
            final String firstAddressKey = firstAddress.getKey();
            final ItemShippingDetailsDraft itemShippingDetailsDraft = ItemShippingDetailsDraftBuilder.of(Arrays.asList(ItemShippingTargetBuilder.of(firstAddressKey, 2L).build())).build();
            final Cart updatedCart = client().executeBlocking(CartUpdateCommand.of(cart, SetLineItemShippingDetails.of(firstLineItem.getId(), itemShippingDetailsDraft)));
            final LineItem updatedLineItem = updatedCart.getLineItems().stream().filter(lineItem -> lineItem.getId().equals(firstLineItem.getId())).findAny().get();
            assertThat(updatedLineItem.getShippingDetails().getTargetsMap().get(firstAddressKey)).isNotNull();
        });
    }

    @Test
    public void testRemoveLineItemDetails(){
        withFilledCartAndMultipleAddresses(client(), cart -> {
            final LineItem firstLineItem = cart.getLineItems().get(0);
            final List<Address> addresses = cart.getItemShippingAddresses();
            final Address firstAddress = addresses.get(0);
            final String firstAddressKey = firstAddress.getKey();
            final ItemShippingDetailsDraft itemShippingDetailsDraft = ItemShippingDetailsDraftBuilder.of(Arrays.asList(ItemShippingTargetBuilder.of(firstAddressKey, 2L).build())).build();
            final Cart updatedCart = client().executeBlocking(CartUpdateCommand.of(cart, SetLineItemShippingDetails.of(firstLineItem.getId(), itemShippingDetailsDraft)));
            final LineItem updatedLineItem = updatedCart.getLineItems().stream().filter(lineItem -> lineItem.getId().equals(firstLineItem.getId())).findAny().get();
            assertThat(updatedLineItem.getShippingDetails().getTargetsMap().get(firstAddressKey)).isNotNull();

            final Cart updatedCart2 = client().executeBlocking(CartUpdateCommand.of(updatedCart, RemoveLineItem.of(firstLineItem.getId(), 1L).withShippingDetailsToRemove(itemShippingDetailsDraft)));
            final LineItem updatedLineItem2 = updatedCart2.getLineItems().stream().filter(lineItem -> lineItem.getId().equals(firstLineItem.getId())).findAny().get();
            //Check if line item details is removed
            assertThat(updatedLineItem2.getShippingDetails().getTargetsMap().get(firstAddressKey)).isNull();

        });
    }


    public void withFilledCartAndMultipleAddresses(final BlockingSphereClient client, final Consumer<Cart> f) {

        final List<Address> addressList = createAddressArray();
        withFilledCart(client(), cart -> {
            List<UpdateAction<Cart>> addAddresses = addressList.stream().map(AddItemShippingAddress::of).collect(Collectors.toList());
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
