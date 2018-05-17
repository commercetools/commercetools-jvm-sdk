package io.sphere.sdk.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;
import io.sphere.sdk.utils.HighPrecisionMoneyImpl;

import javax.money.MonetaryAmount;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

final class MoneySerializer extends StdScalarSerializer<MonetaryAmount> {
    static final long serialVersionUID = 0L;

    MoneySerializer() {
        super(MonetaryAmount.class);
    }

    @Override
    public void serialize(final MonetaryAmount monetaryAmount, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws IOException {

        final MoneyRepresentation moneyRepresentation;

        if (monetaryAmount instanceof HighPrecisionMoneyImpl) {
            moneyRepresentation = new HighPrecisionMoneyRepresentation((HighPrecisionMoneyImpl) monetaryAmount);
        } else {
            moneyRepresentation = new CentPrecisionMoneyRepresentation(monetaryAmount);
        }

        jsonGenerator.writeObject(moneyRepresentation);
    }
}
