package io.sphere.sdk.projects.commands;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.projects.Project;
import io.sphere.sdk.projects.commands.updateactions.*;
import io.sphere.sdk.projects.queries.ProjectGet;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static io.sphere.sdk.models.DefaultCurrencyUnits.EUR;
import static io.sphere.sdk.models.DefaultCurrencyUnits.USD;

public class ProjectUpdateActionsIntegrationTest extends IntegrationTest {

    private static final String PROJECT_NAME = "TestProject";
    private static final List<String> PROJECT_CURRENCIES = Arrays.asList(EUR.getCurrencyCode(), USD.getCurrencyCode());
    private static final List<CountryCode> PROJECT_COUNTRIES = Arrays.asList(CountryCode.DE, CountryCode.US);
    private static final List<Locale> PROJECT_LOCALES = Arrays.asList(Locale.GERMANY, new Locale("de", "AT"), Locale.ENGLISH);
    private static final List<String> PROJECT_LANGUAGES = PROJECT_LOCALES.stream().map(Locale::toLanguageTag).collect(Collectors.toList());
    private static final Boolean PROJECT_MESSAGES_ENABLED = true;

    @BeforeClass
    @AfterClass
    public static void resetProject() {
        final Project project = client().executeBlocking(ProjectGet.of());
        final ProjectUpdateCommand updateCommand = ProjectUpdateCommand.of(project, Arrays.asList(
                ChangeName.of(PROJECT_NAME),
                ChangeCurrencies.of(PROJECT_CURRENCIES),
                ChangeCountries.of(PROJECT_COUNTRIES),
                ChangeLanguages.of(PROJECT_LANGUAGES),
                ChangeMessagesEnabled.of(PROJECT_MESSAGES_ENABLED)
        ));

        final Project updatedProject = client().executeBlocking(updateCommand);

        softAssert(soft -> {
            soft.assertThat(updatedProject.getKey()).isEqualTo(getSphereClientConfig().getProjectKey());
            soft.assertThat(updatedProject.getName()).as("name").isEqualTo(PROJECT_NAME);
            soft.assertThat(updatedProject.getCountries()).as("countries").isEqualTo(PROJECT_COUNTRIES);
            soft.assertThat(updatedProject.getLanguages()).as("languages").isEqualTo(PROJECT_LANGUAGES);
            soft.assertThat(updatedProject.getLanguageLocales()).as("languages as locale").isEqualTo(PROJECT_LOCALES);
            soft.assertThat(updatedProject.getCreatedAt()).as("createdAt").isNotNull();
            soft.assertThat(updatedProject.getCurrencies()).as("currencies").isEqualTo(PROJECT_CURRENCIES);
            soft.assertThat(updatedProject.getCurrencyUnits()).as("currencies as unit").contains(EUR);
            soft.assertThat(updatedProject.getMessages().isEnabled()).isEqualTo(PROJECT_MESSAGES_ENABLED);

        });

    }


    @Test
    public void execution() {

        final Project project = client().executeBlocking(ProjectGet.of());
        softAssert(soft -> {
            soft.assertThat(project.getKey()).isEqualTo(getSphereClientConfig().getProjectKey());
            soft.assertThat(project.getName()).as("name").isEqualTo(PROJECT_NAME);
            soft.assertThat(project.getCountries()).as("countries").isEqualTo(PROJECT_COUNTRIES);
            soft.assertThat(project.getLanguages()).as("languages").isEqualTo(PROJECT_LANGUAGES);
            soft.assertThat(project.getLanguageLocales()).as("languages as locale").isEqualTo(PROJECT_LOCALES);
            soft.assertThat(project.getCreatedAt()).as("createdAt").isNotNull();
            soft.assertThat(project.getCurrencies()).as("currencies").isEqualTo(PROJECT_CURRENCIES);
            soft.assertThat(project.getCurrencyUnits()).as("currencies as unit").contains(EUR);
            soft.assertThat(project.getMessages().isEnabled()).isEqualTo(PROJECT_MESSAGES_ENABLED);

        });

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
                ChangeMessagesEnabled.of(new_project_messages_enabled)
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

        });


    }
}
