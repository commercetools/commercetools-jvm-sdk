package io.sphere.sdk.carts;

import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.utils.MoneyImpl;
import org.junit.Before;
import org.junit.Test;

import static io.sphere.sdk.test.SphereTestUtils.EUR;
import static org.assertj.core.api.Assertions.assertThat;

public class LineItemLikeTest {

    private LineItem lineItemWithTaxes;
    private LineItem lineItemWithoutTaxes;

    @Before
    public void setUp() throws Exception {
        lineItemWithTaxes = SphereJsonUtils.readObjectFromResource("cart-with-taxes.json", Cart.class).getLineItems().get(0);
        lineItemWithoutTaxes = SphereJsonUtils.readObjectFromResource("cart-without-taxes.json", Cart.class).getLineItems().get(0);
    }

    @Test
    public void estimatesTotalGrossPrice() throws Exception {
        assertThat(lineItemWithoutTaxes.estimateTotalGrossPrice()).isEqualTo(MoneyImpl.ofCents(188750, EUR));
        assertThat(lineItemWithTaxes.estimateTotalGrossPrice()).isEqualTo(MoneyImpl.ofCents(188750, EUR));

    }

    @Test
    public void estimatesTotalNetPrice() throws Exception {
        assertThat(lineItemWithoutTaxes.estimateTotalNetPrice()).isEqualTo(MoneyImpl.ofCents(188750, EUR));
        assertThat(lineItemWithTaxes.estimateTotalNetPrice()).isEqualTo(MoneyImpl.ofCents(158613, EUR));

    }
}
