package io.sphere.sdk.json;

import com.fasterxml.jackson.databind.module.SimpleModule;

import javax.money.*;

final class JavaMoneyModule extends SimpleModule {
    private static final long serialVersionUID = 0L;

    JavaMoneyModule() {
        addSerializer(MonetaryAmount.class, new MoneySerializer());
        addSerializer(CurrencyUnit.class, new CurrencyUnitSerializer());
        addDeserializer(MonetaryAmount.class, new MoneyDeserializer());
        addDeserializer(CurrencyUnit.class, new CurrencyUnitDeserializer());
    }
}
