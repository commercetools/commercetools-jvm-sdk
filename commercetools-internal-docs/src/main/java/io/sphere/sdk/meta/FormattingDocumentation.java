package io.sphere.sdk.meta;

import org.javamoney.moneta.format.CurrencyStyle;
import org.javamoney.moneta.function.MonetaryQueries;

import java.time.format.DateTimeFormatter;

/**
 *
 * <h3 id="temporal-data">Temporal Data</h3>
 *
 * <h4 id="formatting-temporal-data"></h4>
 *
 * <p>Do not use {@link java.text.SimpleDateFormat}, it does not work with all temporal data types of the SDK:</p>
 *
 * {@include.example io.sphere.sdk.meta.FormattingDocumentationTest#doNotUseSimpleDateFormat()}
 *
 * Use {@link java.time.LocalDate#format(DateTimeFormatter)} with {@link DateTimeFormatter} instead:
 *
 * {@include.example io.sphere.sdk.meta.FormattingDocumentationTest#formatLocalDate()}
 * {@include.example io.sphere.sdk.meta.FormattingDocumentationTest#formatLocalTime()}
 * {@include.example io.sphere.sdk.meta.FormattingDocumentationTest#formatZonedDateTime()}
 *
 *
 * <h3 id="money">Monetary Data</h3>
 *
 * The SDK has a dependency to Moneta, the reference implementation for <a href="https://java.net/projects/javamoney" target="_blank">JSR 354 - Currency and Money</a>.
 * We suggest to read the <a href="https://github.com/JavaMoney/jsr354-ri/blob/master/src/main/asciidoc/userguide.adoc" target="_blank">Moneta Userguide</a>.
 *
 * <h4 id="creating-money-values">Creating MonetaryAmount values</h4>
 *
 * The following example shows different ways to create the same money representation:
 *
 * {@include.example io.sphere.sdk.meta.FormattingDocumentationTest#createMoney()}
 *
 * <h4 id="formatting-money-values">Formatting MonetaryAmount values</h4>
 *
 * <p>For formatting you can just get a formatter per locale.</p>
 * <p>Example for Germany:</p>
 *
 * {@include.example io.sphere.sdk.meta.FormattingDocumentationTest#formatMoneyByGermanLocale()}
 *
 * <p>Example for the USA:</p>
 *
 * {@include.example io.sphere.sdk.meta.FormattingDocumentationTest#formatMoneyByUsLocale()}
 *
 * <p>You can also use the locale and force to use the currency symbol instead of the currency code:</p>
 * {@include.example io.sphere.sdk.meta.FormattingDocumentationTest#formatMoneyCustom()}
 *
 * <p>If that is not flexible enough you can specify a pattern:</p>
 *
 * {@include.example io.sphere.sdk.meta.FormattingDocumentationTest#formatMoneyCustom3()}
 *
 * <p>&#x00A4; is the currency sign which can be formatted with the styles {@link CurrencyStyle#CODE}, {@link CurrencyStyle#NAME}, {@link CurrencyStyle#NUMERIC_CODE} and {@link CurrencyStyle#SYMBOL}.</p>
 *
 * <p>Be aware of that there is a difference if you use a locale with a language or a locale with both language and country:</p>
 *
 * {@include.example io.sphere.sdk.meta.FormattingDocumentationTest#formatMoneyCustomLocales()}
 *
 * <h4 id="obtain-minor-unit-amount">Obtain the cent amounts</h4>
 *
 * Use {@link MonetaryQueries#convertMinorPart()} to get the <em>whole amount</em> as cents:
 *
 * {@include.example io.sphere.sdk.meta.FormattingDocumentationTest#getAmountInCents()}
 *
 * Use {@link MonetaryQueries#extractMinorPart()} to get the <em>minor unit (cent) part</em>:
 *
 * {@include.example io.sphere.sdk.meta.FormattingDocumentationTest#getCents()}
 *
 * <h4 id="major-unit-amount">Obtain the smooth amount</h4>
 *
 * Use {@link MonetaryQueries#extractMajorPart()} to get the smooth amount:
 *
 * {@include.example io.sphere.sdk.meta.FormattingDocumentationTest#getMajorUnitAmount()}
 *
 * <h4 id="sorting-money-values">Sorting MonetaryAmount values</h4>
 *
 * <p>By the amount:</p>
 * {@include.example io.sphere.sdk.meta.FormattingDocumentationTest#moneySortByAmount()}
 *
 * <p>By the currency and the amount:</p>
 *
 * {@include.example io.sphere.sdk.meta.FormattingDocumentationTest#moneySortByCurrencyAndAmount()}
 *
 * <h4 id="round-money-values">Rounding MonetaryAmount values</h4>
 *
 * <p>See <a href="https://github.com/JavaMoney/jsr354-ri/blob/master/src/main/asciidoc/userguide.adoc#23-rounding">JavaMoney Moneta User Guide - Rounding</a>.</p>
 *
 * {@include.example io.sphere.sdk.meta.FormattingDocumentationTest#roundMoneyDownExample()}
 * {@include.example io.sphere.sdk.meta.FormattingDocumentationTest#roundMoneyUpExample()}
 *
 *
 */
public final class FormattingDocumentation {
    private FormattingDocumentation() {
    }
}
