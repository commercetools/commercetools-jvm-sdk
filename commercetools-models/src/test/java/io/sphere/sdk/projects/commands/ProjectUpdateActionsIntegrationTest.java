package io.sphere.sdk.projects.commands;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.models.LastModifiedBy;
import io.sphere.sdk.projects.ExternalOAuth;
import io.sphere.sdk.projects.MessagesConfigurationDraft;
import io.sphere.sdk.projects.Project;
import io.sphere.sdk.projects.SearchIndexingConfigurationStatus;
import io.sphere.sdk.projects.commands.updateactions.*;
import io.sphere.sdk.projects.queries.ProjectGet;
import org.assertj.core.api.Assertions;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static io.sphere.sdk.models.DefaultCurrencyUnits.USD;


public class ProjectUpdateActionsIntegrationTest extends ProjectIntegrationTest{

    @After
    public void unsetExternalAuthForPerformanceReasons(){
        final Project project = client().executeBlocking(ProjectGet.of());
        final ProjectUpdateCommand updateCommand = ProjectUpdateCommand.of(project, SetExternalOAuth.ofUnset());
        final Project updatedProject = client().executeBlocking(updateCommand);
        Assertions.assertThat(updatedProject.getExternalOAuth()).isNull();
    }

    @Ignore("Disabled due as it's flaky with cached project information")
    @Test
    public void checkChangeCountryTaxRateFallbackEnabledIsWorking(){
        final Project project = client().executeBlocking(ProjectGet.of());
        final Boolean countryTaxRateFallbackEnabledActual = project.getCarts().getCountryTaxRateFallbackEnabled();

        final ProjectUpdateCommand updateCommand = ProjectUpdateCommand.of(project, ChangeCountryTaxRateFallbackEnabled.of(!countryTaxRateFallbackEnabledActual));
        final Project updatedProject = client().executeBlocking(updateCommand);
        assertThat(updatedProject.getCarts().getCountryTaxRateFallbackEnabled()).isEqualTo(!countryTaxRateFallbackEnabledActual);
        final ProjectUpdateCommand reverseCommand = ProjectUpdateCommand.of(updatedProject, ChangeCountryTaxRateFallbackEnabled.of(countryTaxRateFallbackEnabledActual));
        final Project reversedProject = client().executeBlocking(reverseCommand);
        assertThat(reversedProject.getCarts().getCountryTaxRateFallbackEnabled()).isEqualTo(countryTaxRateFallbackEnabledActual);

    }

    @Ignore("disabled as search integration tests getting flaky")
    @Test
    public void checkSearchConfiguration(){
        final Project project = client().executeBlocking(ProjectGet.of());
        final ProjectUpdateCommand updateCommand = ProjectUpdateCommand.of(project, ChangeProductSearchIndexingEnabled.of(true));
        final Project updatedProject = client().executeBlocking(updateCommand);

        final SearchIndexingConfigurationStatus status = updatedProject.getSearchIndexing().getProducts().getStatus();
        assertThat(status).isInstanceOf(SearchIndexingConfigurationStatus.class);
        assertThat(updatedProject.getSearchIndexing().getProducts().getStatus()).isNotEqualTo(SearchIndexingConfigurationStatus.DEACTIVATED);
        assertThat(updatedProject.getSearchIndexing().getProducts().getLastModifiedAt()).isNotNull();
        assertThat(updatedProject.getSearchIndexing().getProducts().getLastModifiedBy()).isInstanceOf(LastModifiedBy.class);
    }


    @Ignore("Disable because of problems with External OAuth")
    @Test
    public void execution() throws Exception{

        final Project project = client().executeBlocking(ProjectGet.of());
        final String new_project_name = "NewName";
        final List<String> new_project_currencies = Arrays.asList(USD.getCurrencyCode());
        final List<CountryCode> new_project_countries = Arrays.asList(CountryCode.CA);
        final List<Locale> new_project_locales = Arrays.asList(Locale.FRANCE);
        final List<String> new_project_languages = new_project_locales.stream().map(Locale::toLanguageTag).collect(Collectors.toList());
        final Boolean new_project_messages_enabled = false;
        final Long delete_days_after_activation = 20L;
        final URL url = new URL("https://invalid.cmo");
        final ExternalOAuth externalOAuth = ExternalOAuth.of("customheader: customValue", url);



        final ProjectUpdateCommand updateCommand = ProjectUpdateCommand.of(project, Arrays.asList(
                ChangeName.of(new_project_name),
                ChangeCurrencies.of(new_project_currencies),
                ChangeCountries.of(new_project_countries),
                ChangeLanguages.of(new_project_languages),
                ChangeMessagesConfiguration.of(MessagesConfigurationDraft.of(new_project_messages_enabled, delete_days_after_activation)),
                SetShippingRateInputType.ofUnset(),
                SetExternalOAuth.of(externalOAuth)
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
            soft.assertThat(updatedProject.getMessages().getDeleteDaysAfterCreation()).isEqualTo(delete_days_after_activation);
            soft.assertThat(updatedProject.getShippingRateInputType()).isNull();
            soft.assertThat(updatedProject.getExternalOAuth()).isEqualToIgnoringGivenFields(externalOAuth,"authorizationHeader");
        });
    }

}
