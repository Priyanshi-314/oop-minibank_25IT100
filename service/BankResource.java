package service;

public class BankResource implements AutoCloseable {

    public BankResource() {

        System.out.println(
            "Bank resource opened successfully."
        );
    }

    public void process() {

        System.out.println(
            "Processing banking resource..."
        );
    }

    @Override
    public void close() {

        System.out.println(
            "Bank resource closed automatically."
        );
    }
}