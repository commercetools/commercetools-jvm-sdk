package io.sphere.sdk.products;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.productdiscounts.AbsoluteProductDiscountValue;
import io.sphere.sdk.productdiscounts.DiscountedPrice;
import io.sphere.sdk.productdiscounts.ProductDiscount;
import io.sphere.sdk.utils.MoneyImpl;
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
                "          }", Price.class);
        assertThat(actual.getValue().isEqualTo(MoneyImpl.ofCents(2800, "EUR")));
    }

    @Test
    public void expandedDiscountedPrice() throws Exception {
        final Product product = SphereJsonUtils.readObjectFromResource("product-with-expanded-discounted-price.json", Product.typeReference());
        final Price price = product.getMasterData().getStaged().getMasterVariant().getPrices().get(0);
        final DiscountedPrice discountedPrice = price.getDiscounted();
        final ProductDiscount productDiscount = discountedPrice.getDiscount().getObj();
        assertThat(productDiscount.getName().get(Locale.ENGLISH)).isEqualTo("demo product discount");
        assertThat(productDiscount.getValue()).isInstanceOf(AbsoluteProductDiscountValue.class);
    }

    @Test
    public void equalsIgnoreId() {
        final String jsonAsString = "{\n" +
                "            \"id\": \"foo-id\",\n" +
                "            \"value\": {\n" +
                "              \"currencyCode\": \"EUR\",\n" +
                "              \"centAmount\": 2800\n" +
                "            }\n" +
                "          }";
        final Price price1 = SphereJsonUtils.readObject(jsonAsString, Price.class);
        final Price price2 = SphereJsonUtils.readObject(jsonAsString.replace("foo-id", "bar-id"), Price.class);
        assertThat(price1.getId()).isEqualTo("foo-id");
        assertThat(price2.getId()).isEqualTo("bar-id");
        assertThat(price1).isNotEqualTo(price2);
        assertThat(price1.equalsIgnoreId(price2)).isTrue();
    }
}