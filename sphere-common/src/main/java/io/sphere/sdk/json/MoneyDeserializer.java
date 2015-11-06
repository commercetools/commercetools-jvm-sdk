package io.sphere.sdk.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import io.sphere.sdk.utils.MoneyImpl;

import javax.money.MonetaryAmount;
import java.io.IOException;

final class MoneyDeserializer extends StdScalarDeserializer<MonetaryAmount> {
    private static final long serialVersionUID = 0L;

    MoneyDeserializer() {
        super(MonetaryAmount.class);
    }

    @Override
    public MonetaryAmount deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
        final MoneyRepresentation moneyRepresentation = deserializationContext.readValue(jsonParser, MoneyRepresentation.class);
        final String currencyCode = moneyRepresentation.getCurrencyCode();
        return MoneyImpl.ofCents(moneyRepresentation.getCentAmount(), currencyCode);
    }
}
