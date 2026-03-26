package myCalendar;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EventTest {

    @Test
    void test_description_rdvPersonnel() {
        Event event = new RdvPersonnel(
                new EventTitle("TITLE"),
                new EventOwner("PROPRIETAIRE"),
                new EventDateTime(LocalDateTime.parse("2018-05-05T11:50:55")),
                new EventDuration(30)
        );
        assertEquals("RDV : TITLE à 2018-05-05T11:50:55", event.description());
    }

    @Test
    void test_description_reunion() {
        Event event = new Reunion(
                new EventTitle("TITLE"),
                new EventOwner("PROPRIETAIRE"),
                new EventDateTime(LocalDateTime.parse("2018-05-05T11:50:55")),
                new EventDuration(30),
                new EventLocation("LIEU"),
                new EventParticipants(List.of("PARTICIPANTS1", "PARTICIPANTS2", "PARTICIPANTS3"))
        );
        assertEquals("Réunion : TITLE à LIEU avec PARTICIPANTS1, PARTICIPANTS2, PARTICIPANTS3", event.description());
    }

    @Test
    void test_description_evenementPeriodique() {
        Event event = new Periodique(
                new EventTitle("TITLE"),
                new EventOwner("PROPRIETAIRE"),
                new EventDateTime(LocalDateTime.parse("2018-05-05T11:50:55")),
                new EventDuration(30),
                new EventFrequency(7)
        );
        assertEquals("Événement périodique : TITLE tous les 7 jours", event.description());
    }
}
