package io.sphere.sdk.orderedit;

import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.client.ConcurrentModificationException;
import io.sphere.sdk.client.NotFoundException;
import io.sphere.sdk.commands.StagedUpdateAction;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.commands.CustomerDeleteCommand;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.orderedits.OrderEdit;
import io.sphere.sdk.orderedits.OrderEditDraft;
import io.sphere.sdk.orderedits.OrderEditDraftBuilder;
import io.sphere.sdk.orderedits.commands.OrderEditCreateCommand;
import io.sphere.sdk.orderedits.commands.OrderEditDeleteCommand;
import io.sphere.sdk.orderedits.expansion.OrderEditExpansionModel;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.OrderFixtures;
import io.sphere.sdk.test.SphereTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class OrderEditFixtures {

    public static void withOrderEdit(final BlockingSphereClient client, final Reference<Order> orderReference, final Consumer<OrderEdit> consumer) {
        final List<StagedUpdateAction<OrderEdit>> stagedActions = new ArrayList<>();
        final OrderEditDraft orderEditDraft = OrderEditDraftBuilder.of(orderReference, stagedActions).key(SphereTestUtils.randomKey()).comment(SphereTestUtils.randomString()).build();
        final OrderEditCreateCommand orderEditCreateCommand = OrderEditCreateCommand.of(orderEditDraft);
        final OrderEdit orderEdit = client.executeBlocking(orderEditCreateCommand);
        consumer.accept(orderEdit);
        delete(client, orderEdit);
    }

    public static void withUpdateableOrderEdit(final BlockingSphereClient client, final Reference<Order> orderReference, final Function<OrderEdit, OrderEdit> f) {
        final List<StagedUpdateAction<OrderEdit>> stagedActions = new ArrayList<>();
        final OrderEditDraft orderEditDraft = OrderEditDraftBuilder.of(orderReference, stagedActions).key(SphereTestUtils.randomKey()).build();
        final OrderEditCreateCommand orderEditCreateCommand = OrderEditCreateCommand.of(orderEditDraft);
        OrderEdit orderEdit = client.executeBlocking(orderEditCreateCommand);
        orderEdit = f.apply(orderEdit);
        delete(client, orderEdit);
    }

    public static void withUpdateableOrderEdit(final BlockingSphereClient client, final Order order, final Function<OrderEdit, OrderEdit> f) {
        final List<StagedUpdateAction<OrderEdit>> stagedActions = new ArrayList<>();
        final OrderEditDraft orderEditDraft = OrderEditDraftBuilder.of(order.toReference(), stagedActions).key(SphereTestUtils.randomKey()).build();
        final OrderEditCreateCommand orderEditCreateCommand = OrderEditCreateCommand.of(orderEditDraft);
        OrderEdit orderEdit = client.executeBlocking(orderEditCreateCommand);
        orderEdit = f.apply(orderEdit);
        delete(client, orderEdit);
    }

    public static void withUpdateableOrderEdit(final BlockingSphereClient client, final Function<OrderEdit, OrderEdit> f) {
        OrderFixtures.withOrder(client, order -> {
            final List<StagedUpdateAction<OrderEdit>> stagedActions = new ArrayList<>();
            final OrderEditDraft orderEditDraft = OrderEditDraftBuilder.of(order.toReference(), stagedActions).key(SphereTestUtils.randomKey()).build();
            final OrderEditCreateCommand orderEditCreateCommand = OrderEditCreateCommand.of(orderEditDraft)
                    .withExpansionPaths(OrderEditExpansionModel::resource);
            OrderEdit orderEdit = client.executeBlocking(orderEditCreateCommand);
            orderEdit = f.apply(orderEdit);
            delete(client, orderEdit);
        });
    }

    private static void delete(final BlockingSphereClient client, final OrderEdit orderEdit) {
        try {
            client.executeBlocking(OrderEditDeleteCommand.of(orderEdit));
        } catch (NotFoundException ignored) {
        } catch (ConcurrentModificationException e) {
            client.executeBlocking(OrderEditDeleteCommand.of(Versioned.of(orderEdit.getId(), e.getCurrentVersion())));
        }
    }
}
