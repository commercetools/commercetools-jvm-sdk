package io.sphere.sdk.queries;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Point;
import io.sphere.sdk.models.SphereEnumeration;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

import static io.sphere.sdk.queries.StringQuerySortingModel.normalize;
import static io.sphere.sdk.utils.SphereInternalUtils.toStream;
import static java.util.stream.Collectors.toList;

public class QueryModelImpl<T> extends Base implements QueryModel<T> {
    @Nullable
    private final QueryModel<T> parent;
    @Nullable
    private final String pathSegment;

    protected QueryModelImpl(@Nullable final QueryModel<T> parent, @Nullable final String pathSegment) {
        this.parent = parent;
        this.pathSegment = pathSegment;
    }

    @Override
    @Nullable
    public String getPathSegment() {
        return pathSegment;
    }

    @Nullable
    @Override
    public QueryModel<T> getParent() {
        return parent;
    }

    protected CurrencyCodeQueryModel<T> currencyCodeModel(final String pathSegment) {
        return new CurrencyCodeQueryModelImpl<>(this, pathSegment);
    }

    protected MoneyQueryModel<T> moneyQueryModel(final String pathSegment) {
        return moneyModel(pathSegment);
    }

    protected MoneyQueryModel<T> moneyModel(final String pathSegment) {
        return new MoneyQueryModelImpl<>(this, pathSegment);
    }

    protected AnyReferenceQueryModel<T> anyReferenceQueryModel(final String pathSegment) {
        return new AnyReferenceQueryModelImpl <>(this, pathSegment);
    }

    protected AnyReferenceQueryModel<T> anyReferenceModel(final String pathSegment) {
        return new AnyReferenceQueryModelImpl <>(this, pathSegment);
    }

    protected <R> ReferenceQueryModel<T, R> referenceModel(final String pathSegment) {
        return new ReferenceQueryModelImpl<>(this, pathSegment);
    }

    protected <R> ReferenceQueryModel<T, R> referenceQueryModel(final String pathSegment) {
        return new ReferenceQueryModelImpl<>(this, pathSegment);
    }

    protected <R> ReferenceOptionalQueryModel<T, R> referenceOptionalModel(final String pathSegment) {
        return new ReferenceOptionalQueryModelImpl<>(this, pathSegment);
    }

    protected <R> ReferenceOptionalQueryModel<T, R> referenceOptionalQueryModel(final String pathSegment) {
        return referenceOptionalModel(pathSegment);
    }

    protected <R> ReferenceCollectionQueryModel<T, R> referenceCollectionQueryModel(final String pathSegment) {
        return new ReferenceCollectionQueryModelImpl<>(this, pathSegment);
    }

    protected <R> ReferenceCollectionQueryModel<T, R> referenceCollectionModel(final String pathSegment) {
        return new ReferenceCollectionQueryModelImpl<>(this, pathSegment);
    }

    protected <E extends SphereEnumeration> SphereEnumerationQueryModelImpl<T, E> sphereEnumerationQueryModel(final String pathSegment) {
        return enumerationQueryModel(pathSegment);
    }

    protected <E extends SphereEnumeration> SphereEnumerationQueryModelImpl<T, E> enumerationQueryModel(final String pathSegment) {
        return new SphereEnumerationQueryModelImpl<>(this, pathSegment);
    }

    protected <E extends SphereEnumeration> SphereEnumerationQueryModelImpl<T, E> sphereEnumerationCollectionQueryModel(final String pathSegment) {
        return enumerationQueryModel(pathSegment);
    }

    protected StringQuerySortingModel<T> stringModel(@Nullable final QueryModel<T> parent, final String pathSegment) {
        return new StringQuerySortingModelImpl<>(parent, pathSegment);
    }

    protected StringQuerySortingModel<T> stringQuerySortingModel(final String pathSegment) {
        return stringModel(pathSegment);
    }

    protected StringQuerySortingModel<T> stringModel(final String pathSegment) {
        return stringModel(this, pathSegment);
    }

    protected StringCollectionQueryModel<T> stringCollectionQueryModel(final String pathSegment) {
        return new StringCollectionQueryModelImpl<>(this, pathSegment);
    }

    protected StringCollectionQueryModel<T> stringCollectionModel(final String pathSegment) {
        return new StringCollectionQueryModelImpl<>(this, pathSegment);
    }

    protected BooleanQueryModel<T> booleanModel(@Nullable final QueryModel<T> parent, final String pathSegment) {
        return new BooleanQueryModelImpl<>(parent, pathSegment);
    }

    protected BooleanQueryModel<T> booleanModel(final String pathSegment) {
        return booleanModel(this, pathSegment);
    }

    protected BooleanQueryModel<T> booleanQueryModel(final String pathSegment) {
        return booleanModel(this, pathSegment);
    }

    protected CountryQueryModel<T> countryQueryModel(final String pathSegment) {
        return new CountryQueryModelImpl<>(this, pathSegment);
    }

    protected LongQuerySortingModel<T> longModel(@Nullable final QueryModel<T> parent, final String pathSegment) {
        return new LongQuerySortingModelImpl<>(parent, pathSegment);
    }

    protected LongQuerySortingModel<T> longQuerySortingModel(final String pathSegment) {
        return longModel(this, pathSegment);
    }

    protected LongQuerySortingModel<T> longModel(final String pathSegment) {
        return longModel(this, pathSegment);
    }

    protected BigDecimalQuerySortingModel<T> bigDecimalModel(@Nullable final QueryModel<T> parent, final String pathSegment) {
        return new BigDecimalQuerySortingModelImpl<>(parent, pathSegment);
    }

    protected BigDecimalQuerySortingModel<T> bigDecimalQuerySortingModel(final String pathSegment) {
        return bigDecimalModel(this, pathSegment);
    }

    protected BigDecimalQuerySortingModel<T> bigDecimalModel(final String pathSegment) {
        return bigDecimalModel(this, pathSegment);
    }


    protected IntegerQuerySortingModel<T> integerQuerySortingModel(final String pathSegment) {
        return integerModel(this, pathSegment);
    }

    protected IntegerQuerySortingModel<T> integerModel(final String pathSegment) {
        return integerModel(this, pathSegment);
    }

    protected IntegerQuerySortingModel<T> integerModel(@Nullable final QueryModel<T> parent, final String pathSegment) {
        return new IntegerQuerySortingModelImpl<>(parent, pathSegment);
    }

    protected DoubleQuerySortingModel<T> doubleModel(final String pathSegment) {
        return new DoubleQuerySortingModelImpl<>(this, pathSegment);
    }

    protected LocalizedStringQuerySortingModelImpl<T> localizedStringQuerySortingModel(final String pathSegment) {
        return new LocalizedStringQuerySortingModelImpl<>(this, pathSegment);
    }

    protected LocalizedStringQuerySortingModel<T> localizedStringQueryModel(final String pathSegment) {
        return new LocalizedStringQuerySortingModelImpl<>(this, pathSegment);
    }

    protected LocalizedStringQuerySortingModelImpl<T> localizedStringOptionalQueryModel(final String pathSegment) {
        return new LocalizedStringQuerySortingModelImpl<>(this, pathSegment);
    }

    protected final TimestampSortingModel<T> timestampSortingModel(final String pathSegment) {
        return new TimestampSortingModelImpl<>(this, pathSegment);
    }

    protected final AddressQueryModel<T> addressModel(final String pathSegment) {
        return new AddressQueryModelImpl<>(this, pathSegment);
    }

    protected final AddressQueryModel<T> addressQueryModel(final String pathSegment) {
        return addressModel(pathSegment);
    }

    protected final AddressCollectionQueryModel<T> addressCollectionQueryModel(final String pathSegment){
        return new AddressCollectionQueryModelImpl<>(parent, pathSegment);
    }

    protected final LocaleQuerySortingModel<T> localeQueryModel(final String pathSegment) {
        return new LocaleQuerySortingModelImpl<>(this, pathSegment);
    }

    protected final LocaleQuerySortingModel<T> localeQuerySortingModel(final String pathSegment) {
        return new LocaleQuerySortingModelImpl<>(this, pathSegment);
    }

    @SuppressWarnings("unchecked")
    protected <V> QueryPredicate<T> isPredicate(final V value) {
        final V normalizedValue = value instanceof String ? (V) normalize((String) value) : value;
        return ComparisonQueryPredicate.ofIsEqualTo(this, normalizedValue);
    }

    protected <V> QueryPredicate<T> isNotPredicate(final V value) {
        return ComparisonQueryPredicate.ofIsNotEqualTo(this, value);
    }

    protected QueryPredicate<T> isNotPredicate(final String value) {
        return ComparisonQueryPredicate.ofIsNotEqualTo(this, normalize(value));
    }

    protected <V> QueryPredicate<T> isInPredicate(final Iterable<V> args) {
        return new IsInQueryPredicate<>(this, args);
    }

    protected QueryPredicate<T> withinCirclePredicate(final Point center, final Double radius) {
        return new WithinCircleQueryPredicate<T>(this, center, radius);
    }

    protected <V> QueryPredicate<T> isNotInPredicate(final Iterable<V> args) {
        return new IsNotInQueryPredicate<>(this, args);
    }

    protected <V> QueryPredicate<T> isGreaterThanPredicate(final V value) {
        return ComparisonQueryPredicate.ofIsGreaterThan(this, value);
    }

    protected <V> QueryPredicate<T> isLessThanPredicate(final V value) {
        return ComparisonQueryPredicate.ofIsLessThan(this, value);
    }

    protected <V> QueryPredicate<T> isLessThanOrEqualToPredicate(final V value) {
        return ComparisonQueryPredicate.ofIsLessThanOrEqualTo(this, value);
    }

    protected <V> QueryPredicate<T> isGreaterThanOrEqualToPredicate(final V value) {
        return ComparisonQueryPredicate.ofGreaterThanOrEqualTo(this, value);
    }

    protected QueryPredicate<T> isPresentPredicate() {
        return new OptionalQueryPredicate<>(this, true);
    }

    protected QueryPredicate<T> isNotPresentPredicate() {
        return new OptionalQueryPredicate<>(this, false);
    }

    protected <X> QueryPredicate<T> embedPredicate(final QueryPredicate<X> embeddedPredicate) {
        return new EmbeddedQueryPredicate<>(this, embeddedPredicate);
    }

    protected QueryPredicate<T> isEmptyCollectionQueryPredicate() {
        return new QueryModelQueryPredicate<T>(this){
            @Override
            protected String render() {
                return " is empty";
            }
        };
    }

    protected QueryPredicate<T> isNotEmptyCollectionQueryPredicate() {
        return new QueryModelQueryPredicate<T>(this){
            @Override
            protected String render() {
                return " is not empty";
            }
        };
    }

    protected List<String> normalizeIterable(final Iterable<String> items) {
        return toStream(items)
                .map(StringQuerySortingModel::normalize).collect(toList());
    }

    public DirectionlessQuerySort<T> sort() {
        return new DirectionlessQuerySort<>(this);
    }

    protected  <E extends SphereEnumeration> List<String> transformSphereEnumeration(final Iterable<E> items) {
        return toStream(items)
                .map(enumValue -> enumValue.toSphereName())
                .map(StringQuerySortingModel::normalize)
                .collect(Collectors.toList());
    }

    protected EnumQueryModel<T> enumQueryModel(final String name) {
        return new EnumLikeQueryModelImpl<>(this, name);
    }

    protected LocalizedEnumQueryModel<T> localizedEnumQueryModel(final String name) {
        return new EnumLikeQueryModelImpl<>(this, name);
    }

    protected GeoJSONQueryModel<T> geoJSONQueryModel(final String pathName) {
        return new GeoJSONQueryModelImpl<>(this, pathName);
    }

    protected KeyReferenceQueryModelImpl<T> keyReferenceQueryModel(final String pathSegment){
        return new KeyReferenceQueryModelImpl<>(this, pathSegment);
    }
}
