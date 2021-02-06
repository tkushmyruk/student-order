package ua.taras.kushmyruk.domain;

public class Address {
    private Street street;
    private String building;
    private String extension;
    private String apartment;
    private String postCode;


    public Address(Street street, String building, String extension, String apartment, String postCode) {
        this.street = street;
        this.building = building;
        this.extension = extension;
        this.apartment = apartment;
        this.postCode = postCode;
    }

    public Address() {
    }

    public Street getStreet() {
        return street;
    }

    public void setStreet(Street street) {
        this.street = street;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    @Override
    public String toString() {
        return "Address{" +
                "street=" + street +
                ", building='" + building + '\'' +
                ", extension='" + extension + '\'' +
                ", apartment='" + apartment + '\'' +
                ", postCode='" + postCode + '\'' +
                '}';
    }
}
