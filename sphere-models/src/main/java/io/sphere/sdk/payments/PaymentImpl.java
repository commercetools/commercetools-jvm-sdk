package io.sphere.sdk.payments;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.ResourceImpl;
import io.sphere.sdk.types.CustomFields;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;
import java.time.ZonedDateTime;
import java.util.List;

final class PaymentImpl extends ResourceImpl<Payment> implements Payment {
    @Nullable
    private final Reference<Customer> customer;
    @Nullable
    private final String externalId;
    @Nullable
    private final String interfaceId;
    private final MonetaryAmount amountPlanned;
    @Nullable
    private final MonetaryAmount amountAuthorized;
    @Nullable
    private final ZonedDateTime authorizedUntil;
    @Nullable
    private final MonetaryAmount amountPaid;
    @Nullable
    private final MonetaryAmount amountRefunded;
    private final PaymentMethodInfo paymentMethodInfo;
    @Nullable
    private final CustomFields custom;
    private final PaymentStatus paymentStatus;
    private final List<Transaction> transactions;
    private final List<CustomFields> interfaceInteractions;

    @JsonCreator
    private PaymentImpl(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, @Nullable final Reference<Customer> customer, @Nullable final String externalId, @Nullable final String interfaceId, final MonetaryAmount amountPlanned, @Nullable final MonetaryAmount amountAuthorized, @Nullable final ZonedDateTime authorizedUntil, @Nullable final MonetaryAmount amountPaid, @Nullable final MonetaryAmount amountRefunded, final PaymentMethodInfo paymentMethodInfo, @Nullable final CustomFields custom, final PaymentStatus paymentStatus, final List<Transaction> transactions, final List<CustomFields> interfaceInteractions) {
        super(id, version, createdAt, lastModifiedAt);
        this.customer = customer;
        this.externalId = externalId;
        this.interfaceId = interfaceId;
        this.amountPlanned = amountPlanned;
        this.amountAuthorized = amountAuthorized;
        this.authorizedUntil = authorizedUntil;
        this.amountPaid = amountPaid;
        this.amountRefunded = amountRefunded;
        this.paymentMethodInfo = paymentMethodInfo;
        this.custom = custom;
        this.paymentStatus = paymentStatus;
        this.transactions = transactions;
        this.interfaceInteractions = interfaceInteractions;
    }

    @Override
    @Nullable
    public Reference<Customer> getCustomer() {
        return customer;
    }

    @Override
    @Nullable
    public String getExternalId() {
        return externalId;
    }

    @Override
    @Nullable
    public String getInterfaceId() {
        return interfaceId;
    }

    @Override
    public MonetaryAmount getAmountPlanned() {
        return amountPlanned;
    }

    @Override
    @Nullable
    public MonetaryAmount getAmountAuthorized() {
        return amountAuthorized;
    }

    @Override
    @Nullable
    public ZonedDateTime getAuthorizedUntil() {
        return authorizedUntil;
    }

    @Override
    @Nullable
    public MonetaryAmount getAmountPaid() {
        return amountPaid;
    }

    @Override
    @Nullable
    public MonetaryAmount getAmountRefunded() {
        return amountRefunded;
    }

    @Override
    public PaymentMethodInfo getPaymentMethodInfo() {
        return paymentMethodInfo;
    }

    @Override
    @Nullable
    public CustomFields getCustom() {
        return custom;
    }

    @Override
    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    @Override
    public List<Transaction> getTransactions() {
        return transactions;
    }

    @Override
    public List<CustomFields> getInterfaceInteractions() {
        return interfaceInteractions;
    }
}
