package io.sphere.sdk.products;

import io.sphere.sdk.queries.Predicate;
import org.junit.Test;

import static java.util.Locale.ENGLISH;
import static java.util.Locale.GERMAN;
import static org.fest.assertions.Assertions.assertThat;

public class ProductQueryCombinationTest {
    @Test
    public void embeddedQueries() throws Exception {
        final Predicate<Product> predicate = ProductQueryModel.get().masterData().current().where((ProductDataQueryModel model) -> model.name().lang(ENGLISH).is("Yes").or(model.name().lang(GERMAN).is("Ja")));
        assertThat(predicate.toSphereQuery()).isEqualTo("masterData(current(name(en=\"Yes\") or name(de=\"Ja\")))");
    }
}
