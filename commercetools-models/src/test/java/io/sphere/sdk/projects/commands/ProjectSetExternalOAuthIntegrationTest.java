package io.sphere.sdk.projects.commands;

import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.client.SphereAccessTokenSupplier;
import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.client.SphereClientConfig;
import io.sphere.sdk.http.HttpClient;
import io.sphere.sdk.projects.ExternalOAuth;
import io.sphere.sdk.projects.Project;
import io.sphere.sdk.projects.commands.updateactions.*;
import io.sphere.sdk.projects.queries.ProjectGet;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import static io.sphere.sdk.test.IntegrationTest.newHttpClient;
import static io.sphere.sdk.test.IntegrationTest.softAssert;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assume.assumeTrue;

/**
 * This test is in a separate test class and uses a separate api client configured from {@link SphereClientConfig#ofEnvironmentVariables(String)}
 * with the 'JVM_SDK_IT_OAUTH' to isolate the changes it does from the other tests.
 */
public class ProjectSetExternalOAuthIntegrationTest {

    private BlockingSphereClient clientForExternalOAuth2;

    @Before
    public void setup() {
        assumeHasExternalOAuth2Config();

        final SphereClientConfig config = SphereClientConfig.ofEnvironmentVariables("JVM_SDK_IT_OAUTH");
        final HttpClient httpClient = newHttpClient();
        final SphereAccessTokenSupplier tokenSupplier = SphereAccessTokenSupplier.ofAutoRefresh(config, httpClient, false);
        final SphereClient underlying = SphereClient.of(config, httpClient, tokenSupplier);
        clientForExternalOAuth2 = BlockingSphereClient.of(underlying, 30, TimeUnit.SECONDS);
    }

    @After
    public void unsetExternalAuthForPerformanceReasons(){
        assumeHasExternalOAuth2Config();

        final Project project = clientForExternalOAuth2.executeBlocking(ProjectGet.of());
        final ProjectUpdateCommand updateCommand = ProjectUpdateCommand.of(project, SetExternalOAuth.ofUnset());
        final Project updatedProject = clientForExternalOAuth2.executeBlocking(updateCommand);
        assertThat(updatedProject.getExternalOAuth()).isNull();
    }

    @Test
    public void setExternalAuth() throws Exception{
        assumeHasExternalOAuth2Config();

        final Project project = clientForExternalOAuth2.executeBlocking(ProjectGet.of());

        final URL url = new URL("https://invalid.cmo");
        final ExternalOAuth externalOAuth = ExternalOAuth.of("customheader: customValue", url);

        final ProjectUpdateCommand updateCommand = ProjectUpdateCommand.of(project, SetExternalOAuth.of(externalOAuth));

        final Project updatedProject = clientForExternalOAuth2.executeBlocking(updateCommand);

        softAssert(soft -> {
            soft.assertThat(updatedProject.getExternalOAuth()).isEqualToIgnoringGivenFields(externalOAuth,"authorizationHeader");
        });
    }

    private void assumeHasExternalOAuth2Config() {
        assumeTrue(System.getenv("JVM_SDK_IT_OAUTH_PROJECT_KEY") != null);
    }
}
