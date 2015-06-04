package io.sphere.sdk.customergroups.queries;

import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.customergroups.expansion.CustomerGroupExpansionModel;
import io.sphere.sdk.http.HttpQueryParameter;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.queries.*;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 {@doc.gen summary customer groups}
 */
final class CustomerGroupQueryImpl extends UltraQueryDslImpl<CustomerGroup, CustomerGroupQuery, CustomerGroupQueryModel<CustomerGroup>, CustomerGroupExpansionModel<CustomerGroup>> implements CustomerGroupQuery {
    CustomerGroupQueryImpl(){
        super(CustomerGroupEndpoint.ENDPOINT.endpoint(), CustomerGroupQuery.resultTypeReference(), CustomerGroupQueryModel.of(), CustomerGroupExpansionModel.of());
    }

    private CustomerGroupQueryImpl(final Optional<QueryPredicate<CustomerGroup>> predicate, final List<QuerySort<CustomerGroup>> sort, final Optional<Long> limit, final Optional<Long> offset, final String endpoint, final Function<HttpResponse, PagedQueryResult<CustomerGroup>> resultMapper, final List<ExpansionPath<CustomerGroup>> expansionPaths, final List<HttpQueryParameter> additionalQueryParameters, final CustomerGroupQueryModel<CustomerGroup> queryModel, final CustomerGroupExpansionModel<CustomerGroup> expansionModel) {
        super(predicate, sort, limit, offset, endpoint, resultMapper, expansionPaths, additionalQueryParameters, queryModel, expansionModel);
    }

    @Override
    protected UltraQueryDslBuilder<CustomerGroup, CustomerGroupQuery, CustomerGroupQueryModel<CustomerGroup>, CustomerGroupExpansionModel<CustomerGroup>> copyBuilder() {
        return new CustomerGroupQueryQueryDslBuilder(this);
    }

    private static class CustomerGroupQueryQueryDslBuilder extends UltraQueryDslBuilder<CustomerGroup, CustomerGroupQuery, CustomerGroupQueryModel<CustomerGroup>, CustomerGroupExpansionModel<CustomerGroup>> {
        public CustomerGroupQueryQueryDslBuilder(final UltraQueryDslImpl<CustomerGroup, CustomerGroupQuery, CustomerGroupQueryModel<CustomerGroup>, CustomerGroupExpansionModel<CustomerGroup>> template) {
            super(template);
        }

        @Override
        public CustomerGroupQueryImpl build() {
            return new CustomerGroupQueryImpl(predicate, sort, limit, offset, endpoint, resultMapper, expansionPaths, additionalQueryParameters, queryModel, expansionModel);
        }
    }
}