package io.sphere.sdk.search.model;

import io.sphere.sdk.models.Base;

import javax.annotation.Nullable;

public class SearchModelImpl<T> extends Base implements SearchModel<T> {
    @Nullable
    private final SearchModel<T> parent;
    @Nullable
    private final String pathSegment;

    protected SearchModelImpl(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment) {
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

    protected LocalizedStringFacetAndFilterSearchModel<T> localizedStringFacetAndFilterSearchModel(final String pathSegment) {
        return new LocalizedStringFacetAndFilterSearchModel<>(this, pathSegment);
    }

    protected EnumFilterSearchModel<T> enumFilterSearchModel(final String pathSegment) {
        return new EnumFilterSearchModel<>(this, pathSegment);
    }

    protected EnumFacetSearchModel<T> enumFacetSearchModel(final String pathSegment) {
        return new EnumFacetSearchModel<>(this, pathSegment);
    }

    protected EnumFacetAndFilterSearchModel<T> enumFacetAndFilterSearchModel(final String pathSegment) {
        return new EnumFacetAndFilterSearchModel<>(this, pathSegment);
    }

    protected LocalizedEnumFilterSearchModel<T> localizedEnumFilterSearchModel(final String pathSegment) {
        return new LocalizedEnumFilterSearchModel<>(this, pathSegment);
    }

    protected LocalizedEnumFacetSearchModel<T> localizedEnumFacetSearchModel(final String pathSegment) {
        return new LocalizedEnumFacetSearchModel<>(this, pathSegment);
    }

    protected LocalizedEnumFacetAndFilterSearchModel<T> localizedEnumFacetAndFilterSearchModel(final String pathSegment) {
        return new LocalizedEnumFacetAndFilterSearchModel<>(this, pathSegment);
    }

    protected MoneyFilterSearchModel<T> moneyFilterSearchModel(final String pathSegment) {
        return new MoneyFilterSearchModel<>(this, pathSegment);
    }

    protected MoneyFacetSearchModel<T> moneyFacetSearchModel(final String pathSegment) {
        return new MoneyFacetSearchModel<>(this, pathSegment);
    }

    protected MoneyFacetAndFilterSearchModel<T> moneyFacetAndFilterSearchModel(final String pathSegment) {
        return new MoneyFacetAndFilterSearchModel<>(this, pathSegment);
    }

    protected ReferenceFilterSearchModel<T> referenceFilterSearchModel(final String pathSegment) {
        return new ReferenceFilterSearchModel<>(this, pathSegment);
    }

    protected ReferenceFacetSearchModel<T> referenceFacetSearchModel(final String pathSegment) {
        return new ReferenceFacetSearchModel<>(this, pathSegment);
    }

    protected ReferenceFacetAndFilterSearchModel<T> referenceFacetAndFilterSearchModel(final String pathSegment) {
        return new ReferenceFacetAndFilterSearchModel<>(this, pathSegment);
    }
}
