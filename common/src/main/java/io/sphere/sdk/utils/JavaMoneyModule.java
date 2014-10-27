package io.sphere.sdk.utils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;
import org.javamoney.moneta.CurrencyUnitBuilder;
import org.javamoney.moneta.Money;

import javax.money.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.apache.commons.lang3.StringUtils.isEmpty;

final class JavaMoneyModule extends SimpleModule {
    private static final long serialVersionUID = 0L;

    JavaMoneyModule() {
        addSerializer(MonetaryAmount.class, new MoneySerializer());
        addSerializer(CurrencyUnit.class, new CurrencyUnitSerializer());
        addDeserializer(MonetaryAmount.class, new MoneyDeserializer());
    }

    private static class MoneySerializer extends StdScalarSerializer<MonetaryAmount> {
        private MoneySerializer() {
            super(MonetaryAmount.class);
        }

        @Override
        public void serialize(final MonetaryAmount monetaryAmount, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
            final BigDecimal value = monetaryAmount.getNumber().numberValue(BigDecimal.class);
            final String currencyCode = monetaryAmount.getCurrency().getCurrencyCode();
            final MoneyRepresentation moneyRepresentation = new MoneyRepresentation(value, currencyCode);
            jsonGenerator.writeObject(moneyRepresentation);
        }
    }

    private static class MoneyDeserializer extends StdScalarDeserializer<MonetaryAmount> {
        private static final long serialVersionUID = 0L;

        private MoneyDeserializer() {
            super(MonetaryAmount.class);
        }

        @Override
        public MonetaryAmount deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            final MoneyRepresentation moneyRepresentation = deserializationContext.readValue(jsonParser, MoneyRepresentation.class);
            final BigDecimal amount = new BigDecimal(moneyRepresentation.getCentAmount()).divide(new BigDecimal(100));
            final String currencyCode = moneyRepresentation.getCurrencyCode();
            return MoneyImpl.of(amount, currencyCode);
        }
    }

    private static class MoneyRepresentation {
        private final long centAmount;
        private final String currencyCode;

        @JsonCreator
        private MoneyRepresentation(final long centAmount, final String currencyCode) {
            this.centAmount = centAmount;
            this.currencyCode = currencyCode;
        }

        /**
         * Creates a new Money instance.
         * Money can't represent cent fractions. The value will be rounded to nearest cent value using RoundingMode.HALF_EVEN.
         * @param amount the money value as fraction, e.g. 43.21 will be 4321 cents.
         * @param currencyCode the ISO 4217 currency code
         */
        @JsonIgnore
        public MoneyRepresentation(final BigDecimal amount, final String currencyCode) {
            this(amountToCents(amount), requireValidCurrencyCode(currencyCode));
        }

        public long getCentAmount() {
            return centAmount;
        }

        /**
         * @return The ISO 4217 currency code, for example "EUR" or "USD".
         */
        public String getCurrencyCode() {
            return currencyCode;
        }

        private static String requireValidCurrencyCode(final String currencyCode) {
            if (isEmpty(currencyCode))
                throw new IllegalArgumentException("Money.currencyCode can't be empty.");
            return currencyCode;
        }

        public static long amountToCents(final BigDecimal centAmount) {
            return centAmount.multiply(new BigDecimal(100)).setScale(0, RoundingMode.HALF_EVEN).longValue();
        }
    }

    private class CurrencyUnitSerializer extends StdScalarSerializer<CurrencyUnit> {
        private CurrencyUnitSerializer() {
            super(CurrencyUnit.class);
        }

        @Override
        public void serialize(final CurrencyUnit currencyUnit, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
            jsonGenerator.writeString(currencyUnit.getCurrencyCode());
        }
    }
}
