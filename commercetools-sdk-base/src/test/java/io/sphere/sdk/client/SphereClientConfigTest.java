package io.sphere.sdk.client;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SphereClientConfigTest {
    @Test
    public void validateProjectKey() throws Exception {
        assertThatThrownBy(() -> SphereClientConfig.of(null, "here", "here", "here", "here"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("");
        assertThatThrownBy(() -> SphereClientConfig.of("", "here", "here", "here", "here"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("");
    }

    @Test
    public void validateClientId() throws Exception {
        assertThatThrownBy(() -> SphereClientConfig.of("here", null, "here", "here", "here"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("clientId");
        assertThatThrownBy(() -> SphereClientConfig.of("here", "", "here", "here", "here"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("clientId");
    }

    @Test
    public void validateClientSecret() throws Exception {
        assertThatThrownBy(() -> SphereClientConfig.of("here", "here", null, "here", "here"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("clientSecret");
        assertThatThrownBy(() -> SphereClientConfig.of("here", "here", "", "here", "here"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("clientSecret");
    }

    @Test
    public void validateAuthUrl() throws Exception {
        assertThatThrownBy(() -> SphereClientConfig.of("here", "here", "here", null, "here"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("authUrl");
        assertThatThrownBy(() -> SphereClientConfig.of("here", "here", "here", "", "here"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("authUrl");
    }

    @Test
    public void validateApiUrl() throws Exception {
        assertThatThrownBy(() -> SphereClientConfig.of("here", "here", "here", "here", null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("apiUrl");
        assertThatThrownBy(() -> SphereClientConfig.of("here", "here", "here", "here", ""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("apiUrl");
    }

    @Test
    public void withApiUrl() throws Exception {
        final SphereClientConfig initial = SphereClientConfig.of("a", "b", "b");
        assertThat(initial.getApiUrl()).isEqualTo("https://api.europe-west1.gcp.commercetools.com");
        final SphereClientConfig updated = initial.withApiUrl("another");
        assertThat(updated.getApiUrl()).isEqualTo("another");
    }
    
    @Test
    public void withAuthUrl() throws Exception {
        final SphereClientConfig initial = SphereClientConfig.of("a", "b", "b");
        assertThat(initial.getAuthUrl()).isEqualTo("https://auth.europe-west1.gcp.commercetools.com");
        final SphereClientConfig updated = initial.withAuthUrl("another");
        assertThat(updated.getAuthUrl()).isEqualTo("another");
    }

    @Test
    public void credentialsFromRaiseExceptionIfOneIsMissing() throws Exception {
        final String expectedMessage = "Missing environment variable 'foo_PROJECT_KEY'.\n" +
                "Usage:\n" +
                "export foo_PROJECT_KEY=\"YOUR project key\"\n" +
                "export foo_CLIENT_ID=\"YOUR client id\"\n" +
                "export foo_CLIENT_SECRET=\"YOUR client secret\"\n" +
                "#optional:\n" +
                "export foo_API_URL=\"https://api.europe-west1.gcp.commercetools.com\"\n" +
                "export foo_AUTH_URL=\"https://auth.europe-west1.gcp.commercetools.com\"\n" +
                "export foo_SCOPES=\"manage_project\"";
        assertThatThrownBy(() -> SphereClientConfigUtils.ofEnvironmentVariables("foo", key -> null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(expectedMessage);
    }

    @Test
    public void credentialsFromEnvironmentVariables() throws Exception {
        final SphereClientConfig config = SphereClientConfigUtils.ofEnvironmentVariables("foo", key -> {
            final Map<String, String> map = new HashMap<>();
            map.put("foo_API_URL", "e");
            map.put("foo_AUTH_URL", "d");
            map.put("foo_PROJECT_KEY", "a");
            map.put("foo_CLIENT_ID", "b");
            map.put("foo_CLIENT_SECRET", "c");
            return map.get(key);
        });
        assertThat(config).isEqualTo(SphereClientConfig.of("a", "b", "c", "d", "e"));
    }

    @Test
    public void allowMissingAuthUrl() throws Exception {
        final SphereClientConfig config = SphereClientConfigUtils.ofEnvironmentVariables("foo", key -> {
            final Map<String, String> map = new HashMap<>();
            map.put("foo_PROJECT_KEY", "a");
            map.put("foo_CLIENT_ID", "b");
            map.put("foo_CLIENT_SECRET", "c");
            return map.get(key);
        });
        assertThat(config).isEqualTo(SphereClientConfig.of("a", "b", "c", "https://auth.europe-west1.gcp.commercetools.com", "https://api.europe-west1.gcp.commercetools.com"));
    }
}
