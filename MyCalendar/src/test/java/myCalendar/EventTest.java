package myCalendar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EventTest {

    private Event event;

    @BeforeEach
    void setUp() {
        event = new Event(EventType.RDV_PERSONNEL, "TITLE", "PROPRIETAIRE", LocalDateTime.parse("2018-05-05T11:50:55"), 30, "LIEU", "PARTICIPANTS", 0);
    }

    @ParameterizedTest
    @CsvSource({
            "RDV_PERSONNEL,     RDV : TITLE à 2018-05-05T11:50:55",
            "REUNION,           Réunion : TITLE à LIEU avec PARTICIPANTS",
            "PERIODIQUE,        Événement périodique : TITLE tous les 0 jours",
    })
    void test_description(String type, String expectedOutput) {
        event.setType(EventType.valueOf(type));
        String actualOutput = event.description();
        assertEquals(expectedOutput, actualOutput);
    }
}
