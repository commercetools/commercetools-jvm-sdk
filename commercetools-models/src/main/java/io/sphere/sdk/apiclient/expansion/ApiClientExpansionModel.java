package io.sphere.sdk.apiclient.expansion;

import io.sphere.sdk.apiclient.ApiClient;
import io.sphere.sdk.expansion.ExpansionPathContainer;

public interface ApiClientExpansionModel<T> extends ExpansionPathContainer<T> {

    static ApiClientExpansionModel<ApiClient> of() {
        return new ApiClientExpansionModelImpl<>();
    }

}
