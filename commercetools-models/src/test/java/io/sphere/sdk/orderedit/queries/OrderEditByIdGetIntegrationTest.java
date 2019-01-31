package io.sphere.sdk.orderedit.queries;

import io.sphere.sdk.orderedit.OrderEditFixtures;
import io.sphere.sdk.orderedits.OrderEdit;
import io.sphere.sdk.orderedits.queries.OrderEditByIdGet;
import io.sphere.sdk.orders.OrderFixtures;
import io.sphere.sdk.test.IntegrationTest;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class OrderEditByIdGetIntegrationTest extends IntegrationTest {

    @Test
    public void execute(){
        OrderFixtures.withOrder(client(), order -> {
            OrderEditFixtures.withOrderEdit(client(), order.toReference(), orderEdit -> {
                OrderEdit queriedOrderEdit = client().executeBlocking(OrderEditByIdGet.of(orderEdit.getId()));
                Assertions.assertThat(queriedOrderEdit).isNotNull();
                Assertions.assertThat(queriedOrderEdit.getId()).isEqualTo(orderEdit.getId());
            });
        });
    }
}
