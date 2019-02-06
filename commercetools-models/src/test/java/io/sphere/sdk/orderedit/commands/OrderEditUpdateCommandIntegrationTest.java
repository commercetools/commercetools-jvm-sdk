package io.sphere.sdk.orderedit.commands;

import io.sphere.sdk.orderedit.OrderEditFixtures;
import io.sphere.sdk.orderedits.OrderEdit;
import io.sphere.sdk.orderedits.commands.OrderEditUpdateCommand;
import io.sphere.sdk.orderedits.commands.stagedactions.OrderEditStagedUpdateAction;
import io.sphere.sdk.orderedits.commands.stagedactions.SetCustomerEmail;
import io.sphere.sdk.orderedits.commands.updateactions.AddStagedAction;
import io.sphere.sdk.orderedits.commands.updateactions.SetComment;
import io.sphere.sdk.orderedits.commands.updateactions.SetKey;
import io.sphere.sdk.orderedits.commands.updateactions.SetStagedActions;
import io.sphere.sdk.orders.OrderFixtures;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.test.SphereTestUtils;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderEditUpdateCommandIntegrationTest extends IntegrationTest {

    @Test
    public void setKey() {
        OrderFixtures.withOrder(client(), order -> {
            OrderEditFixtures.withUpdateableOrderEdit(client(), order.toReference(), orderEdit -> {

                final String newKey = SphereTestUtils.randomKey();
                assertThat(orderEdit.getKey()).isNotEqualTo(newKey);
                final OrderEditUpdateCommand orderEditUpdateCommand = OrderEditUpdateCommand.of(orderEdit, SetKey.of(newKey));
                final OrderEdit updatedOrderEdit = client().executeBlocking(orderEditUpdateCommand);
                Assertions.assertThat(updatedOrderEdit).isNotNull();
                Assertions.assertThat(updatedOrderEdit.getKey()).isEqualTo(newKey);
                return updatedOrderEdit;
            });
        });
    }

    @Test
    public void setComment() {
        OrderFixtures.withOrder(client(), order -> {
            OrderEditFixtures.withUpdateableOrderEdit(client(), order.toReference(), orderEdit -> {

                final String newComment = SphereTestUtils.randomString();
                final OrderEditUpdateCommand orderEditUpdateCommand = OrderEditUpdateCommand.of(orderEdit, SetComment.of(newComment));
                final OrderEdit updatedOrderEdit = client().executeBlocking(orderEditUpdateCommand);
                Assertions.assertThat(updatedOrderEdit).isNotNull();
                Assertions.assertThat(updatedOrderEdit.getComment()).isEqualTo(newComment);
                return updatedOrderEdit;
            });
        });
    }

    @Test
    public void setCommentByKey() {
        OrderFixtures.withOrder(client(), order -> {
            OrderEditFixtures.withUpdateableOrderEdit(client(), order.toReference(), orderEdit -> {

                final String newComment = SphereTestUtils.randomString();
                final OrderEditUpdateCommand orderEditUpdateCommand = OrderEditUpdateCommand.ofKey(orderEdit.getKey(), orderEdit.getVersion(), SetComment.of(newComment));
                final OrderEdit updatedOrderEdit = client().executeBlocking(orderEditUpdateCommand);
                Assertions.assertThat(updatedOrderEdit).isNotNull();
                Assertions.assertThat(updatedOrderEdit.getComment()).isEqualTo(newComment);
                return updatedOrderEdit;
            });
        });
    }

    @Test
    public void setStagedActions() {
        OrderFixtures.withOrder(client(), order -> {
            OrderEditFixtures.withUpdateableOrderEdit(client(), order.toReference(), orderEdit -> {

                final String newCustomerEmail = SphereTestUtils.randomEmail(OrderEditUpdateCommandIntegrationTest.class);
                final List<OrderEditStagedUpdateAction> stagedActions = new ArrayList<>();
                stagedActions.add(SetCustomerEmail.of(newCustomerEmail));
                final OrderEditUpdateCommand orderEditUpdateCommand = OrderEditUpdateCommand.of(orderEdit, SetStagedActions.of(stagedActions));
                final OrderEdit updatedOrderEdit = client().executeBlocking(orderEditUpdateCommand);
                Assertions.assertThat(updatedOrderEdit).isNotNull();
                Assertions.assertThat(updatedOrderEdit.getStagedActions().get(0).getAction()).isEqualTo(stagedActions.get(0).getAction());
                return updatedOrderEdit;
            });
        });
    }

    @Test
    public void addStagedAction() {
        OrderFixtures.withOrder(client(), order -> {
            OrderEditFixtures.withUpdateableOrderEdit(client(), order.toReference(), orderEdit -> {

                String newCustomerEmail = SphereTestUtils.randomEmail(OrderEditUpdateCommandIntegrationTest.class);
                final SetCustomerEmail setCustomerEmail = SetCustomerEmail.of(newCustomerEmail);
                final OrderEditUpdateCommand orderEditUpdateCommand = OrderEditUpdateCommand.of(orderEdit, AddStagedAction.of(setCustomerEmail));
                final OrderEdit updatedOrderEdit = client().executeBlocking(orderEditUpdateCommand);
                Assertions.assertThat(updatedOrderEdit).isNotNull();
                Assertions.assertThat(updatedOrderEdit.getStagedActions().get(0).getAction()).isEqualTo(setCustomerEmail.getAction());
                if(updatedOrderEdit.getStagedActions().get(0) instanceof SetCustomerEmail){
                    SetCustomerEmail addedStagedAction = (SetCustomerEmail) updatedOrderEdit.getStagedActions().get(0);
                    Assertions.assertThat(addedStagedAction.getEmail()).isEqualTo(newCustomerEmail);
                }else{
                    Assertions.fail("Staged action should be instance of SetCustomerEmail");
                }

                return updatedOrderEdit;
            });
        });
    }



}
