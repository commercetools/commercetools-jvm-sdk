package io.sphere.sdk.products;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.utils.JsonUtils;
import io.sphere.sdk.utils.MoneyImpl;
import org.javamoney.moneta.Money;
import org.junit.Test;

import java.math.BigDecimal;

import static org.fest.assertions.Assertions.assertThat;

public class PriceTest {

    @Test
    public void testJsonDeserialized() {
        final Price actual = JsonUtils.readObjectFromJsonString(new TypeReference<Price>() {

        }, "{\n" +
                "            \"value\": {\n" +
                "              \"currencyCode\": \"EUR\",\n" +
                "              \"centAmount\": 2800\n" +
                "            }\n" +
                "          }");

        final Price expected = Price.of(new BigDecimal("28.00"), "EUR");
        assertThat(actual.getValue().isEqualTo(expected.getValue())).isTrue();
        assertThat(actual.getChannel()).isEqualTo(expected.getChannel());
        assertThat(actual.getCountry()).isEqualTo(expected.getCountry());
        assertThat(actual.getCustomerGroup()).isEqualTo(expected.getCustomerGroup());
        assertThat(actual.getDiscounted()).isEqualTo(expected.getDiscounted());
        assertThat(actual).isEqualTo(expected);
    }


}