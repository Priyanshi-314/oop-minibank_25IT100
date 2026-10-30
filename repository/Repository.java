package repository;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

import model.annotation.Id;

public class Repository<T> {

    private final HashMap<String, T> storage = new HashMap<>();

    // SAVE ACCOUNT
    public T save(T entity) {

        if (entity == null) {
            throw new IllegalArgumentException(
                "Entity cannot be null."
            );
        }

        String id = findId(entity);

        storage.put(id, entity);

        return entity;
    }

    // SEARCH ACCOUNT
    public T findById(String id) {

        return storage.get(id);
    }

    // DISPLAY ALL ACCOUNTS
    public ArrayList<T> findAll() {

        return new ArrayList<>(storage.values());
    }

    // DELETE ACCOUNT
    public boolean delete(String id) {

        return storage.remove(id) != null;
    }

    // SAVE MULTIPLE ACCOUNTS
    @SafeVarargs
    public final void saveAll(T... items) {

        for (T item : items) {
            save(item);
        }
    }

    // COUNT ACCOUNTS
    public int count() {

        return storage.size();
    }

    // REFLECTION USING @Id
    private String findId(T entity) {

        Class<?> currentClass = entity.getClass();

        while (currentClass != null
                && currentClass != Object.class) {

            Field[] fields = currentClass.getDeclaredFields();

            for (Field field : fields) {

                if (field.isAnnotationPresent(Id.class)) {

                    try {

                        field.setAccessible(true);

                        Object value = field.get(entity);

                        if (value == null) {
                            throw new IllegalArgumentException(
                                "ID cannot be null."
                            );
                        }

                        return value.toString();

                    } catch (IllegalAccessException e) {

                        throw new IllegalStateException(
                            "Cannot access ID field.",
                            e
                        );
                    }
                }
            }

            currentClass = currentClass.getSuperclass();
        }

        throw new IllegalArgumentException(
            "No field marked with @Id found."
        );
    }

    // BOUNDED GENERIC METHOD
    public static <N extends Number> double average(N[] values) {

        if (values == null || values.length == 0) {

            throw new IllegalArgumentException(
                "Values cannot be empty."
            );
        }

        double sum = 0;

        for (N value : values) {
            sum += value.doubleValue();
        }

        return sum / values.length;
    }
}