package com.ecommerce;

import com.ecommerce.util.IdGenerator;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IdGeneratorTest {
    @Test
    void generatesUniqueIdsAndContinuesAfterExistingIds() {
        IdGenerator generator = new IdGenerator("PROD", 2001);

        assertEquals("PROD2001", generator.nextId(List.of()));
        assertEquals("PROD2002", generator.nextId(List.of("PROD2001")));

        IdGenerator restartedGenerator = new IdGenerator("PROD", 2001);
        assertEquals("PROD2008", restartedGenerator.nextId(List.of("PROD2001", "PROD2007")));
    }

    @Test
    void validatesPrefixesAndStartingNumber() {
        assertThrows(IllegalArgumentException.class, () -> new IdGenerator("cust", 1001));
        assertThrows(IllegalArgumentException.class, () -> new IdGenerator("CUST", -1));
    }
}
