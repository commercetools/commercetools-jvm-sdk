package io.sphere.sdk.projects.queries;

import io.sphere.sdk.projects.Project;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class ProjectGetTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        final Project project = client().executeBlocking(ProjectGet.of());
        assertThat(project.getKey()).isEqualTo(getSphereClientConfig().getProjectKey());
        assertThat(project.getName()).overridingErrorMessage("name").isNotEmpty();
        assertThat(project.getCountries()).overridingErrorMessage("countries").isNotEmpty();
        assertThat(project.getLanguages()).overridingErrorMessage("languages").isNotEmpty();
        assertThat(project.getCreatedAt()).overridingErrorMessage("createdAt").isNotNull();
        assertThat(project.getTrialUntil()).overridingErrorMessage("trialUntil").isNotNull();
    }
}