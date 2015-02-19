package io.sphere.sdk.states.queries;


import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.carts.ItemState;
import io.sphere.sdk.queries.DefaultModelQuery;
import io.sphere.sdk.queries.PagedQueryResult;


public class ItemStateQuery  extends DefaultModelQuery<ItemState> {

    private ItemStateQuery() {
        super(StateEndpoint.ENDPOINT.endpoint(), resultTypeReference());
    }

    public static ItemStateQuery of() {
        return new ItemStateQuery();
    }

    public static TypeReference<PagedQueryResult<ItemState>> resultTypeReference() {
        return new TypeReference<PagedQueryResult<ItemState>>(){
            @Override
            public String toString() {
                return "TypeReference<PagedQueryResult<WFItemState>>";
            }
        };
    }

    public static ItemStateQueryModel model() {
        return ItemStateQueryModel.get();
    }

}

