package io.sphere.sdk.orders.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.messages.OrderCustomerEmailSetMessage;

/**
  Sets the customer email.

 {@doc.gen intro}

 {@include.example io.sphere.sdk.orders.commands.OrderUpdateCommandIntegrationTest#setCustomerEmail()}

 @see Order#getCustomerEmail()
 @see OrderCustomerEmailSetMessage
 */
public final class SetCustomerEmail extends UpdateActionImpl<Order> {
    private final String email;

    private SetCustomerEmail(final String email) {
        super("setCustomerEmail");
        this.email = email;
    }

    public static SetCustomerEmail of(final String email) {
        return new SetCustomerEmail(email);
    }

    public String getEmail() {
        return email;
    }
}
