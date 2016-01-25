package io.sphere.sdk.payments.queries;

import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.payments.Payment;
import io.sphere.sdk.queries.*;
import io.sphere.sdk.types.queries.CustomQueryModel;
import io.sphere.sdk.types.queries.WithCustomQueryModel;

/**
 * {@doc.gen summary payments}
 */
public interface PaymentQueryModel extends ResourceQueryModel<Payment>, WithCustomQueryModel<Payment> {
    CustomQueryModel<Payment> custom();

    ReferenceOptionalQueryModel<Payment, Customer> customer();

    StringQuerySortingModel<Payment> externalId();

    StringQuerySortingModel<Payment> interfaceId();

    MoneyQueryModel<Payment> amountPlanned();

    MoneyQueryModel<Payment> amountAuthorized();

    MoneyQueryModel<Payment> amountPaid();

    MoneyQueryModel<Payment> amountRefunded();

    TimestampSortingModel<Payment> authorizedUntil();

    PaymentMethodInfoQueryModel<Payment> paymentMethodInfo();

    PaymentStatusQueryModel<Payment> paymentStatus();

    TransactionCollectionQueryModel<Payment> transactions();

    static PaymentQueryModel of() {
        return new PaymentQueryModelImpl(null, null);
    }
}
