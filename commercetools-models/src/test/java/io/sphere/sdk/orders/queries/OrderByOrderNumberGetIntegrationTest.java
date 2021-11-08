package io.sphere.sdk.orders.queries;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.carts.*;
import io.sphere.sdk.carts.commands.CartCreateCommand;
import io.sphere.sdk.carts.commands.CartUpdateCommand;
import io.sphere.sdk.carts.commands.updateactions.*;
import io.sphere.sdk.customers.CustomerFixtures;
import io.sphere.sdk.customers.CustomerSignInResult;
import io.sphere.sdk.customers.commands.CustomerSignInCommand;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.models.AddressBuilder;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.PaymentState;
import io.sphere.sdk.orders.ShipmentState;
import io.sphere.sdk.orders.commands.OrderFromCartCreateCommand;
import io.sphere.sdk.orders.commands.OrderUpdateCommand;
import io.sphere.sdk.orders.commands.updateactions.ChangePaymentState;
import io.sphere.sdk.orders.commands.updateactions.ChangeShipmentState;
import io.sphere.sdk.orders.commands.updateactions.SetOrderNumber;
import io.sphere.sdk.products.*;
import io.sphere.sdk.products.commands.ProductCreateCommand;
import io.sphere.sdk.products.commands.ProductDeleteCommand;
import io.sphere.sdk.products.commands.ProductUpdateCommand;
import io.sphere.sdk.products.commands.updateactions.*;
import io.sphere.sdk.projects.CartScoreDraftBuilder;
import io.sphere.sdk.projects.Project;
import io.sphere.sdk.projects.commands.ProjectUpdateCommand;
import io.sphere.sdk.projects.commands.updateactions.SetShippingRateInputType;
import io.sphere.sdk.projects.queries.ProjectGet;
import io.sphere.sdk.shippingmethods.*;
import io.sphere.sdk.shippingmethods.commands.ShippingMethodUpdateCommand;
import io.sphere.sdk.shippingmethods.commands.updateactions.AddShippingRate;
import io.sphere.sdk.shippingmethods.commands.updateactions.AddZone;
import io.sphere.sdk.taxcategories.TaxCategory;
import io.sphere.sdk.taxcategories.TaxCategoryFixtures;
import io.sphere.sdk.taxcategories.queries.TaxCategoryByIdGet;
import io.sphere.sdk.taxcategories.queries.TaxCategoryQuery;
import io.sphere.sdk.taxcategories.queries.TaxCategoryQueryBuilder;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.test.SphereTestUtils;
import io.sphere.sdk.utils.MoneyImpl;
import io.sphere.sdk.zones.ZoneFixtures;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static io.sphere.sdk.carts.CartFixtures.*;
import static io.sphere.sdk.customers.CustomerFixtures.withCustomer;
import static io.sphere.sdk.models.DefaultCurrencyUnits.EUR;
import static io.sphere.sdk.orders.OrderFixtures.withOrder;
import static io.sphere.sdk.products.ProductFixtures.withTaxedProduct;
import static io.sphere.sdk.producttypes.ProductTypeFixtures.withEmptyProductType;
import static io.sphere.sdk.shippingmethods.ShippingMethodFixtures.withShippingMethodForGermany;
import static io.sphere.sdk.shippingmethods.ShippingMethodFixtures.withUpdateableShippingMethod;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static io.sphere.sdk.types.TypeFixtures.withUpdateableType;
import static io.sphere.sdk.zones.ZoneFixtures.withZone;
import static org.assertj.core.api.Assertions.assertThat;


public class OrderByOrderNumberGetIntegrationTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        withOrder(client(), order -> {
            final String orderNumber = randomString();
            final Order orderWithOrderNumber = client().executeBlocking(OrderUpdateCommand.of(order,
                    SetOrderNumber.of(orderNumber)));
            final Order loadedOrder = client().executeBlocking(OrderByOrderNumberGet.of(orderNumber));

            assertThat(loadedOrder.getOrderNumber()).isEqualTo(orderNumber);
            return orderWithOrderNumber;
        });
    }

    @Test
    public void createOrderWithShippingMethodWithTiersInShippingRate() {
        final Project project = client().executeBlocking(ProjectGet.of());
        final Project updatedProjectCartScore = client().executeBlocking(ProjectUpdateCommand.of(project,
                SetShippingRateInputType.of(CartScoreDraftBuilder.of().build())));
        assertThat(updatedProjectCartScore.getShippingRateInputType().getType()).isEqualTo("CartScore");

        withEmptyProductType(client(), randomKey(), productType -> {
            withUpdateableType(client(), type -> {
                withZone(client(), zone -> {
                    withUpdateableShippingMethod(client(), shippingMethod -> {
                        withTaxedProduct(client(), product -> {

                            //addShippingRate
                            final ShippingRate shippingRate = ShippingRate.of(MoneyImpl.of(30, EUR), null, Arrays.asList(
                                    io.sphere.sdk.shippingmethods.CartScoreBuilder.of(0L, PriceFunctionBuilder.of(SphereTestUtils.EUR.getCurrencyCode(), "(50 * x) + 4000").build()).build(),
                                    io.sphere.sdk.shippingmethods.CartScoreBuilder.of(1L, EURO_20).build(),
                                    io.sphere.sdk.shippingmethods.CartScoreBuilder.of(2L, EURO_30).build()
                            ));

                            final CartDraft draft = CartDraft.of(SphereTestUtils.EUR)
                                    .withCountry(CountryCode.DE)
                                    .withTaxMode(TaxMode.PLATFORM)
                                    .withShippingAddress(Address.of(DE));
                            withCustomer(client(), customer -> {
                                withCartDraft(client(), draft, (Cart cart) -> {

                                    assertThat(cart.getLineItems()).hasSize(0);
                                    assertThat(cart.getShippingAddress()).isNotNull();
                                    final TaxCategory taxCategory = TaxCategoryFixtures.defaultTaxCategory(client());
                                    final Cart updatedCartWithShippingMethod = client().executeBlocking(CartUpdateCommand.of(cart, SetCustomShippingMethod.of(shippingMethod.getName(), shippingRate, taxCategory)));
                                    final long quantity = 3;
                                    final String productId = product.getId();

                                    final Cart updatedCart = client().executeBlocking(CartUpdateCommand.of(updatedCartWithShippingMethod, AddLineItem.of(productId, 1, quantity)));
                                    assertThat(updatedCart.getLineItems()).hasSize(1);
                                    final LineItem lineItem = updatedCart.getLineItems().get(0);
                                    assertThat(lineItem.getName()).isEqualTo(product.getMasterData().getStaged().getName());
                                    assertThat(lineItem.getQuantity()).isEqualTo(quantity);

                                    final SetCustomerEmail emailAction = SetCustomerEmail.of(customer.getEmail());
                                    final Cart cartWithEmail = client().executeBlocking(CartUpdateCommand.of(updatedCart, emailAction));

                                    final CustomerSignInCommand signInCommand = CustomerSignInCommand.of(customer.getEmail(), CustomerFixtures.PASSWORD, cart.getId());
                                    final CustomerSignInResult signInResult = client().executeBlocking(signInCommand);

                                    final Order order = client().executeBlocking(OrderFromCartCreateCommand.of(signInResult.getCart()));
                                    final Order orderState = client().executeBlocking(OrderUpdateCommand.of(order, asList(
                                            ChangeShipmentState.of(ShipmentState.READY),
                                            ChangePaymentState.of(PaymentState.PENDING)
                                    )));
                                    final String orderNumber = randomString();
                                    final Order orderWithOrderNumber = client().executeBlocking(OrderUpdateCommand.of(orderState,
                                            SetOrderNumber.of(orderNumber)));

                                    List<ShippingRatePriceTier> listTiers = orderWithOrderNumber.getShippingInfo().getShippingRate().getTiers();
                                    String bla = SphereJsonUtils.toJsonString(orderWithOrderNumber);

                                    assertThat(orderWithOrderNumber.getOrderNumber()).isEqualTo(orderNumber);

                                    return cartWithEmail;
                                });

                            });
                        });
                    });
                }, CountryCode.DE);
                return type;
            });

        });
    }
}