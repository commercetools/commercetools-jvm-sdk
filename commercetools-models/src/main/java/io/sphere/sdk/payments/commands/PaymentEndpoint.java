package io.sphere.sdk.payments.commands;

import io.sphere.sdk.client.JsonEndpoint;
import io.sphere.sdk.payments.Payment;

final class PaymentEndpoint {
    public static final JsonEndpoint<Payment> ENDPOINT = JsonEndpoint.of(Payment.typeReference(), "/payments");
}
