package model;

import java.io.Serializable;

public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    // ==========================================
    // CUSTOMER DETAILS
    // ==========================================

    private final String customerId;
    private String name;
    private String email;
    private String mobile;
    private Address address;

    // ==========================================
    // CUSTOMER ID GENERATION
    // ==========================================

    private static long customerCounter = 100;

    private static synchronized String generateCustomerId() {

        customerCounter++;

        return "CUST" + customerCounter;
    }

    // ==========================================
    // CONSTRUCTOR 1
    // ==========================================

    public Customer(String name, String email, String mobile) {

        this.customerId = generateCustomerId();

        this.name = name;
        this.email = email;
        this.mobile = mobile;
    }

    // ==========================================
    // CONSTRUCTOR 2 - WITH ADDRESS
    // ==========================================

    public Customer(
            String name,
            String email,
            String mobile,
            Address address) {

        this(name, email, mobile);

        this.address = address;
    }

    // ==========================================
    // GETTER METHODS
    // ==========================================

    public String getCustomerId() {

        return customerId;
    }

    public String getName() {

        return name;
    }

    public String getEmail() {

        return email;
    }

    public String getMobile() {

        return mobile;
    }

    public Address getAddress() {

        return address;
    }

    // ==========================================
    // DISPLAY CUSTOMER DETAILS
    // ==========================================

    public void displayCustomer() {

        System.out.println(
                "Customer ID : " + customerId
        );

        System.out.println(
                "Name        : " + name
        );

        System.out.println(
                "Email       : " + email
        );

        System.out.println(
                "Mobile      : " + mobile
        );

        if (address != null) {

            System.out.println(
                    "Address     : " + address
            );
        }
    }

    // ==========================================
    // INNER CLASS - ADDRESS
    // ==========================================

    public static class Address implements Serializable {

        private static final long serialVersionUID = 1L;

        private String city;
        private String state;
        private String pincode;

        // ======================================
        // ADDRESS CONSTRUCTOR
        // ======================================

        public Address(
                String city,
                String state,
                String pincode) {

            this.city = city;
            this.state = state;
            this.pincode = pincode;
        }

        // ======================================
        // ADDRESS GETTERS
        // ======================================

        public String getCity() {

            return city;
        }

        public String getState() {

            return state;
        }

        public String getPincode() {

            return pincode;
        }

        // ======================================
        // DISPLAY ADDRESS
        // ======================================

        @Override
        public String toString() {

            return city + ", " + state + " - " + pincode;
        }
    }
}