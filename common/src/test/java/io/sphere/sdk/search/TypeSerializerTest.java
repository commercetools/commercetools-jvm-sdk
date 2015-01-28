package io.sphere.sdk.search;

import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import org.javamoney.moneta.CurrencyUnitBuilder;
import org.javamoney.moneta.Money;
import org.junit.Test;

import javax.money.CurrencyContext;
import javax.money.CurrencyUnit;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.function.Function;

import static io.sphere.sdk.search.TypeSerializer.*;
import static org.fest.assertions.Assertions.assertThat;

public class TypeSerializerTest {



    @Test
    public void serializesText() throws Exception {
        Function<String, String> serializer = ofText().serializer();
        assertThat(serializer.apply("some\"text")).isEqualTo("\"some\\\"text\"");
    }

    @Test
    public void serializesBoolean() throws Exception {
        Function<Boolean, String> serializer = ofBoolean().serializer();
        assertThat(serializer.apply(true)).isEqualTo("true");
        assertThat(serializer.apply(false)).isEqualTo("false");
    }

    @Test
    public void serializesNumber() throws Exception {
        Function<BigDecimal, String> serializer = ofNumber().serializer();
        assertThat(serializer.apply(number(100))).isEqualTo("100");
        assertThat(serializer.apply(number(10.5))).isEqualTo("10.5");
    }

    @Test
    public void serializesDate() throws Exception {
        Function<LocalDate, String> serializer = ofDate().serializer();
        assertThat(serializer.apply(date("2001-09-11"))).isEqualTo("\"2001-09-11\"");
        assertThat(serializer.apply(date("2001-10-01"))).isEqualTo("\"2001-10-01\"");
    }

    @Test
    public void serializesTime() throws Exception {
        Function<LocalTime, String> serializer = ofTime().serializer();
        assertThat(serializer.apply(time("22:05:19.203"))).isEqualTo("\"22:05:19.203\"");
        assertThat(serializer.apply(time("22:15:09.003"))).isEqualTo("\"22:15:09.003\"");
    }

    @Test
    public void serializesDateTime() throws Exception {
        Function<LocalDateTime, String> serializer = ofDateTime().serializer();
        assertThat(serializer.apply(dateTime("2001-09-11T22:05:09.203"))).isEqualTo("\"2001-09-11T22:05:09.203Z\"");
    }

    @Test
    public void serializesMoneyAmount() throws Exception {
        Function<Money, String> serializer = ofMoneyAmount().serializer();
        assertThat(serializer.apply(money(30))).isEqualTo("3000");
        assertThat(serializer.apply(money(30.5))).isEqualTo("3050");
    }

    @Test
    public void serializesCurrency() throws Exception {
        Function<CurrencyUnit, String> serializer = ofCurrency().serializer();
        assertThat(serializer.apply(currency("EUR"))).isEqualTo("\"eur\"");
        assertThat(serializer.apply(currency("USD"))).isEqualTo("\"usd\"");
    }

    @Test
    public void serializesReference() throws Exception {
        Function<Referenceable<Object>, String> serializer = ofReference().serializer();
        assertThat(serializer.apply(reference("some-id"))).isEqualTo("\"some-id\"");
    }

    private BigDecimal number(final double number) {
        return new BigDecimal(number);
    }

    private LocalDate date(final String date) {
        return LocalDate.parse(date);
    }

    private LocalTime time(final String time) {
        return LocalTime.parse(time);
    }

    private LocalDateTime dateTime(final String dateTime) {
        return LocalDateTime.parse(dateTime);
    }

    private Money money(double amount) {
        return Money.of(new BigDecimal(amount), "EUR");
    }

    private CurrencyUnit currency(final String currencyCode) {
        return CurrencyUnitBuilder.of(currencyCode, CurrencyContext.KEY_PROVIDER).build();
    }

    private Reference<Object> reference(String id) {
        return Reference.of("object", id);
    }
}
