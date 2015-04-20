package io.sphere.sdk.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;

import javax.money.CurrencyUnit;
import java.io.IOException;

final class CurrencyUnitSerializer extends StdScalarSerializer<CurrencyUnit> {
    static final long serialVersionUID = 0L;

    CurrencyUnitSerializer() {
        super(CurrencyUnit.class);
    }

    @Override
    public void serialize(final CurrencyUnit currencyUnit, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(currencyUnit.getCurrencyCode());
    }
}
