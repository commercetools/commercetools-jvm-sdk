package io.sphere.sdk.queries;

import io.sphere.sdk.models.Point;
import org.junit.Test;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

public class WithinCircleQueryPredicateTest {

    @Test
    public void worksWithDeAsDefaultLocale() {
        final Locale defaultLocale = Locale.getDefault();
        try {
            Locale.setDefault(Locale.GERMANY);

            final WithinCircleQueryPredicate queryPredicate = new WithinCircleQueryPredicate(null, Point.of(1.0, 2.0), 3.0);
            assertThat(queryPredicate.render()).isEqualTo(" within circle(1.000000, 2.000000, 3.000000)");
        } finally {
            Locale.setDefault(defaultLocale);
        }
    }
}
