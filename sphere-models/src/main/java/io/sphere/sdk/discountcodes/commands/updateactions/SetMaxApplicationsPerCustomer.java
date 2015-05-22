package io.sphere.sdk.discountcodes.commands.updateactions;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.discountcodes.DiscountCode;

import java.util.Optional;

/**
 * {@include.example io.sphere.sdk.discountcodes.commands.DiscountCodeUpdateCommandTest#setMaxApplicationsPerCustomer()}
 */
public class SetMaxApplicationsPerCustomer extends UpdateAction<DiscountCode> {
    private final Optional<Long> maxApplicationsPerCustomer;

    private SetMaxApplicationsPerCustomer(final Optional<Long> maxApplicationsPerCustomer) {
        super("setMaxApplicationsPerCustomer");
        this.maxApplicationsPerCustomer = maxApplicationsPerCustomer;
    }

    public static SetMaxApplicationsPerCustomer of(final long maxApplicationsPerCustomer) {
        return of(Optional.of(maxApplicationsPerCustomer));
    }

    public static SetMaxApplicationsPerCustomer of(final Optional<Long> maxApplications) {
        return new SetMaxApplicationsPerCustomer(maxApplications);
    }

    public Optional<Long> getMaxApplicationsPerCustomer() {
        return maxApplicationsPerCustomer;
    }
}
