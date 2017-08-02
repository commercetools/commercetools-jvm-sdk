package io.sphere.sdk.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;

import javax.money.MonetaryAmount;
import java.io.IOException;

final class MoneySerializer extends StdScalarSerializer<MonetaryAmount> {
    static final long serialVersionUID = 0L;

    MoneySerializer() {
        super(MonetaryAmount.class);
    }

    @Override
    public void serialize(final MonetaryAmount monetaryAmount, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws IOException {
        final MoneyRepresentation moneyRepresentation = new MoneyRepresentation(monetaryAmount);
        jsonGenerator.writeObject(moneyRepresentation);
    }
}
