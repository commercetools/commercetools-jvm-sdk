package io.sphere.sdk.types.queries;

import io.sphere.sdk.queries.*;

public interface FieldsQueryModel<T> {
    /*
    date, time and set are missing
     */

    StringQueryModel<T> ofString(String name);

    LocalizedStringOptionalQueryModel<T> ofLocalizedString(String name);

    BooleanQueryModel<T> ofBoolean(String name);

    EnumQueryModel<T> ofEnum(String name);

    LocalizedEnumQueryModel<T> ofLocalizedEnum(String name);

    LongQueryModel<T> ofLong(String name);

    BigDecimalQueryModel<T> ofBigDecimal(String name);

    MoneyQueryModel<T> ofMoney(String name);

    TimestampSortingModel<T> ofDateTime(String name);

    StringCollectionQueryModel<T> ofStringCollection(String name);

    AnyReferenceQueryModel<T> ofReference(String name);
}
