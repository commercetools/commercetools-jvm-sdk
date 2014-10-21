package io.sphere.sdk.products;

import io.sphere.sdk.http.ClientRequest;
import io.sphere.sdk.products.queries.search.PagedSearchResult;
import io.sphere.sdk.products.queries.search.ProductProjectionSearch;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;
import play.Logger;

import java.util.Locale;
import java.util.function.Predicate;

import static io.sphere.sdk.products.ProductFixtures.withProduct;
import static io.sphere.sdk.test.SphereTestUtils.toIds;
import static org.fest.assertions.Fail.fail;

public class ProductProjectionSearchIntegrationTest extends IntegrationTest {
    @Test
    public void searchAll() throws Exception {
        withProduct(client(), product -> {
            final ProductProjectionSearch search = new ProductProjectionSearch(ProductProjectionType.STAGED, Locale.ENGLISH);
            final PagedSearchResult<ProductProjection> result =
                    execute(search, res -> toIds(res.getResults()).contains(product.getId()));
        });
    }

    protected static <T> T execute(final ClientRequest<T> clientRequest, final Predicate<T> isOk) {
        return execute(clientRequest, 3, isOk);
    }

    protected static <T> T execute(final ClientRequest<T> clientRequest, final int attemptsLeft, final Predicate<T> isOk) {
        if (attemptsLeft < 1) {
            fail("Could not satisfy the request.");
        }

        T result = execute(clientRequest);
        if (isOk.test(result)) {
            return result;
        } else {
            Logger.info("attempts left " + (attemptsLeft - 1));
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return execute(clientRequest, attemptsLeft - 1, isOk);
        }
    }
}
