package model;
import java.io.Serializable;
import model.annotation.MaxLength;
public class Customer implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L;
    private static long customerCounter = 100;
    private final String customerId;
    @MaxLength(40) private String name;
    private String email;
    private String mobile;
    private Address address;
    public Customer(String name, String email, String mobile, Address address) {
        this.customerId = generateCustomerId();
        this.name = name; this.email = email; this.mobile = mobile; this.address = address;
    }
    private synchronized static String generateCustomerId() { return "CUST" + (++customerCounter); }
    public String getCustomerId() { return customerId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getMobile() { return mobile; }
    public Address getAddress() { return address; }
    @Override public Object clone() throws CloneNotSupportedException { return super.clone(); }
    @Override public String toString() { return "Customer[" + customerId + ": " + name + ", " + email + ", " + mobile + "]"; }
}
