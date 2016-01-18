package io.sphere.sdk.categories.queries;

import org.junit.Test;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

public class CategoryQueryBuilderTest {
    @Test
    public void name() {
        final CategoryQueryBuilder builder = CategoryQueryBuilder.of()
                .predicates(m -> m.id().is("f"))
                .fetchTotal(true)
                .limit(2)
                .offset(5)
                .plusPredicates(m -> m.name().locale(Locale.ENGLISH).is("bar"))
                .sort(m -> m.slug().locale(Locale.ENGLISH).sort().desc())
                .plusExpansionPaths(m -> m.parent());
        final CategoryQuery actual = builder.build();
        final CategoryQuery expected = CategoryQuery.of().byId("f")
                .withFetchTotal(true)
                .withLimit(2)
                .withOffset(5)
                .plusPredicates(m -> m.name().locale(Locale.ENGLISH).is("bar"))
                .withSort(m -> m.slug().locale(Locale.ENGLISH).sort().desc())
                .plusExpansionPaths(m -> m.parent());
        assertThat(actual).isEqualTo(expected);
    }
}