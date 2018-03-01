package io.sphere.sdk.projects.commands;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.projects.Project;
import io.sphere.sdk.projects.commands.updateactions.*;
import io.sphere.sdk.projects.queries.ProjectGet;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static io.sphere.sdk.models.DefaultCurrencyUnits.USD;

public class ProjectUpdateActionsIntegrationTest extends ProjectIntegrationTest{

    @Test
    public void execution() {

        final Project project = client().executeBlocking(ProjectGet.of());
        final String new_project_name = "NewName";
        final List<String> new_project_currencies = Arrays.asList(USD.getCurrencyCode());
        final List<CountryCode> new_project_countries = Arrays.asList(CountryCode.CA);
        final List<Locale> new_project_locales = Arrays.asList(Locale.FRANCE);
        final List<String> new_project_languages = new_project_locales.stream().map(Locale::toLanguageTag).collect(Collectors.toList());
        final Boolean new_project_messages_enabled = false;


        final ProjectUpdateCommand updateCommand = ProjectUpdateCommand.of(project, Arrays.asList(
                ChangeName.of(new_project_name),
                ChangeCurrencies.of(new_project_currencies),
                ChangeCountries.of(new_project_countries),
                ChangeLanguages.of(new_project_languages),
                ChangeMessagesEnabled.of(new_project_messages_enabled),
                SetShippingRateInputType.ofUnset()
        ));

        final Project updatedProject = client().executeBlocking(updateCommand);

        softAssert(soft -> {
            soft.assertThat(updatedProject.getKey()).isEqualTo(getSphereClientConfig().getProjectKey());
            soft.assertThat(updatedProject.getName()).as("name").isEqualTo(new_project_name);
            soft.assertThat(updatedProject.getCountries()).as("countries").isEqualTo(new_project_countries);
            soft.assertThat(updatedProject.getLanguages()).as("languages").isEqualTo(new_project_languages);
            soft.assertThat(updatedProject.getLanguageLocales()).as("languages as locale").isEqualTo(new_project_locales);
            soft.assertThat(updatedProject.getCreatedAt()).as("createdAt").isNotNull();
            soft.assertThat(updatedProject.getCurrencies()).as("currencies").isEqualTo(new_project_currencies);
            soft.assertThat(updatedProject.getCurrencyUnits()).as("currencies as unit").contains(USD);
            soft.assertThat(updatedProject.getMessages().isEnabled()).isEqualTo(new_project_messages_enabled);
            soft.assertThat(updatedProject.getShippingRateInputType()).isNull();

        });
    }

}
