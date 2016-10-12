package io.sphere.sdk.http;

public final class SolutionInfo extends Base {
    private String name;
    private String version;
    private String website;
    private String emergencyContact;

    public SolutionInfo() {
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(final String version) {
        this.version = version;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(final String website) {
        this.website = website;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(final String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }
}
