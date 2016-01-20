package io.sphere.sdk.meta;

import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.customobjects.queries.CustomObjectQuery;
import io.sphere.sdk.products.attributes.Attribute;
import io.sphere.sdk.products.attributes.AttributeAccess;
import io.sphere.sdk.products.attributes.LocalizedToStringProductAttributeConverter;
import io.sphere.sdk.products.search.ProductProjectionSearch;
import io.sphere.sdk.products.search.ProductProjectionSearchBuilder;
import io.sphere.sdk.utils.MoneyImpl;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.util.Locale;

import static java.util.Arrays.asList;

public class M26Demo {
    public void createSearchQueryWithBuilder() {
        @Nullable final String term = getTerm();
        final ProductProjectionSearchBuilder searchBuilder = ProductProjectionSearchBuilder.ofCurrent()
                .limit(30)
                .sort(m -> m.name().locale(Locale.ENGLISH).byAsc());
        if (StringUtils.isNotBlank(term)) {
            searchBuilder.text(Locale.ENGLISH, term);
        }
        final ProductProjectionSearch result = searchBuilder.build();
    }

    private String getTerm() {
        return null;
    }

    public void createSearchQueryClassicWay() {
        @Nullable final String term = getTerm();
        ProductProjectionSearch search = ProductProjectionSearch.ofCurrent()
                .withLimit(30)
                .withSort(m -> m.name().locale(Locale.ENGLISH).byAsc());
        if (StringUtils.isNotBlank(term)) {
            search = search.withText(Locale.ENGLISH, term);
        }
        final ProductProjectionSearch result = search;
    }

    public void queryCustomObjectsByKeyExample() {
        final CustomObjectQuery<JsonNode> customObjectQuery = CustomObjectQuery.ofJsonNode()
                .withPredicates(m -> m.container().is("my-container"))
                .plusPredicates(m -> m.key().isIn(asList("key1", "key2", "key3")));
    }
}
