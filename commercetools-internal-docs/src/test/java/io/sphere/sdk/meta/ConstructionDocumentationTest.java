package io.sphere.sdk.meta;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.models.AddressBuilder;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.products.PriceDraft;
import io.sphere.sdk.products.PriceDraftBuilder;
import io.sphere.sdk.utils.MoneyImpl;
import org.junit.Test;

import javax.money.MonetaryAmount;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static io.sphere.sdk.test.SphereTestUtils.*;

public class ConstructionDocumentationTest {
    public void ofMethodExample() {
        final LocalizedString dogFood = LocalizedString.
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
                .contains("Matt");
    }

    @Test
    public void builderWithTemplateInput() throws Exception {
        //we need an address in advance
        final Address template = AddressBuilder.of(CountryCode.GB)
                .firstName("John").lastName("Smith").build();

        //create an address builder and use an address as template
        final AddressBuilder builder = AddressBuilder.of(template);
        final Address address = builder.firstName("Matt").build();
        assertThat(address.getLastName())
                .overridingErrorMessage("not overwritten values are used from the template")
                .contains("Smith");
        assertThat(address.getFirstName())
                .overridingErrorMessage("fields can be overwritten in the builder")
                .contains("Matt");
    }

    @Test
    public void possibilities1() throws Exception {
        final MonetaryAmount eur100 = MoneyImpl.of(100, EUR);
        final PriceDraft price0 = PriceDraft.of(eur100);//price without country
        //builder style
        final PriceDraft price1 = PriceDraftBuilder.of(eur100).country(DE).build();
        //of method + copy method
        final PriceDraft price2 = PriceDraft.of(eur100).withCountry(DE);
        //withCountry semantically the same to
        final PriceDraft price3 = PriceDraftBuilder.of(PriceDraft.of(eur100)).country(DE).build();
        assertThat(price1).isEqualTo(price2).isEqualTo(price3)
                .isNotEqualTo(price0);
    }
}