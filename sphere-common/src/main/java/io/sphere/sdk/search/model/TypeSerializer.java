package io.sphere.sdk.search.model;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

import static java.time.format.DateTimeFormatter.*;

/**
 * Serializer and deserializer to transform certain types from and to Commercetools Platform format on search endpoint.
 * @param <T> type of the data to transform.
 */
class TypeSerializer<T> {
    private final Function<T, String> serializer;
    private final Function<String, T> deserializer;

    private TypeSerializer(final Function<T, String> serializer, final Function<String, T> deserializer) {
        this.serializer = serializer;
        this.deserializer = deserializer;
    }

    public Function<T, String> getSerializer() {
        return serializer;
    }

    public Function<String, T> getDeserializer() {
        return deserializer;
    }

    /**
     * Serializer to convert the given text to a formatted string with escaped characters.
     * @return the serializer for text data.
     */
    public static TypeSerializer<String> ofString() {
        return new TypeSerializer<>(
                v -> withQuotes(v.replace("\"", "\\\"")),
                s -> s);
    }

    /**
     * Serializer to convert the given boolean into the true/false string.
     * @return the serializer for boolean data.
     */
    public static TypeSerializer<Boolean> ofBoolean() {
        return new TypeSerializer<>(
                v -> v ? "true" : "false",
                s -> Boolean.valueOf(s));
    }

    /**
     * Serializer to convert the given numerical value into a suitable string.
     * @return the serializer for numerical data.
     */
    public static TypeSerializer<BigDecimal> ofNumber() {
        return new TypeSerializer<>(
                v -> v.toPlainString(),
                s -> new BigDecimal(s));
    }

    /**
     * Serializer to convert the given local date into the accepted ISO format.
     * @return the serializer for date data.
     */
    public static TypeSerializer<LocalDate> ofDate() {
        return new TypeSerializer<>(
                v -> withQuotes(v.format(ISO_DATE)),
                s -> LocalDate.parse(s));
    }

    /**
     * Serializer to convert the given local time into the accepted standard format.
     * @return the serializer for time data.
     */
    public static TypeSerializer<LocalTime> ofTime() {
        return new TypeSerializer<>(
                v -> withQuotes(v.format(DateTimeFormatter.ofPattern("HH:mm:ss.SSS"))),
                s -> LocalTime.parse(s));
    }

    /**
     * Serializer to convert the given local datetime into the accepted ISO format with UTC zone.
     * @return the serializer for datetime data.
     */
    public static TypeSerializer<ZonedDateTime> ofDateTime() {
        return new TypeSerializer<>(
                v -> withQuotes(v.withZoneSameInstant(ZoneOffset.UTC).format(ISO_DATE_TIME)),
                s -> ZonedDateTime.parse(s));
    }

    /**
     * Serializer to convert the given money cent amount into a plain string.
     * @return the serializer for money cent amount data.
     */
    public static TypeSerializer<Long> ofMoneyCentAmount() {
        return new TypeSerializer<>(
                v -> v.toString(),
                s -> Long.valueOf(s));
    }

    /**
     * Serializer to convert the given money currency into the accepted format.
     * @return the serializer for currency data.
     */
    public static TypeSerializer<CurrencyUnit> ofCurrency() {
        return new TypeSerializer<>(
                v -> withQuotes(v.getCurrencyCode().toUpperCase()),
                s -> Monetary.getCurrency(s));
    }

    private static String withQuotes(final String text) {
        return "\"" + text + "\"";
    }
}
