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
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import static org.junit.Assume.assumeTrue;

public class ProjectSetExternalOAuthIntegrationTest extends IntegrationTest {

    private BlockingSphereClient clientForExtenalOAuth2;

    @Before
    public void setup() {
        assumeTrue(System.getenv("JVM_SDK_OAUTH_PROJECT_KEY") != null);
        final SphereClientConfig config = SphereClientConfig.ofEnvironmentVariables("JVM_SDK_OAUTH");
        final HttpClient httpClient = newHttpClient();
        final SphereAccessTokenSupplier tokenSupplier = SphereAccessTokenSupplier.ofAutoRefresh(config, httpClient, false);
        final SphereClient underlying = SphereClient.of(config, httpClient, tokenSupplier);
        clientForExtenalOAuth2 = BlockingSphereClient.of(underlying, 30, TimeUnit.SECONDS);
    }

    @After
    public void unsetExternalAuthForPerformanceReasons(){
        final Project project = clientForExtenalOAuth2.executeBlocking(ProjectGet.of());
        final ProjectUpdateCommand updateCommand = ProjectUpdateCommand.of(project, SetExternalOAuth.ofUnset());
        final Project updatedProject = client().executeBlocking(updateCommand);
        Assertions.assertThat(updatedProject.getExternalOAuth()).isNull();
    }

    @Test
    public void execution() throws Exception{
        final Project project = client().executeBlocking(ProjectGet.of());

        final URL url = new URL("https://invalid.cmo");
        final ExternalOAuth externalOAuth = ExternalOAuth.of("customheader: customValue", url);

        final ProjectUpdateCommand updateCommand = ProjectUpdateCommand.of(project, SetExternalOAuth.of(externalOAuth));

        final Project updatedProject = client().executeBlocking(updateCommand);

        softAssert(soft -> {
            soft.assertThat(updatedProject.getExternalOAuth()).isEqualToIgnoringGivenFields(externalOAuth,"authorizationHeader");
        });
    }

}
