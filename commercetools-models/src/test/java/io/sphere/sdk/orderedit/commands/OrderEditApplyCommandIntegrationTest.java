package io.sphere.sdk.orderedit.commands;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.orderedit.OrderEditFixtures;
import io.sphere.sdk.orderedits.OrderEdit;
import io.sphere.sdk.orderedits.commands.OrderEditApplyCommand;
import io.sphere.sdk.orderedits.commands.OrderEditUpdateCommand;
import io.sphere.sdk.orderedits.commands.stagedactions.SetBillingAddress;
import io.sphere.sdk.orderedits.commands.updateactions.AddStagedAction;
import io.sphere.sdk.orderedits.expansion.OrderEditExpansionModel;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.queries.OrderByIdGet;
import io.sphere.sdk.test.IntegrationTest;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.function.Function;

public class OrderEditApplyCommandIntegrationTest extends IntegrationTest {

    @Test
    public void applyOrderEdit() {
        final Function<OrderEdit, OrderEditApplyCommand> function = (OrderEdit orderEdit) -> OrderEditApplyCommand.of(orderEdit.getId(), orderEdit.getVersion(), orderEdit.getResource().getObj().getVersion());
        apply(function);
    }

    @Test
    public void applyOrderEditUsingVersionedOrderEdit() {
        final Function<OrderEdit, OrderEditApplyCommand> function = (OrderEdit orderEdit) -> OrderEditApplyCommand.of(orderEdit, orderEdit.getResource().getObj().getVersion());
        apply(function);
    }

    private void apply(final Function<OrderEdit, OrderEditApplyCommand> createOrderEditApplyCommandFunction) {
        OrderEditFixtures.withUpdateableOrderEdit(client(), orderEdit -> {

            boolean orderBillingAddressNotDE = orderEdit.getResource().getObj().getBillingAddress() == null ||
                    orderEdit.getResource().getObj().getBillingAddress().getCountry() == null ||
                    !orderEdit.getResource().getObj().getBillingAddress().getCountry().equals(CountryCode.DE);
            Assertions.assertThat(orderBillingAddressNotDE).isTrue();

            final Address address = Address.of(CountryCode.DE);
            final SetBillingAddress setBillingAddress = SetBillingAddress.of(address);
            final OrderEditUpdateCommand orderEditUpdateCommand = OrderEditUpdateCommand.of(orderEdit, AddStagedAction.of(setBillingAddress))
                    .withExpansionPaths(OrderEditExpansionModel::resource);
            final OrderEdit updatedOrderEdit = client().executeBlocking(orderEditUpdateCommand);
            Assertions.assertThat(updatedOrderEdit).isNotNull();

            final OrderEditApplyCommand orderEditApplyCommand = createOrderEditApplyCommandFunction.apply(updatedOrderEdit);
            final OrderEdit appliedOrderEdit = client().executeBlocking(orderEditApplyCommand);
            Assertions.assertThat(appliedOrderEdit).isNotNull();

            final OrderByIdGet orderByIdGet = OrderByIdGet.of(updatedOrderEdit.getResource().getObj().getId());
            final Order order = client().executeBlocking(orderByIdGet);
            Assertions.assertThat(order).isNotNull();
            Assertions.assertThat(order.getBillingAddress().getCountry()).isEqualTo(CountryCode.DE);

            return appliedOrderEdit;
        });
    }
}