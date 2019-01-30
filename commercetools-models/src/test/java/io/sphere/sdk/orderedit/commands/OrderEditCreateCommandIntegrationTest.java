package io.sphere.sdk.orderedit.commands;

import io.sphere.sdk.commands.StagedUpdateAction;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.orderedits.OrderEdit;
import io.sphere.sdk.orderedits.OrderEditDraft;
import io.sphere.sdk.orderedits.OrderEditDraftBuilder;
import io.sphere.sdk.orderedits.commands.OrderEditCreateCommand;
import io.sphere.sdk.orderedits.commands.stagedactions.SetCustomerEmail;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static io.sphere.sdk.orders.OrderFixtures.withOrder;
import static org.assertj.core.api.Assertions.assertThat;

public class OrderEditCreateCommandIntegrationTest extends IntegrationTest {

    @Test
    public void execute(){
        withOrder(client(), order -> {
            Reference<Order> orderReference = order.toReference();
            List<StagedUpdateAction<OrderEdit>> stagedActions = new ArrayList<>();
            SetCustomerEmail setCustomerEmailStagedAction = SetCustomerEmail.of("john.doe@commercetools.de");
            stagedActions.add(setCustomerEmailStagedAction);
            OrderEditDraft orderEditDraft = OrderEditDraftBuilder.of(orderReference, stagedActions).build();
            OrderEditCreateCommand orderEditCreateCommand = OrderEditCreateCommand.of(orderEditDraft);
            OrderEdit orderEdit = client().executeBlocking(orderEditCreateCommand);
            assertThat(orderEdit).isNotNull();
            assertThat(orderEdit.getResult()).isNotNull();
            System.out.println(orderEdit.toString());
            return order;
        });
    }

}
