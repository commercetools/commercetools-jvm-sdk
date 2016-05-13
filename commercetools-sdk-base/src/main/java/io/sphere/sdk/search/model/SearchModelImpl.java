package io.sphere.sdk.search.model;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.search.FilterExpression;

import javax.annotation.Nullable;
import java.util.List;

public class SearchModelImpl<T> extends Base implements SearchModel<T> {
    @Nullable
    private final SearchModel<T> parent;
    @Nullable
    private final String pathSegment;

    public SearchModelImpl(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment) {
        this.parent = parent;
        this.pathSegment = pathSegment;
    }

    protected SearchModelImpl(@Nullable final String pathSegment) {
        this(null, pathSegment);
    }

    //for testing
    SearchModelImpl<T> appended(final String pathSegment) {
        return new SearchModelImpl<>(this, pathSegment) ;
    }

    @Nullable
    @Override
    public String getPathSegment() {
        return pathSegment;
    }

    @Nullable
    @Override
    public SearchModel<T> getParent() {
        return parent;
    }

    protected SearchModel<T> searchModel(final String pathSegment) {
        return new SearchModelImpl<>(this, pathSegment);
    }

    protected BooleanSearchModel<T> booleanSearchModel(final String pathSegment) {
        return new BooleanSearchModel<>(this, pathSegment);
    }

    protected StringSearchModel<T> stringSearchModel(final String pathSegment) {
        return new StringSearchModel<>(this, pathSegment);
    }

    protected NumberSearchModel<T> numberSearchModel(final String pathSegment) {
        return new NumberSearchModel<>(this, pathSegment);
    }

    protected DateSearchModel<T> dateSearchModel(final String pathSegment) {
        return new DateSearchModel<>(this, pathSegment);
    }

    protected TimeSearchModel<T> timeSearchModel(final String pathSegment) {
        return new TimeSearchModel<>(this, pathSegment);
    }

    protected DateTimeSearchModel<T> datetimeSearchModel(final String pathSegment) {
        return new DateTimeSearchModel<>(this, pathSegment);
    }

    protected LocalizedStringFilterSearchModel<T> localizedStringFilterSearchModel(final String pathSegment) {
        return new LocalizedStringFilterSearchModel<>(this, pathSegment);
    }

    protected LocalizedStringFacetSearchModel<T> localizedStringFacetSearchModel(final String pathSegment) {
        return new LocalizedStringFacetSearchModel<>(this, pathSegment);
    }

    protected LocalizedStringFacetedSearchSearchModel<T> localizedStringFacetedSearchSearchModel(final String pathSegment) {
        return new LocalizedStringFacetedSearchSearchModel<>(this, pathSegment);
    }

    protected EnumFilterSearchModel<T> enumFilterSearchModel(final String pathSegment) {
        return new EnumFilterSearchModel<>(this, pathSegment);
    }

    protected EnumFacetSearchModel<T> enumFacetSearchModel(final String pathSegment) {
        return new EnumFacetSearchModel<>(this, pathSegment);
    }

    protected EnumFacetedSearchSearchModel<T> enumFacetedSearchSearchModel(final String pathSegment) {
        return new EnumFacetedSearchSearchModel<>(this, pathSegment);
    }

    protected LocalizedEnumFilterSearchModel<T> localizedEnumFilterSearchModel(final String pathSegment) {
        return new LocalizedEnumFilterSearchModel<>(this, pathSegment);
    }

    protected LocalizedEnumFacetSearchModel<T> localizedEnumFacetSearchModel(final String pathSegment) {
        return new LocalizedEnumFacetSearchModel<>(this, pathSegment);
    }

    protected LocalizedEnumFacetedSearchSearchModel<T> localizedEnumFacetedSearchSearchModel(final String pathSegment) {
        return new LocalizedEnumFacetedSearchSearchModel<>(this, pathSegment);
    }

    protected MoneyFilterSearchModel<T> moneyFilterSearchModel(final String pathSegment) {
        return new MoneyFilterSearchModel<>(this, pathSegment);
    }

    protected MoneyFacetSearchModel<T> moneyFacetSearchModel(final String pathSegment) {
        return new MoneyFacetSearchModel<>(this, pathSegment);
    }

    protected MoneyFacetedSearchSearchModel<T> moneyFacetedSearchSearchModel(final String pathSegment) {
        return new MoneyFacetedSearchSearchModel<>(this, pathSegment);
    }

    protected ReferenceFilterSearchModel<T> referenceFilterSearchModel(final String pathSegment) {
        return new ReferenceFilterSearchModelImpl<>(this, pathSegment);
    }

    protected ReferenceFacetSearchModel<T> referenceFacetSearchModel(final String pathSegment) {
        return new ReferenceFacetSearchModelImpl<>(this, pathSegment);
    }

    protected ReferenceFacetedSearchSearchModel<T> referenceFacetedSearchSearchModel(final String pathSegment) {
        return new ReferenceFacetedSearchSearchModel<>(this, pathSegment);
    }

    protected ExistsAndMissingFilterSearchModelSupport<T> existsAndMissingFilterSearchModelSupport(final String fieldName) {
        return new ExistsAndMissingFilterSearchModelSupportImpl<T>(this, fieldName);
    }

    protected List<FilterExpression<T>> existsFilters() {
        return ExistsAndMissingFilterSearchModelSupportUtils.exists(this);
    }

    protected List<FilterExpression<T>> missingFilters() {
        return ExistsAndMissingFilterSearchModelSupportUtils.missing(this);
    }
}
