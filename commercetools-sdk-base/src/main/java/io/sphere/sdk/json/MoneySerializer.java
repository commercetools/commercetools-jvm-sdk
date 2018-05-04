package io.sphere.sdk.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;

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

        new BigDecimal("10.00001").scale();
        final MoneyRepresentation moneyRepresentation = new CentPrecisionMoneyRepresentation(monetaryAmount);//MoneyRepresentation.of(monetaryAmount);
        jsonGenerator.writeObject(moneyRepresentation);
    }
}
