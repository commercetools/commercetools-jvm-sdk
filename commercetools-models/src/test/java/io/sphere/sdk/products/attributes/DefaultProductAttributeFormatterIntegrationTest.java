package io.sphere.sdk.products.attributes;

import io.sphere.sdk.annotations.NotOSGiCompatible;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.ProductsScenario1Fixtures;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Collection;
import java.util.Locale;
import java.util.regex.Pattern;

import static java.util.Arrays.asList;
import static java.util.Collections.singleton;
import static java.util.Collections.singletonList;
import static io.sphere.sdk.products.ProductsScenario1Fixtures.*;
import static org.assertj.core.api.Assertions.assertThat;

@NotOSGiCompatible
public class DefaultProductAttributeFormatterIntegrationTest extends IntegrationTest {

    private static ProductsScenario1Fixtures.Data data;
    private static Product product;
    private static ProductType productType;
    private static ProductAttributeConverter<String> numberAsLongMapper;
    private static ProductAttributeConverter<String> numberAsIntegerMapper;
    private static ProductAttributeConverter<String> numberAsDoubleMapper;

    @BeforeClass
    public static void setupScenario() {
        data = ProductsScenario1Fixtures.createScenario(client());
        product = data.getProduct1();
        productType = data.getProductType();
        numberAsIntegerMapper = new DefaultProductAttributeFormatter(singletonList(productType), asList(Locale.GERMANY, Locale.ENGLISH)) {
            @Override
            protected Collection<String> integerAttributes() {
                return singletonList(ATTR_NAME_NUMBER);
            }

            @Override
            protected Collection<String> integerSetAttributes() {
                return singleton(ATTR_NAME_NUMBER_SET);
            }
        };
        numberAsLongMapper = new DefaultProductAttributeFormatter(singletonList(productType), asList(Locale.GERMANY, Locale.ENGLISH)) {
            @Override
            protected Collection<String> longAttributes() {
                return singletonList(ATTR_NAME_NUMBER);
            }

            @Override
            protected Collection<String> longSetAttributes() {
                return singleton(ATTR_NAME_NUMBER_SET);
            }
        };
        numberAsDoubleMapper = new DefaultProductAttributeFormatter(singletonList(productType), asList(Locale.GERMANY, Locale.ENGLISH));
    }

    @AfterClass
    public static void cleanupScenario() {
        ProductsScenario1Fixtures.destroy(client(), data);
    }

    @Test
    public void convertStringSet() {
        assertThat(converting(ATTR_NAME_TEXT_SET)).isEqualTo("bar, foo");
    }

    @Ignore
    @Test
    public void convertProductTypeReferenceSet() {
    }

    @Ignore
    @Test
    public void convertProductTypeReference() {
    }

    @Test
    public void convertProductReferenceSet() {
        assertThat(converting(ATTR_NAME_REF_SET)).matches(Pattern.compile("\\S+, \\S+"));
    }

    @Test
    public void convertProductReference() {
        assertThat(converting(ATTR_NAME_REF)).matches(Pattern.compile("\\S+"));
    }

    @Test
    public void convertMoneySet() {
        assertThat(converting(ATTR_NAME_MONEY_SET)).isEqualTo("500,00 EUR, 1.000,00 USD");
    }

    @Test
    public void convertLongSet() {
        assertThat(converting(ATTR_NAME_NUMBER_SET)).isEqualTo("5, 10");
    }

    @Test
    public void convertLong() {
        assertThat(converting(ATTR_NAME_NUMBER)).isEqualTo("5");
    }

    @Test
    public void convertTimeSet() {
        assertThat(converting(ATTR_NAME_TIME_SET)).isEqualTo("22:05, 23:06");
    }

    @Test
    public void convertTime() {
        assertThat(converting(ATTR_NAME_TIME)).isEqualTo("22:05");
    }

    @Test
    public void convertLocalizedStringSet() {
        assertThat(converting(ATTR_NAME_LOC_TEXT_SET)).isEqualTo("localized bar, localized foo");
    }

    @Test
    public void convertLocalizedEnumValueSet() {
        assertThat(converting(ATTR_NAME_LOC_ENUM_SET)).isEqualTo("drei, zwei");
    }

    @Test
    public void convertIntegerSet() {
        assertThat(converting(ATTR_NAME_NUMBER_SET, numberAsIntegerMapper)).isEqualTo("5, 10");
    }

    @Test
    public void convertInteger() {
        assertThat(converting(ATTR_NAME_NUMBER, numberAsIntegerMapper)).isEqualTo("5");
    }

    @Test
    public void convertEnumValueSet() {
        assertThat(converting(ATTR_NAME_ENUM_SET)).isEqualTo("three, two");
    }

    @Test
    public void convertDoubleSet() {
        assertThat(converting(ATTR_NAME_NUMBER_SET, numberAsDoubleMapper)).isEqualTo("5.0, 10.0");
    }

    @Test
    public void convertDouble() {
        assertThat(converting(ATTR_NAME_NUMBER, numberAsDoubleMapper)).isEqualTo("5.0");
    }

    @Test
    public void convertDateTimeSet() {
        assertThat(converting(ATTR_NAME_DATE_TIME_SET)).isEqualTo("11.09.01 22:05, 12.10.02 23:06");
    }

    @Test
    public void convertDateSet() {
        assertThat(converting(ATTR_NAME_DATE_SET)).isEqualTo("11.09.2001, 12.10.2002");
    }

    @Ignore
    @Test
    public void convertChannelReferenceSet() {
    }

    @Ignore
    @Test
    public void convertChannelReference() {
    }

    @Ignore
    @Test
    public void convertCategoryReferenceSet() {
    }

    @Ignore
    @Test
    public void convertCategoryReference() {
    }

    @Test
    public void convertBooleanSet() {
        assertThat(converting(ATTR_NAME_BOOLEAN_SET)).isEqualTo("false, true");
    }

    @Test
    public void convertString() {
        assertThat(converting(ATTR_NAME_TEXT)).isEqualTo("foo");
    }

    @Test
    public void convertMoney() {
        assertThat(converting(ATTR_NAME_MONEY)).isEqualTo("500,00 EUR");
    }

    @Test
    public void convertLocalizedString() {
        assertThat(converting(ATTR_NAME_LOC_TEXT2)).isEqualTo("German foo");
    }

    @Test
    public void convertLocalizedEnumValue() {
        assertThat(converting(ATTR_NAME_LOC_ENUM)).isEqualTo("zwei");
    }

    @Test
    public void convertEnumValue() {
        assertThat(converting(ATTR_NAME_ENUM)).isEqualTo("two");
    }

    @Test
    public void convertDateTime() {
        assertThat(converting(ATTR_NAME_DATE_TIME)).isEqualTo("11.09.01 22:05");
    }

    @Test
    public void convertDate() {
        assertThat(converting(ATTR_NAME_DATE)).isEqualTo("11.09.2001");
    }

    @Test
    public void convertBoolean() {
        assertThat(converting(ATTR_NAME_BOOLEAN)).isEqualTo("true");
    }


    private String converting(final String attributeName) {
        return converting(attributeName, numberAsLongMapper);
    }

    private String converting(final String attributeName, final ProductAttributeConverter<String> attributeConverter) {
        final Attribute attribute = attribute(attributeName);
        if (attribute == null) {
            throw new NullPointerException("attribute for " + attributeName + " should not be null");
        }

        return attributeConverter.convert(attribute, productType);
    }

    private Attribute attribute(final String attributeName) {
        return product.getMasterData().getStaged().getMasterVariant().getAttribute(attributeName);
    }
}