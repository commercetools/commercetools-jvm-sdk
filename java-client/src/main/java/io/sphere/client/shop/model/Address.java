package io.sphere.client.shop.model;

import javax.annotation.Nonnull;

import com.neovisionaries.i18n.CountryCode;
import org.codehaus.jackson.map.annotate.JsonFilter;

/** Represents a postal address. */
@JsonFilter("changeAddressIdFilter")
public class Address implements Cloneable {
    private String id = "";
    private String title = "";
    private String salutation = "";
    private String firstName = "";
    private String lastName = "";
    private String careOf = "";
    private String streetName = "";
    private String streetNumber = "";
    private String additionalStreetInfo = "";
    private String postalCode = "";
    private String city = "";
    private String region = "";
    private String state = "";
    @Nonnull private CountryCode country;
    private String company = "";
    private String department = "";
    private String building = "";
    private String apartment = "";
    private String poBox = "";
    private String phone = "";
    private String mobile = "";
    private String email = "";

    // for JSON deserializer
    private Address() {}

    public Address(CountryCode country) {
        this.country = country;
    }

    // ---------------------
    // Getters
    // ---------------------

    /** The id of the address, assigned by the Sphere backend.
     *
     *  The id is represents a snapshot of a customer's address: each change of
     *  {@linkplain Customer#getAddresses customer's address}
     *  (using {@link io.sphere.client.shop.CustomerService#update})
     *  means creating a new address with a new id and replacing the old address with it. */
    public String getId() { return id; }

    /** The title of the addressee (e.g. Dr., Prof.). */
    public String getTitle() { return title; }

    /** The salutation of the addressee (e.g. Mr., Mrs.). */
    public String getSalutation() { return salutation; }

    /** The first name of the addressee. */
    public String getFirstName() { return firstName; }

    /** The last name of the addressee. */
    public String getLastName() { return lastName; }

    /** The c/o field. */
    public String getCareOf() { return careOf; }

    /** The post-office box. */
    public String getPoBox() { return poBox; }

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

    /** The state. */
    public String getState() { return state; }

    /** The country. */
    public CountryCode getCountry() { return country; }

    /** The company name. */
    public String getCompany() { return company; }

    /** The department. */
    public String getDepartment() { return department; }

    /** The building. */
    public String getBuilding() { return building; }

    /** The apartment. */
    public String getApartment() { return apartment; }

    /** The post office box. */
    public String getPOBox() { return poBox; }

    /** The phone number. */
    public String getPhone() { return phone; }

    /** The mobile phone number. */
    public String getMobile() { return mobile; }

    /** The email. */
    public String getEmail() { return email; }

    // ---------------------
    // Setters
    // ---------------------

    /** Sets the title (e.g. Dr., Prof.). */
    public void setTitle(String title) { this.title = title; }

    /** Sets the salutation (e.g. Mr., Mrs.). */
    public void setSalutation(String salutation) { this.salutation = salutation; }

    /** Sets the first name. */
    public void setFirstName(String firstName) { this.firstName = firstName; }

    /** Sets the last name. */
    public void setLastName(String lastName) { this.lastName = lastName; }

    /** Sets the street name. */
    public void setStreetName(String streetName) { this.streetName = streetName; }

    /** Sets the street number. */
    public void setStreetNumber(String streetNumber) { this.streetNumber = streetNumber; }

    /** Sets additional street info (e.g. "Backyard Building"). */
    public void setAdditionalStreetInfo(String additionalStreetInfo) { this.additionalStreetInfo = additionalStreetInfo; }

    /** Sets the postal code. */
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }

    /** Sets the city. */
    public void setCity(String city) { this.city = city; }

    /** Sets the region. */
    public void setRegion(String region) { this.region = region; }

    /** Sets the state (like Alabama or Alaska in the US). */
    public void setState(String state) { this.state = state; }

    /** Sets the country. */
    public void setCountry(CountryCode country) { this.country = country; }

    /** Sets the company. */
    public void setCompany(String company) { this.company = company; }

    /** Sets the department. */
    public void setDepartment(String department) { this.department = department; }

    /** Sets the building. */
    public void setBuilding(String building) { this.building = building; }

    /** Sets the apartment. */
    public void setApartment(String apartment) { this.apartment = apartment; }

    /** Sets the post office Box. */
    public void setPOBox(String poBox) { this.poBox = poBox; }

    /** Sets the phone number. */
    public void setPhone(String phone) { this.phone = phone; }

    /** Sets the mobile phone number. */
    public void setMobile(String mobile) { this.mobile = mobile; }

    /** Sets the email. */
    public void setEmail(String email) { this.email = email; }

    /** Sets the c/o. */
    public void setCareOf(String careOf) { this.careOf = careOf; }

    /** Sets the post-office box. */
    public void setPoBox(String poBox) { this.poBox = poBox; }

    @Override
    public String toString() {
        return "Address{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", salutation='" + salutation + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", careOf='" + careOf + '\'' +
                ", streetName='" + streetName + '\'' +
                ", streetNumber='" + streetNumber + '\'' +
                ", additionalStreetInfo='" + additionalStreetInfo + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", city='" + city + '\'' +
                ", region='" + region + '\'' +
                ", state='" + state + '\'' +
                ", country=" + country +
                ", company='" + company + '\'' +
                ", department='" + department + '\'' +
                ", building='" + building + '\'' +
                ", apartment='" + apartment + '\'' +
                ", poBox='" + poBox + '\'' +
                ", phone='" + phone + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
