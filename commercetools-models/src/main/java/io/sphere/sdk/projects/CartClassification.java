package io.sphere.sdk.projects;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.sphere.sdk.annotations.ResourceValue;
import io.sphere.sdk.models.LocalizedString;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@ResourceValue
@JsonDeserialize(as = CartClassificationImpl.class)
public interface CartClassification extends ShippingRateInputType{

    @Override
    default String getType(){
        return "CartClassification";
    }

    Set<CartClassificationEntry> getValues();

    static CartClassification of(final Map<String, LocalizedString> values){
        final Set<CartClassificationEntry> cartClassificationEntries = values.entrySet().stream()
                .map(entry -> CartClassificationEntry.of(entry.getKey(),entry.getValue()))
                .collect(Collectors.toSet());

        return of(cartClassificationEntries);
    }

    static CartClassification of(final Set<CartClassificationEntry> values){
        return new CartClassificationImpl(values);
    }
}
