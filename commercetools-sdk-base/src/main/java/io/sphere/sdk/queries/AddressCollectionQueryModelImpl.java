package io.sphere.sdk.queries;

import javax.annotation.Nullable;

final class AddressCollectionQueryModelImpl<T> extends QueryModelImpl<T> implements AddressCollectionQueryModel<T> {

    AddressCollectionQueryModelImpl(@Nullable final QueryModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }


    @Override
    public QueryPredicate<T> isEmpty() {
        return isEmptyCollectionQueryPredicate();
    }

    @Override
    public QueryPredicate<T> isNotEmpty() {
        return isNotEmptyCollectionQueryPredicate();
    }

    @Override
    public AddressQueryModel<T> address() {
        return addressQueryModel("");
    }
}
