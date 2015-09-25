package io.sphere.sdk.search;

import javax.annotation.Nullable;

import static java.util.Arrays.asList;

public class MoneyCentAmountSearchModel<T, S extends DirectionlessSearchSortModel<T>> extends SortableRangeTermModel<T, S, Long> implements SearchSortModel<T, S> {

    public MoneyCentAmountSearchModel(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment, final SortBuilder<T, S> sortBuilder) {
        super(parent, pathSegment, sortBuilder);
    }

    @Override
    public RangedFilterSearchModel<T, Long> filtered() {
        return new RangedFilterSearchModel<>(this, null, TypeSerializer.ofMoneyCentAmount());
    }

    @Override
    public RangedFacetSearchModel<T, Long> faceted() {
        return new RangedFacetSearchModel<>(this, null, TypeSerializer.ofMoneyCentAmount());
    }

    @Override
    public FacetedSearchModel<T> facetedSearch() {
        return super.facetedSearch();
    }

    @Override
    public S sorted() {
        final MoneyCentAmountSearchModel<T, S> searchModel;
        if (hasPath(asList("variants", "price", "centAmount"))) {
            searchModel = new MoneyCentAmountSearchModel<>(null, "price", sortBuilder);
        } else {
            searchModel = this;
        }
        return sortBuilder.apply(searchModel);
    }

}
