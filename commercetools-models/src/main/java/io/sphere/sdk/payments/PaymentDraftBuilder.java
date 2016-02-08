package io.sphere.sdk.payments;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Builder;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.types.CustomDraft;
import io.sphere.sdk.types.CustomFieldsDraft;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public final class PaymentDraftBuilder extends Base implements Builder<PaymentDraft>, CustomDraft {
    @Nullable
    private Reference<Customer> customer;
    @Nullable
    private String externalId;
    @Nullable
    private String interfaceId;
    private MonetaryAmount amountPlanned;
    @Nullable
    private MonetaryAmount amountAuthorized;
    @Nullable
    private ZonedDateTime authorizedUntil;
    @Nullable
    private MonetaryAmount amountPaid;
    @Nullable
    private MonetaryAmount amountRefunded;
    @Nullable
    private PaymentMethodInfo paymentMethodInfo;
    @Nullable
    private CustomFieldsDraft custom;
    @Nullable
    private PaymentStatus paymentStatus;
    @Nullable
    private List<TransactionDraft> transactions;
    @Nullable
    private List<CustomFieldsDraft> interfaceInteractions;

    @JsonCreator
    PaymentDraftBuilder(final MonetaryAmount amountPlanned) {
        this.amountPlanned = amountPlanned;
    }

    public static PaymentDraftBuilder of(final MonetaryAmount amountPlanned) {
        return new PaymentDraftBuilder(amountPlanned);
    }

    public PaymentDraftBuilder customer(@Nullable final Referenceable<Customer> customer) {
        this.customer = Optional.ofNullable(customer).map(x -> x.toReference()).orElse(null);
        return this;
    }

    public PaymentDraftBuilder externalId(@Nullable final String externalId) {
        this.externalId = externalId;
        return this;
    }

    public PaymentDraftBuilder interfaceId(@Nullable final String interfaceId) {
        this.interfaceId = interfaceId;
        return this;
    }

    public PaymentDraftBuilder amountPlanned(final MonetaryAmount amountPlanned) {
        this.amountPlanned = amountPlanned;
        return this;
    }

    public PaymentDraftBuilder amountAuthorized(@Nullable final MonetaryAmount amountAuthorized) {
        this.amountAuthorized = amountAuthorized;
        return this;
    }

    public PaymentDraftBuilder authorizedUntil(@Nullable final ZonedDateTime authorizedUntil) {
        this.authorizedUntil = authorizedUntil;
        return this;
    }

    public PaymentDraftBuilder amountPaid(@Nullable final MonetaryAmount amountPaid) {
        this.amountPaid = amountPaid;
        return this;
    }

    public PaymentDraftBuilder amountRefunded(@Nullable final MonetaryAmount amountRefunded) {
        this.amountRefunded = amountRefunded;
        return this;
    }

    public PaymentDraftBuilder paymentMethodInfo(@Nullable final PaymentMethodInfo paymentMethodInfo) {
        this.paymentMethodInfo = paymentMethodInfo;
        return this;
    }

    public PaymentDraftBuilder custom(@Nullable final CustomFieldsDraft custom) {
        this.custom = custom;
        return this;
    }

    public PaymentDraftBuilder paymentStatus(@Nullable final PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
        return this;
    }

    public PaymentDraftBuilder transactions(@Nullable final List<TransactionDraft> transactions) {
        this.transactions = transactions;
        return this;
    }

    public PaymentDraftBuilder interfaceInteractions(@Nullable final List<CustomFieldsDraft> interfaceInteractions) {
        this.interfaceInteractions = interfaceInteractions;
        return this;
    }

    @Nullable
    public MonetaryAmount getAmountAuthorized() {
        return amountAuthorized;
    }

    @Nullable
    public MonetaryAmount getAmountPaid() {
        return amountPaid;
    }

    public MonetaryAmount getAmountPlanned() {
        return amountPlanned;
    }

    @Nullable
    public MonetaryAmount getAmountRefunded() {
        return amountRefunded;
    }

    @Nullable
    public ZonedDateTime getAuthorizedUntil() {
        return authorizedUntil;
    }

    @Nullable
    public CustomFieldsDraft getCustom() {
        return custom;
    }

    @Nullable
    public Reference<Customer> getCustomer() {
        return customer;
    }

    @Nullable
    public String getExternalId() {
        return externalId;
    }

    @Nullable
    public String getInterfaceId() {
        return interfaceId;
    }

    @Nullable
    public List<CustomFieldsDraft> getInterfaceInteractions() {
        return interfaceInteractions;
    }

    @Nullable
    public PaymentMethodInfo getPaymentMethodInfo() {
        return paymentMethodInfo;
    }

    @Nullable
    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    @Nullable
    public List<TransactionDraft> getTransactions() {
        return transactions;
    }

    @Override
    public PaymentDraft build() {
        return new PaymentDraftImpl(amountAuthorized, customer, externalId, interfaceId, amountPlanned, authorizedUntil, amountPaid, amountRefunded, paymentMethodInfo, custom, paymentStatus, transactions, interfaceInteractions);
    }
}
