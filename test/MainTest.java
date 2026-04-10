import org.junit.jupiter.api.Test;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    static class Bogie {
        String name;
        int capacity;

        Bogie(String name, int capacity) {
            this.name = name;
            this.capacity = capacity;
        }
    }

    @Test
    void testFilterBogies() {

        List<Bogie> bogies = new ArrayList<>();
        bogies.add(new Bogie("Sleeper", 72));
        bogies.add(new Bogie("AC Chair", 56));
        bogies.add(new Bogie("First Class", 24));
        bogies.add(new Bogie("General", 90));

        List<Bogie> filtered = bogies.stream()
                .filter(b -> b.capacity > 60)
                .collect(Collectors.toList());

        // Assertions
        assertEquals(2, filtered.size());
        assertEquals("Sleeper", filtered.get(0).name);
        assertEquals("General", filtered.get(1).name);
    }
}