package myCalendar;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalendarManagerTest {

    private static CalendarManager calendarManager;

    @BeforeAll
    static void setUp() {
        calendarManager = new CalendarManager();
        calendarManager.ajouterEvent(EventType.RDV_PERSONNEL, new EventTitle("TITLE1"), "PROPRIETAIRE1", LocalDateTime.parse("2018-05-05T11:50:55"), 30, "LIEU1", "PARTICIPANTS1", 0);
        calendarManager.ajouterEvent(EventType.PERIODIQUE, new EventTitle("TITLE2"), "PROPRIETAIRE2", LocalDateTime.parse("2018-05-06T09:00:00"), 45, "LIEU2", "PARTICIPANTS2", 5);
        calendarManager.ajouterEvent(EventType.REUNION, new EventTitle("TITLE3"), "PROPRIETAIRE3", LocalDateTime.parse("2018-05-10T14:30:00"), 60, "LIEU3", "PARTICIPANTS3", 1);
    }

    @ParameterizedTest(name = "Test période: début {0} - fin {1} -> nombre d'événements attendus : {2}")
    @CsvSource({
            "2018-05-05T00:00:00,   2018-05-05T23:59:59,    1",
            "2018-05-06T00:00:00,   2018-05-06T23:59:59,    1",
            "2018-05-01T00:00:00,   2018-05-31T23:59:59,    3",
            "2019-01-01T00:00:00,   2019-12-31T23:59:59,    1"
    })
    void test_eventsDansLaPeriode(String debut, String fin, int expectedCount) {
        LocalDateTime debutDate = LocalDateTime.parse(debut);
        LocalDateTime finDate = LocalDateTime.parse(fin);

        List<Event> output = calendarManager.eventsDansPeriode(debutDate, finDate);

        assertEquals(expectedCount, output.size());
    }

    @ParameterizedTest(name = "Test chevauchement: e1 débute à {0} pour {1}min -> conflit attendu : {2}")
    @CsvSource({
            // e1 se termine AVANT e2 (Pas de conflit)
            "2018-05-05T11:00:00, 30, false",
            // e1 se termine EXACTEMENT quand e2 commence (Pas de conflit)
            "2018-05-05T11:30:00, 30, false",
            // e1 commence avant e2, mais se termine PENDANT e2 (Conflit)
            "2018-05-05T11:45:00, 30, true",
            // e1 est EXACTEMENT en même temps que e2 (Conflit)
            "2018-05-05T12:00:00, 30, true",
            // e1 commence pendant e2 et se termine après e2 (Conflit)
            "2018-05-05T12:15:00, 30, true",
            // e1 commence EXACTEMENT quand e2 se termine (Pas de conflit)
            "2018-05-05T12:30:00, 30, false",
            // e1 commence APRÈS e2 (Pas de conflit)
            "2018-05-05T13:00:00, 30, false",
            // e1 est ENTIÈREMENT CONTENU dans e2 (Conflit)
            "2018-05-05T12:10:00, 10, true",
            // e1 ENGLOBE ENTIÈREMENT e2 (Conflit)
            "2018-05-05T11:30:00, 90, true"
    })
    void test_conflit_limites_temporelles(String debutE1, int dureeE1, boolean expected) {
        LocalDateTime dateDebutE1 = LocalDateTime.parse(debutE1);
        LocalDateTime dateDebutE2 = LocalDateTime.parse("2018-05-05T12:00:00");

        Event e1 = new Event(EventType.REUNION, new EventTitle("TITLE1"), "PROP1", dateDebutE1, dureeE1, "LIEU1", "PART1", 0);
        Event e2 = new Event(EventType.REUNION, new EventTitle("TITLE2"), "PROP2", dateDebutE2, 30, "LIEU2", "PART2", 0);

        boolean output = calendarManager.conflit(e1, e2);

        assertEquals(expected, output);
    }

    @ParameterizedTest(name = "Test types d'événements: e1 de type {0} et e2 de type {1} -> conflit attendu : {2}")
    @CsvSource({
            "REUNION,       REUNION,        true",
            "PERIODIQUE,    REUNION,        true",
            "REUNION,       PERIODIQUE,     true",
    })
    void test_conflit_type(String type1, String type2, boolean expected) {
        LocalDateTime dateDebut = LocalDateTime.parse("2018-05-05T12:00:00");

        Event e1 = new Event(EventType.valueOf(type1), new EventTitle("TITLE1"), "PROP1", dateDebut, 30, "LIEU1", "PART1", 0);
        Event e2 = new Event(EventType.valueOf(type2), new EventTitle("TITLE2"), "PROP2", dateDebut, 30, "LIEU2", "PART2", 0);

        boolean output = calendarManager.conflit(e1, e2);

        assertEquals(expected, output);
    }
}
