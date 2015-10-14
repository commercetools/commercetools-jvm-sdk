package io.sphere.sdk.search.model;

import javax.annotation.Nullable;

public class MoneyCentAmountSearchModel<T> extends RangeTermModelImpl<T, Long> {

    public MoneyCentAmountSearchModel(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment, TypeSerializer.ofMoneyCentAmount().getSerializer());
    }

//    @Override
//    public S sorted() {
//        final MoneyCentAmountSearchModel<T, S> searchModel;
//        if (hasPath(asList("variants", "price", "centAmount"))) {
//            searchModel = new MoneyCentAmountSearchModel<>(null, "price", sortBuilder);
//        } else {
//            searchModel = this;
//        }
//        return super.sortModel(searchModel);
//    }

}
