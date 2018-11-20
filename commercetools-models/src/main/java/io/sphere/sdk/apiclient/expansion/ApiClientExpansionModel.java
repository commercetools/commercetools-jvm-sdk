package io.sphere.sdk.apiclient.expansion;

import io.sphere.sdk.apiclient.ApiClient;

public interface ApiClientExpansionModel<T> {

    static ApiClientExpansionModel<ApiClient> of() {
        return new ApiClientExpansionModelImpl<>();
    }

}
