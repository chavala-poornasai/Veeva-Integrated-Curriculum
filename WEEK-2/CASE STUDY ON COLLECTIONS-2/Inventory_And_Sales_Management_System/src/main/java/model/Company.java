package model;

public class Company {
    private int companyId;
    private String companyName;
    private String contactDetails;
    private String location;

    public Company(int companyId, String companyName, String contactDetails, String location) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.contactDetails = contactDetails;
        this.location = location;
    }

    public int getCompanyId() {
        return companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getContactDetails() {
        return contactDetails;
    }

    public String getLocation() {
        return location;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setContactDetails(String contactDetails) {
        this.contactDetails = contactDetails;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}

