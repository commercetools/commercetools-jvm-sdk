package io.sphere.sdk.client;

import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.*;

public class TokensFacadeTest extends IntegrationTest {

    @Test//workaround to not put the if into the demo code
    public void testFetchAccessToken() {
        if (!"false".equals(System.getenv("JVM_SDK_IT_SSL_VALIDATION"))) {
            fetchAccessToken();
        }
    }

    public void fetchAccessToken() {
        final SphereAuthConfig config = getSphereClientConfig();
        final CompletionStage<String> stringCompletionStage = TokensFacade.fetchAccessToken(config);
        final String accessToken = SphereClientUtils.blockingWait(stringCompletionStage, 2, TimeUnit.SECONDS);
        assertThat(accessToken).isNotEmpty();
    }
}