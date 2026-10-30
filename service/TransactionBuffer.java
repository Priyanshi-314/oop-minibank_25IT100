package service;

import java.util.LinkedList;
import java.util.Queue;

public class TransactionBuffer {

    private final Queue<Runnable> buffer =
            new LinkedList<>();

    private final int capacity;

    private boolean closed = false;

    public TransactionBuffer(int capacity) {

        if (capacity <= 0) {
            throw new IllegalArgumentException(
                "Capacity must be positive."
            );
        }

        this.capacity = capacity;
    }

    public synchronized void produce(Runnable transaction)
            throws InterruptedException {

        while (buffer.size() == capacity && !closed) {
            wait();
        }

        if (closed) {
            throw new IllegalStateException(
                "Transaction buffer is closed."
            );
        }

        buffer.add(transaction);

        System.out.println(
            "Producer added transaction."
        );

        notifyAll();
    }

    public synchronized Runnable consume()
            throws InterruptedException {

        while (buffer.isEmpty() && !closed) {
            wait();
        }

        if (buffer.isEmpty()) {
            return null;
        }

        Runnable transaction = buffer.remove();

        System.out.println(
            "Consumer received transaction."
        );

        notifyAll();

        return transaction;
    }

    public synchronized void close() {

        closed = true;

        notifyAll();
    }
}