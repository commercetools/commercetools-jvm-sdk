package io.sphere.sdk.projects.commands;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.carts.CartsConfiguration;
import io.sphere.sdk.carts.ShoppingListsConfiguration;
import io.sphere.sdk.projects.Project;
import io.sphere.sdk.projects.commands.updateactions.*;
import io.sphere.sdk.projects.queries.ProjectGet;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static io.sphere.sdk.models.DefaultCurrencyUnits.EUR;
import static io.sphere.sdk.models.DefaultCurrencyUnits.USD;


public abstract class ProjectIntegrationTest extends IntegrationTest {

    private static final String PROJECT_NAME = "TestProject";
    private static final List<String> PROJECT_CURRENCIES = Arrays.asList(EUR.getCurrencyCode(), USD.getCurrencyCode());
    private static final List<CountryCode> PROJECT_COUNTRIES = Arrays.asList(CountryCode.DE, CountryCode.US);
    private static final List<Locale> PROJECT_LOCALES = Arrays.asList(Locale.GERMAN, new Locale("de", "AT"), Locale.ENGLISH);
    private static final List<String> PROJECT_LANGUAGES = PROJECT_LOCALES.stream().map(Locale::toLanguageTag).collect(Collectors.toList());
    private static final Boolean PROJECT_MESSAGES_ENABLED = true;
    private static final CartsConfiguration CARTS_CONFIGURATION = CartsConfiguration.of(10);
    private static final ShoppingListsConfiguration SHOPPING_LISTS_CONFIGURATION = ShoppingListsConfiguration.of(10);
    
    @BeforeClass
    @AfterClass
    public static void resetProject() {
        final Project project = client().executeBlocking(ProjectGet.of());
        final ProjectUpdateCommand updateCommand = ProjectUpdateCommand.of(project, Arrays.asList(
                ChangeName.of(PROJECT_NAME),
                ChangeCurrencies.of(PROJECT_CURRENCIES),
                ChangeCountries.of(PROJECT_COUNTRIES),
                ChangeLanguages.of(PROJECT_LANGUAGES),
                ChangeMessagesEnabled.of(PROJECT_MESSAGES_ENABLED),
                SetShippingRateInputType.ofUnset(),
                ChangeCartsConfiguration.of(CARTS_CONFIGURATION),
                ChangeShoppingListsConfiguration.of(SHOPPING_LISTS_CONFIGURATION)
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
            soft.assertThat(updatedProject.getShippingRateInputType()).isNull();
            soft.assertThat(updatedProject.getCarts().getDeleteDaysAfterLastModification()).isEqualTo(10);
            soft.assertThat(updatedProject.getShoppingLists().getDeleteDaysAfterLastModification()).isEqualTo(10);
        });
    }

}
