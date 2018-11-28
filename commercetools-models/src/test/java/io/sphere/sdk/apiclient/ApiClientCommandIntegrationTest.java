package io.sphere.sdk.apiclient;

import io.sphere.sdk.apiclient.commands.ApiClientCreateCommand;

import io.sphere.sdk.apiclient.commands.ApiClientDeleteCommand;
import io.sphere.sdk.client.SphereScope;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static io.sphere.sdk.client.SphereProjectScope.*;
import static java.util.Arrays.*;

public class ApiClientCommandIntegrationTest extends IntegrationTest {


    @Test
    public void createApiClient(){
        final String projectKey = getSphereClientConfig().getProjectKey();
        final ApiClientCreateCommand createCommand  =ApiClientCreateCommand.of(ApiClientDraftBuilder.of("name", projectKey, MANAGE_MY_ORDERS, MANAGE_API_CLIENTS).build());
        final ApiClient res = client().executeBlocking(createCommand);assertThat(res).isNotNull();
        final String expectedScope = asList(MANAGE_MY_ORDERS, MANAGE_API_CLIENTS).stream().map(SphereScope::toScopeString).map(s -> s+":"+projectKey).collect(Collectors.joining(" "));
        assertThat(res.getScope()).isEqualTo(expectedScope);
        final ApiClient deletedRes =client().executeBlocking(ApiClientDeleteCommand.of(res));
        assertThat(deletedRes).isEqualToIgnoringGivenFields(res,"secret");
    }

}
