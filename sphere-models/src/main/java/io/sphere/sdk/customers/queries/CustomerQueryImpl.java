package io.sphere.sdk.customers.queries;

import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.http.HttpQueryParameter;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.queries.*;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

final class CustomerQueryImpl extends UltraQueryDslImpl<Customer, CustomerQuery, CustomerQueryModel<Customer>, CustomerExpansionModel<Customer>> implements CustomerQuery {
    CustomerQueryImpl(){
        super(CustomerEndpoint.ENDPOINT.endpoint(), CustomerQuery.resultTypeReference(), CustomerQueryModel.of(), CustomerExpansionModel.of());
    }

    private CustomerQueryImpl(final Optional<QueryPredicate<Customer>> predicate, final List<QuerySort<Customer>> sort, final Optional<Long> limit, final Optional<Long> offset, final String endpoint, final Function<HttpResponse, PagedQueryResult<Customer>> resultMapper, final List<ExpansionPath<Customer>> expansionPaths, final List<HttpQueryParameter> additionalQueryParameters, final CustomerQueryModel<Customer> queryModel, final CustomerExpansionModel<Customer> expansionModel) {
        super(predicate, sort, limit, offset, endpoint, resultMapper, expansionPaths, additionalQueryParameters, queryModel, expansionModel);
    }

    @Override
    protected UltraQueryDslBuilder<Customer, CustomerQuery, CustomerQueryModel<Customer>, CustomerExpansionModel<Customer>> copyBuilder() {
        return new CustomerQueryQueryDslBuilder(this);
    }

    private static class CustomerQueryQueryDslBuilder extends UltraQueryDslBuilder<Customer, CustomerQuery, CustomerQueryModel<Customer>, CustomerExpansionModel<Customer>> {
        public CustomerQueryQueryDslBuilder(final UltraQueryDslImpl<Customer, CustomerQuery, CustomerQueryModel<Customer>, CustomerExpansionModel<Customer>> template) {
            super(template);
        }

        @Override
        public CustomerQueryImpl build() {
            return new CustomerQueryImpl(predicate, sort, limit, offset, endpoint, resultMapper, expansionPaths, additionalQueryParameters, queryModel, expansionModel);
        }
    }
}