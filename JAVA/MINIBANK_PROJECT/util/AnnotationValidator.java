package util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import model.annotation.Positive;
import model.annotation.MaxLength;

public class AnnotationValidator {
    public static String[] validate(Object obj) {
        List<String> errors = new ArrayList<>();
        Class<?> clazz = obj.getClass();
        
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            try {
                if (field.isAnnotationPresent(Positive.class)) {
                    Object val = field.get(obj);
                    if (val instanceof Number) {
                        double num = ((Number) val).doubleValue();
                        Positive pos = field.getAnnotation(Positive.class);
                        if (num <= 0) {
                            errors.add(field.getName() + " " + pos.message());
                        }
                    }
                }
                if (field.isAnnotationPresent(MaxLength.class)) {
                    Object val = field.get(obj);
                    if (val instanceof String) {
                        String s = (String) val;
                        MaxLength ml = field.getAnnotation(MaxLength.class);
                        if (s.length() > ml.value()) {
                            errors.add(field.getName() + " exceeds max length of " + ml.value());
                        }
                    }
                }
            } catch (IllegalAccessException e) {
                errors.add("Error validating field: " + field.getName());
            }
        }
        return errors.toArray(new String[0]);
    }
}
