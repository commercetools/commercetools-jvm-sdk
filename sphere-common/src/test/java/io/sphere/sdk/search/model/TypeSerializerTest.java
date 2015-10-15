package io.sphere.sdk.search.model;

import org.junit.Test;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;

import static io.sphere.sdk.search.model.TypeSerializer.*;
import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThat;

public class TypeSerializerTest {

    @Test
    public void serializesText() throws Exception {
        assertThat(ofString().apply("some\"text")).isEqualTo("\"some\\\"text\"");
    }

    @Test
    public void serializesBoolean() throws Exception {
        assertThat(ofBoolean().apply(true)).isEqualTo("true");
        assertThat(ofBoolean().apply(false)).isEqualTo("false");
    }

    @Test
    public void serializesNumber() throws Exception {
        assertThat(ofNumber().apply(valueOf(100))).isEqualTo("100");
        assertThat(ofNumber().apply(valueOf(10.5))).isEqualTo("10.5");
    }

    @Test
    public void serializesDate() throws Exception {
        assertThat(ofDate().apply(date("2001-09-11"))).isEqualTo("\"2001-09-11\"");
        assertThat(ofDate().apply(date("2001-10-01"))).isEqualTo("\"2001-10-01\"");
    }

    @Test
    public void serializesTime() throws Exception {
        assertThat(ofTime().apply(time("22:05:19.203"))).isEqualTo("\"22:05:19.203\"");
        assertThat(ofTime().apply(time("22:15:09.003"))).isEqualTo("\"22:15:09.003\"");
    }

    @Test
    public void serializesDateTime() throws Exception {
        assertThat(ofDateTime().apply(dateTime("2001-09-11T22:05:09.203+02:00"))).isEqualTo("\"2001-09-11T20:05:09.203Z\"");
    }

    @Test
    public void serializesMoneyAmount() throws Exception {
        assertThat(ofMoneyCentAmount().apply(3000L)).isEqualTo("3000");
        assertThat(ofMoneyCentAmount().apply(3050L)).isEqualTo("3050");
    }

    @Test
    public void serializesCurrency() throws Exception {
        assertThat(ofCurrency().apply(currency("EUR"))).isEqualTo("\"EUR\"");
        assertThat(ofCurrency().apply(currency("USD"))).isEqualTo("\"USD\"");
    }

    private LocalDate date(final String date) {
        return LocalDate.parse(date);
    }

    private LocalTime time(final String time) {
        return LocalTime.parse(time);
    }

    private ZonedDateTime dateTime(final String dateTime) {
        return ZonedDateTime.parse(dateTime);
    }

    private CurrencyUnit currency(final String currencyCode) {
        return Monetary.getCurrency(currencyCode);
    }
}
