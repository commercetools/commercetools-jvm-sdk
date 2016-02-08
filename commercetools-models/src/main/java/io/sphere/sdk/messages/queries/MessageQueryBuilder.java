package io.sphere.sdk.messages.queries;

import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.messages.Message;
import io.sphere.sdk.messages.expansion.MessageExpansionModel;
import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.queries.QuerySort;
import io.sphere.sdk.queries.ResourceMetaModelQueryDslBuilderImpl;

import java.util.List;
import java.util.function.Function;

/**

 {@doc.gen summary }

 */
public final class MessageQueryBuilder extends ResourceMetaModelQueryDslBuilderImpl<MessageQueryBuilder, Message, MessageQuery, MessageQueryModel, MessageExpansionModel<Message>> {

    private MessageQueryBuilder(final MessageQuery template) {
        super(template);
    }

    public static MessageQueryBuilder of() {
        return new MessageQueryBuilder(MessageQuery.of());
    }

    @Override
    protected MessageQueryBuilder getThis() {
        return this;
    }

    @Override
    public MessageQuery build() {
        return super.build();
    }

    @Override
    public MessageQueryBuilder fetchTotal(final boolean fetchTotal) {
        return super.fetchTotal(fetchTotal);
    }

    @Override
    public MessageQueryBuilder limit(final Long limit) {
        return super.limit(limit);
    }

    @Override
    public MessageQueryBuilder limit(final long limit) {
        return super.limit(limit);
    }

    @Override
    public MessageQueryBuilder offset(final Long offset) {
        return super.offset(offset);
    }

    @Override
    public MessageQueryBuilder offset(final long offset) {
        return super.offset(offset);
    }

    @Override
    public MessageQueryBuilder plusExpansionPaths(final Function<MessageExpansionModel<Message>, ExpansionPathContainer<Message>> m) {
        return super.plusExpansionPaths(m);
    }

    @Override
    public MessageQueryBuilder plusPredicates(final Function<MessageQueryModel, QueryPredicate<Message>> m) {
        return super.plusPredicates(m);
    }

    @Override
    public MessageQueryBuilder plusPredicates(final QueryPredicate<Message> queryPredicate) {
        return super.plusPredicates(queryPredicate);
    }

    @Override
    public MessageQueryBuilder plusPredicates(final List<QueryPredicate<Message>> queryPredicates) {
        return super.plusPredicates(queryPredicates);
    }

    @Override
    public MessageQueryBuilder plusSort(final Function<MessageQueryModel, QuerySort<Message>> m) {
        return super.plusSort(m);
    }

    @Override
    public MessageQueryBuilder plusSort(final List<QuerySort<Message>> sort) {
        return super.plusSort(sort);
    }

    @Override
    public MessageQueryBuilder plusSort(final QuerySort<Message> sort) {
        return super.plusSort(sort);
    }

    @Override
    public MessageQueryBuilder predicates(final Function<MessageQueryModel, QueryPredicate<Message>> m) {
        return super.predicates(m);
    }

    @Override
    public MessageQueryBuilder predicates(final QueryPredicate<Message> queryPredicate) {
        return super.predicates(queryPredicate);
    }

    @Override
    public MessageQueryBuilder predicates(final List<QueryPredicate<Message>> queryPredicates) {
        return super.predicates(queryPredicates);
    }

    @Override
    public MessageQueryBuilder sort(final Function<MessageQueryModel, QuerySort<Message>> m) {
        return super.sort(m);
    }

    @Override
    public MessageQueryBuilder sort(final List<QuerySort<Message>> sort) {
        return super.sort(sort);
    }

    @Override
    public MessageQueryBuilder sort(final QuerySort<Message> sort) {
        return super.sort(sort);
    }

    @Override
    public MessageQueryBuilder sortMulti(final Function<MessageQueryModel, List<QuerySort<Message>>> m) {
        return super.sortMulti(m);
    }

    @Override
    public MessageQueryBuilder expansionPaths(final Function<MessageExpansionModel<Message>, ExpansionPathContainer<Message>> m) {
        return super.expansionPaths(m);
    }
}
