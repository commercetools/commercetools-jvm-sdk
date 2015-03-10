package io.sphere.sdk.orders.commands;

import io.sphere.sdk.attributes.Attribute;
import io.sphere.sdk.carts.*;
import io.sphere.sdk.channels.ChannelRoles;
import io.sphere.sdk.models.*;
import io.sphere.sdk.orders.*;
import io.sphere.sdk.products.Price;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.ProductVariant;
import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.shippingmethods.ShippingRate;
import io.sphere.sdk.suppliers.TShirtProductTypeDraftSupplier;
import io.sphere.sdk.taxcategories.TaxCategory;
import io.sphere.sdk.taxcategories.TaxRate;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import javax.money.MonetaryAmount;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import static io.sphere.sdk.channels.ChannelFixtures.withPersistentChannel;
import static io.sphere.sdk.customers.CustomerFixtures.withCustomer;
import static io.sphere.sdk.customers.CustomerFixtures.withCustomerInGroup;
import static io.sphere.sdk.products.ProductFixtures.PRICE;
import static io.sphere.sdk.products.ProductFixtures.withProduct;
import static io.sphere.sdk.shippingmethods.ShippingMethodFixtures.withShippingMethodForGermany;
import static io.sphere.sdk.taxcategories.TaxCategoryFixtures.*;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static org.fest.assertions.Assertions.assertThat;
import static io.sphere.sdk.test.OptionalAssert.assertThat;

public class OrderImportCommandTest extends IntegrationTest {
    @Test
    public void minimalOrder() throws Exception {
        withProduct(client(), product -> {
            final String productId = product.getId();
            final int variantId = 1;
            final LocalizedStrings name = en("a name");
            final int quantity = 1;
            final Price price = Price.of(EURO_10);
            final OrderState orderState = OrderState.OPEN;
            final MonetaryAmount amount = EURO_10;
            final ImportProductVariant variant = ImportProductVariantBuilder.of(productId, variantId).build();

            final ImportLineItem importLineItem = ImportLineItemBuilder.of(variant, quantity, price, name).build();
            final ImportOrder importOrder = ImportOrderBuilder.ofLineItems(amount, orderState, asList(importLineItem))
                    .country(DE).build();
            final OrderImportCommand cmd = OrderImportCommand.of(importOrder);

            final Order order = execute(cmd);

            assertThat(order.getOrderState()).isEqualTo(orderState);
            assertThat(order.getTotalPrice()).isEqualTo(amount);
            assertThat(order.getLineItems()).hasSize(1);
            assertThat(order.getCountry()).isPresentAs(DE);
            final LineItem lineItem = order.getLineItems().get(0);
            assertThat(lineItem.getName()).isEqualTo(name);
            assertThat(lineItem.getProductId()).isEqualTo(productId);
            assertThat(lineItem.getVariant().getId()).isEqualTo(variantId);
            assertThat(lineItem.getName()).isEqualTo(name);
            assertThat(lineItem.getQuantity()).isEqualTo(quantity);
            assertThat(lineItem.getPrice()).isEqualTo(price);
        });
    }

    @Test
    public void lineItems() throws Exception {
        withPersistentChannel(client(), ChannelRoles.INVENTORY_SUPPLY, channel -> {
                    withProduct(client(), product -> {
                        final int variantId = 1;
                        final String sku = sku(product);
                        final ImportProductVariant importProductVariant = ImportProductVariantBuilder.of(product.getId(), variantId, sku)
                                .build();
                        final Price price = PRICE;
                        final LocalizedStrings name = randomSlug();
                        final ImportLineItem importLineItem = ImportLineItemBuilder.of(importProductVariant, 2, price, name).supplyChannel(channel).build();
                        testOrderAspect(
                                builder -> builder.lineItems(asList(importLineItem)),
                                order -> {
                                    final LineItem lineItem = order.getLineItems().get(0);
                                    assertThat(lineItem.getProductId()).isEqualTo(product.getId());
                                    assertThat(lineItem.getVariant().getId()).isEqualTo(variantId);
                                    final ProductVariant masterVariant = product.getMasterData().getStaged().getMasterVariant();
                                    assertThat(lineItem.getVariant().getAttributes()).isEqualTo(masterVariant.getAttributes());
                                    assertThat(lineItem.getVariant().getImages()).isEqualTo(masterVariant.getImages());
                                    assertThat(lineItem.getVariant().getPrices()).isEqualTo(masterVariant.getPrices());
                                    assertThat(lineItem.getVariant().getSku()).isPresentAs(masterVariant.getSku().get());
                                    assertThat(lineItem.getQuantity()).isEqualTo(2);
                                    assertThat(lineItem.getPrice()).isEqualTo(price);
                                    assertThat(lineItem.getName()).isEqualTo(name);
                                }
                        );
                    });
                }
        );

    }

    @Test
    public void importBySku() throws Exception {
        checkImportForVariantIdSkuCombination(product ->
                ImportProductVariantBuilder.ofSku(sku(product)));
    }

    private String sku(final Product product) {
        return product.getMasterData().getStaged().getMasterVariant().getSku().get();
    }

    @Test
    public void importByVariantId() throws Exception {
        checkImportForVariantIdSkuCombination(product -> ImportProductVariantBuilder.of(product.getId(), 1));
    }

    private void checkImportForVariantIdSkuCombination(final Function<Product, ImportProductVariantBuilder> f) {
        withProduct(client(), product -> {
            final ImportProductVariant importProductVariant = f.apply(product)
                    .build();
            final Price price = PRICE;
            final LocalizedStrings name = randomSlug();
            final ImportLineItem importLineItem = ImportLineItemBuilder.of(importProductVariant, 2, price, name).build();
            testOrderAspect(
                    builder -> builder.lineItems(asList(importLineItem)),
                    order -> {
                        final LineItem lineItem = order.getLineItems().get(0);
                        assertThat(lineItem.getProductId()).isEqualTo(product.getId());
                        assertThat(lineItem.getVariant().getId()).isEqualTo(1);
                        assertThat(lineItem.getVariant().getSku()).isPresentAs(sku(product));
                    }
            );
        });
    }

    @Test
    public void customLineItems() throws Exception {
        withTransientTaxCategory(client(), taxCategory -> withProduct(client(), product -> {
            final LocalizedStrings name = randomSlug();
            final int quantity = 16;
            final MonetaryAmount money = EURO_20;
            final Reference<TaxCategory> taxCategoryReference = defaultTaxCategory(client()).toReference();
            final String id = "an id";
            final String slug = "a-slug";
            final TaxRate taxRate = taxCategory.getTaxRates().get(0);
            final ImportCustomLineItem customLineItem = ImportCustomLineItemBuilder.of(name, quantity, money, taxCategoryReference)
                    .id(id)
                    .slug(slug)
                    .taxRate(taxRate)
                    .build();
            final List<ImportCustomLineItem> customLineItems = asList(customLineItem);
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
                        assertThat(actual.getTaxRate()).isPresentAs(taxRate);
                    }
            );
        }));
    }

    @Test
    public void orderImportCanOverrideVariantDataInTheOrder() throws Exception {
        final Attribute size = TShirtProductTypeDraftSupplier.Sizes.ATTRIBUTE.valueOf(TShirtProductTypeDraftSupplier.Sizes.S);
        final Attribute color = TShirtProductTypeDraftSupplier.Colors.ATTRIBUTE.valueOf(TShirtProductTypeDraftSupplier.Colors.RED);
        final List<Attribute> attributesOfOrder = asList(size, color);
        final List<Image> images = asList(Image.of("url", ImageDimensions.of(1, 2), "label"));
        final List<Price> prices = asList(Price.of(new BigDecimal("15.23"), DefaultCurrencyUnits.EUR));

        withProduct(client(), product -> {
            final int variantId = 1;
            final ImportProductVariant importProductVariant = ImportProductVariantBuilder.of(product.getId(), variantId, sku(product))
                    .attributes(attributesOfOrder)
                    .images(images)
                    .prices(prices)
                    .build();
            final Price price = PRICE;
            final LocalizedStrings name = randomSlug();
            final ImportLineItem importLineItem = ImportLineItemBuilder.of(importProductVariant, 2, price, name).build();
            testOrderAspect(
                    builder -> builder.lineItems(asList(importLineItem)),
                    order -> {
                        final LineItem lineItem = order.getLineItems().get(0);
                        final ProductVariant masterVariant = product.getMasterData().getStaged().getMasterVariant();
                        final ProductVariant productVariant = lineItem.getVariant();
                        assertThat(productVariant.getAttributes()).isEqualTo(attributesOfOrder).isNotEqualTo(masterVariant.getAttributes());
                        assertThat(productVariant.getImages()).isEqualTo(images).isNotEqualTo(masterVariant.getImages());
                        assertThat(productVariant.getPrices()).isEqualTo(prices).isNotEqualTo(masterVariant.getPrices());
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
                final ShippingRate shippingRate = ShippingRate.of(price, freeAbove);
                final TaxRate taxRate = taxCategory.getTaxRates().get(0);
                final Reference<TaxCategory> taxCategoryRef = taxCategory.toReference();
                final Optional<Reference<ShippingMethod>> shippingMethodRef = Optional.of(shippingMethod.toReference());
                final Instant createdAt = Instant.now().minusSeconds(4);
                final ParcelMeasurements parcelMeasurements = ParcelMeasurements.of(2, 3, 1, 3);
                final DeliveryItem deliveryItem = DeliveryItem.of(new LineItemLike() {
                    private final String id = randomKey();

                    @Override
                    public String getId() {
                        return id;
                    }

                    @Override
                    public long getQuantity() {
                        return 2;
                    }
                }, 5);
                final String deliveryId = randomKey();
                final TrackingData trackingData = TrackingData.of().withTrackingId("tracking id")
                        .withCarrier("carrier").withProvider("provider").withProviderTransaction("prov transaction").withIsReturn(true);
                final Parcel parcel = Parcel.of(randomKey(), createdAt, Optional.of(parcelMeasurements), Optional.of(trackingData));
                final List<Delivery> deliveries = asList(Delivery.of(deliveryId, createdAt, asList(deliveryItem), asList(parcel)));
                final OrderShippingInfo shippingInfo = OrderShippingInfo.of(randomString(), price, shippingRate, taxRate, taxCategoryRef, shippingMethodRef, deliveries);
                testOrderAspect(
                        builder -> builder.shippingInfo(shippingInfo),
                        order -> assertThat(order.getShippingInfo()).isPresentAs(shippingInfo)
                );
            });
        });
    }

    @Test
    public void paymentState() throws Exception {
        final PaymentState paymentState = PaymentState.FAILED;
        testOrderAspect(
                builder -> builder.paymentState(paymentState),
                order -> assertThat(order.getPaymentState()).isPresentAs(paymentState)
        );
    }

    @Test
    public void shipmentState() throws Exception {
        final ShipmentState shipmentState = ShipmentState.SHIPPED;
        testOrderAspect(
                builder -> builder.shipmentState(shipmentState),
                order -> assertThat(order.getShipmentState()).isPresentAs(shipmentState)
        );
    }

    @Test
    public void shippingAddress() throws Exception {
        final Address address = randomAddress().withApartment(randomKey());
        testOrderAspect(
                builder -> builder.shippingAddress(address),
                order -> assertThat(order.getShippingAddress()).isPresentAs(address)
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
                order -> assertThat(order.getTaxedPrice()).isPresentAs(taxedPrice)
        );
    }

    @Test
    public void customerEmail() throws Exception {
        withCustomer(client(), customer ->
                testOrderAspect(
                        builder -> builder.customerEmail(customer.getEmail()),
                        order -> assertThat(order.getCustomerEmail()).isPresentAs(customer.getEmail())
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
                    order -> assertThat(order.getCustomerId()).isPresentAs(customerId));
        });
    }

    @Test
    public void customerGroup() throws Exception {
        withCustomerInGroup(client(), (customer, customerGroup) -> {
            final String customerId = customer.getId();
            testOrderAspect(builder -> builder.customerId(customerId).customerGroup(customerGroup),
                    order -> assertThat(order.getCustomerGroup()).isPresentAs(customerGroup.toReference()));
        });
    }

    @Test
    public void orderNumber() throws Exception {
        final String orderNumber = randomString();
        testOrderAspect(builder -> builder.orderNumber(orderNumber),
                order -> assertThat(order.getOrderNumber()).isPresentAs(orderNumber));
    }

    @Test
    public void billingAddress() throws Exception {
        final Address billingAddress = randomAddress();
        testOrderAspect(builder -> builder.billingAddress(billingAddress),
                order -> assertThat(order.getBillingAddress()).isPresentAs(billingAddress));
    }

    @Test
    public void getCompletedAt() throws Exception {
        final Instant completedAt = Instant.now().minusSeconds(5555);
        testOrderAspect(builder -> builder.completedAt(completedAt),
                order -> assertThat(order.getCompletedAt()).isPresentAs(completedAt));
    }

    private void testOrderAspect(final Consumer<ImportOrderBuilder> orderBuilderConsumer, final Consumer<Order> orderConsumer) {
        withProduct(client(), product -> {
            final String productId = product.getId();
            final int variantId = 1;
            final LocalizedStrings name = en("a name");
            final int quantity = 1;
            final Price price = Price.of(EURO_10);
            final OrderState orderState = OrderState.OPEN;
            final MonetaryAmount amount = EURO_10;
            final ImportProductVariant variant = ImportProductVariantBuilder.of(productId, variantId).build();

            final ImportLineItem importLineItem = ImportLineItemBuilder.of(variant, quantity, price, name).build();
            final ImportOrderBuilder importOrderBuilder = ImportOrderBuilder.ofLineItems(amount, orderState, asList(importLineItem));
            orderBuilderConsumer.accept(importOrderBuilder);
            final ImportOrder importOrder = importOrderBuilder.build();
            final OrderImportCommand cmd = OrderImportCommand.of(importOrder);

            final Order order = execute(cmd);
            orderConsumer.accept(order);
        });
    }
}