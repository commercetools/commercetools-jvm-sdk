package io.sphere.sdk.types.queries;

import io.sphere.sdk.queries.*;

final class FieldsQueryModelImpl<T> extends QueryModelImpl<T> implements FieldsQueryModel<T> {
    public FieldsQueryModelImpl(final QueryModel<T> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public StringQueryModel<T> ofString(final String name) {
        return stringModel(name);
    }

    @Override
    public LocalizedStringOptionalQueryModel<T> ofLocalizedString(final String name) {
        return localizedStringQuerySortingModel(name);
    }

    @Override
    public BooleanQueryModel<T> ofBoolean(final String name) {
        return booleanModel(name);
    }

    @Override
    public EnumQueryModel<T> ofEnum(final String name) {
        return enumQueryModel(name);
    }

    @Override
    public LocalizedEnumQueryModel<T> ofLocalizedEnum(final String name) {
        return localizedEnumQueryModel(name);
    }

    @Override
    public LongQueryModel<T> ofLong(final String name) {
        return longModel(name);
    }

    @Override
    public BigDecimalQueryModel<T> ofBigDecimal(final String name) {
        return bigDecimalModel(name);
    }

    @Override
    public MoneyQueryModel<T> ofMoney(final String name) {
        return moneyModel(name);
    }

    @Override
    public TimestampSortingModel<T> ofDateTime(final String name) {
        return timestampSortingModel(name);
    }

    @Override
    public StringCollectionQueryModel<T> ofStringCollection(final String name) {
        return stringCollectionModel(name);
    }

    @Override
    public AnyReferenceQueryModel<T> ofReference(final String name) {
        return anyReferenceModel(name);
    }
}
