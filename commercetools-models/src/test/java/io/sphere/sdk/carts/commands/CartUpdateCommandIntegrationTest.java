package io.sphere.sdk.carts.commands;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.cartdiscounts.CartPredicate;
import io.sphere.sdk.carts.*;
import io.sphere.sdk.carts.commands.updateactions.*;
import io.sphere.sdk.carts.queries.CartByIdGet;
import io.sphere.sdk.carts.queries.CartQuery;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.channels.ChannelFixtures;
import io.sphere.sdk.channels.ChannelRole;
import io.sphere.sdk.client.ErrorResponseException;
import io.sphere.sdk.client.SphereRequest;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.commands.CustomerUpdateCommand;
import io.sphere.sdk.customers.commands.updateactions.SetAddressCustomType;
import io.sphere.sdk.discountcodes.DiscountCodeInfo;
import io.sphere.sdk.models.*;
import io.sphere.sdk.payments.Payment;
import io.sphere.sdk.products.ByIdVariantIdentifier;
import io.sphere.sdk.products.Price;
import io.sphere.sdk.products.PriceDraft;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.attributes.NamedAttributeAccess;
import io.sphere.sdk.products.commands.ProductUpdateCommand;
import io.sphere.sdk.products.commands.updateactions.ChangePrice;
import io.sphere.sdk.products.commands.updateactions.Publish;
import io.sphere.sdk.products.commands.updateactions.SetAttribute;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.shippingmethods.ShippingRate;
import io.sphere.sdk.shoppinglists.LineItemDraftBuilder;
import io.sphere.sdk.shoppinglists.ShoppingListDraftDsl;
import io.sphere.sdk.taxcategories.ExternalTaxRateDraft;
import io.sphere.sdk.taxcategories.ExternalTaxRateDraftBuilder;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.types.TypeFixtures;
import io.sphere.sdk.utils.MoneyImpl;
import org.junit.Ignore;
import org.junit.Test;

import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static io.sphere.sdk.carts.CartFixtures.*;
import static io.sphere.sdk.carts.CustomLineItemFixtures.createCustomLineItemDraft;
import static io.sphere.sdk.channels.ChannelFixtures.withChannelOfRole;
import static io.sphere.sdk.channels.ChannelRole.INVENTORY_SUPPLY;
import static io.sphere.sdk.channels.ChannelRole.PRODUCT_DISTRIBUTION;
import static io.sphere.sdk.customergroups.CustomerGroupFixtures.withB2cCustomerGroup;
import static io.sphere.sdk.customers.CustomerFixtures.withCustomer;
import static io.sphere.sdk.customers.CustomerFixtures.withCustomerWithOneAddress;
import static io.sphere.sdk.payments.PaymentFixtures.withPayment;
import static io.sphere.sdk.shippingmethods.ShippingMethodFixtures.withDynamicShippingMethodForGermany;
import static io.sphere.sdk.shippingmethods.ShippingMethodFixtures.withShippingMethodForGermany;
import static io.sphere.sdk.shoppinglists.ShoppingListFixtures.newShoppingListDraftBuilder;
import static io.sphere.sdk.shoppinglists.ShoppingListFixtures.withShoppingList;
import static io.sphere.sdk.stores.StoreFixtures.withStore;
import static io.sphere.sdk.suppliers.TShirtProductTypeDraftSupplier.Colors;
import static io.sphere.sdk.taxcategories.TaxCategoryFixtures.withTaxCategory;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CartUpdateCommandIntegrationTest extends IntegrationTest {
    public static final int MASTER_VARIANT_ID = 1;

    @Test
    public void setAnonymousId() throws Exception {
        withEmptyCartAndProduct(client(), (cart, product) -> {
            final String anonymousId = randomString();
            final Cart updatedCart = client().executeBlocking(CartUpdateCommand.of(cart, SetAnonymousId.of(anonymousId)));

            assertThat(updatedCart.getAnonymousId()).isEqualTo(anonymousId);
        });
    }

    @Test
    public void addLineItem() throws Exception {
        withEmptyCartAndProduct(client(), (cart, product) -> {
            assertThat(cart.getLineItems()).isEmpty();
            final long quantity = 3;
            final String productId = product.getId();
            final AddLineItem action = AddLineItem.of(productId, MASTER_VARIANT_ID, quantity);

            final Cart updatedCart = client().executeBlocking(CartUpdateCommand.of(cart, action));
            assertThat(updatedCart.getLineItems()).hasSize(1);
            final LineItem lineItem = updatedCart.getLineItems().get(0);
            assertThat(lineItem.getName()).isEqualTo(product.getMasterData().getStaged().getName());
            assertThat(lineItem.getQuantity()).isEqualTo(quantity);
            assertThat(lineItem.getProductSlug()).isEqualTo(product.getMasterData().getStaged().getSlug());
            assertThat(lineItem.getVariant().getIdentifier()).isEqualTo(ByIdVariantIdentifier.of(lineItem.getProductId(), lineItem.getVariant().getId()));
            assertThat(lineItem.getDiscountedPricePerQuantity()).isNotNull().isEmpty();
            assertThat(lineItem.getLastModifiedAt()).isNotNull();
        });
    }

    @Test
    public void addLineItemMaxQuantity() throws Exception {
        withEmptyCartAndProduct(client(), (cart, product) -> {
            assertThat(cart.getLineItems()).isEmpty();
            final long quantity = Long.MAX_VALUE;
            final String productId = product.getId();
            final AddLineItem action = AddLineItem.of(productId, MASTER_VARIANT_ID, quantity);

            final Cart updatedCart = client().executeBlocking(CartUpdateCommand.of(cart, action));
            assertThat(updatedCart.getLineItems()).hasSize(1);
            final LineItem lineItem = updatedCart.getLineItems().get(0);
            assertThat(lineItem.getName()).isEqualTo(product.getMasterData().getStaged().getName());
            assertThat(lineItem.getQuantity()).isEqualTo(quantity);
            assertThat(lineItem.getProductSlug()).isEqualTo(product.getMasterData().getStaged().getSlug());
            assertThat(lineItem.getVariant().getIdentifier()).isEqualTo(ByIdVariantIdentifier.of(lineItem.getProductId(), lineItem.getVariant().getId()));
            assertThat(lineItem.getDiscountedPricePerQuantity()).isNotNull().isEmpty();
        });
    }

    @Test
    public void addLineItemOfDraftOfSku() throws Exception {
        withEmptyCartAndProduct(client(), (cart, product) -> {
            assertThat(cart.getLineItems()).isEmpty();
            final long quantity = 3;
            final Channel inventorySupplyChannel = ChannelFixtures.persistentChannelOfRole(client(), ChannelRole.INVENTORY_SUPPLY);
            final Channel distributionChannel = ChannelFixtures.persistentChannelOfRole(client(), ChannelRole.PRODUCT_DISTRIBUTION);

            final String sku = product.getMasterData().getStaged().getMasterVariant().getSku();
            final LineItemDraft lineItemDraft =
                    io.sphere.sdk.carts.LineItemDraftBuilder.ofSku(sku, quantity)
                            .distributionChannel(distributionChannel)
                            .supplyChannel(inventorySupplyChannel)
                            .build();

            final AddLineItem action = AddLineItem.of(lineItemDraft);

            final Cart updatedCart = client().executeBlocking(CartUpdateCommand.of(cart, action));
            assertThat(updatedCart.getLineItems()).hasSize(1);
            final LineItem lineItem = updatedCart.getLineItems().get(0);
            assertThat(lineItem.getName()).isEqualTo(product.getMasterData().getStaged().getName());
            assertThat(lineItem.getQuantity()).isEqualTo(quantity);
            assertThat(lineItem.getProductSlug()).isEqualTo(product.getMasterData().getStaged().getSlug());
            assertThat(lineItem.getVariant().getIdentifier()).isEqualTo(ByIdVariantIdentifier.of(lineItem.getProductId(), lineItem.getVariant().getId()));
            assertThat(lineItem.getSupplyChannel().toReference()).isEqualTo(inventorySupplyChannel.toReference());
            assertThat(lineItem.getDistributionChannel().toReference()).isEqualTo(distributionChannel.toReference());
            assertThat(lineItem.getDiscountedPricePerQuantity()).isNotNull().isEmpty();
        });
    }

    @Test
    public void addLineItemOfDraftOfAddedAt() {
        withEmptyCartAndProduct(client(), (cart, product) -> {
            assertThat(cart.getLineItems()).isEmpty();

            final long quantity = 3;
            final ZonedDateTime addedAt = ZonedDateTime.now();
            final String sku = product.getMasterData().getStaged().getMasterVariant().getSku();
            final LineItemDraft lineItemDraft =
                    io.sphere.sdk.carts.LineItemDraftBuilder.ofSku(sku, quantity)
                            .addedAt(addedAt)
                            .build();

            final AddLineItem action = AddLineItem.of(lineItemDraft);

            final Cart updatedCart = client().executeBlocking(CartUpdateCommand.of(cart, action));
            assertThat(updatedCart.getLineItems()).hasSize(1);
            final LineItem lineItem = updatedCart.getLineItems().get(0);
            assertThat(lineItem.getName()).isEqualTo(product.getMasterData().getStaged().getName());
            assertThat(lineItem.getQuantity()).isEqualTo(quantity);
            assertThat(lineItem.getProductSlug()).isEqualTo(product.getMasterData().getStaged().getSlug());
            assertThat(lineItem.getVariant().getIdentifier()).isEqualTo(ByIdVariantIdentifier.of(lineItem.getProductId(), lineItem.getVariant().getId()));
            assertThat(lineItem.getAddedAt()).isEqualTo(addedAt);
            assertThat(lineItem.getDiscountedPricePerQuantity()).isNotNull().isEmpty();
        });
    }

    @Test
    public void addLineItemOfDraftOfVariantIdentifier() throws Exception {
        withEmptyCartAndProduct(client(), (cart, product) -> {
            assertThat(cart.getLineItems()).isEmpty();
            final long quantity = 3;
            final Channel inventorySupplyChannel = ChannelFixtures.persistentChannelOfRole(client(), ChannelRole.INVENTORY_SUPPLY);
            final Channel distributionChannel = ChannelFixtures.persistentChannelOfRole(client(), ChannelRole.PRODUCT_DISTRIBUTION);

            ByIdVariantIdentifier variantIdentifier = product.getMasterData().getStaged().getMasterVariant().getIdentifier();
            final LineItemDraft lineItemDraft =
                    io.sphere.sdk.carts.LineItemDraftBuilder.ofVariantIdentifier(variantIdentifier, quantity)
                            .distributionChannel(distributionChannel)
                            .supplyChannel(inventorySupplyChannel)
                            .build();

            final AddLineItem action = AddLineItem.of(lineItemDraft);

            final Cart updatedCart = client().executeBlocking(CartUpdateCommand.of(cart, action));
            assertThat(updatedCart.getLineItems()).hasSize(1);
            final LineItem lineItem = updatedCart.getLineItems().get(0);
            assertThat(lineItem.getName()).isEqualTo(product.getMasterData().getStaged().getName());
            assertThat(lineItem.getQuantity()).isEqualTo(quantity);
            assertThat(lineItem.getProductSlug()).isEqualTo(product.getMasterData().getStaged().getSlug());
            assertThat(lineItem.getVariant().getIdentifier()).isEqualTo(ByIdVariantIdentifier.of(lineItem.getProductId(), lineItem.getVariant().getId()));
            assertThat(lineItem.getSupplyChannel().toReference()).isEqualTo(inventorySupplyChannel.toReference());
            assertThat(lineItem.getDistributionChannel().toReference()).isEqualTo(distributionChannel.toReference());
            assertThat(lineItem.getDiscountedPricePerQuantity()).isNotNull().isEmpty();
        });
    }

    @Test
    public void setDeleteDaysAfterLastModification() throws Exception {
        withCart(client(), cart -> {
            final int deleteDaysAfterLastModification = 11;
            final Cart updatedCart = client().executeBlocking(
                    CartUpdateCommand.of(cart, SetDeleteDaysAfterLastModification.of(deleteDaysAfterLastModification)));
            assertThat(updatedCart.getDeleteDaysAfterLastModification()).isEqualTo(deleteDaysAfterLastModification);
            return updatedCart;
        });
    }

    @Test
    public void addLineItemWithChannels() throws Exception {
        final Channel inventorySupplyChannel = ChannelFixtures.persistentChannelOfRole(client(), ChannelRole.INVENTORY_SUPPLY);
        final Channel distributionChannel = ChannelFixtures.persistentChannelOfRole(client(), ChannelRole.PRODUCT_DISTRIBUTION);

        withEmptyCartAndProduct(client(), (cart, product) -> {
            assertThat(cart.getLineItems()).hasSize(0);
            final long quantity = 3;
            final String productId = product.getId();
            final AddLineItem action = AddLineItem.of(productId, MASTER_VARIANT_ID, quantity)
                    .withSupplyChannel(inventorySupplyChannel)
                    .withDistributionChannel(distributionChannel);

            final Cart updatedCart = client().executeBlocking(CartUpdateCommand.of(cart, action));
            assertThat(updatedCart.getLineItems()).hasSize(1);
            final LineItem lineItem = updatedCart.getLineItems().get(0);
            assertThat(lineItem.getDistributionChannel()).isEqualTo(distributionChannel.toReference());
            assertThat(lineItem.getSupplyChannel()).isEqualTo(inventorySupplyChannel.toReference());

            //check expansion and query
            final SphereRequest<PagedQueryResult<Cart>> sphereRequest = CartQuery.of()
                    .withSort(m -> m.createdAt().sort().desc())
                    .withLimit(1L)
                    .withPredicates(
                            m -> m.lineItems().supplyChannel().is(inventorySupplyChannel)
                                    .and(m.lineItems().distributionChannel().is(distributionChannel)))
                    .plusExpansionPaths(m -> m.lineItems(0).supplyChannel())
                    .plusExpansionPaths(m -> m.lineItems(0).distributionChannel());
            final Cart loadedCart = client().executeBlocking(sphereRequest).head().get();
            final LineItem loadedLineItem = loadedCart.getLineItems().get(0);
            assertThat(loadedLineItem.getDistributionChannel().getObj()).isNotNull();
            assertThat(loadedLineItem.getSupplyChannel().getObj()).isNotNull();
        });
    }

    @Test
    public void removeLineItem() throws Exception {
        withEmptyCartAndProduct(client(), (cart, product) -> {
            assertThat(cart.getLineItems()).hasSize(0);
            final AddLineItem action = AddLineItem.of(product.getId(), MASTER_VARIANT_ID, 3L);

            final Cart cartWith3 = client().executeBlocking(CartUpdateCommand.of(cart, action));
            final LineItem lineItem = cartWith3.getLineItems().get(0);
            assertThat(lineItem.getQuantity()).isEqualTo(3);

            final Cart cartWith2 = client().executeBlocking(CartUpdateCommand.of(cartWith3, RemoveLineItem.of(lineItem, 1L)));
            assertThat(cartWith2.getLineItems().get(0).getQuantity()).isEqualTo(2);

            final Cart cartWith0 = client().executeBlocking(CartUpdateCommand.of(cartWith2, RemoveLineItem.of(lineItem)));
            assertThat(cartWith0.getLineItems()).hasSize(0);
        });
    }

    @Test
    public void changeLineItemQuantity() throws Exception {
        withEmptyCartAndProduct(client(), (cart, product) -> {
            assertThat(cart.getLineItems()).hasSize(0);
            final AddLineItem action = AddLineItem.of(product.getId(), MASTER_VARIANT_ID, 3L);

            final Cart cartWith3 = client().executeBlocking(CartUpdateCommand.of(cart, action));
            final LineItem lineItem = cartWith3.getLineItems().get(0);
            assertThat(lineItem.getQuantity()).isEqualTo(3);

            final Cart cartWith2 = client().executeBlocking(CartUpdateCommand.of(cartWith3, ChangeLineItemQuantity.of(lineItem, 2L)));
            assertThat(cartWith2.getLineItems().get(0).getQuantity()).isEqualTo(2);

            final Cart cartWith0 = client().executeBlocking(CartUpdateCommand.of(cartWith2, ChangeLineItemQuantity.of(lineItem, 0L)));
            assertThat(cartWith0.getLineItems()).hasSize(0);
        });
    }

    @Test
    public void addCustomLineItem() throws Exception {
        withTaxCategory(client(), taxCategory -> {
            final Cart cart = createCartWithCountry(client());
            assertThat(cart.getCustomLineItems()).isEmpty();
            final MonetaryAmount money = MoneyImpl.of("23.50", EUR);
            final String slug = "thing-slug";//you handle to identify the custom line item
            final LocalizedString name = en("thing");
            final long quantity = 5;
            final CustomLineItemDraft item = CustomLineItemDraft.of(name, slug, money, taxCategory, quantity, null);

            final Cart cartWith5 = client().executeBlocking(CartUpdateCommand.of(cart, AddCustomLineItem.of(item)));
            assertThat(cartWith5.getCustomLineItems()).hasSize(1);
            final CustomLineItem customLineItem = cartWith5.getCustomLineItems().get(0);
            assertThat(customLineItem.getMoney()).isEqualTo(money);
            assertThat(customLineItem.getName()).isEqualTo(name);
            assertThat(customLineItem.getQuantity()).isEqualTo(quantity);
            assertThat(customLineItem.getSlug()).isEqualTo(slug);
            final Set<ItemState> state = customLineItem.getState();
            assertThat(state).hasSize(1);
            assertThat(state).extracting("quantity").containsOnly(quantity);
            assertThat(customLineItem.getTaxCategory()).isEqualTo(taxCategory.toReference());

            final CartQuery cartQuery = CartQuery.of()
                    .withPredicates(m -> m.customLineItems().slug().is(customLineItem.getSlug())
                            .and(m.id().is(cart.getId())));
            assertThat(client().executeBlocking(cartQuery).head().get().getId()).isEqualTo(cart.getId());
        });
    }

    @Ignore
    @Test
    public void addMergesSameCustomLineItems() throws Exception {
        withTaxCategory(client(), taxCategory -> {
            final Cart cart = createCartWithCountry(client());
            assertThat(cart.getCustomLineItems()).isEmpty();
            final MonetaryAmount money = MoneyImpl.of("23.50", EUR);
            final String slug = "thing-slug";//you handle to identify the custom line item
            final LocalizedString name = en("thing");
            final long quantity = 5;
            final CustomLineItemDraft item = CustomLineItemDraft.of(name, slug, money, taxCategory, quantity, null);

            final Cart cartWith5 = client().executeBlocking(CartUpdateCommand.of(cart, Arrays.asList(AddCustomLineItem.of(item), AddCustomLineItem.of(item))));
            assertThat(cartWith5.getCustomLineItems()).hasSize(1);
            final CustomLineItem customLineItem = cartWith5.getCustomLineItems().get(0);
            assertThat(customLineItem.getMoney()).isEqualTo(money);
            assertThat(customLineItem.getName()).isEqualTo(name);
            assertThat(customLineItem.getQuantity()).isEqualTo(quantity * 2);
            assertThat(customLineItem.getSlug()).isEqualTo(slug);
            final Set<ItemState> state = customLineItem.getState();
            assertThat(state).hasSize(1);
            assertThat(state).extracting("quantity").containsOnly(quantity * 2);
            assertThat(customLineItem.getTaxCategory()).isEqualTo(taxCategory.toReference());

            final CartQuery cartQuery = CartQuery.of()
                    .withPredicates(m -> m.customLineItems().slug().is(customLineItem.getSlug())
                            .and(m.id().is(cart.getId())));
            assertThat(client().executeBlocking(cartQuery).head().get().getId()).isEqualTo(cart.getId());
        });
    }

    @Test
    public void changeCustomLineItemQuantity() throws Exception {
        withTaxCategory(client(), taxCategory -> {
            final Cart cart = createCartWithCountry(client());
            assertThat(cart.getCustomLineItems()).hasSize(0);
            final CustomLineItemDraft draftItem = createCustomLineItemDraft(taxCategory);

            final Cart cartWithCustomLineItem = client().executeBlocking(CartUpdateCommand.of(cart, AddCustomLineItem.of(draftItem)));
            assertThat(cartWithCustomLineItem.getCustomLineItems()).hasSize(1);
            final CustomLineItem customLineItem = cartWithCustomLineItem.getCustomLineItems().get(0);
            assertThat(customLineItem.getQuantity()).isEqualTo(5L);

            final Cart updatedCart = client().executeBlocking(CartUpdateCommand.of(cartWithCustomLineItem, ChangeCustomLineItemQuantity.of(customLineItem, 12L)));
            assertThat(updatedCart.getCustomLineItems().get(0).getQuantity()).isEqualTo(12L);
        });
    }

    @Test
    public void changeCustomLineItemMoney() throws Exception {
        withTaxCategory(client(), taxCategory -> {
            final Cart cart = createCartWithCountry(client());
            assertThat(cart.getCustomLineItems()).hasSize(0);
            final CustomLineItemDraft draftItem = createCustomLineItemDraft(taxCategory);//money=MoneyImpl.ofCents(2350, EUR)

            final Cart cartWithCustomLineItem = client().executeBlocking(CartUpdateCommand.of(cart, AddCustomLineItem.of(draftItem)));
            assertThat(cartWithCustomLineItem.getCustomLineItems()).hasSize(1);
            final CustomLineItem customLineItem = cartWithCustomLineItem.getCustomLineItems().get(0);
            assertThat(customLineItem.getMoney()).isEqualTo(MoneyImpl.ofCents(2350, EUR));

            final MonetaryAmount newAmount = MoneyImpl.ofCents(1234, EUR);
            final Cart updatedCart = client().executeBlocking(CartUpdateCommand.of(cartWithCustomLineItem, ChangeCustomLineItemMoney.of(customLineItem, newAmount)));
            assertThat(updatedCart.getCustomLineItems().get(0).getMoney()).isEqualTo(newAmount);
        });
    }

    @Test
    public void removeCustomLineItem() throws Exception {
        withTaxCategory(client(), taxCategory -> {
            final Cart cart = createCartWithCountry(client());
            assertThat(cart.getCustomLineItems()).hasSize(0);
            final CustomLineItemDraft draftItem = createCustomLineItemDraft(taxCategory);

            final Cart cartWithCustomLineItem = client().executeBlocking(CartUpdateCommand.of(cart, AddCustomLineItem.of(draftItem)));
            assertThat(cartWithCustomLineItem.getCustomLineItems()).hasSize(1);
            final CustomLineItem customLineItem = cartWithCustomLineItem.getCustomLineItems().get(0);

            final Cart emptyCart = client().executeBlocking(CartUpdateCommand.of(cartWithCustomLineItem, RemoveCustomLineItem.of(customLineItem)));
            assertThat(emptyCart.getCustomLineItems()).hasSize(0);
        });
    }

    @Test
    public void setCustomerEmail() throws Exception {
        final Cart cart = createCartWithCountry(client());
        assertThat(cart.getCustomerEmail()).isNull();
        final String email = "info@commercetools.de";
        final Cart cartWithEmail = client().executeBlocking(CartUpdateCommand.of(cart, SetCustomerEmail.of(email)));
        assertThat(cartWithEmail.getCustomerEmail()).contains(email);
    }

    @Test
    public void setCustomerGroup() throws Exception {
        withB2cCustomerGroup(client(), customerGroup -> {
            final Cart cart = createCartWithCountry(client());
            assertThat(cart.getCustomerGroup()).isNull();
            Cart updatedCart = client().executeBlocking(CartUpdateCommand.of(cart, SetCustomerGroup.of(customerGroup)));
            assertThat(updatedCart.getCustomerGroup().toReference()).isEqualTo(customerGroup.toReference());
            client().executeBlocking(CartDeleteCommand.of(updatedCart));
        });
    }

    @Test
    public void setShippingAddress() throws Exception {
        final Cart cart = createCartWithCountry(client());
        assertThat(cart.getShippingAddress()).isNull();
        final Address address = AddressBuilder.of(DE).build();
        final Cart cartWithAddress = client().executeBlocking(CartUpdateCommand.of(cart, SetShippingAddress.of(address)));
        assertThat(cartWithAddress.getShippingAddress()).isEqualTo(address);

        //you can query by shippingAddress fields
        final CartQuery query = CartQuery.of()
                .withPredicates(m -> m.shippingAddress().country().is(DE).and(m.id().is(cart.getId())));
        assertThat(client().executeBlocking(query).head()).contains(cartWithAddress);

        final Cart cartWithoutAddress = client().executeBlocking(CartUpdateCommand.of(cartWithAddress, SetShippingAddress.of(null)));
        assertThat(cartWithoutAddress.getShippingAddress()).isNull();
    }

    @Test
    public void setBillingAddress() throws Exception {
        final Cart cart = createCartWithCountry(client());
        assertThat(cart.getBillingAddress()).isNull();
        final Address address = AddressBuilder.of(DE).build();
        final Cart cartWithAddress = client().executeBlocking(CartUpdateCommand.of(cart, SetBillingAddress.of(address)));
        assertThat(cartWithAddress.getBillingAddress()).isEqualTo(address);

        //you can query by billingAddress fields
        final CartQuery query = CartQuery.of()
                .withPredicates(m -> m.billingAddress().country().is(DE).and(m.id().is(cart.getId())));
        assertThat(client().executeBlocking(query).head()).contains(cartWithAddress);

        final Cart cartWithoutAddress = client().executeBlocking(CartUpdateCommand.of(cartWithAddress, SetBillingAddress.of(null)));
        assertThat(cartWithoutAddress.getBillingAddress()).isNull();
    }

    @Test
    public void setCountry() throws Exception {
        final Cart cart = createCartWithoutCountry(client());
        assertThat(cart.getCountry()).isNull();
        final Cart cartWithCountry = client().executeBlocking(CartUpdateCommand.of(cart, SetCountry.of(DE)));
        assertThat(cartWithCountry.getCountry()).isEqualTo(DE);
        final Cart cartWithoutCountry = client().executeBlocking(CartUpdateCommand.of(cartWithCountry, SetCountry.of(null)));
        assertThat(cartWithoutCountry.getCountry()).isNull();
    }

    @Test
    public void setCustomShippingMethod() throws Exception {
        withTaxCategory(client(), taxCategory -> {
            final Cart cart = createCartWithShippingAddress(client());
            assertThat(cart.getShippingInfo()).isNull();
            final MonetaryAmount price = MoneyImpl.of(new BigDecimal("23.50"), EUR);
            final ShippingRate shippingRate = ShippingRate.of(price, null, Collections.emptyList());
            final String shippingMethodName = "custom-shipping";
            final SetCustomShippingMethod action = SetCustomShippingMethod.of(shippingMethodName, shippingRate, taxCategory);
            final Cart cartWithShippingMethod = client().executeBlocking(CartUpdateCommand.of(cart, action));
            final CartShippingInfo shippingInfo = cartWithShippingMethod.getShippingInfo();
            assertThat(shippingInfo.getPrice()).isEqualTo(price);
            assertThat(shippingInfo.getShippingMethod()).isNull();
            assertThat(shippingInfo.getShippingMethodName()).isEqualTo(shippingMethodName);
            assertThat(shippingInfo.getShippingRate()).isEqualTo(shippingRate);
            assertThat(shippingInfo.getTaxCategory()).isEqualTo(taxCategory.toReference());
            assertThat(shippingInfo.getTaxRate()).isNotNull();
        });
    }

    @Test
    public void setShippingMethod() throws Exception {
        withShippingMethodForGermany(client(), shippingMethod -> {
            withCart(client(), createCartWithShippingAddress(client()), cart -> {
                //add shipping method
                assertThat(cart.getShippingInfo()).isNull();
                final CartUpdateCommand updateCommand =
                        CartUpdateCommand.of(cart, SetShippingMethod.of(shippingMethod.toResourceIdentifier()))
                                .plusExpansionPaths(m -> m.shippingInfo().shippingMethod().taxCategory())
                                .plusExpansionPaths(m -> m.shippingInfo().taxCategory());
                final Cart cartWithShippingMethod = client().executeBlocking(updateCommand);
                assertThat(cartWithShippingMethod.getShippingInfo().getShippingMethodState()).isEqualTo(ShippingMethodState.MATCHES_CART);
                assertThat(cartWithShippingMethod.getShippingInfo().getShippingMethod()).isEqualTo(shippingMethod.toReference());
                assertThat(cartWithShippingMethod.getShippingInfo().getShippingMethod().getObj())
                        .as("reference expansion shippingMethod")
                        .isEqualTo(shippingMethod);
                assertThat(cartWithShippingMethod.getShippingInfo().getShippingMethod().getObj().getTaxCategory().getObj())
                        .as("reference expansion taxCategory")
                        .isEqualTo(cartWithShippingMethod.getShippingInfo().getTaxCategory().getObj())
                        .isNotNull();

                //remove shipping method
                final Cart cartWithoutShippingMethod = client().executeBlocking(CartUpdateCommand.of(cartWithShippingMethod, SetShippingMethod.ofRemove()));
                assertThat(cartWithoutShippingMethod.getShippingInfo()).isNull();

                return cartWithoutShippingMethod;
            });
        });
    }

    @Test
    public void setShippingMethodDoesNotMatchCart() throws Exception {
        withDynamicShippingMethodForGermany(client(), CartPredicate.of("customer.email=\"john@example.com\""), shippingMethod -> {
            withCart(client(), createCartWithShippingAddress(client()), cart -> {
                final CartUpdateCommand updateCommand =
                        CartUpdateCommand.of(cart, SetShippingMethod.of(shippingMethod.toResourceIdentifier()));
                assertThatThrownBy(() -> client().executeBlocking(updateCommand)).isInstanceOf(ErrorResponseException.class).hasMessageContaining("does not match");

                return cart;
            });
        });
    }

    @Test
    public void setShippingMethodById() throws Exception {
        withShippingMethodForGermany(client(), shippingMethod -> {
            withCart(client(), createCartWithShippingAddress(client()), cart -> {
                //add shipping method
                assertThat(cart.getShippingInfo()).isNull();
                final CartUpdateCommand updateCommand =
                        CartUpdateCommand.of(cart, SetShippingMethod.ofId(shippingMethod.getId()))
                                .plusExpansionPaths(m -> m.shippingInfo().shippingMethod().taxCategory())
                                .plusExpansionPaths(m -> m.shippingInfo().taxCategory());
                final Cart cartWithShippingMethod = client().executeBlocking(updateCommand);
                assertThat(cartWithShippingMethod.getShippingInfo().getShippingMethod()).isEqualTo(shippingMethod.toReference());
                assertThat(cartWithShippingMethod.getShippingInfo().getShippingMethod().getObj())
                        .as("reference expansion shippingMethod")
                        .isEqualTo(shippingMethod);

                //remove shipping method
                final Cart cartWithoutShippingMethod = client().executeBlocking(CartUpdateCommand.of(cartWithShippingMethod, SetShippingMethod.ofRemove()));
                assertThat(cartWithoutShippingMethod.getShippingInfo()).isNull();

                return cartWithoutShippingMethod;
            });
        });
    }

    @Test
    public void setCustomerId() throws Exception {
        withCustomer(client(), customer -> {
            final Cart cart = createCartWithCountry(client());
            assertThat(cart.getCustomerId()).isNull();
            final Cart cartWithCustomerId = client().executeBlocking(CartUpdateCommand.of(cart, SetCustomerId.ofCustomer(customer)));
            assertThat(cartWithCustomerId.getCustomerId()).contains(customer.getId());
            final Cart cartWithoutCustomerId = client().executeBlocking(CartUpdateCommand.of(cartWithCustomerId, SetCustomerId.empty()));
            assertThat(cartWithoutCustomerId.getCustomerId()).isNull();
        });
    }

    @Test
    public void recalculate() throws Exception {
        withEmptyCartAndProduct(client(), (emptyCart, product) -> {
            final AddLineItem action = AddLineItem.of(product.getId(), MASTER_VARIANT_ID, 1L);

            final Cart cartWithLineItem = client().executeBlocking(CartUpdateCommand.of(emptyCart, action));
            final Price oldPrice = cartWithLineItem.getLineItems().get(0).getPrice();
            final PriceDraft priceDraft = PriceDraft.of(oldPrice).withValue(oldPrice.getValue().multiply(2));
            final Product productWithChangedPrice =
                    client().executeBlocking(ProductUpdateCommand.of(product, asList(ChangePrice.of(oldPrice, priceDraft), Publish.of())));

            final List<Price> prices = productWithChangedPrice.getMasterData().getCurrent().getMasterVariant().getPrices();
            assertThat(prices.stream().map(price -> PriceDraft.of(price)).collect(Collectors.toList()))
                    .as("we updated the price of the product")
                    .isEqualTo(asList(priceDraft));

            final LineItem lineItemOfTheChangedProduct =
                    client().executeBlocking(CartByIdGet.of(cartWithLineItem)).getLineItems().get(0);
            assertThat(lineItemOfTheChangedProduct.getPrice())
                    .as("the new product price is not automatically propagated to the line item in the cart")
                    .isEqualTo(oldPrice).isNotEqualTo(priceDraft);

            final Cart recalculatedCart = client().executeBlocking(CartUpdateCommand.of(cartWithLineItem, Recalculate.of()));

            assertThat(PriceDraft.of(recalculatedCart.getLineItems().get(0).getPrice()))
                    .as("recalculate updated the price of the line item in the cart")
                    .isEqualTo(priceDraft);
            assertThat(recalculatedCart.getTotalPrice())
                    .as("recalculate also updated the total price of the cart")
                    .isEqualTo(priceDraft.getValue()).isNotEqualTo(cartWithLineItem.getTotalPrice());
        });
    }

    @Test
    public void recalculateAndUpdateProductData() throws Exception {
        withEmptyCartAndProduct(client(), (emptyCart, product) -> {
            //create cart with line item
            final AddLineItem action = AddLineItem.of(product.getId(), MASTER_VARIANT_ID, 1L);
            final Cart cartWithLineItem = client().executeBlocking(CartUpdateCommand.of(emptyCart, action));
            final NamedAttributeAccess<LocalizedEnumValue> colorAttribute = Colors.ATTRIBUTE;
            final LocalizedEnumValue oldColor = cartWithLineItem.getLineItems().get(0).getVariant().findAttribute(colorAttribute).get();

            //update the product
            final LocalizedEnumValue newValueForColor = Colors.RED;
            final SetAttribute localizedEnumUpdate = SetAttribute.of(MASTER_VARIANT_ID, colorAttribute, newValueForColor);
            final Product updatedProduct = client().executeBlocking(ProductUpdateCommand.of(product, asList(localizedEnumUpdate, Publish.of())));
            assertThat(updatedProduct.getMasterData().getCurrent().getMasterVariant().findAttribute(colorAttribute)).contains(newValueForColor);

            //check the line item in the cart, the product data will not be updated
            final LineItem lineItemOfTheChangedProduct =
                    client().executeBlocking(CartByIdGet.of(cartWithLineItem)).getLineItems().get(0);
            assertThat(lineItemOfTheChangedProduct.getVariant().findAttribute(colorAttribute))
                    .as("the new product attribute value is not automatically propagated to the line item in the cart")
                    .contains(oldColor);

            //use recalculate with updateProductData flag, the product data will be updated
            final Cart recalculatedCart = client().executeBlocking(CartUpdateCommand.of(cartWithLineItem, Recalculate.of().withUpdateProductData(Boolean.TRUE)));
            assertThat(recalculatedCart.getLineItems().get(0).getVariant().findAttribute(colorAttribute))
                    .contains(newValueForColor);
        });
    }

    @Test
    public void moneyPortionIsPresent() throws Exception {
        withFilledCart(client(), cart -> {
            final MonetaryAmount money = cart.getTaxedPrice().getTaxPortions().get(0).getAmount();
            assertThat(money).isNotNull();
        });
    }

    @Test
    public void removeDiscountCode() throws Exception {
        withCartAndDiscountCode(client(), (cart, discountCode) -> {
            //addDiscountCode
            final Cart cartWithCode = client().executeBlocking(CartUpdateCommand.of(cart, AddDiscountCode.of(discountCode)));
            final DiscountCodeInfo discountCodeInfo = cartWithCode.getDiscountCodes().get(0);
            assertThat(discountCodeInfo.getDiscountCode()).isEqualTo(discountCode.toReference());

            //removeDiscountCode
            final Cart updatedCart = client().executeBlocking(CartUpdateCommand.of(cartWithCode, RemoveDiscountCode.of(discountCode)));
            assertThat(updatedCart.getDiscountCodes()).isEmpty();

            return updatedCart;
        });
    }

    @Test
    public void addPayment() {
        withPayment(client(), payment -> {
            withCart(client(), cart -> {
                //add payment
                final CartUpdateCommand command = CartUpdateCommand.of(cart, AddPayment.of(payment))
                        .withExpansionPaths(m -> m.paymentInfo().payments());
                final Cart cartWithPayment = client().executeBlocking(command);

                final Reference<Payment> paymentReference = cartWithPayment.getPaymentInfo().getPayments().get(0);
                assertThat(paymentReference).isEqualTo(payment.toReference());
                assertThat(paymentReference).is(expanded(payment));

                //query cart by payment
                final CartQuery cartQuery = CartQuery.of()
                        .withPredicates(m -> m.paymentInfo().payments().isIn(singletonList(payment)));
                assertThat(client().executeBlocking(cartQuery).head()).contains(cartWithPayment);

                //remove payment
                final Cart cartWithoutPayment = client().executeBlocking(CartUpdateCommand.of(cartWithPayment, RemovePayment.of(payment)));

                assertThat(cartWithoutPayment.getPaymentInfo()).isNull();

                return cartWithoutPayment;
            });
            return payment;
        });
    }

    @Test
    public void locale() {
        withCart(client(), cart -> {
            assertThat(cart.getLocale()).isNull();
            final Cart updatedCart = client().executeBlocking(CartUpdateCommand.of(cart, SetLocale.of(Locale.GERMAN)));
            assertThat(updatedCart.getLocale()).isEqualTo(GERMAN);
            return updatedCart;
        });
    }

    @Test
    public void setLineItemTotalPrice() throws Exception {
        withFilledCart(client(), (Cart cart) -> {
            final LineItem originalLineItem = cart.getLineItems().get(0);
            assertThat(originalLineItem.getPrice().getValue()).isEqualTo(MoneyImpl.ofCents(1234, EUR));
            assertThat(originalLineItem.getTotalPrice()).isEqualTo(MoneyImpl.ofCents(3702, EUR));
            assertThat(originalLineItem.getQuantity()).isEqualTo(3L);
            assertThat(originalLineItem.getPriceMode()).isEqualTo(LineItemPriceMode.PLATFORM);

            final String lineItemId = originalLineItem.getId();
            final MonetaryAmount itemPrice = MoneyImpl.ofCents(100, EUR);
            final MonetaryAmount totalPrice = MoneyImpl.ofCents(300, EUR);
            final SetLineItemTotalPrice updateAction = SetLineItemTotalPrice.of(lineItemId,
                    ExternalLineItemTotalPrice.ofPriceAndTotalPrice(itemPrice, totalPrice));
            final Cart updatedCart = client().executeBlocking(CartUpdateCommand.of(cart, updateAction));

            final LineItem lineItem = updatedCart.getLineItems().get(0);
            assertThat(lineItem.getPrice().getValue()).isEqualTo(itemPrice);
            assertThat(lineItem.getTotalPrice()).isEqualTo(totalPrice);
            assertThat(lineItem.getPriceMode()).isEqualTo(LineItemPriceMode.EXTERNAL_TOTAL);
        });
    }

    @Test
    public void setCartTotalTax() throws Exception {
        withFilledCartWithTaxMode(client(), TaxMode.EXTERNAL_AMOUNT, cart -> {
            final MonetaryAmount externalTotalGross = MoneyImpl.ofCents(1000, EUR);
            final SetCartTotalTax setCartTotalTax = SetCartTotalTax.of(externalTotalGross);
            final Cart updatedCart = client().executeBlocking(CartUpdateCommand.of(cart, setCartTotalTax));

            assertThat(updatedCart.getTaxedPrice().getTotalGross()).isEqualTo(externalTotalGross);
        });
    }

    @Test
    public void setShippingMethodTaxAmount() throws Exception {
        withShippingMethodForGermany(client(), shippingMethod -> {
            withCustomLineItemFilledCartWithTaxMode(client(), TaxMode.EXTERNAL_AMOUNT, cart -> {
                final Cart cartWithShippingMethod = client().executeBlocking(CartUpdateCommand.of(cart, SetShippingMethod.of(shippingMethod.toResourceIdentifier())));
                assertThat(cartWithShippingMethod.getShippingInfo()).isNotNull();
                assertThat(cartWithShippingMethod.getShippingInfo().getTaxedPrice()).isNull();

                final ExternalTaxRateDraft taxRate = ExternalTaxRateDraftBuilder
                        .ofAmount(1.0, "Test Tax", CountryCode.DE)
                        .build();
                final MonetaryAmount totalGross = MoneyImpl.ofCents(100000, EUR);
                final ExternalTaxAmountDraftDsl taxAmountDraft = ExternalTaxAmountDraftBuilder
                        .of(totalGross, taxRate)
                        .build();
                final SetShippingMethodTaxAmount setShippingMethodTaxAmount = SetShippingMethodTaxAmount.of(taxAmountDraft);

                final Cart updatedCart = client().executeBlocking(CartUpdateCommand.of(cartWithShippingMethod, setShippingMethodTaxAmount));

                final TaxedItemPrice taxedPrice = updatedCart.getShippingInfo().getTaxedPrice();

                assertThat(taxedPrice.getTotalGross()).isEqualTo(totalGross);

                return updatedCart;
            });
        });
    }

    @Test
    public void setLineItemTaxAmount() throws Exception {
        withFilledCartWithTaxMode(client(), TaxMode.EXTERNAL_AMOUNT, cart -> {
            final LineItem originalLineItem = cart.getLineItems().get(0);
            final ExternalTaxRateDraft taxRate = ExternalTaxRateDraftBuilder
                    .ofAmount(1.0, "Test Tax", CountryCode.DE)
                    .build();
            final MonetaryAmount totalGross = MoneyImpl.ofCents(1000, EUR);
            final ExternalTaxAmountDraftDsl taxAmountDraft = ExternalTaxAmountDraftBuilder
                    .of(totalGross, taxRate)
                    .build();
            final SetLineItemTaxAmount setLineItemTaxAmount = SetLineItemTaxAmount.of(originalLineItem.getId(), taxAmountDraft);
            final Cart updatedCart = client().executeBlocking(CartUpdateCommand.of(cart, setLineItemTaxAmount));
            final LineItem updatedLineItem = updatedCart.getLineItems().get(0);
            assertThat(updatedLineItem.getTaxedPrice().getTotalGross()).isEqualTo(totalGross);
        });
    }

    @Test
    public void setCustomLineItemTaxAmount() throws Exception {
        withCustomLineItemFilledCartWithTaxMode(client(), TaxMode.EXTERNAL_AMOUNT, cart -> {
            final CustomLineItem originalCustomLineItem = cart.getCustomLineItems().get(0);
            final ExternalTaxRateDraft taxRate = ExternalTaxRateDraftBuilder
                    .ofAmount(1.0, "Test Tax", CountryCode.DE)
                    .build();
            final MonetaryAmount totalGross = MoneyImpl.ofCents(1000, EUR);
            final ExternalTaxAmountDraftDsl taxAmountDraft = ExternalTaxAmountDraftBuilder
                    .of(totalGross, taxRate)
                    .build();
            final SetCustomLineItemTaxAmount setCustomLineItemTaxAmount = SetCustomLineItemTaxAmount.of(originalCustomLineItem.getId(), taxAmountDraft);
            final Cart updatedCart = client().executeBlocking(CartUpdateCommand.of(cart, setCustomLineItemTaxAmount));
            final CustomLineItem updatedCustomLineItem = updatedCart.getCustomLineItems().get(0);
            assertThat(updatedCustomLineItem.getTaxedPrice().getTotalGross()).isEqualTo(totalGross);

            return updatedCart;
        });
    }

    @Test
    public void setLineItemExternalPrice() throws Exception {
        withFilledCart(client(), (Cart cart) -> {
            final LineItem originalLineItem = cart.getLineItems().get(0);
            assertThat(originalLineItem.getPrice().getValue()).isEqualTo(MoneyImpl.ofCents(1234, EUR));
            assertThat(originalLineItem.getTotalPrice()).isEqualTo(MoneyImpl.ofCents(3702, EUR));
            assertThat(originalLineItem.getQuantity()).isEqualTo(3L);
            assertThat(originalLineItem.getPriceMode()).isEqualTo(LineItemPriceMode.PLATFORM);

            final String lineItemId = originalLineItem.getId();
            final MonetaryAmount externalPrice = EURO_30;
            final SetLineItemPrice updateAction = SetLineItemPrice.of(lineItemId, externalPrice);
            final Cart updatedCart = client().executeBlocking(CartUpdateCommand.of(cart, updateAction));

            final LineItem lineItem = updatedCart.getLineItems().get(0);
            assertThat(lineItem.getPrice().getValue()).isEqualTo(externalPrice);
            assertThat(lineItem.getPriceMode()).isEqualTo(LineItemPriceMode.EXTERNAL_PRICE);
        });
    }

    @Test
    public void addShoppingList() throws Exception {
        withEmptyCartAndProduct(client(), (cart, product) -> {
            assertThat(cart.getLineItems()).isEmpty();

            final List<io.sphere.sdk.shoppinglists.LineItemDraft> lineItemDrafts = asList(
                    LineItemDraftBuilder.of(product.getId()).quantity(1L).build(),
                    LineItemDraftBuilder.of(product.getId()).quantity(2L).build(),
                    LineItemDraftBuilder.of(product.getId()).quantity(3L).build());
            final ShoppingListDraftDsl shoppingListDraft = newShoppingListDraftBuilder().lineItems(lineItemDrafts).build();

            withShoppingList(client(), shoppingListDraft, shoppingList -> {
                final Cart updatedCart = client().executeBlocking(
                        CartUpdateCommand.of(cart, AddShoppingList.of(shoppingList.toReference())));

                final List<LineItem> lineItems = updatedCart.getLineItems();
                assertThat(lineItems.size()).isEqualTo(1);

                final LineItem lineItem = lineItems.get(0);
                assertThat(lineItem.getQuantity()).isEqualTo(6);
                assertThat(lineItem.getProductId()).isEqualTo(product.getId());

                return shoppingList;
            });
        });
    }

    @Test
    public void cartInStoreSetAnonymousId() throws Exception {
        withStore(client(), store -> {
            CartDraft cartDraft = CartDraft.of(EUR).withStore(store.toResourceIdentifier());
            withCartDraft(client(), cartDraft, cart -> {
                final String anonymousId = randomString();

                final Cart updatedCart = client().executeBlocking(CartInStoreUpdateCommand.of(store.getKey(), cart, SetAnonymousId.of(anonymousId)));
                assertThat(updatedCart).isNotNull();
                assertThat(updatedCart.getAnonymousId()).isEqualTo(anonymousId);
                assertThat(updatedCart.getStore()).isNotNull();
                assertThat(updatedCart.getStore().getKey()).isEqualTo(store.getKey());
                return updatedCart;
            });
        });
    }

    @Test
    public void setKey() {
        withCart(client(), cart -> {
            assertThat(cart.getKey()).isNull();
            final String key = randomKey();
            final Cart updatedCart = client().executeBlocking(CartUpdateCommand.of(cart, SetKey.of(key)));
            assertThat(updatedCart.getKey()).isEqualTo(key);
            return updatedCart;
        });
    }

    @Test
    public void setBillingAddressCustomType() throws Exception {
        final Address a = Address.of(CountryCode.DE);
        TypeFixtures.withUpdateableType(client(), type -> {
            withCartDraft(client(), CartDraft.of(EUR).withCountry(CountryCode.DE).withBillingAddress(a), cart -> {
                final Cart updatedCart =
                        client().executeBlocking(CartUpdateCommand.of(cart, SetBillingAddressCustomType.ofTypeIdAndObjects(type.getId(), TypeFixtures.STRING_FIELD_NAME, "bar")));
                assertThat(updatedCart.getBillingAddress().getCustomFields().getFieldAsString(TypeFixtures.STRING_FIELD_NAME)).isEqualTo("bar");

                final Cart updatedCart2 =
                        client().executeBlocking(CartUpdateCommand.of(updatedCart, SetBillingAddressCustomField.ofObject(TypeFixtures.STRING_FIELD_NAME, "bar2")));
                assertThat(updatedCart2.getBillingAddress().getCustomFields().getFieldAsString(TypeFixtures.STRING_FIELD_NAME)).isEqualTo("bar2");

                return updatedCart2;
            });
            return type;
        });
    }

    @Test
    public void setShippingAddressCustomType() throws Exception {
        final Address a = Address.of(CountryCode.DE);
        TypeFixtures.withUpdateableType(client(), type -> {
            withCartDraft(client(), CartDraft.of(EUR).withCountry(CountryCode.DE).withShippingAddress(a), cart -> {
                final Cart updatedCart =
                        client().executeBlocking(CartUpdateCommand.of(cart, SetShippingAddressCustomType.ofTypeIdAndObjects(type.getId(), TypeFixtures.STRING_FIELD_NAME, "bar")));
                assertThat(updatedCart.getShippingAddress().getCustomFields().getFieldAsString(TypeFixtures.STRING_FIELD_NAME)).isEqualTo("bar");

                final Cart updatedCart2 =
                        client().executeBlocking(CartUpdateCommand.of(updatedCart, SetShippingAddressCustomField.ofObject(TypeFixtures.STRING_FIELD_NAME, "bar2")));
                assertThat(updatedCart2.getShippingAddress().getCustomFields().getFieldAsString(TypeFixtures.STRING_FIELD_NAME)).isEqualTo("bar2");

                return updatedCart2;
            });
            return type;
        });
    }

    @Test
    public void setLineItemDistributionChannel() throws Exception {
        withChannelOfRole(client(), PRODUCT_DISTRIBUTION, channel -> {
            withFilledCart(client(), cart -> {
                final String lineItemId = cart.getLineItems().get(0).getId();
                final Reference<Channel> channelReference = Channel.referenceOfId(channel.getId());
                final SetLineItemDistributionChannel updateAction = SetLineItemDistributionChannel.of(lineItemId, channelReference);
                final Cart updatedCart = client().executeBlocking(CartUpdateCommand.of(cart, updateAction));

                final LineItem lineItem = updatedCart.getLineItems().get(0);
                assertThat(lineItem.getDistributionChannel()).isEqualTo(updatedCart.getLineItems().get(0).getDistributionChannel());
            });
        });
    }

    @Test
    public void setLineItemSupplyChannel() throws Exception {
        withChannelOfRole(client(), INVENTORY_SUPPLY, channel -> {
            withFilledCart(client(), cart -> {
                final String lineItemId = cart.getLineItems().get(0).getId();
                final Reference<Channel> channelReference = Channel.referenceOfId(channel.getId());
                final SetLineItemSupplyChannel updateAction = SetLineItemSupplyChannel.of(lineItemId, channelReference);
                final Cart updatedCart = client().executeBlocking(CartUpdateCommand.of(cart, updateAction));

                final LineItem lineItem = updatedCart.getLineItems().get(0);
                assertThat(lineItem.getSupplyChannel()).isEqualTo(updatedCart.getLineItems().get(0).getSupplyChannel());
            });
        });
    }
}
