package io.sphere.sdk.carts;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.ResourceValue;
import io.sphere.sdk.models.LocalizedString;

@ResourceValue
@JsonDeserialize(as = ClassificationShippingRateInputDraftImpl.class)
public interface ClassificationShippingRateInputDraft extends ShippingRateInputDraft {

    @Override
    default String getType() {
        return "Classification";
    }

    String getKey();


    static ClassificationShippingRateInputDraft of(final String key) {
        return new ClassificationShippingRateInputDraftImpl(key);
    }

}
