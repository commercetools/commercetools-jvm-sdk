package io.sphere.sdk.projects.queries;

import io.sphere.sdk.projects.Project;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.Locale;

import static io.sphere.sdk.models.DefaultCurrencyUnits.EUR;

public class ProjectGetIntegrationTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        final Project project = client().executeBlocking(ProjectGet.of());
        softAssert(soft -> {
            soft.assertThat(project.getKey()).isEqualTo(getSphereClientConfig().getProjectKey());
            soft.assertThat(project.getName()).as("name").isNotEmpty();
            soft.assertThat(project.getCountries()).as("countries").isNotEmpty();
            soft.assertThat(project.getLanguages()).as("languages").contains("en");
            soft.assertThat(project.getLanguageLocales()).as("languages as locale").contains(Locale.ENGLISH);
            soft.assertThat(project.getCreatedAt()).as("createdAt").isNotNull();
            soft.assertThat(project.getCurrencies()).as("currencies").contains("EUR");
            soft.assertThat(project.getCurrencyUnits()).as("currencies as unit").contains(EUR);
            soft.assertThat(project.getMessages().isEnabled()).isTrue();
            //there is a test for trialUntil missing since some ci test projects don't possess a value for this field
        });
    }
}