package io.sphere.sdk.types;

import io.sphere.sdk.test.IntegrationTest;
import org.junit.ClassRule;
import org.junit.Test;

import static io.sphere.sdk.types.TypeFixtureRule.*;
import static io.sphere.sdk.types.TypeFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;

public class CustomFieldsGetterIntegrationTest extends IntegrationTest {
    private static CustomFields custom;

    @ClassRule
    public static TypeFixtureRule resource = new TypeFixtureRule(client()) {
        @Override
        protected void before() throws Throwable {
            super.before();
            custom = getCategory().getCustom();
        };

        @Override
        protected void after() {
            super.after();
            custom = null;
        };
    };

    @Test
    public void getFieldAsString() throws Exception {
        assertThat(custom.getFieldAsString(STRING_FIELD_NAME)).isEqualTo(STRING);
    }

    @Test
    public void getFieldAsBoolean() throws Exception {
        assertThat(custom.getFieldAsBoolean(BOOLEAN_FIELD_NAME)).isEqualTo(BOOLEAN);
    }

    @Test
    public void getFieldAsLocalizedString() throws Exception {
        assertThat(custom.getFieldAsLocalizedString(LOC_STRING_FIELD_NAME)).isEqualTo(LOCALIZED_STRING);
    }

    @Test
    public void getFieldAsEnumKey() throws Exception {
        assertThat(custom.getFieldAsEnumKey(ENUM_FIELD_NAME)).isEqualTo(ENUM_KEY);
    }

    @Test
    public void getFieldAsLocalizedEnumKey() throws Exception {
        assertThat(custom.getFieldAsLocalizedEnumKey(LOCALIZED_ENUM_FIELD_NAME)).isEqualTo(LOC_ENUM_KEY);
    }

    @Test
    public void getFieldAsInteger() throws Exception {
        assertThat(custom.getFieldAsInteger(INT_FIELD_NAME)).isEqualTo(INT);
    }

    @Test
    public void getFieldAsLong() throws Exception {
        assertThat(custom.getFieldAsLong(INT_FIELD_NAME)).isEqualTo(INT);
    }

    @Test
    public void getFieldAsDouble() throws Exception {
        assertThat(custom.getFieldAsDouble(DOUBLE_FIELD_NAME)).isEqualTo(DOUBLE);
    }

    @Test
    public void getFieldAsBigDeciml() throws Exception {
        assertThat(custom.getFieldAsBigDecimal(BIG_DECIMAL_FIELD_NAME)).isEqualTo(BIG_DECIMAL);
    }

    @Test
    public void getFieldAsMoney() throws Exception {
        assertThat(custom.getFieldAsMoney(MONEY_FIELD_NAME)).isEqualTo(MONETARY_AMOUNT);
    }

    @Test
    public void getFieldAsDate() throws Exception {
        assertThat(custom.getFieldAsDate(DATE_FIELD_NAME)).isEqualTo(DATE);
    }

    @Test
    public void getFieldAsDateTime() throws Exception {
        assertThat(custom.getFieldAsDateTime(DATETIME_FIELD_NAME)).isEqualTo(ZONED_DATE_TIME);
    }

    @Test
    public void getFieldAsTime() throws Exception {
        assertThat(custom.getFieldAsTime(TIME_FIELD_NAME)).isEqualTo(TIME);
    }
}