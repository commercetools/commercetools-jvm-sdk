package example;

import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.queries.ProductQuery;
import io.sphere.sdk.products.queries.ProductQueryModel;
import io.sphere.sdk.queries.Predicate;
import io.sphere.sdk.queries.Query;

import java.util.Locale;
import java.util.function.Supplier;

public class ByEnglishNameProductQuerySupplier implements Supplier<Query<Product>> {
    @Override
    public Query<Product> get() {
        final Predicate<Product> predicate = ProductQueryModel.get().
                masterData().current().name().lang(Locale.ENGLISH).is("simple cotton t-shirt");
        return new ProductQuery().withPredicate(predicate);
    }
}
