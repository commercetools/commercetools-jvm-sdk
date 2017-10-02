package io.sphere.sdk.projects.commands;

import io.sphere.sdk.projects.CartScore;
import io.sphere.sdk.projects.CartValue;
import io.sphere.sdk.projects.Project;
import io.sphere.sdk.projects.commands.updateactions.SetShippingRateInputType;
import io.sphere.sdk.projects.queries.ProjectGet;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ProjectCartScoreIntegrationTest extends ProjectIntegrationTest {

    @Test
    public void execution() {

        final Project project = client().executeBlocking(ProjectGet.of());
        final Project updatedProjectCartScore = client().executeBlocking(ProjectUpdateCommand.of(project, SetShippingRateInputType.of(CartScore.of())));
        assertThat(updatedProjectCartScore.getShippingRateInputType()).isNotNull();
        assertThat(updatedProjectCartScore.getShippingRateInputType().getType()).isEqualTo(CartScore.of().getType());

    }

}
