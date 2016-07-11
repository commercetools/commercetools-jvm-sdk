package io.sphere.sdk.search.model;

import io.sphere.sdk.models.Base;

import javax.money.CurrencyUnit;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

import static java.time.format.DateTimeFormatter.*;

/**
 * Serializer to transform certain types to Commercetools Platform format on search endpoint.
 * @param <V> type of the data to transform.
 */
public final class TypeSerializer<V> extends Base implements Function<V, String> {
    private final Function<V, String> serializer;

    @Override
    public String apply(final V value) {
        return serializer.apply(value);
    }

    private TypeSerializer(final Function<V, String> serializer) {
        this.serializer = serializer;
    }

    /**
     * Serializer to convert the given element to a formatted string with escaped characters.
     * @return the serializer for text data.
     * @param serializer serializer
     * @param <V> type of the data to transform.
     */
    public static <V> TypeSerializer<V> ofRawFunction(final Function<V, String> serializer) {
        return new TypeSerializer<>(serializer);
    }

    /**
     * Serializer to convert the given text to a formatted string with escaped characters.
     * @return the serializer for text data.
     */
    public static TypeSerializer<String> ofString() {
        return new TypeSerializer<>(v -> withQuotes(v.replace("\"", "\\\"")));
    }

    /**
     * Serializer to convert the given text to a raw string without escaping characters or adding quotes.
     * @return the serializer for raw text data.
     */
    public static TypeSerializer<String> ofRawString() {
        return new TypeSerializer<>(v -> v);
    }

    /**
     * Serializer to convert the given boolean into the true/false string.
     * @return the serializer for boolean data.
     */
    public static TypeSerializer<Boolean> ofBoolean() {
        return new TypeSerializer<>(v -> v ? "true" : "false");
    }

    /**
     * Serializer to convert the given numerical value into a suitable string.
     * @return the serializer for numerical data.
     */
    public static TypeSerializer<BigDecimal> ofNumber() {
        return new TypeSerializer<>(v -> v.toPlainString());
    }

    /**
     * Serializer to convert the given local date into the accepted ISO format.
     * @return the serializer for date data.
     */
    public static TypeSerializer<LocalDate> ofDate() {
        return new TypeSerializer<>(v -> withQuotes(v.format(ISO_DATE)));
    }

    /**
     * Serializer to convert the given local time into the accepted standard format.
     * @return the serializer for time data.
     */
    public static TypeSerializer<LocalTime> ofTime() {
        return new TypeSerializer<>(v -> withQuotes(v.format(DateTimeFormatter.ofPattern("HH:mm:ss.SSS"))));
    }

    /**
     * Serializer to convert the given local datetime into the accepted ISO format with UTC zone.
     * @return the serializer for datetime data.
     */
    public static TypeSerializer<ZonedDateTime> ofDateTime() {
        return new TypeSerializer<>(v -> withQuotes(v.withZoneSameInstant(ZoneOffset.UTC).format(ISO_DATE_TIME)));
    }

    /**
     * Serializer to convert the given money cent amount into a plain string.
     * @return the serializer for money cent amount data.
     */
    public static TypeSerializer<Long> ofMoneyCentAmount() {
        return new TypeSerializer<>(v -> v.toString());
    }

    /**
     * Serializer to convert the given money currency into the accepted format.
     * @return the serializer for currency data.
     */
    public static TypeSerializer<CurrencyUnit> ofCurrency() {
        return new TypeSerializer<>(v -> withQuotes(v.getCurrencyCode().toUpperCase()));
    }

    private static String withQuotes(final String text) {
        return "\"" + text + "\"";
    }
}
