package io.sphere.sdk.payments.queries;

import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.payments.Payment;
import io.sphere.sdk.queries.*;
import io.sphere.sdk.types.queries.CustomQueryModel;
import io.sphere.sdk.types.queries.CustomQueryModelImpl;
import io.sphere.sdk.types.queries.CustomResourceQueryModelImpl;
import io.sphere.sdk.types.queries.WithCustomQueryModel;

/**
 * {@doc.gen summary payments}
 */
public final class PaymentQueryModel extends CustomResourceQueryModelImpl<Payment> implements WithCustomQueryModel<Payment> {

    public static PaymentQueryModel of() {
        return new PaymentQueryModel(null, null);
    }

    private PaymentQueryModel(QueryModel<Payment> parent, String pathSegment) {
        super(parent, pathSegment);
    }

    public CustomQueryModel<Payment> custom() {
        return new CustomQueryModelImpl<>(this, "custom");
    }

    public ReferenceOptionalQueryModel<Payment, Customer> customer() {
        return referenceOptionalModel("customer");
    }

    public StringQuerySortingModel<Payment> externalId() {
        return stringModel("externalId");
    }

    public StringQuerySortingModel<Payment> interfaceId() {
        return stringModel("interfaceId");
    }

    public MoneyQueryModel<Payment> amountPlanned() {
        return moneyModel("amountPlanned");
    }

    public MoneyQueryModel<Payment> amountAuthorized() {
        return moneyModel("amountAuthorized");
    }

    public MoneyQueryModel<Payment> amountPaid() {
        return moneyModel("amountPaid");
    }

    public MoneyQueryModel<Payment> amountRefunded() {
        return moneyModel("amountRefunded");
    }

    public TimestampSortingModel<Payment> authorizedUntil() {
        return timestampSortingModel("authorizedUntil");
    }

    public PaymentMethodInfoQueryModel<Payment> paymentMethodInfo() {
        return new PaymentMethodInfoQueryModelImpl<>(this, "paymentMethodInfo");
    }

    public PaymentStatusQueryModel<Payment> paymentStatus() {
        return new PaymentStatusQueryModelImpl<>(this, "paymentStatus");
    }

    public TransactionCollectionQueryModel<Payment> transactions() {
        return new TransactionCollectionQueryModelImpl<>(this, "transactions");
    }
}
