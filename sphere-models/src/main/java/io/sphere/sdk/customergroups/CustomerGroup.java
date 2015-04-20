package io.sphere.sdk.customergroups;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.models.DefaultModel;
import io.sphere.sdk.models.Reference;

import java.util.Optional;

@JsonDeserialize(as = CustomerGroupImpl.class)
public interface CustomerGroup extends DefaultModel<CustomerGroup> {

    String getName();

    @Override
    default Reference<CustomerGroup> toReference() {
        return Reference.of(typeId(), getId(), this);
    }

    public static String typeId(){
        return "customer-group";
    }

    public static TypeReference<CustomerGroup> typeReference(){
        return new TypeReference<CustomerGroup>() {
            @Override
            public String toString() {
                return "TypeReference<CustomerGroup>";
            }
        };
    }
}
