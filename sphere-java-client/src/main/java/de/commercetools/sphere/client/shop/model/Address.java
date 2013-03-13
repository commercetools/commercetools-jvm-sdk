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

    /** The country, see http://www.iso.ch/iso/en/prods-services/iso3166ma/02iso-3166-code-lists/list-en1.html
     *  (a valid java.util.Locale country). */
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

    /** Title (e.g. Dr., Prof.). */
    public void setTitle(String title) {
        this.title = title;
    }

    /** Salutation (e.g. Mr., Mrs.). */
    public void setSalutation(String salutation) { this.salutation = salutation; }

    /** First name. */
    public void setFirstName(String firstName) { this.firstName = firstName; }

    /** Last name. */
    public void setLastName(String lastName) { this.lastName = lastName; }


    /** Street name. */
    public void setStreetName(String streetName) { this.streetName = streetName; }

    /** Street number. */
    public void setStreetNumber(String streetNumber) { this.streetNumber = streetNumber; }

    /** Additional street info (e.g. Backyard Building). */
    public void setAdditionalStreetInfo(String additionalStreetInfo) { this.additionalStreetInfo = additionalStreetInfo; }

    /** Postal code. */
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }

    /** City. */
    public void setCity(String city) { this.city = city; }

    /** Region. */
    public void setRegion(String region) { this.region = region; }

    /** Country, see http://www.iso.ch/iso/en/prods-services/iso3166ma/02iso-3166-code-lists/list-en1.html
     *  (a valid java.util.Locale country). */
    public void setCountry(String country) { this.country = country; }

    /** Company. */
    public void setCompany(String company) { this.company = company; }

    /** Department. */
    public void setDepartment(String department) { this.department = department; }

    /** Building. */
    public void setBuilding(String building) { this.building = building; }

    /** Apartment. */
    public void setApartment(String apartment) { this.apartment = apartment; }

    /** PO Box. */
    public void setpOBox(String pOBox) { this.pOBox = pOBox; }

    /** Phone. */
    public void setPhone(String phone) { this.phone = phone; }

    /** Mobile. */
    public void setMobile(String mobile) { this.mobile = mobile; }

    /** Email. */
    public void setEmail(String email) { this.email = email; }
}
