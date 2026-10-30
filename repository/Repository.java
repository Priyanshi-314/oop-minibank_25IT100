package repository;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

import model.annotation.Id;

// ==========================================
// PRACTICAL 13 & 14 - GENERIC REPOSITORY
// ==========================================

public class Repository<T> {

    // Store entities using their unique IDs
    private final HashMap<String, T> storage =
            new HashMap<>();

    // ==========================================
    // SAVE ENTITY
    // ==========================================

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

    // ==========================================
    // FIND ENTITY BY ID
    // ==========================================

    public T findById(String id) {

        return storage.get(id);
    }

    // ==========================================
    // FIND ALL ENTITIES
    // ==========================================

    public ArrayList<T> findAll() {

        return new ArrayList<>(storage.values());
    }

    // ==========================================
    // DELETE ENTITY
    // ==========================================

    public boolean delete(String id) {

        return storage.remove(id) != null;
    }

    // ==========================================
    // SAVE MULTIPLE ENTITIES
    // ==========================================

    @SafeVarargs
    public final void saveAll(T... items) {

        if (items == null) {
            throw new IllegalArgumentException(
                "Items cannot be null."
            );
        }

        for (T item : items) {
            save(item);
        }
    }

    // ==========================================
    // COUNT ENTITIES
    // ==========================================

    public int count() {

        return storage.size();
    }

    // ==========================================
    // FIND ID USING REFLECTION
    // ==========================================

    private String findId(T entity) {

        Class<?> currentClass = entity.getClass();

        while (currentClass != null
                && currentClass != Object.class) {

            Field[] fields =
                    currentClass.getDeclaredFields();

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

    // ==========================================
    // BOUNDED GENERIC METHOD
    // ==========================================

    public static <N extends Number> double average(
            N[] values) {

        if (values == null || values.length == 0) {

            throw new IllegalArgumentException(
                "Values cannot be empty."
            );
        }

        double sum = 0;

        for (N value : values) {

            if (value == null) {
                throw new IllegalArgumentException(
                    "Array cannot contain null values."
                );
            }

            sum += value.doubleValue();
        }

        return sum / values.length;
    }
}