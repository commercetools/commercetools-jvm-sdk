package io.sphere.sdk.projects;


import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Builder;

public final class CartValueDraftBuilder extends Base implements Builder<CartValueDraft> {



    private CartValueDraftBuilder(){

    }

    public static CartValueDraftBuilder of(){
        CartValueDraftBuilder draftBuilder =  new CartValueDraftBuilder();
        return draftBuilder;
    }


    @Override
    public CartValueDraft build() {
        return new CartValueDraftImpl();
    }
}
