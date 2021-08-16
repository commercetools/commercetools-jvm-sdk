package io.sphere.sdk.carts.commands;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.carts.*;
import io.sphere.sdk.carts.queries.CartQuery;
import io.sphere.sdk.discountcodes.DiscountCodeDraft;
import io.sphere.sdk.discountcodes.DiscountCodeFixtures;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.products.BySkuVariantIdentifier;
import io.sphere.sdk.stores.Store;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.test.JsonNodeReferenceResolver;
import io.sphere.sdk.types.CustomFieldsDraft;
import io.sphere.sdk.types.FieldDefinition;
import io.sphere.sdk.types.StringFieldType;
import io.sphere.sdk.types.TypeFixtures;
import io.sphere.sdk.types.commands.TypeDeleteCommand;
import io.sphere.sdk.types.queries.TypeQuery;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static io.sphere.sdk.cartdiscounts.CartDiscountFixtures.withCartDiscount;
import static io.sphere.sdk.customers.CustomerFixtures.withCustomer;
import static io.sphere.sdk.discountcodes.DiscountCodeFixtures.withDiscountCode;
import static io.sphere.sdk.products.ProductFixtures.withTaxedProduct;
import static io.sphere.sdk.shippingmethods.ShippingMethodFixtures.withShippingMethodForGermany;
import static io.sphere.sdk.stores.StoreFixtures.withStore;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static io.sphere.sdk.types.TypeFixtures.withType;
import static io.sphere.sdk.types.TypeFixtures.withUpdateableType;
import static java.util.Collections.singletonList;
import static java.util.Collections.singletonMap;
import static org.assertj.core.api.Assertions.assertThat;

public class    CartCreateCommandIntegrationTest extends IntegrationTest {
    @BeforeClass
    public static void prepare() {
        client().executeBlocking(TypeQuery.of().withPredicates(m -> m.key().is("json-demo-type-key")))
                .getResults()
                .forEach(type -> {
                    client().executeBlocking(CartQuery.of().withPredicates(m -> m.custom().type().is(type)))
                            .getResults()
                            .forEach(cart -> client().executeBlocking(CartDeleteCommand.of(cart)));
                    client().executeBlocking(TypeDeleteCommand.of(type));
                });
    }

    @Test
    public void execution() throws Exception {
        final CartDraft cartDraft = CartDraft.of(EUR).withCountry(DE);
        final Cart cart = client().executeBlocking(CartCreateCommand.of(cartDraft));
        assertThat(cart.getTotalPrice().getCurrency().getCurrencyCode()).isEqualTo(EUR.getCurrencyCode());
        assertThat(cart.getCountry()).isEqualTo(DE);
        assertThat(cart.getTotalPrice().isZero()).isTrue();
    }

    @Test
    public void cartCreationWithTTL() throws Exception {
        final int deleteDaysAfterLastModification = 1;
        final CartDraft cartDraft = CartDraft.of(EUR)
                .withCountry(DE)
                .withDeleteDaysAfterLastModification(deleteDaysAfterLastModification);
        final Cart cart = client().executeBlocking(CartCreateCommand.of(cartDraft));
        assertThat(cart.getTotalPrice().getCurrency().getCurrencyCode()).isEqualTo(EUR.getCurrencyCode());
        assertThat(cart.getCountry()).isEqualTo(DE);
        assertThat(cart.getTotalPrice().isZero()).isTrue();
        assertThat(cart.getDeleteDaysAfterLastModification()).isEqualTo(deleteDaysAfterLastModification);
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
                    withTaxedProduct(client(), product1 -> {
                        withTaxedProduct(client(), product2 -> {
                            withTaxedProduct(client(), product3 -> {
                                final LineItemDraft lineItemDraft1 = LineItemDraft.of(product1, 1, 15);
                                final LineItemDraft lineItemDraftOfVariantIdentifier = LineItemDraftBuilder.ofVariantIdentifier(product2.getMasterData().getStaged().getMasterVariant().getIdentifier(), 25L).build();
                                String sku = product3.getMasterData().getStaged().getMasterVariant().getSku();
                                final LineItemDraft lineItemDraftOfSku = LineItemDraftBuilder.ofSkuVariantIdentifier(BySkuVariantIdentifier.of(sku), 35L).build();
                                final List<LineItemDraft> lineItems = asList(lineItemDraft1, lineItemDraftOfVariantIdentifier, lineItemDraftOfSku);

                                final List<CustomLineItemDraft> customLineItems = singletonList(CustomLineItemDraft.of(randomSlug(), "foo-bar", EURO_5, product1.getTaxCategory(), 1L, null));

                                final CartDraft cartDraft = CartDraft.of(EUR)
                                        .withCountry(DE)
                                        .withLocale(Locale.GERMAN)
                                        .withCustomerId(customerId)
                                        .withCustomerEmail(customerEmail)
                                        .withLineItems(lineItems)
                                        .withCustomLineItems(customLineItems)
                                        .withBillingAddress(billingAddress)
                                        .withShippingAddress(shippingAddress)
                                        .withShippingMethod(shippingMethod)
                                        .withCustom(customFieldsDraft);
                                final CartCreateCommand cartCreateCommand = CartCreateCommand.of(cartDraft)
                                        .plusExpansionPaths(m -> m.lineItems().productType());
                                final Cart cart = client().executeBlocking(cartCreateCommand);

                                softAssert(s -> {
                                    s.assertThat(cart.getCountry()).isEqualTo(DE);
                                    s.assertThat(cart.getLocale()).isEqualTo(Locale.GERMAN);
                                    s.assertThat(cart.getTotalPrice().getCurrency()).isEqualTo(EUR);
                                    s.assertThat(cart.getCurrency()).isEqualTo(EUR);
                                    s.assertThat(cart.getCustomerId()).isEqualTo(customerId);
                                    s.assertThat(cart.getCustomerEmail()).isEqualTo(customerEmail);
                                    s.assertThat(cart.getLineItems()).hasSize(3);
                                    final LineItem lineItem1 = cart.getLineItems().get(0);
                                    s.assertThat(lineItem1.getProductId()).isEqualTo(product1.getId());
                                    s.assertThat(lineItem1.getQuantity()).isEqualTo(15);
                                    s.assertThat(lineItem1.getProductType()).isEqualTo(product1.getProductType());
                                    s.assertThat(lineItem1.getProductType().getObj()).isNotNull();

                                    final LineItem lineItem2 = cart.getLineItems().get(1);
                                    s.assertThat(lineItem2.getProductId()).isEqualTo(product2.getId());
                                    s.assertThat(lineItem2.getVariant().getId()).isEqualTo(product2.getMasterData().getStaged().getMasterVariant().getId());

                                    final LineItem lineItem3 = cart.getLineItems().get(2);
                                    s.assertThat(lineItem3.getProductId()).isEqualTo(product3.getId());
                                    s.assertThat(lineItem3.getVariant().getId()).isEqualTo(product3.getMasterData().getStaged().getMasterVariant().getId());
                                    s.assertThat(lineItem3.getVariant().getSku()).isEqualTo(product3.getMasterData().getStaged().getMasterVariant().getSku());

                                    s.assertThat(cart.getCustomLineItems().get(0).getSlug()).isEqualTo("foo-bar");
                                    s.assertThat(cart.getBillingAddress()).isEqualTo(billingAddress);
                                    s.assertThat(cart.getShippingAddress()).isEqualTo(shippingAddress);
                                    s.assertThat(cart.getCustom().getFieldsJsonMap()).isEqualTo(customFieldsDraft.getFields());
                                });

                                //cleanup
                                client().executeBlocking(CartDeleteCommand.of(cart));
                            });
                        });
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

    @Test
    public void createByJson() {
        final JsonNodeReferenceResolver referenceResolver = new JsonNodeReferenceResolver();
        withTaxedProduct(client(), product -> {
            withType(client(),
                    typeBuilder -> typeBuilder.key("json-demo-type-key").fieldDefinitions(singletonList(FieldDefinition.of(StringFieldType.of(), "stringField", randomSlug(), true))),
                    type -> {
                        referenceResolver.addResourceByKey("sample-product", product);
                        referenceResolver.addResourceByKey("standard-tax", product.getTaxCategory());
                        final CartDraft cartDraft = draftFromJsonResource("drafts-tests/cart.json", CartDraft.class, referenceResolver);

                        final Cart cart = client().executeBlocking(CartCreateCommand.of(cartDraft));

                        assertThat(cart.getCurrency()).isEqualTo(EUR);
                        assertThat(cart.getCountry()).isEqualTo(CountryCode.DE);
                        assertThat(cart.getInventoryMode()).isEqualTo(InventoryMode.TRACK_ONLY);
                        final LineItem lineItem = cart.getLineItems().get(0);
                        assertThat(lineItem.getProductId()).isEqualTo(product.getId());
                        assertThat(lineItem.getVariant().getId()).isEqualTo(1);
                        assertThat(lineItem.getQuantity()).isEqualTo(2);
                        final CustomLineItem customLineItem = cart.getCustomLineItems().get(0);
                        assertThat(customLineItem.getName().get(Locale.ENGLISH)).isEqualTo("a custom line item");
                        assertThat(customLineItem.getQuantity()).isEqualTo(3);
                        assertThat(customLineItem.getMoney()).isEqualTo(EURO_20);
                        assertThat(customLineItem.getSlug()).isEqualTo("foo");
                        assertThat(customLineItem.getTaxCategory()).isEqualTo(product.getTaxCategory());
                        final Address expectedAddress = Address.of(DE).withLastName("Osgood").withFax("0300000000");
                        assertThat(cart.getShippingAddress()).isEqualTo(expectedAddress);
                        assertThat(cart.getCustom().getFieldAsString("stringField")).isEqualTo("bar");

                        client().executeBlocking(CartDeleteCommand.of(cart));
                    });
        });

    }

    @Test
    public void createCartWithActiveDiscount() throws Exception {
        withCartDiscount(client(), builder -> builder.requiresDiscountCode(true), cartDiscount -> {
            DiscountCodeDraft discountCodeDraft = DiscountCodeDraft.of(randomString(), cartDiscount).withActive(true);
            withDiscountCode(client(), discountCodeDraft, discountCode -> {
                CartDraft cartDraft = CartDraft.of(EUR).withCountry(DE).withDiscountCodes(Arrays.asList(discountCode.getCode()));
                Cart cart = client().executeBlocking(CartCreateCommand.of(cartDraft));
                assertThat(cart.getDiscountCodes().get(0).getDiscountCode().getId()).isEqualTo(discountCode.getId());

                client().executeBlocking(CartDeleteCommand.of(cart));
            });
        });
    }

    @Test
    public void anonymousCartId() throws Exception {
        final String anonymousId = randomKey();
        final CartDraft cartDraft = CartDraft.of(EUR).withCountry(DE)
                .withAnonymousId(anonymousId);
        final Cart cart = client().executeBlocking(CartCreateCommand.of(cartDraft));
        assertThat(cart.getAnonymousId()).isEqualTo(anonymousId);
    }

    @Test
    public void withDraft() {
        final CartDraft draft = CartDraftDsl.of(EUR);
        final CartDraft newDraft = CartDraftDsl.of(USD);
        final CartCreateCommand prevCmd = CartCreateCommand.of(draft).withExpansionPaths(m -> m.lineItems().state().state().transitions());
        final CartCreateCommand cmd = prevCmd
                .withDraft(newDraft);
        assertThat(cmd.getDraft()).isEqualTo(newDraft);
        assertThat(cmd.expansionPaths()).isEqualTo(prevCmd.expansionPaths());
    }

    @Test
    public void createCartWithStoreReference() {
        withStore(client(), store -> {
            final ResourceIdentifier<Store> storeResourceIdentifier = ResourceIdentifier.ofId(store.getId(), "store");
            final CartDraft cartDraft = CartDraft.of(EUR).withCountry(DE).withStore(storeResourceIdentifier);
            final Cart cart = client().executeBlocking(CartCreateCommand.of(cartDraft));
            assertThat(cart).isNotNull();
            assertThat(cart.getStore()).isNotNull();
            assertThat(cart.getStore().getKey()).isEqualTo(store.getKey());

            client().executeBlocking(CartDeleteCommand.of(cart));
        });
    }

    @Test
    public void createCartInStore(){
        withStore(client(), store -> {
            final CartDraft cartDraft = CartDraft.of(EUR).withCountry(DE);
            final Cart cart = client().executeBlocking(CartInStoreCreateCommand.of(store.getKey(), cartDraft));
            assertThat(cart).isNotNull();
            assertThat(cart.getStore()).isNotNull();
            assertThat(cart.getStore().getKey()).isEqualTo(store.getKey());
            client().executeBlocking(CartDeleteCommand.of(cart));
        });
    }

    private void testInventoryMode(final InventoryMode inventoryMode) {
        final Cart cart = client().executeBlocking(CartCreateCommand.of(CartDraft.of(EUR).withInventoryMode(inventoryMode)));
        assertThat(cart.getInventoryMode()).isEqualTo(inventoryMode);
        client().executeBlocking(CartDeleteCommand.of(cart));
    }
}
