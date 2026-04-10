import org.junit.jupiter.api.Test;
import java.util.*;
import java.util.stream.Collectors;
import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    // ------------------- BASIC MODEL -------------------
    static class Bogie {
        String name;
        int capacity;

        Bogie(String name, int capacity) {
            this.name = name;
            this.capacity = capacity;
        }
    }

    // ------------------- GOODS MODEL -------------------
    static class GoodsBogie {
        String type;
        String cargo;

        GoodsBogie(String type, String cargo) {
            this.type = type;
            this.cargo = cargo;
        }
    }

    // ------------------- ✅ FIX ADDED HERE -------------------
    // Custom Exception
    static class InvalidCapacityException extends Exception {
        public InvalidCapacityException(String message) {
            super(message);
        }
    }

    // Passenger Bogie with validation
    static class PassengerBogie {
        String name;
        int capacity;

        PassengerBogie(String name, int capacity) throws InvalidCapacityException {
            if (capacity <= 0) {
                throw new InvalidCapacityException("Capacity must be greater than zero");
            }
            this.name = name;
            this.capacity = capacity;
        }
    }
    // --------------------------------------------------------

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

        assertEquals(2, filtered.size());
        assertEquals("Sleeper", filtered.get(0).name);
        assertEquals("General", filtered.get(1).name);
    }

    @Test
    void testUC9_GroupBogies() {
        List<Bogie> bogies = new ArrayList<>();
        bogies.add(new Bogie("Sleeper", 72));
        bogies.add(new Bogie("AC Chair", 56));
        bogies.add(new Bogie("First Class", 24));
        bogies.add(new Bogie("Sleeper", 70));
        bogies.add(new Bogie("AC Chair", 68));

        Map<String, List<Bogie>> grouped =
                bogies.stream()
                        .collect(Collectors.groupingBy(b -> b.name));

        assertEquals(2, grouped.get("Sleeper").size());
        assertEquals(2, grouped.get("AC Chair").size());
        assertEquals(1, grouped.get("First Class").size());
    }

    @Test
    void testUC10_TotalSeats() {
        List<Bogie> bogies = new ArrayList<>();
        bogies.add(new Bogie("Sleeper", 72));
        bogies.add(new Bogie("AC Chair", 56));
        bogies.add(new Bogie("First Class", 24));
        bogies.add(new Bogie("Sleeper", 70));

        int totalSeats = bogies.stream()
                .map(b -> b.capacity)
                .reduce(0, Integer::sum);

        assertEquals(222, totalSeats);
    }

    @Test
    void testUC11_ValidateTrainAndCargo() {
        String trainId = "TRN-6524";
        String cargoCode = "PET-FH";

        String trainPattern = "TRN-\\d{4}";
        String cargoPattern = "PET-[A-Z]{2}";

        assertTrue(trainId.matches(trainPattern));
        assertTrue(cargoCode.matches(cargoPattern));
    }

    @Test
    void testUC12_SafetyCompliance() {
        List<GoodsBogie> goodsBogies = new ArrayList<>();

        goodsBogies.add(new GoodsBogie("Cylindrical", "Petroleum"));
        goodsBogies.add(new GoodsBogie("Open", "Coal"));
        goodsBogies.add(new GoodsBogie("Box", "Grain"));

        // ❌ invalid case
        goodsBogies.add(new GoodsBogie("Open", "Petroleum"));

        boolean isSafe = goodsBogies.stream()
                .allMatch(b ->
                        !b.cargo.equals("Petroleum") || b.type.equals("Cylindrical")
                );

        assertFalse(isSafe);
    }

    @Test
    void testUC13_PerformanceComparison() {
        List<Bogie> bogies = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            bogies.add(new Bogie("Sleeper", 72));
        }

        long startLoop = System.nanoTime();
        int totalLoop = 0;
        for (Bogie b : bogies) {
            totalLoop += b.capacity;
        }
        long loopTime = System.nanoTime() - startLoop;

        long startStream = System.nanoTime();
        int totalStream = bogies.stream()
                .mapToInt(b -> b.capacity)
                .sum();
        long streamTime = System.nanoTime() - startStream;

        assertEquals(totalLoop, totalStream);
        assertTrue(loopTime > 0);
        assertTrue(streamTime > 0);
    }

    @Test
    void testUC14_InvalidCapacityException() {

        // ✅ valid
        try {
            PassengerBogie b = new PassengerBogie("Sleeper", 72);
            assertEquals(72, b.capacity);
        } catch (InvalidCapacityException e) {
            fail("Should not throw exception");
        }

        // ❌ invalid
        Exception exception = assertThrows(
                InvalidCapacityException.class,
                () -> new PassengerBogie("AC Chair", 0)
        );

        assertEquals("Capacity must be greater than zero", exception.getMessage());
    }
}