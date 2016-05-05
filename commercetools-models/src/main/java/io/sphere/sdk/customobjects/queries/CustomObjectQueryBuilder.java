package io.sphere.sdk.customobjects.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.customobjects.expansion.CustomObjectExpansionModel;
import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.json.TypeReferences;
import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.queries.QuerySort;
import io.sphere.sdk.queries.ResourceMetaModelQueryDslBuilderImpl;

import java.util.List;
import java.util.function.Function;

/**

 {@doc.gen summary }

 */
public final class CustomObjectQueryBuilder<T> extends ResourceMetaModelQueryDslBuilderImpl<CustomObjectQueryBuilder<T>, CustomObject<T>, CustomObjectQuery<T>, CustomObjectQueryModel<CustomObject<T>>, CustomObjectExpansionModel<CustomObject<T>>> {

    private CustomObjectQueryBuilder(final CustomObjectQuery<T> template) {
        super(template);
    }

    public static <T> CustomObjectQueryBuilder<T> of(final TypeReference<T> valueTypeReference) {
        return new CustomObjectQueryBuilder<>(CustomObjectQuery.of(valueTypeReference));
    }

    public static <T> CustomObjectQueryBuilder<T> of(final Class<T> valueClass) {
        return new CustomObjectQueryBuilder<>(CustomObjectQuery.of(valueClass));
    }

    public static CustomObjectQueryBuilder<JsonNode> ofJsonNode() {
        return of(TypeReferences.jsonNodeTypeReference());
    }

    @Override
    protected CustomObjectQueryBuilder<T> getThis() {
        return this;
    }

    @Override
    public CustomObjectQuery<T> build() {
        return super.build();
    }

    @Override
    public CustomObjectQueryBuilder<T> fetchTotal(final boolean fetchTotal) {
        return super.fetchTotal(fetchTotal);
    }

    @Override
    public CustomObjectQueryBuilder<T> limit(final Long limit) {
        return super.limit(limit);
    }

    @Override
    public CustomObjectQueryBuilder<T> limit(final long limit) {
        return super.limit(limit);
    }

    @Override
    public CustomObjectQueryBuilder<T> offset(final Long offset) {
        return super.offset(offset);
    }

    @Override
    public CustomObjectQueryBuilder<T> offset(final long offset) {
        return super.offset(offset);
    }

    @Override
    public CustomObjectQueryBuilder<T> plusExpansionPaths(final Function<CustomObjectExpansionModel<CustomObject<T>>, ExpansionPathContainer<CustomObject<T>>> m) {
        return super.plusExpansionPaths(m);
    }

    @Override
    public CustomObjectQueryBuilder<T> plusPredicates(final Function<CustomObjectQueryModel<CustomObject<T>>, QueryPredicate<CustomObject<T>>> m) {
        return super.plusPredicates(m);
    }

    @Override
    public CustomObjectQueryBuilder<T> plusPredicates(final QueryPredicate<CustomObject<T>> queryPredicate) {
        return super.plusPredicates(queryPredicate);
    }

    @Override
    public CustomObjectQueryBuilder<T> plusPredicates(final List<QueryPredicate<CustomObject<T>>> queryPredicates) {
        return super.plusPredicates(queryPredicates);
    }

    @Override
    public CustomObjectQueryBuilder<T> plusSort(final Function<CustomObjectQueryModel<CustomObject<T>>, QuerySort<CustomObject<T>>> m) {
        return super.plusSort(m);
    }

    @Override
    public CustomObjectQueryBuilder<T> plusSort(final List<QuerySort<CustomObject<T>>> sort) {
        return super.plusSort(sort);
    }

    @Override
    public CustomObjectQueryBuilder<T> plusSort(final QuerySort<CustomObject<T>> sort) {
        return super.plusSort(sort);
    }

    @Override
    public CustomObjectQueryBuilder<T> predicates(final Function<CustomObjectQueryModel<CustomObject<T>>, QueryPredicate<CustomObject<T>>> m) {
        return super.predicates(m);
    }

    @Override
    public CustomObjectQueryBuilder<T> predicates(final QueryPredicate<CustomObject<T>> queryPredicate) {
        return super.predicates(queryPredicate);
    }

    @Override
    public CustomObjectQueryBuilder<T> predicates(final List<QueryPredicate<CustomObject<T>>> queryPredicates) {
        return super.predicates(queryPredicates);
    }

    @Override
    public CustomObjectQueryBuilder<T> sort(final Function<CustomObjectQueryModel<CustomObject<T>>, QuerySort<CustomObject<T>>> m) {
        return super.sort(m);
    }

    @Override
    public CustomObjectQueryBuilder<T> sort(final List<QuerySort<CustomObject<T>>> sort) {
        return super.sort(sort);
    }

    @Override
    public CustomObjectQueryBuilder<T> sort(final QuerySort<CustomObject<T>> sort) {
        return super.sort(sort);
    }

    @Override
    public CustomObjectQueryBuilder<T> sortMulti(final Function<CustomObjectQueryModel<CustomObject<T>>, List<QuerySort<CustomObject<T>>>> m) {
        return super.sortMulti(m);
    }

    @Override
    public CustomObjectQueryBuilder<T> expansionPaths(final Function<CustomObjectExpansionModel<CustomObject<T>>, ExpansionPathContainer<CustomObject<T>>> m) {
        return super.expansionPaths(m);
    }
}
