package com.wm.automation.base;

import java.util.HashMap;
import java.util.Map;

/**
 * Holds per-scenario state shared across step-definition classes.
 * One instance is created per Cucumber scenario via PicoContainer DI.
 */
public class ScenarioContext {

    private final Map<String, Object> context = new HashMap<>();

    public void set(String key, Object value) {
        context.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        return (T) context.get(key);
    }

    public <T> T get(String key, Class<T> type) {
        return type.cast(context.get(key));
    }

    public boolean contains(String key) {
        return context.containsKey(key);
    }

    public void remove(String key) {
        context.remove(key);
    }

    public void clear() {
        context.clear();
    }
}
