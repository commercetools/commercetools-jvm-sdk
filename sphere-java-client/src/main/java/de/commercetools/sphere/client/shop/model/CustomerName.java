package de.commercetools.sphere.client.shop.model;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;

import java.util.ArrayList;
import java.util.List;

/** Customer name object. */
public class CustomerName {
    private String title;
    private String firstName;
    private String middleName;
    private String lastName;

    /** Creates a customer name with first and last name. */
    public CustomerName(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /** Creates a customer name with first, middle and last name. */
    public CustomerName(String firstName, String middleName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
    }

    /** Creates a customer name with title, first, middle and last name. */
    public CustomerName(String title, String firstName, String middleName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.title = title;
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

    /** Customer's first name. */
    public String getFirstName() { return firstName; }

    /** Customer's last name. */
    public String getLastName() { return lastName; }

    /** Customer's middle name. Use e.g. middle names joined by spaces if multiple middle names are needed. */
    public String getMiddleName() { return middleName; }

    /** Customer's title. */
    public String getTitle() { return title; }

    /** Sets customer's first name. */
    public void setFirstName(String firstName) { this.firstName = firstName; }

    /** Sets customer's last name. */
    public void setLastName(String lastName) { this.lastName = lastName; }

    /** Sets customer's middle name. Use e.g. middle names joined by spaces if multiple middle names are needed. */
    public void setMiddleName(String middleName) { this.middleName = middleName; }

    /** Sets customer's title. */
    public void setTitle(String title) { this.title = title; }
}
