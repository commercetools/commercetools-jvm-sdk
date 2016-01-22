package io.sphere.sdk.payments.queries;

import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.payments.Payment;
import io.sphere.sdk.queries.*;
import io.sphere.sdk.types.queries.CustomQueryModel;
import io.sphere.sdk.types.queries.CustomQueryModelImpl;
import io.sphere.sdk.types.queries.CustomResourceQueryModelImpl;

final class PaymentQueryModelImpl extends CustomResourceQueryModelImpl<Payment> implements PaymentQueryModel {

    PaymentQueryModelImpl(QueryModel<Payment> parent, String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public CustomQueryModel<Payment> custom() {
        return new CustomQueryModelImpl<>(this, "custom");
    }

    @Override
    public ReferenceOptionalQueryModel<Payment, Customer> customer() {
        return referenceOptionalModel("customer");
    }

    @Override
    public StringQuerySortingModel<Payment> externalId() {
        return stringModel("externalId");
    }

    @Override
    public StringQuerySortingModel<Payment> interfaceId() {
        return stringModel("interfaceId");
    }

    @Override
    public MoneyQueryModel<Payment> amountPlanned() {
        return moneyModel("amountPlanned");
    }

    @Override
    public MoneyQueryModel<Payment> amountAuthorized() {
        return moneyModel("amountAuthorized");
    }

    @Override
    public MoneyQueryModel<Payment> amountPaid() {
        return moneyModel("amountPaid");
    }

    @Override
    public MoneyQueryModel<Payment> amountRefunded() {
        return moneyModel("amountRefunded");
    }

    @Override
    public TimestampSortingModel<Payment> authorizedUntil() {
        return timestampSortingModel("authorizedUntil");
    }

    @Override
    public PaymentMethodInfoQueryModel<Payment> paymentMethodInfo() {
        return new PaymentMethodInfoQueryModelImpl<>(this, "paymentMethodInfo");
    }

    @Override
    public PaymentStatusQueryModel<Payment> paymentStatus() {
        return new PaymentStatusQueryModelImpl<>(this, "paymentStatus");
    }

    @Override
    public TransactionCollectionQueryModel<Payment> transactions() {
        return new TransactionCollectionQueryModelImpl<>(this, "transactions");
    }
}
