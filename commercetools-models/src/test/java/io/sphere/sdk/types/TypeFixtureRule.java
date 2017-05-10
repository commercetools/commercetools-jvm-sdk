package io.sphere.sdk.types;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.CategoryDraft;
import io.sphere.sdk.categories.CategoryDraftBuilder;
import io.sphere.sdk.categories.commands.CategoryCreateCommand;
import io.sphere.sdk.categories.commands.CategoryDeleteCommand;
import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.types.commands.TypeCreateCommand;
import io.sphere.sdk.types.commands.TypeDeleteCommand;
import io.sphere.sdk.utils.MoneyImpl;
import org.junit.rules.ExternalResource;

import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static io.sphere.sdk.test.SphereTestUtils.*;
import static io.sphere.sdk.types.TypeFixtures.*;
import static io.sphere.sdk.types.TypeFixtures.TIME_FIELD_NAME;
import static io.sphere.sdk.types.TypeFixtures.createTypeDraftBuilder;

/**
 * Provides a type and a category with that type as test data.
 */
public class TypeFixtureRule extends ExternalResource {
    public static final LocalDate DATE = LocalDate.now().minusDays(1);
    public static final boolean BOOLEAN = true;
    public static final ZonedDateTime ZONED_DATE_TIME = ZonedDateTime.now();
    public static final double DOUBLE = 3223.43;
    public static final BigDecimal BIG_DECIMAL = new BigDecimal("3223.43");
    public static final String ENUM_KEY = "key1";
    public static final String LOC_ENUM_KEY = "key1";
    public static final int INT = 4343;
    public static final LocalizedString LOCALIZED_STRING = en("loc");
    public static final MonetaryAmount MONETARY_AMOUNT = MoneyImpl.ofCents(1234, EUR);
    public static final String STRING = "dfsdsfs";
    public static final LocalTime TIME = LocalTime.now().plusHours(4);
    public static final HashSet<String> STRING_SET = new HashSet<>(asList("s1", "s2"));
    private Type type;
    private Category category;
    private final BlockingSphereClient client;

    public TypeFixtureRule(final BlockingSphereClient client) {
        this.client = client;
    }

    @Override
    protected void before() throws Throwable {
        createType();
        createCategory();
    }

    @Override
    protected void after() {
        client.executeBlocking(CategoryDeleteCommand.of(category));
        category = null;
        client.executeBlocking(TypeDeleteCommand.of(type));
        type = null;
    }

    private void createCategory() {
        final Map<String, Object> fields = new HashMap<>();
        fields.put(BOOLEAN_FIELD_NAME, BOOLEAN);
        fields.put(DATE_FIELD_NAME, DATE);
        fields.put(DATETIME_FIELD_NAME, ZONED_DATE_TIME);
        fields.put(DOUBLE_FIELD_NAME, DOUBLE);
        fields.put(BIG_DECIMAL_FIELD_NAME,BIG_DECIMAL);
        fields.put(ENUM_FIELD_NAME, ENUM_KEY);
        fields.put(INT_FIELD_NAME, INT);
        fields.put(LOCALIZED_ENUM_FIELD_NAME, LOC_ENUM_KEY);
        fields.put(LOC_STRING_FIELD_NAME, LOCALIZED_STRING);
        fields.put(MONEY_FIELD_NAME, MONETARY_AMOUNT);
        fields.put(STRING_FIELD_NAME, STRING);
        fields.put(TIME_FIELD_NAME, TIME);
        fields.put(STRING_SET_FIELD_NAME, STRING_SET);
        final CategoryDraft categoryDraft = CategoryDraftBuilder.of(randomSlug(), randomSlug())
                .custom(CustomFieldsDraft.ofTypeIdAndObjects(type.getId(), fields))
                .build();
        category = client.executeBlocking(CategoryCreateCommand.of(categoryDraft));
    }

    private void createType() {
        final TypeDraftBuilder typeDraftBuilder = createTypeDraftBuilder();
        final TypeDraft typeDraft = typeDraftBuilder
                .build();
        type = client.executeBlocking(TypeCreateCommand.of(typeDraft));
    }

    public Category getCategory() {
        return category;
    }

    public Type getType() {
        return type;
    }
}
