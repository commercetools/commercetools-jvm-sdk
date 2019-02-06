package io.sphere.sdk.orderedit.commands;

import io.sphere.sdk.commands.StagedUpdateAction;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.orderedits.OrderEdit;
import io.sphere.sdk.orderedits.OrderEditDraft;
import io.sphere.sdk.orderedits.OrderEditDraftBuilder;
import io.sphere.sdk.orderedits.commands.OrderEditCreateCommand;
import io.sphere.sdk.orderedits.commands.OrderEditDeleteCommand;
import io.sphere.sdk.orderedits.commands.stagedactions.SetCustomerEmail;
import io.sphere.sdk.orderedits.queries.OrderEditByIdGet;
import io.sphere.sdk.orderedits.queries.OrderEditByKeyGet;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.test.SphereTestUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static io.sphere.sdk.orders.OrderFixtures.withOrder;
import static org.assertj.core.api.Assertions.assertThat;

public class OrderEditCreateCommandIntegrationTest extends IntegrationTest {

    @Test
    public void createAndDeleteOrderEditById() {
        withOrder(client(), order -> {
            final OrderEdit orderEdit = createOrderEdit(order);
            assertThat(orderEdit).isNotNull();
            assertThat(orderEdit.getResult()).isNotNull();

            final OrderEditDeleteCommand orderEditDeleteCommand = OrderEditDeleteCommand.of(orderEdit);
            client().executeBlocking(orderEditDeleteCommand);

            final OrderEditByIdGet orderEditByIdGet = OrderEditByIdGet.of(orderEdit.getId());
            final OrderEdit deletedOrderEdit = client().executeBlocking(orderEditByIdGet);
            assertThat(deletedOrderEdit).isNull();

            return order;
        });
    }

    @Test
    public void createAndDeleteOrderEditByKey() {
        withOrder(client(), order -> {
            final OrderEdit orderEdit = createOrderEdit(order);
            assertThat(orderEdit).isNotNull();
            final OrderEditDeleteCommand orderEditDeleteByKeyCommand = OrderEditDeleteCommand.ofKey(orderEdit.getKey(), orderEdit.getVersion());
            client().executeBlocking(orderEditDeleteByKeyCommand);

            final OrderEditByKeyGet orderEditByKeyGet = OrderEditByKeyGet.of(orderEdit.getKey());
            final OrderEdit deletedOrderEdit2 = client().executeBlocking(orderEditByKeyGet);
            assertThat(deletedOrderEdit2).isNull();

            return order;
        });
    }

    private OrderEdit createOrderEdit(final Order order) {
        final Reference<Order> orderReference = order.toReference();
        final List<StagedUpdateAction<OrderEdit>> stagedActions = new ArrayList<>();
        final SetCustomerEmail setCustomerEmailStagedAction = SetCustomerEmail.of("john.doe@commercetools.de");
        stagedActions.add(setCustomerEmailStagedAction);
        final OrderEditDraft orderEditDraft = OrderEditDraftBuilder.of(orderReference, stagedActions).key(SphereTestUtils.randomKey()).build();
        final OrderEditCreateCommand orderEditCreateCommand = OrderEditCreateCommand.of(orderEditDraft);
        return client().executeBlocking(orderEditCreateCommand);
    }

}