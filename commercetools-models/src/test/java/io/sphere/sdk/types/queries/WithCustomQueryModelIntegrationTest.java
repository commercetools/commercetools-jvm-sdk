package io.sphere.sdk.types.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.commands.CategoryUpdateCommand;
import io.sphere.sdk.categories.commands.updateactions.SetCustomType;
import io.sphere.sdk.categories.queries.CategoryQuery;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.types.TypeFixtureRule;
import org.junit.ClassRule;
import org.junit.Test;

import java.util.*;
import java.util.function.Function;

import static io.sphere.sdk.categories.CategoryFixtures.withCategory;
import static io.sphere.sdk.types.TypeFixtureRule.*;
import static io.sphere.sdk.types.TypeFixtures.*;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

public class WithCustomQueryModelIntegrationTest extends IntegrationTest {
    @ClassRule
    public static TypeFixtureRule typeFixtureRule = new TypeFixtureRule(client());

    @Test
    public void queryByString() {
        checkQuery(fields -> fields.ofString(STRING_FIELD_NAME).is(STRING));
    }

    @Test
    public void queryByLocalizedString() {
        checkQuery(fields -> fields.ofLocalizedString(LOC_STRING_FIELD_NAME).locale(Locale.ENGLISH).is("loc"));
    }

    @Test
    public void queryByBoolean() {
        checkQuery(fields -> fields.ofBoolean(BOOLEAN_FIELD_NAME).is(true));
    }

    @Test
    public void queryByEnumKey() {
        checkQuery(fields -> fields.ofEnum(ENUM_FIELD_NAME).key().is(ENUM_KEY));
    }

    @Test
    public void queryByLocalizedEnumKey() {
        checkQuery(fields -> fields.ofLocalizedEnum(ENUM_FIELD_NAME).key().is(LOC_ENUM_KEY));
    }

    @Test
    public void queryByNumber() {
        checkQuery(fields -> fields.ofLong(INT_FIELD_NAME).is((long) INT));
    }

    @Test
    public void queryByDecimal() {
        checkQuery(fields -> fields.ofBigDecimal(BIG_DECIMAL_FIELD_NAME).is(BIG_DECIMAL));
    }

    @Test
    public void queryByMoneyCentAmount() {
        checkQuery(fields -> fields.ofMoney(MONEY_FIELD_NAME).centAmount().is(1234L));
    }

    @Test
    public void queryByMoneyCurrencyCode() {
        checkQuery(fields -> fields.ofMoney(MONEY_FIELD_NAME).currencyCode().is("EUR"));
    }

    @Test
    public void queryByDateTime() {
        checkQuery(fields -> fields.ofDateTime(DATETIME_FIELD_NAME).isGreaterThan(ZONED_DATE_TIME.minusMinutes(1)));
    }

    @Test
    public void queryByStringCollection() {
        checkQuery(fields -> fields.ofStringCollection(STRING_SET_FIELD_NAME).containsAny(singletonList("s1")));
    }

    @Test
    public void queryByReference() {

            withUpdateableType(client(), type -> {
                withCategory(client(), referencedCategory -> {
                    withCategory(client(), category -> {
                        final TypeReference<Reference<Category>> TYPE_REFERENCE = new TypeReference<Reference<Category>>() {};
                        final Map<String, Object> fields = new HashMap<>();
                        fields.put(CAT_REFERENCE_FIELD_NAME, referencedCategory.toReference());
                        final CategoryUpdateCommand categoryUpdateCommand = CategoryUpdateCommand.of(category, SetCustomType.ofTypeIdAndObjects(type.getId(), fields));
                        final ExpansionPath<Category> expansionPath = ExpansionPath.of("custom.fields." + CAT_REFERENCE_FIELD_NAME);
                        final Category updatedCategory = client().executeBlocking(categoryUpdateCommand.withExpansionPaths(expansionPath));
                        final Reference<Category> createdReference = updatedCategory.getCustom().getField(CAT_REFERENCE_FIELD_NAME, TYPE_REFERENCE);
                        final CategoryQuery categoryQuery = CategoryQuery.of()
                                .plusExpansionPaths(expansionPath)
                                .plusPredicates(m -> m.is(updatedCategory))
                                .plusPredicates(m -> m.custom().fields().ofReference(CAT_REFERENCE_FIELD_NAME).id().is(createdReference.getId()));
                        final List<Category> results = client().executeBlocking(categoryQuery).getResults();
                        assertThat(results).hasSize(1);
                    });
                });
                return type;
            });
    }

    private void checkQuery(final Function<FieldsQueryModel<Category>, QueryPredicate<Category>> f) {

        final CategoryQuery categoryQuery = CategoryQuery.of()
                .plusPredicates(m -> m.is(typeFixtureRule.getCategory()))
                .plusPredicates(m -> f.apply(m.custom().fields()));

        final List<Category> results = client().executeBlocking(categoryQuery).getResults();
        assertThat(results).hasSize(1);
        assertThat(results.get(0)).isEqualTo(typeFixtureRule.getCategory());
    }
}
