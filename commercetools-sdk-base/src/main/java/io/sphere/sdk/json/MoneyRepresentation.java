package io.sphere.sdk.json;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.javamoney.moneta.spi.DefaultRoundingProvider;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.money.MonetaryRounding;
import javax.money.RoundingQueryBuilder;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

import static org.apache.commons.lang3.StringUtils.isEmpty;


@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type",
        defaultImpl = CentPrecisionMoneyRepresentation.class
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CentPrecisionMoneyRepresentation.class, name = "centPrecision"),
        @JsonSubTypes.Type(value = HighPrecisionMoneyRepresentation.class, name = "highPrecision")
})
abstract class MoneyRepresentation {


    private final Long centAmount;
    private final String currencyCode;

    protected static final DefaultRoundingProvider ROUNDING_PROVIDER = new DefaultRoundingProvider();

    protected MoneyRepresentation(final Long centAmount, final String currencyCode) {
        this.centAmount = centAmount;
        this.currencyCode = currencyCode;
    }

    public Long getCentAmount() {
        return centAmount;
    }

    /**
     * @return The ISO 4217 currency code, for example "EUR" or "USD".
     */
    public String getCurrencyCode() {
        return currencyCode;
    }

    protected static String requireValidCurrencyCode(final String currencyCode) {
        if (isEmpty(currencyCode))
            throw new IllegalArgumentException("Money.currencyCode can't be empty.");
        return currencyCode;
    }

    protected static long amountToCents(final MonetaryAmount monetaryAmount) {
        final MonetaryRounding ROUNDING =
                ROUNDING_PROVIDER.getRounding(RoundingQueryBuilder.of().setRoundingName("default").setCurrency(monetaryAmount.getCurrency())
                        .build());
        return monetaryAmount
                .with(ROUNDING)
                .query(amount -> queryFrom(monetaryAmount).longValue());
    }


    protected static BigDecimal queryFrom(final MonetaryAmount amount) {
        Objects.requireNonNull(amount, "Amount required.");
        BigDecimal number = amount.getNumber().numberValue(BigDecimal.class);
        CurrencyUnit cur = amount.getCurrency();
        int scale = cur.getDefaultFractionDigits();
        if(scale<0){
            scale = 0;
        }
        number = number.setScale(scale, RoundingMode.HALF_EVEN);
        return number.movePointRight(number.scale());
    }

    protected static BigDecimal queryFrom(final MonetaryAmount amount, final int fractionsDigit) {
        Objects.requireNonNull(amount, "Amount required.");
        BigDecimal number = amount.getNumber().numberValue(BigDecimal.class);
        number = number.setScale(fractionsDigit, RoundingMode.HALF_EVEN);
        return number.movePointRight(number.scale());
    }

}
