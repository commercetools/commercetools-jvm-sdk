package io.sphere.sdk.customers.commands.updateactions;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.customers.Customer;

import java.util.Optional;
import java.time.LocalDate;

/**
 * Sets a date of birth for the customer.
 *
 * {@include.example io.sphere.sdk.customers.commands.CustomerUpdateCommandTest#setDateOfBirth()}
 */
public class SetDateOfBirth extends UpdateAction<Customer> {
    private final Optional<LocalDate> dateOfBirth;

    private SetDateOfBirth(final Optional<LocalDate> dateOfBirth) {
        super("setDateOfBirth");
        this.dateOfBirth = dateOfBirth;
    }

    public static SetDateOfBirth of(final Optional<LocalDate> dateOfBirth) {
        return new SetDateOfBirth(dateOfBirth);
    }

    public static SetDateOfBirth of(final LocalDate dateOfBirth) {
        return of(Optional.of(dateOfBirth));
    }

    public Optional<LocalDate> getDateOfBirth() {
        return dateOfBirth;
    }
}
