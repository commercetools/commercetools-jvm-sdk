package io.sphere.sdk.orders;

import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.shippingmethods.*;
import io.sphere.sdk.utils.MoneyImpl;
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

    @Test
    public void serializeCartClassification() {
        final ShippingRatePriceTier tier = CartClassificationBuilder.of("small", MoneyImpl.ofCents(100, "EUR")).build();
        final String s = SphereJsonUtils.toPrettyJsonString(tier);

        Assertions.assertThat(s).containsOnlyOnce("CartClassification");
        final ShippingRatePriceTier shippingRatePriceTier = SphereJsonUtils.readObject(s, ShippingRatePriceTier.class);
        Assertions.assertThat(shippingRatePriceTier).isInstanceOf(CartClassification.class);
        final String t = SphereJsonUtils.toJsonString(shippingRatePriceTier);
        Assertions.assertThat(t).containsOnlyOnce("CartClassification");
    }

    @Test
    public void serializeCartScore() {
        final ShippingRatePriceTier tier = CartScoreBuilder.of(100L, MoneyImpl.ofCents(100, "EUR")).build();
        final String s = SphereJsonUtils.toPrettyJsonString(tier);

        Assertions.assertThat(s).containsOnlyOnce("CartScore");
        final ShippingRatePriceTier shippingRatePriceTier = SphereJsonUtils.readObject(s, ShippingRatePriceTier.class);
        Assertions.assertThat(shippingRatePriceTier).isInstanceOf(CartScore.class);
        final String t = SphereJsonUtils.toJsonString(shippingRatePriceTier);
        Assertions.assertThat(t).containsOnlyOnce("CartScore");
    }

    @Test
    public void serializeCartValue() {
        final ShippingRatePriceTier tier = CartValueBuilder.of(100L, MoneyImpl.ofCents(100, "EUR")).build();
        final String s = SphereJsonUtils.toPrettyJsonString(tier);

        Assertions.assertThat(s).containsOnlyOnce("CartValue");
        final ShippingRatePriceTier shippingRatePriceTier = SphereJsonUtils.readObject(s, ShippingRatePriceTier.class);
        Assertions.assertThat(shippingRatePriceTier).isInstanceOf(CartValue.class);
        final String t = SphereJsonUtils.toJsonString(shippingRatePriceTier);
        Assertions.assertThat(t).containsOnlyOnce("CartValue");
    }
}
