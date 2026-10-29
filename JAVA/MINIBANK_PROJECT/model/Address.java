package model;
import java.io.Serializable;
import model.annotation.MaxLength;
public class Address implements Serializable {
    private static final long serialVersionUID = 1L;
    @MaxLength(50) private String line;
    @MaxLength(30) private String city;
    @MaxLength(10) private String pincode;
    public Address(String line, String city, String pincode) {
        this.line = line; this.city = city; this.pincode = pincode;
    }
    public String getLine() { return line; }
    public String getCity() { return city; }
    public String getPincode() { return pincode; }
    @Override public String toString() { return line + ", " + city + " - " + pincode; }
}
