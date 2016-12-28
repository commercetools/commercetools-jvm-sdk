package io.sphere.sdk.carts;

import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.utils.MoneyImpl;
import org.junit.Before;
import org.junit.Test;

import javax.money.MonetaryAmount;

import static io.sphere.sdk.models.DefaultCurrencyUnits.EUR;
import static org.assertj.core.api.Assertions.assertThat;

public class CartLikeTest {

    private Cart cartWithTaxes;
    private Cart cartWithoutTaxes;
    private Cart emptyCart;

    @Before
    public void setUp() throws Exception {
        cartWithTaxes = SphereJsonUtils.readObjectFromResource("carts/cart-with-taxes.json", Cart.class);
        cartWithoutTaxes = SphereJsonUtils.readObjectFromResource("carts/cart-without-taxes.json", Cart.class);
        emptyCart = SphereJsonUtils.readObjectFromResource("carts/cart-empty.json", Cart.class);
    }

    @Test
    public void calculatesTotalAppliedTaxes() throws Exception {
        assertThat(emptyCart.calculateTotalAppliedTaxes()).isEmpty();
        assertThat(cartWithoutTaxes.calculateTotalAppliedTaxes()).isEmpty();
        assertThat(cartWithTaxes.calculateTotalAppliedTaxes()).contains(MoneyImpl.ofCents(262540 - 220622, EUR));
    }

    @Test
    public void calculatesSubTotalPrice() throws Exception {
        assertThat(emptyCart.calculateSubTotalPrice()).isEqualTo(MoneyImpl.ofCents(0, EUR));
        final MonetaryAmount sumLineItems = MoneyImpl.ofCents(188750 + 65000 + 8400, EUR);
        assertThat(cartWithoutTaxes.calculateSubTotalPrice()).isEqualTo(sumLineItems);
        assertThat(cartWithTaxes.calculateSubTotalPrice()).isEqualTo(sumLineItems);
    }
}
