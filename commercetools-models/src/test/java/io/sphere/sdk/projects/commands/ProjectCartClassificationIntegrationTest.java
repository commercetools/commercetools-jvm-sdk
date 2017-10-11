package io.sphere.sdk.projects.commands;

import io.sphere.sdk.models.LocalizedEnumValue;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.projects.*;
import io.sphere.sdk.projects.commands.updateactions.SetShippingRateInputType;
import io.sphere.sdk.projects.queries.ProjectGet;
import org.junit.Test;

import java.util.Collections;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

public class ProjectCartClassificationIntegrationTest extends ProjectIntegrationTest{

    @Test
    public void execution() {

        final Project project = client().executeBlocking(ProjectGet.of());
        final CartClassificationDraft cartClassification = CartClassificationDraftBuilder.of(Collections.singleton(LocalizedEnumValue.of("Small", LocalizedString.of(Locale.ENGLISH, "Small", Locale.GERMAN, "Klein")))).build();
        final Project updatedProjectCartClassification = client().executeBlocking(ProjectUpdateCommand.of(project, SetShippingRateInputType.of(cartClassification)));
        assertThat(updatedProjectCartClassification.getShippingRateInputType()).isNotNull();
        assertThat(updatedProjectCartClassification.getShippingRateInputType().getType()).isEqualTo("CartClassification");
        assertThat(((CartClassification)updatedProjectCartClassification.getShippingRateInputType()).getValues()).containsOnly(
                LocalizedEnumValue.of("Small", LocalizedString.of(Locale.ENGLISH, "Small", Locale.GERMAN, "Klein"))
        );
    }
}
