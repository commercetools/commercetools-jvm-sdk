package io.sphere.sdk.search.model;

import javax.annotation.Nullable;

public class SortableSearchModel<T, S extends SortSearchModel<T>> extends SearchModelImpl<T> {
    protected final SearchSortFactory<T, S> searchSortFactory;

    protected SortableSearchModel(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment,
                               final SearchSortFactory<T, S> searchSortFactory) {
        super(parent, pathSegment);
        this.searchSortFactory = searchSortFactory;
    }

    public S sorted() {
        return sorted(this);
    }

    public S sorted(final String pathSegment) {
        return new SortableSearchModel<>(this, pathSegment, searchSortFactory).sorted();
    }

    public S sorted(final SortableSearchModel<T, S> searchModel) {
        return searchSortFactory.apply(searchModel);
    }

    @Override
    protected SortableSearchModel<T, S> searchModel(final String pathSegment) {
        return new SortableSearchModel<>(this, pathSegment, searchSortFactory);
    }

    protected SortableSearchModel<T, S> searchModel(final SearchModel<T> parent, final String pathSegment) {
        return new SortableSearchModel<>(parent, pathSegment, searchSortFactory);
    }

    protected LocalizedStringSortSearchModel<T, S> localizedStringSortSearchModel(final String pathSegment) {
        return new LocalizedStringSortSearchModel<>(this, pathSegment, searchSortFactory);
    }

    protected EnumSortSearchModel<T, S> enumSortSearchModel(final String pathSegment) {
        return new EnumSortSearchModel<>(this, pathSegment, searchSortFactory);
    }

    protected LocalizedEnumSortSearchModel<T, S> localizedEnumSortSearchModel(final String pathSegment) {
        return new LocalizedEnumSortSearchModel<>(this, pathSegment, searchSortFactory);
    }

    protected MoneySortSearchModel<T, S> moneySortSearchModel(final String pathSegment) {
        return new MoneySortSearchModel<>(this, pathSegment, searchSortFactory);
    }
}
