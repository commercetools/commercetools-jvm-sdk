package io.sphere.sdk.orderedit;

import io.sphere.sdk.orderedits.OrderEdit;
import io.sphere.sdk.orderedits.queries.OrderEditQuery;
import io.sphere.sdk.orders.OrderFixtures;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.test.IntegrationTest;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class OrderEditQueryIntegrationTest extends IntegrationTest {

    @Test
    public void queryByKey() {
        OrderFixtures.withOrder(client(), order -> {
            OrderEditFixtures.withOrderEdit(client(), order.toReference(), orderEdit -> {
                final PagedQueryResult<OrderEdit> result = client().executeBlocking(OrderEditQuery.of().byKey(orderEdit.getKey()));
                Assertions.assertThat(result.getResults().size()).isEqualTo(1);
                Assertions.assertThat(result.getResults().get(0).getId()).isEqualTo(orderEdit.getId());
            });
        });
    }

    @Test
    public void queryByPredicate(){
        OrderFixtures.withOrder(client(), order -> {
            OrderEditFixtures.withOrderEdit(client(), order.toReference(), orderEdit -> {
                final PagedQueryResult<OrderEdit> result = client().executeBlocking(
                        OrderEditQuery.of().plusPredicates(orderEditQueryModel -> orderEditQueryModel.comment().is(orderEdit.getComment())));
                Assertions.assertThat(result.getResults().size()).isEqualTo(1);
                Assertions.assertThat(result.getResults().get(0).getId()).isEqualTo(orderEdit.getId());
            });
        });
    }
}