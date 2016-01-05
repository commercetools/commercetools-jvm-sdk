package io.sphere.sdk.types;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.CategoryFixtures;
import io.sphere.sdk.categories.commands.CategoryUpdateCommand;
import io.sphere.sdk.categories.commands.updateactions.SetCustomType;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.utils.MoneyImpl;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.money.MonetaryAmount;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

import static io.sphere.sdk.test.SphereTestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static io.sphere.sdk.types.TypeFixtures.*;

public class CustomFieldsTest extends IntegrationTest {
    public static final LocalDate DATE = LocalDate.now().minusDays(1);
    public static final boolean BOOLEAN = true;
    public static final ZonedDateTime ZONED_DATE_TIME = ZonedDateTime.now();
    public static final double DOUBLE = 3223.43;
    public static final String ENUM_KEY = "key1";
    public static final String LOC_ENUM_KEY = "key1";
    public static final int INT = 4343;
    public static final LocalizedString LOCALIZED_STRING = en("loc");
    public static final MonetaryAmount MONETARY_AMOUNT = MoneyImpl.ofCents(1234, EUR);
    public static final String STRING = "dfsdsfs";
    public static final LocalTime TIME = LocalTime.now().plusHours(4);
    private static CustomFields custom;

    @BeforeClass
    public static void setUp() throws Exception {
        TypeFixtures.withUpdateableType(client(), type -> {
            CategoryFixtures.withCategory(client(), category -> {
                final Map<String, Object> fields = new HashMap<>();

                fields.put(BOOLEAN_FIELD_NAME, BOOLEAN);
                fields.put(DATE_FIELD_NAME, DATE);
                fields.put(DATETIME_FIELD_NAME, ZONED_DATE_TIME);
                fields.put(DOUBLE_FIELD_NAME, DOUBLE);
                fields.put(ENUM_FIELD_NAME, ENUM_KEY);
                fields.put(INT_FIELD_NAME, INT);
                fields.put(LOCALIZED_ENUM_FIELD_NAME, LOC_ENUM_KEY);
                fields.put(LOC_STRING_FIELD_NAME, LOCALIZED_STRING);
                fields.put(MONEY_FIELD_NAME, MONETARY_AMOUNT);
                fields.put(STRING_FIELD_NAME, STRING);
                fields.put(TIME_FIELD_NAME, TIME);

                final Category updatedCategory = client().executeBlocking(CategoryUpdateCommand.of(category, SetCustomType.ofTypeIdAndObjects(type.getId(), fields)));

                custom = updatedCategory.getCustom();
            });
            return type;
        });
    }

    @AfterClass
    public static void cleanUp() {
        custom = null;
    }

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