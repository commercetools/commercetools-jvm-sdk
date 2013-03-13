package de.commercetools.sphere.client.shop.model;

/** Represents a postal address. */
public class Address {
    private String title = "";
    private String salutation = "";
    private String firstName = "";
    private String lastName = "";
    private String streetName = "";
    private String streetNumber = "";
    private String additionalStreetInfo = "";
    private String postalCode = "";
    private String city = "";
    private String region = "";
    private String country = "";
    private String company = "";
    private String department = "";
    private String building = "";
    private String apartment = "";
    private String pOBox = "";
    private String phone = "";
    private String mobile = "";
    private String email = "";

    // for JSON deserializer
    private Address() {}


    /** The title of the addressee (e.g. Dr., Prof.). */
    public String getTitle() { return title; }

    /** The salutation of the addressee (e.g. Mr., Mrs.). */
    public String getSalutation() { return salutation; }

    /** The first name of the addressee. */
    public String getFirstName() { return firstName; }

    /** The last name of the addressee. */
    public String getLastName() { return lastName; }

    /** The street name of the addressee. */
    public String getStreetName() { return streetName; }

    /** The street number of the addressee. */
    public String getStreetNumber() { return streetNumber; }

    /** The additional street info (e.g. Backyard Building). */
    public String getAdditionalStreetInfo() { return additionalStreetInfo; }

    /** The postal code. */
    public String getPostalCode() { return postalCode; }

    /** The city. */
    public String getCity() { return city; }

    /** The region. */
    public String getRegion() { return region; }

    /** The country. */
    public String getCountry() { return country; }

    /** The company name. */
    public String getCompany() { return company; }

    /** The department. */
    public String getDepartment() { return department; }

    /** The building. */
    public String getBuilding() { return building; }

    /** The apartment. */
    public String getApartment() { return apartment; }

    /** PO Box. */
    public String getpOBox() { return pOBox; }

    /** Phone. */
    public String getPhone() { return phone; }

    /** Mobile. */
    public String getMobile() { return mobile; }

    /** Email. */
    public String getEmail() { return email; }
}
