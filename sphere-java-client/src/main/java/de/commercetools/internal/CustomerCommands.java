package de.commercetools.internal;

import net.jcip.annotations.Immutable;

/** Commands issued against the HTTP endpoints for working with shopping carts. */
public class CustomerCommands {

    @Immutable
    public static final class CreateCustomer implements Command {
        private final String email;
        private final String password;
        private final String firstName;
        private final String lastName;
        private final String middleName;
        private final String title;


        public CreateCustomer(String email, String password, String firstName, String lastName, String middleName, String title) {
            this.email = email;
            this.password = password;
            this.firstName = firstName;
            this.lastName = lastName;
            this.middleName = middleName;
            this.title = title;
        }

        public String getEmail() { return email; }
        public String getPassword() { return password; }
        public String getFirstName() { return firstName; }
        public String getLastName() { return lastName; }
        public String getMiddleName() { return middleName; }
        public String getTitle() { return title; }
    }

    @Immutable
    public static final class ChangeConsumerPassword extends CommandBase {
        private final String currentPassword;
        private final String newPassword;

        public ChangeConsumerPassword(String id, int version, String currentPassword, String newPassword) {
            super(id, version);
            this.currentPassword = currentPassword;
            this.newPassword = newPassword;
        }

        public String getCurrentPassword() { return currentPassword; }
        public String getNewPassword() { return newPassword; }
    }

}
