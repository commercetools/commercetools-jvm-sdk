package io.sphere.sdk.meta;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.models.AddressBuilder;
import io.sphere.sdk.models.LocalizedStrings;
import org.junit.Test;

import java.util.Locale;

import static org.fest.assertions.Assertions.assertThat;
import static io.sphere.sdk.test.OptionalAssert.assertThat;

public class ConstructionDocumentationTest {
    public void ofMethodExample() {
        final LocalizedStrings dogFood = LocalizedStrings.
                of(Locale.ENGLISH, "dog food", Locale.GERMAN, "Hundefutter");
    }

    public void builderExample() {
        final Address commercetoolsMunich = AddressBuilder.of(CountryCode.DE)
                .company("commercetools GmbH")
                .streetName("Adams-Lehmann-Str.").streetNumber("44")
                .postalCode("80797").city("München").build();
    }

    @Test
    public void builderIsMutableExample() throws Exception {
        final AddressBuilder builder = AddressBuilder.of(CountryCode.GB)
                .firstName("John").lastName("Smith");
        final Address john1 = builder.build();
        final Address john2 = builder.build();
        assertThat(john1 == john2)
                .overridingErrorMessage("different build() calls create different instances")
                .isFalse();
        final AddressBuilder builder1 = builder.firstName("Stefanie");
        final AddressBuilder builder2 = builder.firstName("Matt");
        assertThat(builder1 == builder && builder2 == builder1)
                .overridingErrorMessage("builders are mutable and return themselves")
                .isTrue();
        assertThat(builder1.build().getFirstName())
                .overridingErrorMessage("the last setting wins")
                .isPresentAs("Matt");
    }

    @Test
    public void builderWithTemplateInput() throws Exception {
        final Address template = AddressBuilder.of(CountryCode.GB)
                .firstName("John").lastName("Smith").build();
        final AddressBuilder builder = AddressBuilder.of(template);
        final Address address = builder.firstName("Matt").build();
        assertThat(address.getLastName())
                .overridingErrorMessage("not overwritten values are used from the template")
                .isPresentAs("Smith");
        assertThat(address.getFirstName())
                .overridingErrorMessage("fields can be overwritten in the builder")
                .isPresentAs("Matt");
    }
}