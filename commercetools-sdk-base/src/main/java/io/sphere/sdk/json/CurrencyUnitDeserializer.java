package io.sphere.sdk.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.io.IOException;

final class CurrencyUnitDeserializer extends StdScalarDeserializer<CurrencyUnit> {
    private static final long serialVersionUID = 0L;

    CurrencyUnitDeserializer() {
        super(CurrencyUnit.class);
    }

    @Override
    public CurrencyUnit deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
        final String currencyCode = deserializationContext.readValue(jsonParser, String.class);
        return Monetary.getCurrency(currencyCode);
    }
}
