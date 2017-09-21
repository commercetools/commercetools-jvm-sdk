package io.sphere.sdk.types.queries;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.queries.CategoryQuery;
import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.types.TypeFixtureRule;
import org.junit.ClassRule;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;

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
        final CategoryQuery categoryQuery = CategoryQuery.of()
                .plusPredicates(m -> m.is(typeFixtureRule.getCategory()))
                .plusPredicates(m -> m.custom().fields().ofReference(CAT_REFERENCE_FIELD_NAME).id().is("427da94c-de0e-489f-b4c0-6f9fe4b8fe48"));
        final List<Category> results = client().executeBlocking(categoryQuery).getResults();
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
