package io.sphere.sdk.orders;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.json.SphereJsonUtils;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class OrderDeserializationTest {


    @Test
    public void deserializeRefusedGifts(){
        final Order order = SphereJsonUtils.readObjectFromResource("order/order-with-refused-gift.json", Order.class);
        assertThat(order.getRefusedGifts()).hasSize(1);
        assertThat(order.getRefusedGifts().get(0).getId()).isEqualTo("<my-order-discount-id>");
    }


}
