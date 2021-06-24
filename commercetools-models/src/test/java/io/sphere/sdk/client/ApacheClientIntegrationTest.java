package io.sphere.sdk.client;

import io.sphere.sdk.http.ApacheHttpClientAdapter;
import io.sphere.sdk.http.HttpClient;
import io.sphere.sdk.projects.Project;
import io.sphere.sdk.projects.queries.ProjectGet;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.test.SphereTestUtils;
import org.apache.hc.client5.http.impl.async.HttpAsyncClients;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class ApacheClientIntegrationTest extends IntegrationTest {
    @Test
    public void itWorks() throws Exception {
        //we cannot check this in TeamCity, this test is rather reserved for Travis ci
        if (!"false".equals(System.getenv("JVM_SDK_IT_SSL_VALIDATION"))) {
            final SphereClientConfig config = getSphereClientConfig();
            final HttpClient httpClient = ApacheHttpClientAdapter.of(HttpAsyncClients.createDefault());
            final SphereAccessTokenSupplier supplier = SphereAccessTokenSupplier.ofOneTimeFetchingToken(config, httpClient, false);
            final SphereClient client = SphereClient.of(config, httpClient, supplier);
            final Project project = client.execute(ProjectGet.of()).toCompletableFuture().join();
            assertThat(project.getKey()).isEqualTo(getSphereClientConfig().getProjectKey());
        }
    }


    /**
     * This Exception is caused by the fact that the client closes after trying api callback with invalid scope
     * **/
    @Test(expected = IllegalStateException.class )
    public void stopRetriesOnInvalidConfig() throws Exception{
        final SphereClientConfig clientConfig = getSphereClientConfig();
        final SphereClientConfig badConfig = SphereClientConfig.of(clientConfig.getProjectKey()+"LL",clientConfig.getClientId(),clientConfig.getClientSecret() ,clientConfig.getAuthUrl() ,clientConfig.getApiUrl()  );
        final HttpClient httpClient = newHttpClient();
        final SphereAccessTokenSupplier tokenSupplier = SphereAccessTokenSupplier.ofAutoRefresh(badConfig, httpClient, false);
        final SphereClient underlying = SphereClient.of(badConfig, httpClient, tokenSupplier);
        BlockingSphereClient localClient = BlockingSphereClient.of(underlying, 30, TimeUnit.SECONDS);
        try { assertProjectSettingsAreFine(localClient);} catch (Exception e) {}


        // In less then 100 retries the client should shut down (it should be only 1 try when we have an authorization problem,
        // but a race condition doesn't allow the interrupting thread from evaluating the number of retries immediately)

        SphereTestUtils.assertEventually(() -> {
            for (int i = 0; i < 1_000; i++) {
                try {
                    assertProjectSettingsAreFine(localClient);
                } catch (Exception e) {
                    if (e instanceof IllegalStateException) {
                        throw e;
                    }
                }
            }
        });




    }
}
