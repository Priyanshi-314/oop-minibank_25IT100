public class Customer {

    private final String customerId;
    private String name;
    private String email;
    private String mobile;

    private Address address;

    private static long customerCounter = 100;

    private static String generateCustomerId() {
        customerCounter++;
        return "CUST" + customerCounter;
    }

    // Constructor from Practical 2
    public Customer(
        String name,
        String email,
        String mobile
    ) {
        this.customerId = generateCustomerId();
        this.name = name;
        this.email = email;
        this.mobile = mobile;
    }

    // PRACTICAL 3: Overloaded constructor
    public Customer(
        String name,
        String email,
        String mobile,
        Address address
    ) {
        this(name, email, mobile);
        this.address = address;
    }

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

    public void displayCustomer() {

        System.out.println("Customer ID : " + customerId);
        System.out.println("Name        : " + name);
        System.out.println("Email       : " + email);
        System.out.println("Mobile      : " + mobile);

        if (address != null) {
            System.out.println("Address     : " + address);
        }
    }

    // PRACTICAL 3: Static nested class
    public static class Address {

        private String city;
        private String state;
        private String pincode;

        public Address(
            String city,
            String state,
            String pincode
        ) {
            this.city = city;
            this.state = state;
            this.pincode = pincode;
        }

        public String getCity() {
            return city;
        }

        public String getState() {
            return state;
        }

        public String getPincode() {
            return pincode;
        }

        @Override
        public String toString() {
            return city + ", " + state + " - " + pincode;
        }
    }
}