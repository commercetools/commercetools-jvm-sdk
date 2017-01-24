package io.sphere.sdk.products.attributes;

import io.sphere.sdk.products.ProductVariant;
import io.sphere.sdk.products.ProductsScenario1Fixtures;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.sphere.sdk.products.ProductsScenario1Fixtures.*;
import static org.assertj.core.api.Assertions.assertThat;

public class AttributesValueMethodIntegrationTest extends IntegrationTest {
    private static ProductsScenario1Fixtures.Data data;
    private static ProductVariant masterVariant;

    @BeforeClass
    public static void setupScenario() {
        data = ProductsScenario1Fixtures.createScenario(client());
        masterVariant = data.getProduct1().getMasterData().getStaged().getMasterVariant();
    }

    @AfterClass
    public static void cleanupScenario() {
        ProductsScenario1Fixtures.destroy(client(), data);
    }

    @Test
    public void stringType() {
        assertThat(attribute(ATTR_NAME_TEXT).getValueAsString()).isEqualTo("foo");
    }

    @Test
    public void stringSetType() {
        assertThat(attribute(ATTR_NAME_TEXT_SET).getValueAsStringSet()).containsExactly("foo", "bar");
    }

    @Test
    public void stringSetTypeRemainOrdered() {
        assertThat(attribute(ATTR_NAME_TEXT_SET2).getValueAsStringSet()).containsExactly(TEXT_THREE, TEXT_ONE, TEXT_TWO);
    }

    @Test
    public void booleanType() {
        assertThat(attribute(ATTR_NAME_BOOLEAN).getValueAsBoolean()).isEqualTo(true);
    }

    @Test
    public void booleanSetType() {
        assertThat(attribute(ATTR_NAME_BOOLEAN_SET).getValueAsBooleanSet()).contains(true, false);
    }

    @Test
    public void localizedStringType() {
        assertThat(attribute(ATTR_NAME_LOC_TEXT).getValueAsLocalizedString()).isEqualTo(LOC_TEXT_FOO);
    }

    @Test
    public void localizedStringSetType() {
        assertThat(attribute(ATTR_NAME_LOC_TEXT_SET).getValueAsLocalizedStringSet()).contains(LOC_TEXT_FOO, LOC_TEXT_BAR);
    }

    @Test
    public void enumType() {
        assertThat(attribute(ATTR_NAME_ENUM).getValueAsEnumValue()).isEqualTo(ENUM_TWO);
    }

    @Test
    public void localizedEnumType() {
        assertThat(attribute(ATTR_NAME_LOC_ENUM).getValueAsLocalizedEnumValue()).isEqualTo(LOC_ENUM_TWO);
    }

    @Test
    public void localizedEnumSetType() {
        assertThat(attribute(ATTR_NAME_LOC_ENUM_SET).getValueAsLocalizedEnumValueSet()).contains(LOC_ENUM_TWO, LOC_ENUM_THREE);
    }

    @Test
    public void doubleType() {
        assertThat(attribute(ATTR_NAME_NUMBER).getValueAsDouble()).isEqualTo(5d);
    }

    @Test
    public void doubleSetType() {
        assertThat(attribute(ATTR_NAME_NUMBER_SET).getValueAsDoubleSet()).contains(5d, 10d);
    }

    @Test
    public void integerType() {
        assertThat(attribute(ATTR_NAME_NUMBER).getValueAsInteger()).isEqualTo(5);
    }

    @Test
    public void integerSetType() {
        assertThat(attribute(ATTR_NAME_NUMBER_SET).getValueAsIntegerSet()).contains(5, 10);
    }

    @Test
    public void longType() {
        assertThat(attribute(ATTR_NAME_NUMBER).getValueAsLong()).isEqualTo(5L);
    }

    @Test
    public void longSetType() {
        assertThat(attribute(ATTR_NAME_NUMBER_SET).getValueAsLongSet()).contains(5L, 10L);
    }

    @Test
    public void moneyType() {
        assertThat(attribute(ATTR_NAME_MONEY).getValueAsMoney()).isEqualTo(MONEY_500_EUR);
    }

    @Test
    public void moneySetType() {
        assertThat(attribute(ATTR_NAME_MONEY_SET).getValueAsMoneySet()).contains(MONEY_500_EUR, MONEY_1000_USD);
    }

    @Test
    public void dateType() {
        assertThat(attribute(ATTR_NAME_DATE).getValueAsLocalDate()).isEqualTo(DATE_2001);
    }

    @Test
    public void dateSetType() {
        assertThat(attribute(ATTR_NAME_DATE_SET).getValueAsLocalDateSet()).contains(DATE_2001, DATE_2002);
    }

    @Test
    public void timeType() {
        assertThat(attribute(ATTR_NAME_TIME).getValueAsLocalTime()).isEqualTo(TIME_22H);
    }

    @Test
    public void timeTypeSet() {
        assertThat(attribute(ATTR_NAME_TIME_SET).getValueAsLocalTimeSet()).contains(TIME_22H, TIME_23H);
    }

    @Test
    public void dateTimeType() {
        assertThat(attribute(ATTR_NAME_DATE_TIME).getValueAsDateTime()).isEqualTo(DATE_TIME_2001_22H);
    }

    @Test
    public void dateTimeSetType() {
        assertThat(attribute(ATTR_NAME_DATE_TIME_SET).getValueAsDateTimeSet()).contains(DATE_TIME_2001_22H, DATE_TIME_2002_23H);
    }

    private Attribute attribute(final String attributeName) {
        return masterVariant.getAttribute(attributeName);
    }
}
