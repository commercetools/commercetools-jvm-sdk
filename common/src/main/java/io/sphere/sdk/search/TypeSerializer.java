package io.sphere.sdk.search;

import io.sphere.sdk.models.Referenceable;
import org.javamoney.moneta.Money;

import javax.money.CurrencyUnit;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

import static java.time.ZoneOffset.UTC;
import static java.time.format.DateTimeFormatter.ISO_DATE;
import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;
import static java.math.RoundingMode.HALF_EVEN;

public class TypeSerializer<T> {
    private final Function<T, String> serializer;

    private TypeSerializer(Function<T, String> serializer) {
        this.serializer = serializer;
    }

    public Function<T, String> serializer() {
        return serializer;
    }

    /**
     * Converts the given text to a formatted string with escaped characters.
     */
    public static TypeSerializer<String> ofText() {
        return new TypeSerializer<>(v -> withQuotes(v.replace("\"", "\\\"")));
    }

    /**
     * Converts the given boolean into the true/false string.
     */
    public static TypeSerializer<Boolean> ofBoolean() {
        return new TypeSerializer<>(v -> v ? "true" : "false");
    }

    /**
     * Converts the given numerical value into a suitable string.
     */
    public static TypeSerializer<BigDecimal> ofNumber() {
        return new TypeSerializer<>(v -> v.toPlainString());
    }

    /**
     * Converts the given local date into the accepted ISO format.
     */
    public static TypeSerializer<LocalDate> ofDate() {
        return new TypeSerializer<>(v -> withQuotes(v.format(ISO_DATE)));
    }

    /**
     * Converts the given local time into the accepted standard format.
     */
    public static TypeSerializer<LocalTime> ofTime() {
        return new TypeSerializer<>(v -> withQuotes(v.format(DateTimeFormatter.ofPattern("HH:mm:ss.SSS"))));
    }

    /**
     * Converts the given local datetime into the accepted ISO format with UTC zone.
     */
    public static TypeSerializer<LocalDateTime> ofDateTime() {
        return new TypeSerializer<>(v -> withQuotes(v.atZone(UTC).format(ISO_DATE_TIME)));
    }

    /**
     * Converts the given money amount into cent amount (e.g. from "20,00" to "2000").
     */
    public static TypeSerializer<BigDecimal> ofMoneyAmount() {
        return new TypeSerializer<>(v -> v.movePointRight(2).setScale(0, HALF_EVEN).toPlainString());
    }

    /**
     * Converts the given money currency into the accepted format.
     */
    public static TypeSerializer<CurrencyUnit> ofCurrency() {
        return new TypeSerializer<>(v -> withQuotes(v.getCurrencyCode().toLowerCase()));
    }

    /**
     * Converts the given reference into the identifier value.
     */
    public static <R> TypeSerializer<Referenceable<R>> ofReference() {
        return new TypeSerializer<>(v -> withQuotes(v.toReference().getId()));
    }

    private static String withQuotes(final String text) {
        return "\"" + text + "\"";
    }
}
