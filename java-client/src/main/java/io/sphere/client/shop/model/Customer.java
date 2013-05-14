package io.sphere.client.shop.model;

import java.util.ArrayList;
import java.util.List;

import io.sphere.client.model.Reference;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/** A customer that exists in the backend. */
@JsonIgnoreProperties("type")
public class Customer {
    private String id;
    private int version;
    private String email = "";
    @JsonProperty("title") private String title = "";
    @JsonProperty("firstName") private String firstName = "";
    @JsonProperty("middleName") private String middleName = "";
    @JsonProperty("lastName") private String lastName = "";
    @JsonProperty("password") private String passwordHash = "";    // not exposed (needed?)
    private List<Address> addresses = new ArrayList<Address>();  // initialize to prevent NPEs
    private int defaultShippingAddress;
    private int defaultBillingAddress;
    @JsonProperty("isEmailVerified") private boolean isEmailVerified;
    private Reference<CustomerGroup> customerGroup;

    // for JSON deserializer
    private Customer() {}

    public Customer(String id, int version) {
        this.id = id;
        this.version = version;
    }

    /** Unique id of this customer. */
    public String getId() { return id; }

    /** Version of this customer. */
    public int getVersion() { return version; }

    /** Email address of the customer. */
    public String getEmail() { return email; }

    /** Returns the parts of customer's name conveniently wrapped in a single object. */
    public CustomerName getName() {
        return new CustomerName(title,  firstName, middleName, lastName);
    }

    /** Customer's title. */
    public String getTitle() { return title; }

    /** The first name. */
    public String getFirstName() { return firstName; }

    /** The last name. */
    public String getLastName() { return lastName; }

    /** The middle name. Use e.g. middle names joined by spaces if multiple middle names are needed. */
    public String getMiddleName() { return middleName; }

    /** A list of customer's addresses. */
    public List<Address> getAddresses() { return addresses; }

    /** Index of the default shipping address in the shipping addresses list. Optional. */
    public int getDefaultShippingAddress() { return defaultShippingAddress; }

    /** Index of the default billing address in the billing addresses list. Optional. */
    public int getDefaultBillingAddress() { return defaultBillingAddress; }

    /** If true, customer's email has been verified. */
    public boolean isEmailVerified() { return isEmailVerified; }

    /** The customer group this customer belongs to. Optional. */
    public Reference<CustomerGroup> getCustomerGroup() { return customerGroup; }
}
