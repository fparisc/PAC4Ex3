package edu.uoc.pac4.data;

import edu.uoc.pac4.particle.*;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DataRepositoryTest {

    @Test
    @Order(1)
    @DisplayName("getDataEntriesPerDay")
    public void testGetDataEntriesPerDay() {
        try {
            LocalDate day1 = LocalDate.of(2025, 1, 1);
            LocalDate day2 = LocalDate.of(2025, 1, 2);
            LocalDate day3 = LocalDate.of(2025, 1, 3);

            DataEntry[] entries = new DataEntry[] {
                    new DataEntry(1, "Entry 1", day1.atTime(10, 0), "Obs1", new Electron("e1", 0.5, -1.0, 0.5, 1.0, 1)),
                    new DataEntry(2, "Entry 2", day1.atTime(11, 0), "Obs2", new Muon("m1", 105.0, -1.0, 0.5, 2.0, 1, 2.2e-6)),
                    new DataEntry(3, "Entry 3", day2.atTime(9, 30), "Obs3", new Gluon("g1", 0.0, 0.0, 1.0, 0.1, true, "red")),
                    new DataEntry(4, "Entry 4", day3.atTime(15, 0), "Obs4", new Exciton("x1", 0.01, 0.0, 0.0, 0.5, 5.0, 1.0, MaterialType.SEMICONDUCTOR, 1.5, 2.5e-9))
            };

            DataRepository repo = new DataRepository("Repo", entries);

            List<DataEntry> resultDay1 = repo.getDataEntriesPerDay(day1);
            assertEquals(2, resultDay1.size());
            assertTrue(resultDay1.stream().allMatch(e -> e.getTimestamp().toLocalDate().equals(day1)));

            List<DataEntry> resultDay2 = repo.getDataEntriesPerDay(day2);
            assertEquals(1, resultDay2.size());

            List<DataEntry> resultDay3 = repo.getDataEntriesPerDay(day3);
            assertEquals(1, resultDay3.size());
        } catch (Exception e) {
            fail("[ERROR] Unexpected exception in testGetDataEntriesPerDay: " + e.getMessage());
        }
    }

    @Test
    @Order(2)
    @DisplayName("getDataEntriesCountPerParticle")
    public void testGetDataEntriesCountPerParticle() {
        try {
            DataEntry[] entries = new DataEntry[] {
                    new DataEntry(1, "E1", LocalDateTime.of(2025, 1, 1, 10, 0), "obs", new Electron("e1", 0.5, -1.0, 0.5, 1.2, 1)),
                    new DataEntry(2, "E2", LocalDateTime.of(2025, 1, 1, 11, 0), "obs", new Electron("e2", 0.5, -1.0, 0.5, 1.2, 1)),
                    new DataEntry(3, "E3", LocalDateTime.of(2025, 1, 2, 9, 0), "obs", new Muon("m1", 105.0, -1.0, 0.5, 2.0, 1, 2.2e-6)),
                    new DataEntry(4, "E4", LocalDateTime.of(2025, 1, 2, 10, 0), "obs", new Gluon("g1", 0.0, 0.0, 1.0, 0.1, true, "red")),
                    new DataEntry(5, "E5", LocalDateTime.of(2025, 1, 3, 14, 0), "obs", new Exciton("x1", 0.01, 0.0, 0.0, 0.5, 5.0, 1.0, MaterialType.SEMICONDUCTOR, 1.5, 2.5e-9)),
                    new DataEntry(6, "E6", LocalDateTime.of(2025, 1, 3, 15, 0), "obs", new Exciton("x2", 0.01, 0.0, 0.0, 0.5, 5.0, 1.0, MaterialType.SEMICONDUCTOR, 1.5, 2.5e-9))
            };

            DataRepository repo = new DataRepository("TestRepo", entries);
            Map<String, Long> counts = repo.getDataEntriesCountPerParticle();

            assertEquals(2, counts.get(Electron.class.getSimpleName()));
            assertEquals(1, counts.get(Muon.class.getSimpleName()));
            assertEquals(1, counts.get(Gluon.class.getSimpleName()));
            assertEquals(2, counts.get(Exciton.class.getSimpleName()));
            assertEquals(4, counts.size());

        } catch (Exception e) {
            fail("[ERROR] Unexpected exception in testGetDataEntriesCountPerParticle: " + e.getMessage());
        }
    }

}
