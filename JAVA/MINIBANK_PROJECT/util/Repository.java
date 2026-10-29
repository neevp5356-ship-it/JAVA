package util;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import model.annotation.Id;

public class Repository<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private final HashMap<String, T> store = new HashMap<>();

    public synchronized T save(T entity) {
        String id = extractId(entity);
        store.put(id, entity);
        return entity;
    }

    @SafeVarargs
    public final synchronized void saveAll(T... items) {
        for (T item : items) {
            save(item);
        }
    }

    public synchronized T findById(String id) {
        return store.get(id);
    }

    public synchronized ArrayList<T> findAll() {
        return new ArrayList<>(store.values());
    }

    public synchronized boolean delete(String id) {
        return store.remove(id) != null;
    }

    public synchronized int size() {
        return store.size();
    }

    private String extractId(T entity) {
        Class<?> clazz = entity.getClass();
        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(Id.class)) {
                    field.setAccessible(true);
                    try {
                        Object val = field.get(entity);
                        if (val != null) return val.toString();
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("Failed to read @Id field", e);
                    }
                }
            }
            clazz = clazz.getSuperclass();
        }
        throw new RuntimeException("No field annotated with @Id found in " + entity.getClass().getName());
    }
}
