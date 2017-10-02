package io.sphere.sdk.projects;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.ResourceValue;
import io.sphere.sdk.models.LocalizedString;

@ResourceValue
@JsonDeserialize(as = CartClassificationEntryImpl.class)
public interface CartClassificationEntry {

    String getKey();

    LocalizedString getLabel();

    static CartClassificationEntry of(final String key, final LocalizedString label) {
        return new CartClassificationEntryImpl(key, label);
    }

}
