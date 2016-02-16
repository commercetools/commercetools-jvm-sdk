package io.sphere.sdk.discountcodes.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.discountcodes.DiscountCode;

import javax.annotation.Nullable;

/**
 * Sets the maximum number of applicable discount codes per customer.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.discountcodes.commands.DiscountCodeUpdateCommandIntegrationTest#setMaxApplicationsPerCustomer()}
 */
public final class SetMaxApplicationsPerCustomer extends UpdateActionImpl<DiscountCode> {
    @Nullable
    private final Long maxApplicationsPerCustomer;

    private SetMaxApplicationsPerCustomer(@Nullable final Long maxApplicationsPerCustomer) {
        super("setMaxApplicationsPerCustomer");
        this.maxApplicationsPerCustomer = maxApplicationsPerCustomer;
    }

    public static SetMaxApplicationsPerCustomer of(@Nullable final Long maxApplications) {
        return new SetMaxApplicationsPerCustomer(maxApplications);
    }

    @Nullable
    public Long getMaxApplicationsPerCustomer() {
        return maxApplicationsPerCustomer;
    }
}
