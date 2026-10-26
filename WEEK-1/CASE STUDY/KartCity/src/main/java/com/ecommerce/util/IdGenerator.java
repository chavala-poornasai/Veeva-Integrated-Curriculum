package com.ecommerce.util;

import java.util.HashSet;
import java.util.Set;

public class IdGenerator {
    private final String prefix;
    private long lastNumber;

    public IdGenerator(String prefix, long firstNumber) {
        if (prefix == null || !prefix.matches("[A-Z]+")) {
            throw new IllegalArgumentException("ID prefix must contain uppercase letters");
        }
        if (firstNumber < 0) {
            throw new IllegalArgumentException("First ID number cannot be negative");
        }
        this.prefix = prefix;
        this.lastNumber = firstNumber - 1;
    }

    public synchronized String nextId(Iterable<String> existingIds) {
        Set<String> existing = new HashSet<>();
        long largestExistingNumber = lastNumber;
        if (existingIds != null) {
            for (String id : existingIds) {
                existing.add(id);
                if (id != null && id.matches(prefix + "\\d+")) {
                    long number = Long.parseLong(id.substring(prefix.length()));
                    largestExistingNumber = Math.max(largestExistingNumber, number);
                }
            }
        }

        lastNumber = largestExistingNumber;
        String candidate;
        do {
            candidate = prefix + ++lastNumber;
        } while (existing.contains(candidate));
        return candidate;
    }

    public static void validateId(String id, String prefix, String entityName) {
        if (id == null || !id.matches(prefix + "\\d+")) {
            throw new IllegalArgumentException(entityName + " ID must use the format " + prefix + "<number>");
        }
    }
}
