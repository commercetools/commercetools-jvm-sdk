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
    public void execute() {
        withOrder(client(), order -> {
            final Reference<Order> orderReference = order.toReference();
            final List<StagedUpdateAction<OrderEdit>> stagedActions = new ArrayList<>();
            final SetCustomerEmail setCustomerEmailStagedAction = SetCustomerEmail.of("john.doe@commercetools.de");
            stagedActions.add(setCustomerEmailStagedAction);
            final OrderEditDraft orderEditDraft = OrderEditDraftBuilder.of(orderReference, stagedActions).key(SphereTestUtils.randomKey()).build();
            final OrderEditCreateCommand orderEditCreateCommand = OrderEditCreateCommand.of(orderEditDraft);
            final OrderEdit orderEdit = client().executeBlocking(orderEditCreateCommand);
            assertThat(orderEdit).isNotNull();
            assertThat(orderEdit.getResult()).isNotNull();

            final OrderEditDeleteCommand orderEditDeleteCommand = OrderEditDeleteCommand.of(orderEdit);
            client().executeBlocking(orderEditDeleteCommand);

            final OrderEditByIdGet orderEditByIdGet = OrderEditByIdGet.of(orderEdit.getId());
            final OrderEdit deletedOrderEdit = client().executeBlocking(orderEditByIdGet);
            assertThat(deletedOrderEdit).isNull();

            //create new order edit and delete it by key
            final OrderEdit orderEdit2 = client().executeBlocking(orderEditCreateCommand);
            assertThat(orderEdit2).isNotNull();
            final OrderEditDeleteCommand orderEditDeleteByKeyCommand = OrderEditDeleteCommand.ofKey(orderEdit2.getKey(), orderEdit2.getVersion());
            client().executeBlocking(orderEditDeleteByKeyCommand);

            final OrderEditByKeyGet orderEditByKeyGet = OrderEditByKeyGet.of(orderEdit2.getKey());
            final OrderEdit deletedOrderEdit2 = client().executeBlocking(orderEditByKeyGet);
            assertThat(deletedOrderEdit2).isNull();

            return order;
        });
    }
}