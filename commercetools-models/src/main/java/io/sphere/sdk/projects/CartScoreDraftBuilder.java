package io.sphere.sdk.projects;


import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Builder;

public final class CartScoreDraftBuilder extends Base implements Builder<CartScoreDraft> {



    private CartScoreDraftBuilder(){

    }

    public static CartScoreDraftBuilder of(){
        CartScoreDraftBuilder draftBuilder =  new CartScoreDraftBuilder();
        return draftBuilder;
    }


    @Override
    public CartScoreDraft build() {
        return new CartScoreDraftImpl();
    }
}
