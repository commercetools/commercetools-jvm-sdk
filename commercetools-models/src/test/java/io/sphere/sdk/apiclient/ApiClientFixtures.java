package io.sphere.sdk.apiclient;

import io.sphere.sdk.apiclient.commands.ApiClientCreateCommand;
import io.sphere.sdk.apiclient.commands.ApiClientDeleteCommand;
import io.sphere.sdk.client.*;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public final class ApiClientFixtures {

    public static final String GENERATED_CLIENT_NAME = "GENERATED_CLIENT";

    public static void withApiClient(final BlockingSphereClient client,final ApiClientDraft apiClientDraft,final Consumer<ApiClient> consumer){
        ApiClient apiClient = client.executeBlocking(ApiClientCreateCommand.of(apiClientDraft));
        consumer.accept(apiClient);
        client.executeBlocking(ApiClientDeleteCommand.of(apiClient));
    }

    public static void withApiClient(final BlockingSphereClient client, final List<SphereScope> scopes, final Consumer<ApiClient> consumer){
        final SphereApiConfig config = client.getConfig();
        final ApiClientDraft apiClientDraft = ApiClientDraftBuilder.of(GENERATED_CLIENT_NAME, config.getProjectKey(), scopes).build();
        withApiClient(client,apiClientDraft,consumer);
    }

    public static SphereAuthConfig toSphereAuthConfig(final SphereAuthConfig sphereAuthConfig, final ApiClient apiClient) {

        final List<SphereScope> scopes = Arrays.stream(apiClient.getScope()
                .split("\\s")).map(s -> s.split(":"))
                .map(strings -> strings[0])
                .map(SphereProjectScope::of)
                .collect(Collectors.toList());

        return SphereAuthConfigBuilder.ofKeyIdSecret(apiClient.getProjectKey(),apiClient.getId(),apiClient.getSecret())
                .scopes(scopes)
                .authUrl(sphereAuthConfig.getAuthUrl())
                .build();
    }
    public static SphereClientConfig toSphereClientConfig(final SphereClientConfig sphereClientConfig, final ApiClient apiClient) {

        final List<SphereScope> scopes = Arrays.stream(apiClient.getScope()
                .split("\\s")).map(s -> s.split(":"))
                .map(strings -> strings[0])
                .map(SphereProjectScope::of)
                .collect(Collectors.toList());

        return SphereClientConfigBuilder.ofKeyIdSecret(apiClient.getProjectKey(),apiClient.getId(),apiClient.getSecret())
                .scopes(scopes)
                .authUrl(sphereClientConfig.getAuthUrl())
                .apiUrl(sphereClientConfig.getApiUrl())
                .build();
    }
    
    public static SphereAuthConfig toMySphereClientConfig(final SphereAuthConfig sphereAuthConfig, final ApiClient apiClient) {
        final List<SphereScope> scopes = Arrays.stream(apiClient.getScope()
                .split("\\s")).map(s -> s.split(":"))
                .map(strings -> strings[0])
                .map(SphereProjectScope::of)
                .collect(Collectors.toList());

        return SphereAuthConfigBuilder.ofKeyIdSecret(apiClient.getProjectKey(),apiClient.getId(),apiClient.getSecret())
                .scopes(scopes)
                .authUrl(sphereAuthConfig.getAuthUrl())
                .build();
    }
}
