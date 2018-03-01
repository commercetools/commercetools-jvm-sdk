package io.sphere.sdk.meta;

import io.sphere.sdk.models.DefaultCurrencyUnits;
import io.sphere.sdk.utils.MoneyImpl;
import org.javamoney.moneta.format.CurrencyStyle;
import org.javamoney.moneta.function.MonetaryFunctions;
import org.javamoney.moneta.function.MonetaryOperators;
import org.javamoney.moneta.function.MonetaryQueries;
import org.junit.Test;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.format.AmountFormatQueryBuilder;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class FormattingDocumentationTest {
    @Test
    public void createMoney() throws Exception {
        final List<MonetaryAmount> monetaryAmounts = asList(
                MoneyImpl.of(new BigDecimal("1234.56"), Monetary.getCurrency("EUR")),
                MoneyImpl.of(new BigDecimal("1234.56"), DefaultCurrencyUnits.EUR),
                MoneyImpl.of("1234.56", "EUR"),
                MoneyImpl.ofCents(123456, Monetary.getCurrency("EUR")),
                MoneyImpl.ofCents(123456, Monetary.getCurrency(Locale.GERMANY)),//auto select per county
                MoneyImpl.ofCents(123456, DefaultCurrencyUnits.EUR),
                MoneyImpl.ofCents(123456, "EUR")
        );
        monetaryAmounts.forEach(monetaryAmount -> {
            assertThat(monetaryAmount.getCurrency()).isEqualTo(DefaultCurrencyUnits.EUR);
            final long centAmount = monetaryAmount.scaleByPowerOfTen(2).getNumber().longValue();
            assertThat(centAmount).isEqualTo(123456L);
        });
    }

    @Test
    public void formatMoneyByGermanLocale() throws Exception {
        final MonetaryAmountFormat format = MonetaryFormats.getAmountFormat(Locale.GERMANY);
        assertThat(format.format(MoneyImpl.ofCents(123456, "EUR"))).isEqualTo("1.234,56 EUR");
    }

    @Test
    public void formatMoneyByUsLocale() throws Exception {
        final MonetaryAmountFormat format = MonetaryFormats.getAmountFormat(Locale.US);
        assertThat(format.format(MoneyImpl.ofCents(123456, "USD"))).isEqualTo("USD1,234.56");
    }

    @Test
    public void formatMoneyCustom() throws Exception {
        final MonetaryAmountFormat format = MonetaryFormats.getAmountFormat(
                AmountFormatQueryBuilder.of(Locale.US)
                        .set(CurrencyStyle.SYMBOL)
                        .build()
        );
        final String formatted = format.format(MoneyImpl.ofCents(123456, "USD"));
        assertThat(formatted).isEqualTo("$1,234.56");
    }

    @Test
    public void formatMoneyCustomLocales() throws Exception {
        final Locale germany = Locale.GERMANY;
        assertThat(germany.toLanguageTag()).as("contains language and country").isEqualTo("de-DE");
        final Locale german = Locale.GERMAN;
        assertThat(german.toLanguageTag()).as("contains language").isEqualTo("de");

        final MonetaryAmount amount = MoneyImpl.ofCents(123456, "EUR");

        final MonetaryAmountFormat formatByLanguageAndCountry = MonetaryFormats.getAmountFormat(
                AmountFormatQueryBuilder.of(germany)
                        .set(CurrencyStyle.SYMBOL)
                        .build()
        );
        final MonetaryAmountFormat formatByJustLanguage = MonetaryFormats.getAmountFormat(
                AmountFormatQueryBuilder.of(german)
                        .set(CurrencyStyle.SYMBOL)
                        .build()
        );

        assertThat(formatByLanguageAndCountry.format(amount)).as("contains symbol").isEqualTo("1.234,56 €");
        assertThat(formatByJustLanguage.format(amount)).as("contains not symbol").isEqualTo("EUR 1.234,56");
    }

    @Test
    public void formatMoneyCustom2() throws Exception {
        final MonetaryAmountFormat format = MonetaryFormats.getAmountFormat(
                AmountFormatQueryBuilder.of(Locale.US)
                        .set(CurrencyStyle.SYMBOL)
                        .set("pattern", "#,##0.00### ¤")
                        .build()
        );
        final String formatted = format.format(MoneyImpl.ofCents(123456, "USD"));
        assertThat(formatted).isEqualTo("1,234.56 $");
    }

    @Test
    public void formatMoneyCustom3() throws Exception {
        final MonetaryAmountFormat format = MonetaryFormats.getAmountFormat(
                AmountFormatQueryBuilder.of(Locale.US)
                        .set(CurrencyStyle.SYMBOL)
                        .set("pattern", "###0.00### ¤")
                        .build()
        );
        final String formatted = format.format(MoneyImpl.ofCents(123456, "USD"));
        assertThat(formatted).isEqualTo("1234.56 $");
    }

    @Test
    public void moneySortByAmount() throws Exception {
        final MonetaryAmount a = MoneyImpl.ofCents(-200, "EUR");
        final MonetaryAmount b = MoneyImpl.ofCents(100, "EUR");
        final MonetaryAmount c = MoneyImpl.ofCents(5000, "EUR");
        final List<MonetaryAmount> monetaryAmounts = asList(c, a, b);
        final Comparator<MonetaryAmount> comparator = MonetaryFunctions.sortNumber();//useful for all the same currency
        final List<MonetaryAmount> sorted = monetaryAmounts.stream().sorted(comparator).collect(toList());
        assertThat(sorted).containsExactly(a, b, c);
    }

    @Test
    public void moneySortByCurrencyAndAmount() throws Exception {
        final MonetaryAmount a = MoneyImpl.ofCents(-200, "EUR");
        final MonetaryAmount b = MoneyImpl.ofCents(100, "EUR");
        final MonetaryAmount c = MoneyImpl.ofCents(5000, "EUR");
        final MonetaryAmount d = MoneyImpl.ofCents(100, "USD");
        final MonetaryAmount e = MoneyImpl.ofCents(5000, "USD");
        final List<MonetaryAmount> monetaryAmounts = asList(c, d, a, e, b);
        final Comparator<MonetaryAmount> comparator =
                MonetaryFunctions.sortCurrencyUnit().thenComparing(MonetaryFunctions.sortNumber());
        final List<MonetaryAmount> sorted = monetaryAmounts.stream().sorted(comparator).collect(toList());
        assertThat(sorted).isEqualTo(asList(a, b, c, d, e));
    }

    @Test
    public void doNotUseSimpleDateFormat() throws Exception {
        final LocalDate localDate = LocalDate.now();
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yy");
        assertThatThrownBy(() -> simpleDateFormat.format(localDate))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Cannot format given Object as a Date");
    }

    @Test
    public void formatLocalDate() throws Exception {
        final LocalDate localDate = LocalDate.of(2015, 7, 25);
        assertThat(localDate.format(DateTimeFormatter.ofPattern("dd.MM.yy"))).isEqualTo("25.07.15");
        assertThat(localDate.format(DateTimeFormatter.ISO_LOCAL_DATE)).isEqualTo("2015-07-25");
        assertThat(localDate.format(
                DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(Locale.GERMANY)
        )).isEqualTo("25.07.2015");
        assertThat(localDate.format(
                DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).withLocale(Locale.GERMANY)
        )).isEqualTo("Samstag, 25. Juli 2015");
    }

    @Test
    public void formatLocalTime() throws Exception {
        final LocalTime localTime = LocalTime.of(16, 35);
        assertThat(localTime.format(DateTimeFormatter.ofPattern("HH:mm"))).isEqualTo("16:35");
        assertThat(localTime.format(DateTimeFormatter.ISO_LOCAL_TIME)).isEqualTo("16:35:00");
        assertThat(localTime.format(
                DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM).withLocale(Locale.GERMANY)
        )).isEqualTo("16:35:00");
    }

    @Test
    public void formatZonedDateTime() throws Exception {
        final String timeAsString = "2015-07-09T07:46:40.230Z";//typical date format from the platform
        final ZonedDateTime dateTime = ZonedDateTime.parse(timeAsString);
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        assertThat(dateTime.format(formatter)).isEqualTo("09.07.2015 07:46");
        assertThat(dateTime.withZoneSameInstant(ZoneOffset.UTC).format(formatter))
                .overridingErrorMessage("Timezone is UTC, so without transforming the value into the right timezone, " +
                        "the displayed date is not accurate")
                .isEqualTo("09.07.2015 07:46");
        assertThat(dateTime.withZoneSameInstant(ZoneId.of("Europe/Berlin")).format(formatter))
                .isEqualTo("09.07.2015 09:46");
        assertThat(dateTime.withZoneSameInstant(ZoneId.of("Europe/London")).format(formatter))
                .isEqualTo("09.07.2015 08:46");
        assertThat(dateTime.withZoneSameInstant(ZoneId.of("America/New_York")).format(formatter))
                .isEqualTo("09.07.2015 03:46");
        assertThat(dateTime.withZoneSameInstant(ZoneId.of("America/Los_Angeles")).format(formatter))
                .isEqualTo("09.07.2015 00:46");
        assertThat(dateTime.withZoneSameInstant(ZoneId.of("Europe/Berlin")).format(
                DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).withLocale(Locale.GERMANY)
        )).isEqualTo("Donnerstag, 9. Juli 2015");
    }

    @Test
    public void getAmountInCents() throws Exception {
        final MonetaryAmount amount = MoneyImpl.of(new BigDecimal("1234.56"), "EUR");
        final Long centAmount = amount.query(MonetaryQueries.convertMinorPart());
        assertThat(centAmount).isEqualTo(123456);
    }

    @Test
    public void getCents() throws Exception {
        final MonetaryAmount amount = MoneyImpl.of(new BigDecimal("1234.56"), "EUR");
        final Long centPart = amount.with(MonetaryOperators.minorPart()).query(MonetaryQueries.extractMinorPart());
        assertThat(centPart).isEqualTo(56);
    }

    @Test
    public void getMajorUnitAmount() throws Exception {
        final MonetaryAmount amount = MoneyImpl.of(new BigDecimal("1234.56"), "EUR");
        final Long centAmount = amount.query(MonetaryQueries.extractMajorPart());
        assertThat(centAmount).isEqualTo(1234);
    }

    @Test
    public void roundMoneyUpExample() throws Exception {
        final MonetaryAmount amount = MoneyImpl.of(new BigDecimal("13.3749"), "EUR");
        final MonetaryAmount rounded = amount.with(Monetary.getDefaultRounding());
        assertThat(rounded).isEqualTo(MoneyImpl.of(new BigDecimal("13.37"), "EUR"));
    }

    @Test
    public void roundMoneyDownExample() throws Exception {
        final MonetaryAmount amount = MoneyImpl.of(new BigDecimal("13.3750"), "EUR");
        final MonetaryAmount rounded = amount.with(Monetary.getDefaultRounding());
        assertThat(rounded).isEqualTo(MoneyImpl.of(new BigDecimal("13.38"), "EUR"));
    }
}