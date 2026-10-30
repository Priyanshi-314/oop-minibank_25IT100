package persistence;

import java.io.BufferedReader;
import java.io.IOException;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

import java.nio.file.attribute.BasicFileAttributes;

public class ReportGenerator {

    public static void generate(
            Path logsFolder,
            Path reportFile) throws IOException {

        long totalDeposits = 0;
        long totalWithdrawals = 0;

        StringBuilder fileDetails = new StringBuilder();

        Files.createDirectories(logsFolder);

        try (DirectoryStream<Path> files =
                     Files.newDirectoryStream(logsFolder, "*.log")) {

            for (Path file : files) {

                if (!Files.isRegularFile(file)) {
                    continue;
                }

                BasicFileAttributes attributes =
                        Files.readAttributes(
                            file,
                            BasicFileAttributes.class
                        );

                fileDetails.append(
                    "File: " + file.getFileName()
                    + ", Size: " + attributes.size()
                    + " bytes"
                    + ", Modified: "
                    + attributes.lastModifiedTime()
                    + System.lineSeparator()
                );

                try (BufferedReader reader =
                             Files.newBufferedReader(file)) {

                    String line;

                    while ((line = reader.readLine()) != null) {

                        String[] parts = line.trim().split("\\s+");

                        if (parts.length != 3) {
                            continue;
                        }

                        long amount;

                        try {
                            amount = Long.parseLong(parts[2]);
                        } catch (NumberFormatException e) {
                            continue;
                        }

                        if (amount <= 0) {
                            continue;
                        }

                        if (parts[0].equals("DEPOSIT")) {

                            totalDeposits += amount;

                        } else if (parts[0].equals("WITHDRAW")) {

                            totalWithdrawals += amount;
                        }
                    }
                }
            }
        }

        long netChange = totalDeposits - totalWithdrawals;

        String report =
                "=== MINIBANK TRANSACTION REPORT ===\n"
                + "Total Deposits   : Rs. " + totalDeposits + "\n"
                + "Total Withdrawals: Rs. " + totalWithdrawals + "\n"
                + "Net Change       : Rs. " + netChange + "\n\n"
                + "=== LOG FILE DETAILS ===\n"
                + fileDetails;

        Path parent = reportFile.getParent();

        if (parent != null) {
            Files.createDirectories(parent);
        }

        Files.writeString(reportFile, report);

        System.out.println(report);
    }
}