package io.sphere.sdk.orderedit.commands.stagedactions;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.orderedit.OrderEditFixtures;
import io.sphere.sdk.orderedit.commands.OrderEditUpdateCommandIntegrationTest;
import io.sphere.sdk.orderedits.OrderEdit;
import io.sphere.sdk.orderedits.commands.OrderEditUpdateCommand;
import io.sphere.sdk.orderedits.commands.stagedactions.*;
import io.sphere.sdk.orderedits.commands.updateactions.AddStagedAction;
import io.sphere.sdk.shippingmethods.ShippingMethodFixtures;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.test.SphereTestUtils;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.Locale;


public class OrderEditStagedActionsIntegrationTest extends IntegrationTest {

    @Test
    public void setCustomerEmail() {
        final String newCustomerEmail = SphereTestUtils.randomEmail(OrderEditUpdateCommandIntegrationTest.class);
        final SetCustomerEmail setCustomerEmail = SetCustomerEmail.of(newCustomerEmail);
        testOrderEditStagedUpdateAction(setCustomerEmail);
    }

    @Test
    public void setShippingAddress() {
        final Address address = Address.of(CountryCode.DE);
        final SetShippingAddress setShippingAddress = SetShippingAddress.of(address);
        testOrderEditStagedUpdateAction(setShippingAddress);
    }

    @Test
    public void setBillingAddress(){
        final Address address = Address.of(CountryCode.DE);
        final SetBillingAddress setBillingAddress = SetBillingAddress.of(address);
        testOrderEditStagedUpdateAction(setBillingAddress);
    }

    @Test
    public void setLocale() {
        final SetLocale setLocale = SetLocale.of(Locale.GERMAN);
        testOrderEditStagedUpdateAction(setLocale);
    }

    @Test
    public void setCountry() {
        final SetCountry setCountry = SetCountry.of(SphereTestUtils.DE);
        testOrderEditStagedUpdateAction(setCountry);
    }

    @Test
    public void setShippingMethod() {
        ShippingMethodFixtures.withShippingMethodForGermany(client(), shippingMethod -> {
            SetShippingMethod setShippingMethod = SetShippingMethod.of(shippingMethod.toResourceIdentifier(), null);
            testOrderEditStagedUpdateAction(setShippingMethod);
        });
    }

    private void testOrderEditStagedUpdateAction(final OrderEditStagedUpdateAction orderEditStagedUpdateAction) {
        OrderEditFixtures.withUpdateableOrderEdit(client(), orderEdit -> {

            final OrderEditUpdateCommand orderEditUpdateCommand = OrderEditUpdateCommand.of(orderEdit, AddStagedAction.of(orderEditStagedUpdateAction));
            final OrderEdit updatedOrderEdit = client().executeBlocking(orderEditUpdateCommand);
            Assertions.assertThat(updatedOrderEdit).isNotNull();
            Assertions.assertThat(updatedOrderEdit.getStagedActions().get(0).getAction()).isEqualTo(orderEditStagedUpdateAction.getAction());

            Assertions.assertThat(updatedOrderEdit.getStagedActions().get(0)).isInstanceOf(orderEditStagedUpdateAction.getClass());

            return updatedOrderEdit;
        });
    }
}