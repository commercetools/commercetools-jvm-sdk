package io.sphere.sdk.orders.commands;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.cartdiscounts.DiscountedLineItemPriceForQuantity;
import io.sphere.sdk.carts.*;
import io.sphere.sdk.channels.ChannelRole;
import io.sphere.sdk.models.*;
import io.sphere.sdk.orders.*;
import io.sphere.sdk.products.*;
import io.sphere.sdk.products.attributes.Attribute;
import io.sphere.sdk.products.attributes.AttributeAccess;
import io.sphere.sdk.products.attributes.AttributeImportDraft;
import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.shippingmethods.ShippingRate;
import io.sphere.sdk.stores.Store;
import io.sphere.sdk.suppliers.TShirtProductTypeDraftSupplier;
import io.sphere.sdk.taxcategories.TaxCategory;
import io.sphere.sdk.taxcategories.TaxRate;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.test.JsonNodeReferenceResolver;
import io.sphere.sdk.test.SphereTestUtils;
import io.sphere.sdk.types.CustomFields;
import io.sphere.sdk.types.CustomFieldsDraft;
import io.sphere.sdk.types.Type;
import io.sphere.sdk.utils.MoneyImpl;
import org.junit.Test;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import static io.sphere.sdk.channels.ChannelFixtures.withPersistentChannel;
import static io.sphere.sdk.customers.CustomerFixtures.withCustomer;
import static io.sphere.sdk.customers.CustomerFixtures.withCustomerInGroup;
import static io.sphere.sdk.models.DefaultCurrencyUnits.EUR;
import static io.sphere.sdk.products.ProductFixtures.*;
import static io.sphere.sdk.shippingmethods.ShippingMethodFixtures.withShippingMethodForGermany;
import static io.sphere.sdk.stores.StoreFixtures.withStore;
import static io.sphere.sdk.taxcategories.TaxCategoryFixtures.defaultTaxCategory;
import static io.sphere.sdk.taxcategories.TaxCategoryFixtures.withTransientTaxCategory;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static io.sphere.sdk.types.TypeFixtures.STRING_FIELD_NAME;
import static io.sphere.sdk.types.TypeFixtures.withUpdateableType;
import static java.util.Collections.singletonMap;
import static org.assertj.core.api.Assertions.assertThat;

public class OrderImportCommandIntegrationTest extends IntegrationTest {
    public static final Price PRICE = Price.of(MoneyImpl.of(new BigDecimal("12.34"), EUR)).withCountry(DE);

    @Test
    public void minimalOrder() throws Exception {
        withProduct(client(), product -> {
            final String productId = product.getId();
            final int variantId = 1;
            final LocalizedString name = en("a name");
            final long quantity = 1;
            final Price price = Price.of(EURO_10);
            final OrderState orderState = OrderState.OPEN;
            final MonetaryAmount amount = EURO_10;
            final ProductVariantImportDraft variant = ProductVariantImportDraftBuilder.of(productId, variantId).build();

            final LineItemImportDraft lineItemImportDraft = LineItemImportDraftBuilder.of(variant, quantity, price, name).build();
            final OrderImportDraft orderImportDraft = OrderImportDraftBuilder.ofLineItems(amount, orderState, asList(lineItemImportDraft))
                    .country(DE).build();
            final OrderImportCommand cmd = OrderImportCommand.of(orderImportDraft);

            final Order order = client().executeBlocking(cmd);

            assertThat(order.getOrderState()).isEqualTo(orderState);
            assertThat(order.getTotalPrice()).isEqualTo(amount);
            assertThat(order.getLineItems()).hasSize(1);
            assertThat(order.getCountry()).isEqualTo(DE);
            assertThat(order.getCart()).isNull();
            final LineItem lineItem = order.getLineItems().get(0);
            assertThat(lineItem.getName()).isEqualTo(name);
            assertThat(lineItem.getProductId()).isEqualTo(productId);
            assertThat(lineItem.getVariant().getId()).isEqualTo(variantId);
            assertThat(lineItem.getName()).isEqualTo(name);
            assertThat(lineItem.getQuantity()).isEqualTo(quantity);
        });
    }

    @Test
    public void lineItems() throws Exception {
        withUpdateableType(client(), type -> {
            withPersistentChannel(client(), ChannelRole.INVENTORY_SUPPLY, channel -> {
                        withProduct(client(), product -> {
                            final String value = "foo";
                            final PriceDraft price = PriceDraft.of(EURO_1)
                                    .withCustom(CustomFieldsDraft.ofTypeIdAndObjects(type.getId(), singletonMap(STRING_FIELD_NAME, value)));
                            final String orderNumber = randomString();
                            final int variantId = 1;
                            final String sku = sku(product);
                            final ProductVariantImportDraft productVariantImportDraft = ProductVariantImportDraftBuilder.of(product.getId(), variantId, sku)
                                    .prices(asList(price))
                                    .build();

                            final LocalizedString name = randomSlug();
                            final LineItemImportDraft lineItemImportDraft = LineItemImportDraftBuilder.of(productVariantImportDraft, 2L, price, name)
                                    .supplyChannel(channel)
                                    .build();
                            testOrderAspect(
                                    builder -> builder.lineItems(asList(lineItemImportDraft)).orderNumber(orderNumber),
                                    order -> {
                                        final LineItem lineItem = order.getLineItems().get(0);
                                        assertThat(lineItem.getProductId()).isEqualTo(product.getId());
                                        assertThat(lineItem.getVariant().getId()).isEqualTo(variantId);
                                        final ProductVariant masterVariant = product.getMasterData().getStaged().getMasterVariant();
                                        assertThat(lineItem.getVariant().getAttributes()).isEqualTo(masterVariant.getAttributes());
                                        assertThat(lineItem.getVariant().getImages()).isEqualTo(masterVariant.getImages());
                                        assertThat(lineItem.getVariant().getPrices()).isNotEmpty();
                                        assertThat(lineItem.getVariant().getSku()).contains(masterVariant.getSku());
                                        assertThat(lineItem.getQuantity()).isEqualTo(2);
                                        assertThat(lineItem.getName()).isEqualTo(name);
                                        assertThat(lineItem.getPrice().getCustom()).isNotNull();
                                    }
                            );
                        });
                    }
            );
        return type;
        });


    }

    @Test
    public void importBySku() throws Exception {
        checkImportForVariantIdSkuCombination(product ->
                ProductVariantImportDraftBuilder.ofSku(sku(product)));
    }

    private String sku(final Product product) {
        return product.getMasterData().getStaged().getMasterVariant().getSku();
    }

    @Test
    public void importByVariantId() throws Exception {
        checkImportForVariantIdSkuCombination(product -> ProductVariantImportDraftBuilder.of(product.getId(), 1));
    }

    private void checkImportForVariantIdSkuCombination(final Function<Product, ProductVariantImportDraftBuilder> f) {
        withProduct(client(), product -> {
            final ProductVariantImportDraft productVariantImportDraft = f.apply(product)
                    .build();
            final Price price = PRICE;
            final LocalizedString name = randomSlug();
            final LineItemImportDraft lineItemImportDraft = LineItemImportDraftBuilder.of(productVariantImportDraft, 2L, price, name).build();
            testOrderAspect(
                    builder -> builder.lineItems(asList(lineItemImportDraft)),
                    order -> {
                        final LineItem lineItem = order.getLineItems().get(0);
                        assertThat(lineItem.getProductId()).isEqualTo(product.getId());
                        assertThat(lineItem.getVariant().getId()).isEqualTo(1);
                        assertThat(lineItem.getVariant().getSku()).contains(sku(product));
                    }
            );
        });
    }

    @Test
    public void customLineItems() throws Exception {
        withUpdateableType(client(), type -> {
            withTransientTaxCategory(client(), taxCategory -> withProduct(client(), product -> {
                final LocalizedString name = randomSlug();
                final long quantity = 16;
                final MonetaryAmount money = EURO_20;
                final Reference<TaxCategory> taxCategoryReference = defaultTaxCategory(client()).toReference();
                final String id = "an id";
                final String slug = "a-slug";
                final TaxRate taxRate = taxCategory.getTaxRates().get(0);
                final String value = "foo";
                final PriceDraft price = PriceDraft.of(EURO_1)
                        .withCustom(CustomFieldsDraft.ofTypeIdAndObjects(type.getId(), singletonMap(STRING_FIELD_NAME, value)));
                final CustomLineItemImportDraft customLineItem = CustomLineItemImportDraftBuilder.of(name, quantity, money, taxCategoryReference)
                        .id(id)
                        .slug(slug)
                        .taxRate(taxRate)
                        .build();
                final List<CustomLineItemImportDraft> customLineItems = asList(customLineItem);
                testOrderAspect(
                        builder -> builder.customLineItems(customLineItems),
                        order -> {
                            assertThat(order.getCustomLineItems()).hasSize(1);
                            final CustomLineItem actual = order.getCustomLineItems().get(0);
                            assertThat(actual.getMoney()).isEqualTo(money);
                            assertThat(actual.getQuantity()).isEqualTo(quantity);
                            assertThat(actual.getName()).isEqualTo(name);
                            assertThat(actual.getSlug()).isEqualTo(slug);
                            assertThat(actual.getTaxCategory()).isEqualTo(taxCategoryReference);
                            assertThat(actual.getTaxRate()).isEqualTo(taxRate);
                        }
                );
            }));
            return type;
                });

    }

    @Test
    public void orderImportCanOverrideVariantDataInTheOrder() throws Exception {
        final List<AttributeImportDraft> attributeImportDrafts = asList(
                AttributeImportDraft.of(TShirtProductTypeDraftSupplier.Sizes.ATTRIBUTE.getName(), TShirtProductTypeDraftSupplier.Sizes.S),
                AttributeImportDraft.of(TShirtProductTypeDraftSupplier.Colors.ATTRIBUTE.getName(), TShirtProductTypeDraftSupplier.Colors.RED)
        );
        final List<Attribute> attributesOfOrder = asList(TShirtProductTypeDraftSupplier.Sizes.ATTRIBUTE.valueOf(TShirtProductTypeDraftSupplier.Sizes.S),
                TShirtProductTypeDraftSupplier.Colors.ATTRIBUTE.valueOf(TShirtProductTypeDraftSupplier.Colors.RED));
        final List<Image> images = asList(Image.of("url", ImageDimensions.of(1, 2), "label"));
        final List<PriceDraft> prices = asList(PriceDraft.of(new BigDecimal("15.23"), DefaultCurrencyUnits.EUR));

        withProduct(client(), product -> {
            final int variantId = 1;
            final ProductVariantImportDraft productVariantImportDraft = ProductVariantImportDraftBuilder.of(product.getId(), variantId, sku(product))
                    .attributes(attributeImportDrafts)
                    .images(images)
                    .prices(prices)
                    .build();
            final Price price = PRICE;
            final LocalizedString name = randomSlug();
            final LineItemImportDraft lineItemImportDraft = LineItemImportDraftBuilder.of(productVariantImportDraft, 2L, price, name).build();
            testOrderAspect(
                    builder -> builder.lineItems(asList(lineItemImportDraft)),
                    order -> {
                        final LineItem lineItem = order.getLineItems().get(0);
                        final ProductVariant masterVariant = product.getMasterData().getStaged().getMasterVariant();
                        final ProductVariant productVariant = lineItem.getVariant();
                        assertThat(productVariant.getAttributes()).isEqualTo(attributesOfOrder).isNotEqualTo(masterVariant.getAttributes());
                        assertThat(productVariant.getImages()).isEqualTo(images).isNotEqualTo(masterVariant.getImages());
                        assertEqualPrices(productVariant.getPrices(), prices);

                    }
            );
        });

    }

    @Test
    public void shippingInfo() throws Exception {
        withShippingMethodForGermany(client(), shippingMethod -> {
            withTransientTaxCategory(client(), taxCategory -> {
                final MonetaryAmount price = EURO_5;
                final MonetaryAmount freeAbove = EURO_30;
                final ShippingRate shippingRate = ShippingRate.of(price, freeAbove, Collections.EMPTY_LIST);
                final TaxRate taxRate = taxCategory.getTaxRates().get(0);
                final Reference<TaxCategory> taxCategoryRef = taxCategory.toReference();
                final Reference<ShippingMethod> shippingMethodRef = shippingMethod.toReference();
                final ZonedDateTime createdAt = SphereTestUtils.now().minusSeconds(4);
                final ParcelMeasurements parcelMeasurements = ParcelMeasurements.of(2, 3, 1, 3);
                final LineItemLike lineItem = new LineItemLike() {
                    private final String id = randomUUID();

                    @Override
                    public String getId() {
                        return id;
                    }

                    @Override
                    public Set<ItemState> getState() {
                        throw new UnsupportedOperationException();
                    }

                    @Override
                    public Long getQuantity() {
                        return 2L;
                    }

                    @Override
                    public MonetaryAmount getTotalPrice() {
                        return null;
                    }

                    @Nullable
                    @Override
                    public TaxedItemPrice getTaxedPrice() {
                        return null;
                    }

                    @Override
                    public List<DiscountedLineItemPriceForQuantity> getDiscountedPricePerQuantity() {
                        return null;
                    }

                    @Override
                    public CustomFields getCustom() {
                        return null;
                    }
                };
                final DeliveryItem deliveryItem = DeliveryItem.of(lineItem, 5L);
                final String deliveryId = randomUUID();
                final TrackingData trackingData = TrackingData.of().withTrackingId("tracking id")
                        .withCarrier("carrier").withProvider("provider").withProviderTransaction("prov transaction").withIsReturn(true);
                final Parcel parcel = Parcel.of(createdAt, randomUUID(),asList(), parcelMeasurements, trackingData);
                final List<Delivery> deliveries = asList(Delivery.of(deliveryId, createdAt, asList(deliveryItem), asList(parcel)));
                final ShippingInfoImportDraft shippingInfo = ShippingInfoImportDraftBuilder.of(randomString(), price, shippingRate, taxRate, taxCategoryRef, shippingMethodRef,ShippingMethodState.DOES_NOT_MATCH_CART, deliveries).build();
                testOrderAspect(
                        builder -> builder.shippingInfo(shippingInfo),
                        order -> assertThat(order.getShippingInfo()).
                                isEqualToIgnoringGivenFields(shippingInfo, "taxedPrice", "shippingMethodState")
                );
            });
        });
    }

    private String randomUUID() {
        return UUID.randomUUID().toString();
    }

    @Test
    public void paymentState() throws Exception {
        final PaymentState paymentState = PaymentState.FAILED;
        testOrderAspect(
                builder -> builder.paymentState(paymentState),
                order -> assertThat(order.getPaymentState()).isEqualTo(paymentState)
        );
    }

    @Test
    public void shipmentState() throws Exception {
        final ShipmentState shipmentState = ShipmentState.SHIPPED;
        testOrderAspect(
                builder -> builder.shipmentState(shipmentState),
                order -> assertThat(order.getShipmentState()).isEqualTo(shipmentState)
        );
    }

    @Test
    public void shippingAddress() throws Exception {
        final Address address = AddressBuilder.of(CountryCode.DE).city(randomString()).build().withApartment(randomKey());
        testOrderAspect(
                builder -> builder.shippingAddress(address),
                order -> assertThat(order.getShippingAddress()).isEqualTo(address)
        );
    }

    @Test
    public void taxedPrice() throws Exception {
        final MonetaryAmount totalNet = EURO_10;
        final double v = 0.19;
        final MonetaryAmount totalGross = totalNet.multiply(v + 1);
        final MonetaryAmount taxes = totalGross.negate().add(totalNet);
        final TaxedPrice taxedPrice = TaxedPrice.of(totalNet, totalGross, asList(TaxPortion.of(v, taxes)));
        testOrderAspect(
                builder -> builder.taxedPrice(taxedPrice),
                order -> assertThat(order.getTaxedPrice()).isEqualTo(taxedPrice)
        );
    }

    @Test
    public void customerEmail() throws Exception {
        withCustomer(client(), customer ->
                testOrderAspect(
                        builder -> builder.customerEmail(customer.getEmail()),
                        order -> assertThat(order.getCustomerEmail()).contains(customer.getEmail())
                )
        );
    }

    @Test
    public void customerId() throws Exception {
        withCustomer(client(), customer -> {
            final String customerId = customer.getId();
            testOrderAspect(builder -> {
                        builder.customerId(customerId);
                    },
                    order -> assertThat(order.getCustomerId()).contains(customerId));
        });
    }

    @Test
    public void inventoryMode() throws Exception {
        testOrderAspect(builder -> {
                    builder.inventoryMode(null);
                },
                order -> assertThat(order.getInventoryMode()).isEqualTo(InventoryMode.NONE));

        withProductOfStock(client(), 5, product -> {
            final int variantId = 1;
            final String sku = product.getMasterData().getStaged().getMasterVariant().getSku();

            final String productId = product.getId();
            final LocalizedString name = en("a name");
            final long quantity = 1;
            final Price price = Price.of(EURO_10);
            final OrderState orderState = OrderState.COMPLETE;
            final MonetaryAmount amount = EURO_10;
            final TaxCalculationMode taxCalculationMode = TaxCalculationMode.LINE_ITEM_LEVEL;
            final ProductVariantImportDraft variant = ProductVariantImportDraftBuilder.of(productId, variantId, sku).build();
            final LineItemImportDraft lineItemImportDraft = LineItemImportDraftBuilder.of(variant, quantity, price, name).build();
            final OrderImportDraft orderImportDraft = OrderImportDraftBuilder.ofLineItems(amount, orderState, asList(lineItemImportDraft))
                    .inventoryMode(InventoryMode.TRACK_ONLY)
                    .taxCalculationMode(taxCalculationMode)
                    .build();
            final OrderImportCommand cmd = OrderImportCommand.of(orderImportDraft);

            final Order order = client().executeBlocking(cmd);
            assertThat(order.getInventoryMode()).isEqualTo(InventoryMode.TRACK_ONLY);
            assertThat(order.getTaxCalculationMode()).isEqualTo(taxCalculationMode);
            client().executeBlocking(OrderDeleteCommand.of(order));
        });
    }

    @Test
    public void customerGroup() throws Exception {
        withCustomerInGroup(client(), (customer, customerGroup) -> {
            final String customerId = customer.getId();
            testOrderAspect(builder -> builder.customerId(customerId).customerGroup(customerGroup),
                    order -> assertThat(order.getCustomerGroup()).isEqualTo(customerGroup.toReference()));
        });
    }

    @Test
    public void orderNumber() throws Exception {
        final String orderNumber = randomString();
        testOrderAspect(builder -> builder.orderNumber(orderNumber),
                order -> assertThat(order.getOrderNumber()).contains(orderNumber));
    }

    @Test
    public void billingAddress() throws Exception {
        final Address billingAddress = AddressBuilder.of(CountryCode.DE).city(randomString()).build();
        testOrderAspect(builder -> builder.billingAddress(billingAddress),
                order -> assertThat(order.getBillingAddress()).isEqualTo(billingAddress));
    }

    @Test
    public void originCustomer() throws Exception {
        CartOrigin origin = CartOrigin.CUSTOMER;
        testOrderAspect(orderImportDraftBuilder -> orderImportDraftBuilder.origin(origin),
                order -> assertThat(order.getOrigin()).isEqualTo(origin)
        );
    }

    @Test
    public void originMerchant() throws Exception {
        CartOrigin origin = CartOrigin.MERCHANT;
        testOrderAspect(orderImportDraftBuilder -> orderImportDraftBuilder.origin(origin),
                order -> assertThat(order.getOrigin()).isEqualTo(origin)
        );
    }


    @Test
    public void getCompletedAt() throws Exception {
        final ZonedDateTime completedAt = SphereTestUtils.now().minusSeconds(5555);
        testOrderAspect(builder -> builder.completedAt(completedAt),
                order -> assertThat(order.getCompletedAt()).isEqualTo(completedAt));
    }

    private void testOrderAspect(final Consumer<OrderImportDraftBuilder> orderBuilderConsumer, final Consumer<Order> orderConsumer) {
        withProduct(client(), product -> {
            final String productId = product.getId();
            final int variantId = 1;
            final LocalizedString name = en("a name");
            final long quantity = 1;
            final Price price = Price.of(EURO_10);
            final OrderState orderState = OrderState.OPEN;
            final MonetaryAmount amount = EURO_10;
            final ProductVariantImportDraft variant = ProductVariantImportDraftBuilder.of(productId, variantId).build();

            final LineItemImportDraft lineItemImportDraft = LineItemImportDraftBuilder.of(variant, quantity, price, name).build();
            final OrderImportDraftBuilder orderImportDraftBuilder = OrderImportDraftBuilder.ofLineItems(amount, orderState, asList(lineItemImportDraft));
            orderBuilderConsumer.accept(orderImportDraftBuilder);
            final OrderImportDraft orderImportDraft = orderImportDraftBuilder.build();
            final OrderImportCommand cmd = OrderImportCommand.of(orderImportDraft);

            final Order order = client().executeBlocking(cmd);
            orderConsumer.accept(order);
            client().executeBlocking(OrderDeleteCommand.of(order));
        });
    }

    @Test
    public void createByJson() {
        withCustomer(client(), customer -> {
            withTaxedProduct(client(), product -> {
                final JsonNodeReferenceResolver referenceResolver = new JsonNodeReferenceResolver();
                referenceResolver.addResourceByKey("a-customer", customer);
                final String productId = product.getId();
                referenceResolver.addResourceByKey("a-product", productId);
                final Reference<TaxCategory> taxCategoryReference = product.getTaxCategory();
                referenceResolver.addResourceByKey("standard-tax", taxCategoryReference);
                final OrderImportDraft orderImportDraft = draftFromJsonResource("drafts-tests/order.json", OrderImportDraft.class, referenceResolver);

                final Order order = client().executeBlocking(OrderImportCommand.of(orderImportDraft));

                assertThat(order.getCustomerId()).isEqualTo(customer.getId());
                final LineItem lineItem = order.getLineItems().get(0);
                assertThat(lineItem.getProductId()).isEqualTo(productId);
                assertThat(lineItem.getVariant().getId()).isEqualTo(1);
                assertThat(lineItem.getVariant().getPrices().get(0).getValue()).isEqualTo(EURO_20);
                assertThat(lineItem.getVariant().getAttribute("foo").getValue(AttributeAccess.ofString())).isEqualTo("bar");
                assertThat(lineItem.getTaxRate().getName()).isEqualTo("default-tax");
                assertThat(lineItem.getTaxRate().isIncludedInPrice()).isTrue();
                assertThat(lineItem.getTaxRate().getCountry()).isEqualTo(CountryCode.DE);
                final CustomLineItem customLineItem = order.getCustomLineItems().get(0);
                assertThat(customLineItem.getName().get(ENGLISH)).isEqualTo("a custom line item");
                assertThat(customLineItem.getQuantity()).isEqualTo(3);
                assertThat(customLineItem.getMoney()).isEqualTo(EURO_20);
                assertThat(customLineItem.getSlug()).isEqualTo("foo");
                assertThat(customLineItem.getTaxCategory()).isEqualTo(taxCategoryReference);
                assertThat(order.getTotalPrice()).isEqualTo(EURO_20);
                assertThat(order.getTaxedPrice().getTotalGross()).isEqualTo(EURO_20);
                assertThat(order.getShippingAddress().getLastName()).isEqualTo("Osgood");
                assertThat(order.getOrderState()).isEqualTo(OrderState.COMPLETE);

                client().executeBlocking(OrderDeleteCommand.of(order));
            });
        });
    }

    @Test
    public void minimalOrderInStore() {
        withStore(client(), store -> {
            withProduct(client(), product -> {
                final String productId = product.getId();
                final int variantId = 1;
                final LocalizedString name = en("a name");
                final long quantity = 1;
                final Price price = Price.of(EURO_10);
                final OrderState orderState = OrderState.OPEN;
                final MonetaryAmount amount = EURO_10;
                final ProductVariantImportDraft variant = ProductVariantImportDraftBuilder.of(productId, variantId).build();
                final KeyReference<Store> storeKeyReference = KeyReference.of(store.getKey(), "store");

                final LineItemImportDraft lineItemImportDraft = LineItemImportDraftBuilder.of(variant, quantity, price, name).build();
                final OrderImportDraft orderImportDraft = OrderImportDraftBuilder.ofLineItems(amount, orderState, asList(lineItemImportDraft))
                        .country(DE).store(storeKeyReference).build();
                final OrderImportCommand cmd = OrderImportCommand.of(orderImportDraft);

                final Order order = client().executeBlocking(cmd);

                assertThat(order.getOrderState()).isEqualTo(orderState);
                assertThat(order.getTotalPrice()).isEqualTo(amount);
                assertThat(order.getLineItems()).hasSize(1);
                assertThat(order.getCountry()).isEqualTo(DE);
                assertThat(order.getCart()).isNull();
                assertThat(order.getStore().getKey()).isEqualTo(storeKeyReference.getKey());
                final LineItem lineItem = order.getLineItems().get(0);
                assertThat(lineItem.getName()).isEqualTo(name);
                assertThat(lineItem.getProductId()).isEqualTo(productId);
                assertThat(lineItem.getVariant().getId()).isEqualTo(variantId);
                assertThat(lineItem.getName()).isEqualTo(name);
                assertThat(lineItem.getQuantity()).isEqualTo(quantity);

                client().executeBlocking(OrderDeleteCommand.of(order));
            });
        });
    }

    @Test
    public void customLineItemCustomTyppe() throws Exception {
        withUpdateableType(client(), type -> {
            withTransientTaxCategory(client(), taxCategory -> withProduct(client(), product -> {
                final LocalizedString name = randomSlug();
                final long quantity = 16;
                final MonetaryAmount money = EURO_20;
                final Reference<TaxCategory> taxCategoryReference = defaultTaxCategory(client()).toReference();
                final String id = "an id";
                final String slug = "a-slug";
                final TaxRate taxRate = taxCategory.getTaxRates().get(0);
                final String value = "foo";
                final CustomLineItemImportDraft customLineItem = CustomLineItemImportDraftBuilder.of(name, quantity, money, taxCategoryReference, CustomFieldsDraft.ofTypeIdAndObjects(type.getId(), singletonMap(STRING_FIELD_NAME, value)))
                        .id(id)
                        .slug(slug)
                        .taxRate(taxRate)
                        .build();
                final List<CustomLineItemImportDraft> customLineItems = asList(customLineItem);
                testOrderAspect(
                        builder -> builder.customLineItems(customLineItems),
                        order -> {
                            assertThat(order.getCustomLineItems()).hasSize(1);
                            final CustomLineItem actual = order.getCustomLineItems().get(0);
                            assertThat(actual.getMoney()).isEqualTo(money);
                            assertThat(actual.getQuantity()).isEqualTo(quantity);
                            assertThat(actual.getName()).isEqualTo(name);
                            assertThat(actual.getSlug()).isEqualTo(slug);
                            assertThat(actual.getTaxCategory()).isEqualTo(taxCategoryReference);
                            assertThat(actual.getTaxRate()).isEqualTo(taxRate);
                            assertThat(actual.getCustom().getFieldAsString(STRING_FIELD_NAME)).isEqualTo(value);
                        }
                );
            }));
            return type;
        });
    }

    @Test
    public void itemShippingDetails() throws Exception {
            withTransientTaxCategory(client(), taxCategory -> withProduct(client(), product -> {
                final LocalizedString name = randomSlug();
                final long quantity = 16;
                final MonetaryAmount money = EURO_20;
                final Reference<TaxCategory> taxCategoryReference = defaultTaxCategory(client()).toReference();
                final String id = "an id";
                final String slug = "a-slug";
                final TaxRate taxRate = taxCategory.getTaxRates().get(0);
                final String value = "foo";

                final List<Address> itemShippingAddresses = createAddressArray();
                final String addressKey1 = itemShippingAddresses.get(0).getKey();
                final String addressKey2 = itemShippingAddresses.get(1).getKey();

                final ItemShippingDetailsDraft itemShippingDetailsDraft = ItemShippingDetailsDraftBuilder.of(Arrays.asList(ItemShippingTargetBuilder.of(addressKey1, 2L).build(),ItemShippingTargetBuilder.of(addressKey2, 14L).build())).build();

                final CustomLineItemImportDraft customLineItem = CustomLineItemImportDraftBuilder.of(name, quantity, money, taxCategoryReference, itemShippingDetailsDraft)
                        .id(id)
                        .slug(slug)
                        .taxRate(taxRate)
                        .build();
                final List<CustomLineItemImportDraft> customLineItems = asList(customLineItem);
                testOrderAspect(
                        builder -> builder.itemShippingAddresses(itemShippingAddresses).customLineItems(customLineItems),
                        order -> {
                            assertThat(order.getCustomLineItems()).hasSize(1);
                            final CustomLineItem actual = order.getCustomLineItems().get(0);
                            assertThat(actual.getMoney()).isEqualTo(money);
                            assertThat(actual.getQuantity()).isEqualTo(quantity);
                            assertThat(actual.getName()).isEqualTo(name);
                            assertThat(actual.getSlug()).isEqualTo(slug);
                            assertThat(actual.getTaxCategory()).isEqualTo(taxCategoryReference);
                            assertThat(actual.getTaxRate()).isEqualTo(taxRate);
                            assertThat(actual.getShippingDetails().getTargetsMap().get(addressKey1)).isEqualTo(2);
                            assertThat(actual.getShippingDetails().getTargetsMap().get(addressKey2)).isEqualTo(14);
                        }
                );
            }));
    }

    public final static List<Address> createAddressArray() {
        final Address address1 = Address.of(CountryCode.DE).withKey(SphereTestUtils.randomKey());
        final Address address2 = Address.of(CountryCode.FR).withKey(SphereTestUtils.randomKey());
        final Address address3 = Address.of(CountryCode.MA).withKey(SphereTestUtils.randomKey());
        final Address address4 = Address.of(CountryCode.IT).withKey(SphereTestUtils.randomKey());
        return Arrays.asList(address1, address2, address3, address4);
    }

    public void assertEqualPrice(final Price price,final PriceDraft priceDraft){
        assertThat(priceDraft).isEqualTo(PriceDraft.of(price));
    }

    public void assertEqualPrices(final List<Price> price,final List<PriceDraft> priceDraft){

        final List<PriceDraft> transformedPriceDrafts = price.stream().map(PriceDraft::of).collect(Collectors.toList());
        assertThat(transformedPriceDrafts).isEqualTo(priceDraft);

    }
}
