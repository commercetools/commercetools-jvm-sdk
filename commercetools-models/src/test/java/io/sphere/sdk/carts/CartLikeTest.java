package io.sphere.sdk.carts;

import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.utils.MoneyImpl;
import org.junit.Before;
import org.junit.Test;

import static io.sphere.sdk.models.DefaultCurrencyUnits.EUR;
import static org.assertj.core.api.Assertions.assertThat;

public class CartLikeTest {

    private Cart cartWithTaxes;
    private Cart cartWithoutTaxes;

    @Before
    public void setUp() throws Exception {
        cartWithTaxes = SphereJsonUtils.readObjectFromResource("cart-with-taxes.json", Cart.class);
        cartWithoutTaxes = SphereJsonUtils.readObjectFromResource("cart-without-taxes.json", Cart.class);
    }

    @Test
    public void estimatesTotalGrossPrice() throws Exception {
        assertThat(cartWithoutTaxes.estimateTotalGrossPrice()).isEqualTo(MoneyImpl.ofCents(262150, EUR));
        assertThat(cartWithTaxes.estimateTotalGrossPrice()).isEqualTo(MoneyImpl.ofCents(262540, EUR));
    }

    @Test
    public void estimatesTotalNetPrice() throws Exception {
        assertThat(cartWithoutTaxes.estimateTotalNetPrice()).isEqualTo(MoneyImpl.ofCents(262150, EUR));
        assertThat(cartWithTaxes.estimateTotalNetPrice()).isEqualTo(MoneyImpl.ofCents(220622, EUR));
    }

    @Test
    public void calculatesTotalAppliedTaxes() throws Exception {
        assertThat(cartWithoutTaxes.calculateTotalAppliedTaxes()).isEmpty();
        assertThat(cartWithTaxes.calculateTotalAppliedTaxes()).contains(MoneyImpl.ofCents(41918, EUR));
    }

    @Test
    public void estimatesSubTotalGrossPrice() throws Exception {
        assertThat(cartWithoutTaxes.estimateSubTotalGrossPrice()).isEqualTo(MoneyImpl.ofCents(262150, EUR));
        assertThat(cartWithTaxes.estimateSubTotalGrossPrice()).isEqualTo(MoneyImpl.ofCents(262150, EUR));
    }

    @Test
    public void estimatesSubTotalNetPrice() throws Exception {
        assertThat(cartWithoutTaxes.estimateSubTotalNetPrice()).isEqualTo(MoneyImpl.ofCents(262150, EUR));
        assertThat(cartWithTaxes.estimateSubTotalNetPrice()).isEqualTo(MoneyImpl.ofCents(220294, EUR));
    }
}
