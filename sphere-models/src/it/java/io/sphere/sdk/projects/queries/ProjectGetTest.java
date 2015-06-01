package io.sphere.sdk.projects.queries;

import io.sphere.sdk.projects.Project;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class ProjectGetTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        final Project project = execute(ProjectGet.of());
        assertThat(project.getKey()).isEqualTo(getSphereClientConfig().getProjectKey());
        assertThat(project.getName()).isNotEmpty();
        assertThat(project.getCountries()).isNotEmpty();
        assertThat(project.getLanguages()).isNotEmpty();
        assertThat(project.getCreatedAt()).isNotNull();
        assertThat(project.getTrialUntil()).isNotNull();
    }
}