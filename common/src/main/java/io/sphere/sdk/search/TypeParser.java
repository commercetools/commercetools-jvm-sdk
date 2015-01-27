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

public class TypeParser<T> {
    private final Function<T, String> renderer;

    private TypeParser(Function<T, String> renderer) {
        this.renderer = renderer;
    }

    public Function<T, String> renderer() {
        return renderer;
    }

    /**
     * Converts the given text to a formatted string with escaped characters.
     */
    public static TypeParser<String> ofText() {
        return new TypeParser<>(v -> withQuotes(v.replace("\"", "\\\"")));
    }

    /**
     * Converts the given boolean into the true/false string.
     */
    public static TypeParser<Boolean> ofBoolean() {
        return new TypeParser<>(v -> v ? "true" : "false");
    }

    /**
     * Converts the given numerical value into a suitable string.
     */
    public static TypeParser<BigDecimal> ofNumber() {
        return new TypeParser<>(v -> v.toPlainString());
    }

    /**
     * Converts the given local datetime into the accepted ISO format with UTC zone.
     */
    public static TypeParser<LocalDateTime> ofDateTime() {
        return new TypeParser<>(v -> withQuotes(v.atZone(UTC).format(ISO_DATE_TIME)));
    }

    /**
     * Converts the given local date into the accepted ISO format.
     */
    public static TypeParser<LocalDate> ofDate() {
        return new TypeParser<>(v -> withQuotes(v.format(ISO_DATE)));
    }

    /**
     * Converts the given local time into the accepted standard format.
     */
    public static TypeParser<LocalTime> ofTime() {
        return new TypeParser<>(v -> withQuotes(v.format(DateTimeFormatter.ofPattern("HH:mm:ss.SSS"))));
    }

    /**
     * Converts the given money amount into cent amount (e.g. from "20,00 EUR" to "2000").
     */
    public static TypeParser<Money> ofMoneyAmount() {
        return new TypeParser<>(v -> v.getNumberStripped().movePointRight(2).setScale(0, HALF_EVEN).toPlainString());
    }

    /**
     * Converts the given money currency into the accepted format.
     */
    public static TypeParser<CurrencyUnit> ofCurrency() {
        return new TypeParser<>(v -> withQuotes(v.getCurrencyCode().toLowerCase()));
    }

    /**
     * Converts the given reference into the identifier value.
     */
    public static <R> TypeParser<Referenceable<R>> ofReference() {
        return new TypeParser<>(v -> withQuotes(v.toReference().getId()));
    }

    private static String withQuotes(final String text) {
        return "\"" + text + "\"";
    }
}
