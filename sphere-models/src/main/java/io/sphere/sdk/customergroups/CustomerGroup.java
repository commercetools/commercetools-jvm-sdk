package io.sphere.sdk.customergroups;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.models.Resource;
import io.sphere.sdk.models.Reference;

@JsonDeserialize(as = CustomerGroupImpl.class)
public interface CustomerGroup extends Resource<CustomerGroup> {

    String getName();

    @Override
    default Reference<CustomerGroup> toReference() {
        return Reference.of(typeId(), getId(), this);
    }

    static String typeId(){
        return "customer-group";
    }

    static TypeReference<CustomerGroup> typeReference(){
        return new TypeReference<CustomerGroup>() {
            @Override
            public String toString() {
                return "TypeReference<CustomerGroup>";
            }
        };
    }
}
