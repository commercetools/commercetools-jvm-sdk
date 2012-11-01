package de.commercetools.sphere.client.shop.model;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/** A customer that exists on the backend. */
@JsonIgnoreProperties("type")
public class Customer {
    private String id;
    private int version;
    private String email;
    private String firstName;
    private String lastName;
    private String middleName;
    private String title;
    private String password;
    private List<Address> shippingAddresses = new ArrayList<Address>();  // initialize to prevent NPEs
    private int defaultShippingAddress;
    private boolean isEmailVerified;


    // for JSON deserializer
    private Customer() {}

    protected Customer(String id, int version) {
        this.id = id;
        this.version = version;
    }

    /** Unique id of this customer. */
    public String getId() {
        return id;
    }

    /** Version of this customer that increases when the customer is changed. */
    public int getVersion() {
        return version;
    }

    /** The email of the customer. */
    public String getEmail() {
        return email;
    }

    /** First name of the customer. */
    public String getFirstName() {
        return firstName;
    }

    /** Last name of the customer. */
    public String getLastName() {
        return lastName;
    }

    /** Middle name of the customer. */
    public String getMiddleName() {
        return middleName;
    }

    /** The title (e.g. dr.) of the customer. */
    public String getTitle() {
        return title;
    }

    /** Password of the customer. */
    public String getPassword() {
        return password;
    }

    /** The list of customer's shipping addresses. */
    public List<Address> getShippingAddresses() {
        return shippingAddresses;
    }

    /** The index of the default shipping address in shippingAddresses. */
    public int getDefaultShippingAddress() {
        return defaultShippingAddress;
    }

    /** A flag indicating that the customer verified the email. */
    public boolean isEmailVerified() {
        return isEmailVerified;
    }
}
