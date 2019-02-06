package io.sphere.sdk.orderedit.queries;

import io.sphere.sdk.orderedit.OrderEditFixtures;
import io.sphere.sdk.orderedits.OrderEdit;
import io.sphere.sdk.orderedits.queries.OrderEditByKeyGet;
import io.sphere.sdk.orders.OrderFixtures;
import io.sphere.sdk.test.IntegrationTest;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class OrderEditByKeyGetIntegrationTest extends IntegrationTest {

    @Test
    public void execute() {
        OrderFixtures.withOrder(client(), order -> {
                OrderEditFixtures.withOrderEdit(client(), order.toReference(), orderEdit -> {
                OrderEdit queriedOrderEdit = client().executeBlocking(OrderEditByKeyGet.of(orderEdit.getKey()));
                Assertions.assertThat(queriedOrderEdit).isNotNull();
                Assertions.assertThat(queriedOrderEdit.getKey()).isEqualTo(orderEdit.getKey());
            });
        });
    }
}
