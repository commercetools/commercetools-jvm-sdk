package io.sphere.sdk.products;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.productdiscounts.AbsoluteProductDiscountValue;
import io.sphere.sdk.productdiscounts.DiscountedPrice;
import io.sphere.sdk.productdiscounts.ProductDiscount;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Locale;

import static io.sphere.sdk.models.DefaultCurrencyUnits.EUR;
import static org.assertj.core.api.Assertions.assertThat;

public class PriceTest {

    @Test
    public void testJsonDeserialized() {
        final Price actual = SphereJsonUtils.readObject("{\n" +
                "            \"value\": {\n" +
                "              \"currencyCode\": \"EUR\",\n" +
                "              \"centAmount\": 2800\n" +
                "            }\n" +
                "          }", new TypeReference<Price>() {

        });

        final Price expected = Price.of(new BigDecimal("28.00"), EUR);
        assertThat(actual.getValue().isEqualTo(expected.getValue())).isTrue();
        assertThat(actual.getChannel()).isEqualTo(expected.getChannel());
        assertThat(actual.getCountry()).isEqualTo(expected.getCountry());
        assertThat(actual.getCustomerGroup()).isEqualTo(expected.getCustomerGroup());
        assertThat(actual.getDiscounted()).isEqualTo(expected.getDiscounted());
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void expandedDiscountedPrice() throws Exception {
        final Product product = SphereJsonUtils.readObjectFromResource("product-with-expanded-discounted-price.json", Product.typeReference());
        final Price price = product.getMasterData().getStaged().getMasterVariant().getPrices().get(0);
        final DiscountedPrice discountedPrice = price.getDiscounted().get();
        final ProductDiscount productDiscount = discountedPrice.getDiscount().getObj().get();
        assertThat(productDiscount.getName().get(Locale.ENGLISH).get()).isEqualTo("demo product discount");
        assertThat(productDiscount.getValue()).isInstanceOf(AbsoluteProductDiscountValue.class);
    }
}