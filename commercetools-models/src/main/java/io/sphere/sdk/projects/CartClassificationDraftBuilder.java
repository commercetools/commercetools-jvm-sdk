package io.sphere.sdk.projects;


import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Builder;

import java.util.Set;

public final class CartClassificationDraftBuilder extends Base implements Builder<CartClassificationDraft> {


    private Set<CartClassificationEntry> values;

    private CartClassificationDraftBuilder(){

    }

    public static CartClassificationDraftBuilder of(final Set<CartClassificationEntry> values){
        CartClassificationDraftBuilder draftBuilder =  new CartClassificationDraftBuilder();
        draftBuilder.values(values);
        return draftBuilder;
    }

    public static CartClassificationDraftBuilder of(final CartClassificationDraft cartClassificationDraft){
        return of(cartClassificationDraft.getValues());
    }

    public void values(Set<CartClassificationEntry> values) {
        this.values = values;
    }

    public Set<CartClassificationEntry> getValues() {
        return values;
    }

    @Override
    public CartClassificationDraft build() {
        return new CartClassificationDraftImpl(values);
    }


}
