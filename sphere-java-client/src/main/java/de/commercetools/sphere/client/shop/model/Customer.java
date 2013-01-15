package de.commercetools.sphere.client.shop.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/** A customer that exists in the backend. */
@JsonIgnoreProperties("type")
public class Customer {
    private String id;
    private int version;
    private String email = "";
    private String firstName = "";
    private String lastName = "";
    private String middleName = "";
    private String title = "";
    private String password = "";
    private List<Address> shippingAddresses = new ArrayList<Address>();  // initialize to prevent NPEs
    private int defaultShippingAddress;
    @JsonProperty("isEmailVerified") private boolean isEmailVerified;

    // for JSON deserializer
    private Customer() {}

    public Customer(String id, int version) {
        this.id = id;
        this.version = version;
    }

    /** Unique id of this customer. */
    public String getId() { return id; }

    /** Version of this customer that increases when the customer is modified. */
    public int getVersion() { return version; }

    /** Email address of the customer. */
    public String getEmail() { return email; }

    /** First name of the customer. */
    public String getFirstName() { return firstName; }

    /** Last name of the customer. */
    public String getLastName() { return lastName; }

    /** Middle name of the customer. */
    public String getMiddleName() { return middleName; }

    /** Title (e.g. Dr.) of the customer. */
    public String getTitle() { return title; }

    /** Password of the customer. */
    public String getPassword() { return password; }

    /** List of customer's shipping addresses. */
    public List<Address> getShippingAddresses() { return shippingAddresses; }

    /** Index of the default shipping address in the shipping addresses list. */
    public int getDefaultShippingAddress() { return defaultShippingAddress; }

    /** A flag indicating that the customer email has been verified. */
    public boolean isEmailVerified() { return isEmailVerified; }
}
