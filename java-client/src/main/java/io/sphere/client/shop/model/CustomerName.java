package io.sphere.client.shop.model;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import net.jcip.annotations.Immutable;

import javax.annotation.Nonnull;

import static io.sphere.internal.util.Util.emptyIfNull;

import java.util.ArrayList;
import java.util.List;

/** Value object representing a name of a {@link Customer customer}. */
@Immutable
public class CustomerName {
    @Nonnull private final String title;
    @Nonnull private final String firstName;
    @Nonnull private final String middleName;
    @Nonnull private final String lastName;

    /** Customer's first name. */
    public String getFirstName() { return firstName; }

    /** Customer's last name. */
    public String getLastName() { return lastName; }

    /** Customer's middle name. If multiple middle names are needed, use e.g. middle names joined by spaces. */
    public String getMiddleName() { return middleName; }

    /** Customer's title. */
    public String getTitle() { return title; }

    /** Creates a customer name with first and last name. */
    public CustomerName(String firstName, String lastName) {
        this.title = "";
        this.firstName = emptyIfNull(firstName);
        this.middleName = "";
        this.lastName = emptyIfNull(lastName);
    }

    /** Creates a customer name with first, middle and last name. */
    public CustomerName(String firstName, String middleName, String lastName) {
        this.title = "";
        this.firstName = emptyIfNull(firstName);
        this.lastName = emptyIfNull(lastName);
        this.middleName = emptyIfNull(middleName);
    }

    /** Creates a customer name with title, first, middle and last name. */
    public CustomerName(String title, String firstName, String middleName, String lastName) {
        this.title = emptyIfNull(title);
        this.firstName = emptyIfNull(firstName);
        this.lastName = emptyIfNull(lastName);
        this.middleName = emptyIfNull(middleName);
    }

    /** Parses a CustomerName object from a string in form 'firstName [middleName1 .. middleNameN] lastName' (ignoring title). */
    public static CustomerName parse(String name) {
        ArrayList<String> nameParts = new ArrayList<String>();
        for (String part : name.split(" ")) {
            if (!Strings.isNullOrEmpty(part)) nameParts.add(part);
        }
        String firstName = (nameParts.size() >= 1) ? nameParts.get(0) : "";
        String lastName = (nameParts.size() >= 2) ? nameParts.get(nameParts.size() - 1) : "";
        String middleName = (nameParts.size() >= 3) ? Joiner.on(" ").join(nameParts.subList(1, nameParts.size() - 1)) : "";
        return new CustomerName(firstName, middleName, lastName);
    }

    @Override public String toString() {
        List<String> nameParts = new ArrayList<String>();
        if (!Strings.isNullOrEmpty(title)) nameParts.add(title);
        if (!Strings.isNullOrEmpty(firstName)) nameParts.add(firstName);
        if (!Strings.isNullOrEmpty(middleName)) nameParts.add(middleName);
        if (!Strings.isNullOrEmpty(lastName)) nameParts.add(lastName);
        return Joiner.on(" ").join(nameParts);
    }

    // ----------------------------
    // equals() and hashCode()
    // ----------------------------

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomerName that = (CustomerName) o;

        if (!firstName.equals(that.firstName)) return false;
        if (!lastName.equals(that.lastName)) return false;
        if (!middleName.equals(that.middleName)) return false;
        if (!title.equals(that.title)) return false;

        return true;
    }

    @Override public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + firstName.hashCode();
        result = 31 * result + middleName.hashCode();
        result = 31 * result + lastName.hashCode();
        return result;
    }
}
