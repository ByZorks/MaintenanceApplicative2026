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
        calendarManager.ajouterRdvPersonnel(
                new EventTitle("TITLE1"),
                new EventOwner("PROPRIETAIRE1"),
                new EventDateTime(LocalDateTime.parse("2018-05-05T11:50:55")),
                new EventDuration(30)
        );
        calendarManager.ajouterPeriodique(
                new EventTitle("TITLE2"),
                new EventOwner("PROPRIETAIRE2"),
                new EventDateTime(LocalDateTime.parse("2018-05-06T09:00:00")),
                new EventDuration(45),
                new EventFrequency(5)
        );
        calendarManager.ajouterReunion(
                new EventTitle("TITLE3"),
                new EventOwner("PROPRIETAIRE3"),
                new EventDateTime(LocalDateTime.parse("2018-05-10T14:30:00")),
                new EventDuration(60),
                new EventLocation("LIEU3"),
                new EventParticipants(List.of("PARTICIPANTS3"))
        );
    }

    @ParameterizedTest(name = "début {0} - fin {1} -> nombre d'événements attendus : {2}")
    @CsvSource({
            "2018-05-05T00:00:00,   2018-05-05T23:59:59,    1",
            "2018-05-06T00:00:00,   2018-05-06T23:59:59,    1",
            "2018-05-01T00:00:00,   2018-05-31T23:59:59,    3",
            "2019-01-01T00:00:00,   2019-12-31T23:59:59,    1"
    })
    void test_eventsDansLaPeriode(String debut, String fin, int expectedCount) {
        EventDateTime debutDate = new EventDateTime(LocalDateTime.parse(debut));
        EventDateTime finDate = new EventDateTime(LocalDateTime.parse(fin));

        List<Event> output = calendarManager.eventsDansPeriode(debutDate, finDate);

        assertEquals(expectedCount, output.size());
    }

    @ParameterizedTest(name = "e1 débute à {0} pour {1}min -> conflit attendu : {2}")
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
        Event e1 = new Reunion(
                new EventTitle("TITLE1"),
                new EventOwner("PROPRIETAIRE1"),
                new EventDateTime(LocalDateTime.parse(debutE1)),
                new EventDuration(dureeE1),
                new EventLocation("LIEU1"),
                new EventParticipants(List.of("PART1"))
        );
        Event e2 = new Reunion(
                new EventTitle("TITLE2"),
                new EventOwner("PROPRIETAIRE2"),
                new EventDateTime(LocalDateTime.parse("2018-05-05T12:00:00")),
                new EventDuration(30),
                new EventLocation("LIEU2"),
                new EventParticipants(List.of("PART2"))
        );

        boolean output = calendarManager.conflit(e1, e2);

        assertEquals(expected, output);
    }

    @ParameterizedTest(name = "e1={0} e2={1} -> conflit={2}")
    @CsvSource({
            "REUNION,       REUNION,        true",
            "PERIODIQUE,    REUNION,        true",
            "REUNION,       PERIODIQUE,     true",
    })
    void test_conflit_type(String type1, String type2, boolean expected) {
        EventDateTime dateDebut = new EventDateTime(LocalDateTime.parse("2018-05-05T12:00:00"));

        Event e1 = creerEvent(type1, "TITLE1", dateDebut);
        Event e2 = creerEvent(type2, "TITLE2", dateDebut);

        assertEquals(expected, calendarManager.conflit(e1, e2));
    }

    private Event creerEvent(String type, String titre, EventDateTime dateDebut) {
        return switch (type) {
            case "REUNION" -> new Reunion(
                    new EventTitle(titre),
                    new EventOwner("PROPRIETAIRE"),
                    dateDebut,
                    new EventDuration(30),
                    new EventLocation("LIEU"),
                    new EventParticipants(List.of("PART"))
            );
            case "PERIODIQUE" -> new Periodique(
                    new EventTitle(titre),
                    new EventOwner("PROPRIETAIRE"),
                    dateDebut,
                    new EventDuration(30),
                    new EventFrequency(7)
            );
            default -> new RdvPersonnel(
                    new EventTitle(titre),
                    new EventOwner("PROPRIETAIRE"),
                    dateDebut,
                    new EventDuration(30)
            );
        };
    }
}
