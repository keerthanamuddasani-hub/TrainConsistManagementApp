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

    @Test
    void testUC9_GroupBogies() {

        // Step 1: Create list
        List<Bogie> bogies = new ArrayList<>();
        bogies.add(new Bogie("Sleeper", 72));
        bogies.add(new Bogie("AC Chair", 56));
        bogies.add(new Bogie("First Class", 24));
        bogies.add(new Bogie("Sleeper", 70));
        bogies.add(new Bogie("AC Chair", 68));

        // Step 2: Group using Streams
        Map<String, List<Bogie>> grouped =
                bogies.stream()
                        .collect(Collectors.groupingBy(b -> b.name));

        // Step 3: Check results (Assertions)

        // Sleeper should have 2
        assertEquals(2, grouped.get("Sleeper").size());

        // AC Chair should have 2
        assertEquals(2, grouped.get("AC Chair").size());

        // First Class should have 1
        assertEquals(1, grouped.get("First Class").size());
    }
    @Test
    void testUC10_TotalSeats() {

        List<Bogie> bogies = new ArrayList<>();
        bogies.add(new Bogie("Sleeper", 72));
        bogies.add(new Bogie("AC Chair", 56));
        bogies.add(new Bogie("First Class", 24));
        bogies.add(new Bogie("Sleeper", 70));

        // Step 1: Map capacities
        // Step 2: Reduce to total sum
        int totalSeats = bogies.stream()
                .map(b -> b.capacity)
                .reduce(0, Integer::sum);

        // Expected total = 72 + 56 + 24 + 70 = 222
        assertEquals(222, totalSeats);
    }
    @Test
    void testUC11_ValidateTrainAndCargo() {

        // Sample inputs
        String trainId = "TRN-6524";
        String cargoCode = "PET-FH";

        // Regex patterns
        String trainPattern = "TRN-\\d{4}";
        String cargoPattern = "PET-[A-Z]{2}";

        // Validation
        boolean isTrainValid = trainId.matches(trainPattern);
        boolean isCargoValid = cargoCode.matches(cargoPattern);

        // Assertions
        assertTrue(isTrainValid);
        assertTrue(isCargoValid);
    }
}