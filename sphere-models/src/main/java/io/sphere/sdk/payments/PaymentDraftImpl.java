package io.sphere.sdk.payments;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.types.CustomFieldsDraft;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;
import java.time.ZonedDateTime;
import java.util.List;

final class PaymentDraftImpl extends Base implements PaymentDraft {
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
    @Nullable
    private final PaymentMethodInfo paymentMethodInfo;
    @Nullable
    private final CustomFieldsDraft custom;
    @Nullable
    private final PaymentStatus paymentStatus;
    private final List<TransactionDraft> transactions;
    private final List<CustomFieldsDraft> interfaceInteractions;

    @JsonCreator
    PaymentDraftImpl(@Nullable final MonetaryAmount amountAuthorized, @Nullable final Reference<Customer> customer, @Nullable final String externalId, @Nullable final String interfaceId, final MonetaryAmount amountPlanned, @Nullable final ZonedDateTime authorizedUntil, @Nullable final MonetaryAmount amountPaid, @Nullable final MonetaryAmount amountRefunded, @Nullable final PaymentMethodInfo paymentMethodInfo, @Nullable final CustomFieldsDraft custom, @Nullable final PaymentStatus paymentStatus, final List<TransactionDraft> transactions, final List<CustomFieldsDraft> interfaceInteractions) {
        this.amountAuthorized = amountAuthorized;
        this.customer = customer;
        this.externalId = externalId;
        this.interfaceId = interfaceId;
        this.amountPlanned = amountPlanned;
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
    public MonetaryAmount getAmountAuthorized() {
        return amountAuthorized;
    }

    @Override
    @Nullable
    public MonetaryAmount getAmountPaid() {
        return amountPaid;
    }

    @Override
    public MonetaryAmount getAmountPlanned() {
        return amountPlanned;
    }

    @Override
    @Nullable
    public MonetaryAmount getAmountRefunded() {
        return amountRefunded;
    }

    @Override
    @Nullable
    public ZonedDateTime getAuthorizedUntil() {
        return authorizedUntil;
    }

    @Override
    @Nullable
    public CustomFieldsDraft getCustom() {
        return custom;
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
    public List<CustomFieldsDraft> getInterfaceInteractions() {
        return interfaceInteractions;
    }

    @Override
    @Nullable
    public PaymentMethodInfo getPaymentMethodInfo() {
        return paymentMethodInfo;
    }

    @Override
    @Nullable
    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    @Override
    public List<TransactionDraft> getTransactions() {
        return transactions;
    }
}
