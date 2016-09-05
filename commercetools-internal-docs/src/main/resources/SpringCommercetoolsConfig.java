import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.client.SphereClientConfig;
import io.sphere.sdk.client.SphereClientFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Configuration
public class CommercetoolsConfig {

    @Bean(destroyMethod = "close")
    public BlockingSphereClient sphereClient(
            @Value("${ctp.projectKey}") final String projectKey,
            @Value("${ctp.clientId}") final String clientId,
            @Value("${ctp.clientSecret}") final String clientSecret,
            @Value("${ctp.authUrl}") final String authUrl,
            @Value("${ctp.apiUrl}") final String apiUrl
    ) throws IOException {
        final SphereClientConfig config = SphereClientConfig.of(projectKey, clientId, clientSecret, authUrl, apiUrl);
        final SphereClient asyncClient = SphereClientFactory.of().createClient(config);
        return BlockingSphereClient.of(asyncClient, 20, TimeUnit.SECONDS);
    }
}
