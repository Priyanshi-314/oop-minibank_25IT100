import java.util.regex.Pattern;

public class Validator {

    // Indian mobile number: 10 digits, starts with 6, 7, 8 or 9
    private static final Pattern MOBILE_PATTERN =
            Pattern.compile("^[6-9][0-9]{9}$");

    // Basic email validation
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile(
                "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
            );

    // PAN: 5 letters, 4 digits, 1 letter
    private static final Pattern PAN_PATTERN =
            Pattern.compile("^[A-Z]{5}[0-9]{4}[A-Z]$");

    // IFSC: 4 letters, 0, then 6 letters or digits
    private static final Pattern IFSC_PATTERN =
            Pattern.compile("^[A-Z]{4}0[A-Z0-9]{6}$");

    public static boolean isValidMobile(String mobile) {
        return mobile != null
                && MOBILE_PATTERN.matcher(mobile).matches();
    }

    public static boolean isValidEmail(String email) {
        return email != null
                && EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean isValidPan(String pan) {
        return pan != null
                && PAN_PATTERN.matcher(pan).matches();
    }

    public static boolean isValidIfsc(String ifsc) {
        return ifsc != null
                && IFSC_PATTERN.matcher(ifsc).matches();
    }

    // Supplementary requirement: positive whole-number amount
    public static boolean isValidAmount(String amount) {

        if (amount == null || !amount.matches("^[0-9]+$")) {
            return false;
        }

        try {
            return Long.parseLong(amount) > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}