public class StatementFormatter {

    public static String buildStatement(Account account) {

        if (account == null) {
            throw new IllegalArgumentException(
                "Account cannot be null."
            );
        }

        StringBuilder statement = new StringBuilder();

        statement.append("\n");
        statement.append("====================================\n");
        statement.append("        MINIBANK STATEMENT\n");
        statement.append("====================================\n");

        statement.append("Account Number : ")
                 .append(account.getAccountNumber())
                 .append("\n");

        statement.append("Account Holder : ")
                 .append(account.getOwnerName())
                 .append("\n");

        statement.append("Current Balance: Rs. ")
                 .append(account.getBalance())
                 .append("\n");

        statement.append("Account Status : ")
                 .append(account.isActive() ? "ACTIVE" : "INACTIVE")
                 .append("\n");

        statement.append("====================================\n");

        return statement.toString();
    }
}