package io.sphere.sdk.apiclient.queries;

import io.sphere.sdk.annotations.HasQueryModelImplementation;
import io.sphere.sdk.apiclient.ApiClient;
import io.sphere.sdk.queries.StringQuerySortingModel;
import io.sphere.sdk.queries.TimestampSortingModel;

@HasQueryModelImplementation()
public interface ApiClientQueryModel {

    static ApiClientQueryModel of() {
        return new ApiClientQueryModelImpl(null, null);
    }

    StringQuerySortingModel<ApiClient> id();

    StringQuerySortingModel<ApiClient> name();

    StringQuerySortingModel<ApiClient> scope();

    TimestampSortingModel<ApiClient> createdAt();

    TimestampSortingModel<ApiClient> lastUsedAt();

    StringQuerySortingModel<ApiClient> secret();


}