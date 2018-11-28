package io.sphere.sdk.apiclient.commands;

import io.sphere.sdk.apiclient.ApiClient;
import io.sphere.sdk.commands.DeleteCommand;

public interface ApiClientDeleteCommand extends DeleteCommand<ApiClient> {

    static ApiClientDeleteCommand of(final ApiClient apiClient){
        return new ApiClientDeleteCommandImpl(apiClient.getId());
    }

    static ApiClientDeleteCommand of(final String  id){
        return new ApiClientDeleteCommandImpl(id);
    }
}
