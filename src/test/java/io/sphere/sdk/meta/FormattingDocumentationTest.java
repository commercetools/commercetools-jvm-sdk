package io.sphere.sdk.meta;

import io.sphere.sdk.models.DefaultCurrencyUnits;
import io.sphere.sdk.utils.MoneyImpl;
import org.javamoney.moneta.format.CurrencyStyle;
import org.javamoney.moneta.function.MonetaryFunctions;
import org.junit.Test;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.format.AmountFormatQueryBuilder;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.*;

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
}