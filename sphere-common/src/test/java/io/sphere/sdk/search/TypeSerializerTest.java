package io.sphere.sdk.search;

import org.junit.Test;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.function.Function;

import static io.sphere.sdk.search.TypeSerializer.*;
import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThat;

public class TypeSerializerTest {

    @Test
    public void serializesText() throws Exception {
        Function<String, String> serializer = ofString().getSerializer();
        assertThat(serializer.apply("some\"text")).isEqualTo("\"some\\\"text\"");
    }

    @Test
    public void deserializesText() throws Exception {
        Function<String, String> deserializer = ofString().getDeserializer();
        assertThat(deserializer.apply("some\"text")).isEqualTo("some\"text");
    }

    @Test
    public void serializesBoolean() throws Exception {
        Function<Boolean, String> serializer = ofBoolean().getSerializer();
        assertThat(serializer.apply(true)).isEqualTo("true");
        assertThat(serializer.apply(false)).isEqualTo("false");
    }

    @Test
    public void deserializesBoolean() throws Exception {
        final Function<String, Boolean> deserializer = ofBoolean().getDeserializer();
        assertThat(deserializer.apply("true")).isEqualTo(true);
        assertThat(deserializer.apply("false")).isEqualTo(false);
    }

    @Test
    public void serializesNumber() throws Exception {
        Function<BigDecimal, String> serializer = ofNumber().getSerializer();
        assertThat(serializer.apply(valueOf(100))).isEqualTo("100");
        assertThat(serializer.apply(valueOf(10.5))).isEqualTo("10.5");
    }

    @Test
    public void deserializesNumber() throws Exception {
        final Function<String, BigDecimal> deserializer = ofNumber().getDeserializer();
        assertThat(deserializer.apply("100")).isEqualTo(valueOf(100));
        assertThat(deserializer.apply("10.5")).isEqualTo(valueOf(10.5));
    }

    @Test
    public void serializesDate() throws Exception {
        Function<LocalDate, String> serializer = ofDate().getSerializer();
        assertThat(serializer.apply(date("2001-09-11"))).isEqualTo("\"2001-09-11\"");
        assertThat(serializer.apply(date("2001-10-01"))).isEqualTo("\"2001-10-01\"");
    }

    @Test
    public void deserializesDate() throws Exception {
        final Function<String, LocalDate> deserializer = ofDate().getDeserializer();
        assertThat(deserializer.apply("2001-09-11")).isEqualTo(date("2001-09-11"));
        assertThat(deserializer.apply("2001-10-01")).isEqualTo(date("2001-10-01"));
    }

    @Test
    public void serializesTime() throws Exception {
        Function<LocalTime, String> serializer = ofTime().getSerializer();
        assertThat(serializer.apply(time("22:05:19.203"))).isEqualTo("\"22:05:19.203\"");
        assertThat(serializer.apply(time("22:15:09.003"))).isEqualTo("\"22:15:09.003\"");
    }

    @Test
    public void deserializesTime() throws Exception {
        final Function<String, LocalTime> deserializer = ofTime().getDeserializer();
        assertThat(deserializer.apply("22:05:19.203")).isEqualTo(time("22:05:19.203"));
        assertThat(deserializer.apply("22:15:09.003")).isEqualTo(time("22:15:09.003"));
    }

    @Test
    public void serializesDateTime() throws Exception {
        Function<ZonedDateTime, String> serializer = ofDateTime().getSerializer();
        assertThat(serializer.apply(dateTime("2001-09-11T22:05:09.203+02:00"))).isEqualTo("\"2001-09-11T20:05:09.203Z\"");
    }

    @Test
    public void deserializesDateTime() throws Exception {
        final Function<String, ZonedDateTime> deserializer = ofDateTime().getDeserializer();
        assertThat(deserializer.apply("2001-09-11T22:05:09.203+02:00")).isEqualTo(dateTime("2001-09-11T20:05:09.203Z"));
    }

    @Test
    public void serializesMoneyAmount() throws Exception {
        Function<Long, String> serializer = ofMoneyCentAmount().getSerializer();
        assertThat(serializer.apply(3000L)).isEqualTo("3000");
        assertThat(serializer.apply(3050L)).isEqualTo("3050");
    }

    @Test
    public void deserializesMoneyAmount() throws Exception {
        final Function<String, Long> deserializer = ofMoneyCentAmount().getDeserializer();
        assertThat(deserializer.apply("3000")).isEqualTo(3000L);
        assertThat(deserializer.apply("3050")).isEqualTo(3050L);
    }

    @Test
    public void serializesCurrency() throws Exception {
        Function<CurrencyUnit, String> serializer = ofCurrency().getSerializer();
        assertThat(serializer.apply(currency("EUR"))).isEqualTo("\"EUR\"");
        assertThat(serializer.apply(currency("USD"))).isEqualTo("\"USD\"");
    }

    @Test
    public void deserializesCurrency() throws Exception {
        final Function<String, CurrencyUnit> deserializer = ofCurrency().getDeserializer();
        assertThat(deserializer.apply("EUR")).isEqualTo(currency("EUR"));
        assertThat(deserializer.apply("USD")).isEqualTo(currency("USD"));
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
