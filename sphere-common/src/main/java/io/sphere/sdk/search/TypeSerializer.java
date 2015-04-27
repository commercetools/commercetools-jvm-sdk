package io.sphere.sdk.search;

import io.sphere.sdk.models.Referenceable;

import javax.money.CurrencyUnit;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

import static java.math.RoundingMode.HALF_EVEN;
import static java.time.format.DateTimeFormatter.*;

/**
 * Serializer to transform certain types into SPHERE format.
 * @param <T> type of the data to transform.
 */
public class TypeSerializer<T> {
    private final Function<T, String> serializer;

    private TypeSerializer(Function<T, String> serializer) {
        this.serializer = serializer;
    }

    public Function<T, String> serializer() {
        return serializer;
    }

    /**
     * Serializer to convert the given text to a formatted string with escaped characters.
     * @return the serializer for text data.
     */
    public static TypeSerializer<String> ofText() {
        return new TypeSerializer<>(v -> withQuotes(v.replace("\"", "\\\"")));
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
     * Serializer to convert the given money amount into cent amount (e.g. from "20,00" to "2000").
     * @return the serializer for money amount data.
     */
    public static TypeSerializer<BigDecimal> ofMoneyAmount() {
        return new TypeSerializer<>(v -> v.movePointRight(2).setScale(0, HALF_EVEN).toPlainString());
    }

    /**
     * Serializer to convert the given money currency into the accepted format.
     * @return the serializer for currency data.
     */
    public static TypeSerializer<CurrencyUnit> ofCurrency() {
        return new TypeSerializer<>(v -> withQuotes(v.getCurrencyCode().toUpperCase()));
    }

    /**
     * Serializer to convert the given reference into the identifier value.
     * @param <R> type of the reference.
     * @return the serializer for references of the given type.
     */
    public static <R> TypeSerializer<Referenceable<R>> ofReference() {
        return new TypeSerializer<>(v -> withQuotes(v.toReference().getId()));
    }

    private static String withQuotes(final String text) {
        return "\"" + text + "\"";
    }
}
