package io.sphere.sdk.discountcodes.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.discountcodes.DiscountCode;

import javax.annotation.Nullable;

/**
 * {@include.example io.sphere.sdk.discountcodes.commands.DiscountCodeUpdateCommandTest#setMaxApplicationsPerCustomer()}
 */
public class SetMaxApplicationsPerCustomer extends UpdateActionImpl<DiscountCode> {
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
