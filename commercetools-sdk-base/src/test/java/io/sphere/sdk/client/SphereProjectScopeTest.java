package io.sphere.sdk.client;

import io.sphere.sdk.json.SphereJsonUtils;
import org.junit.Test;

import static io.sphere.sdk.client.SphereProjectScope.MANAGE_PROJECT;
import static org.assertj.core.api.Assertions.assertThat;

public class SphereProjectScopeTest {

    @Test
    public void toSphereScope() {
        assertThat(MANAGE_PROJECT.toScopeString()).isEqualTo("manage_project");
    }

    @Test
    public void ofScopeString() {
        assertThat(SphereProjectScope.ofScopeString("manage_project")).isEqualTo(MANAGE_PROJECT);
    }

    @Test
    public void scopeSerialization() {
        final String serializedScope = SphereJsonUtils.toPrettyJsonString(SphereProjectScope.ofScopeString("manage_project"));
        assertThat(serializedScope).isEqualTo("\"MANAGE_PROJECT\"");
    }

}