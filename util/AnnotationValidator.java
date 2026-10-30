package util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import model.annotation.Positive;
import model.annotation.MaxLength;

public class AnnotationValidator {

    public static String[] validate(Object obj) {

        if (obj == null) {
            throw new IllegalArgumentException(
                "Object cannot be null."
            );
        }

        List<String> errors = new ArrayList<>();

        Class<?> currentClass = obj.getClass();

        while (currentClass != null
                && currentClass != Object.class) {

            Field[] fields = currentClass.getDeclaredFields();

            for (Field field : fields) {

                if (!field.isAnnotationPresent(Positive.class)
                        && !field.isAnnotationPresent(MaxLength.class)) {
                    continue;
                }

                try {

                    field.setAccessible(true);

                    Object value = field.get(obj);

                    // Check @Positive
                    if (field.isAnnotationPresent(Positive.class)) {

                        Positive annotation =
                                field.getAnnotation(Positive.class);

                        if (!(value instanceof Number)
                                || ((Number) value).doubleValue() <= 0) {

                            errors.add(
                                field.getName() + " "
                                + annotation.message()
                            );
                        }
                    }

                    // Check @MaxLength
                    if (field.isAnnotationPresent(MaxLength.class)) {

                        MaxLength annotation =
                                field.getAnnotation(MaxLength.class);

                        if (value instanceof String text
                                && text.length() > annotation.value()) {

                            errors.add(
                                field.getName()
                                + " must contain at most "
                                + annotation.value()
                                + " characters"
                            );
                        }
                    }

                } catch (IllegalAccessException e) {

                    errors.add(
                        "Cannot access field: " + field.getName()
                    );
                }
            }

            currentClass = currentClass.getSuperclass();
        }

        return errors.toArray(new String[0]);
    }
}