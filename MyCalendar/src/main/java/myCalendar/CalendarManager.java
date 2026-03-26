package myCalendar;

import java.util.ArrayList;
import java.util.List;

public class CalendarManager {
    private final List<Event> events;

    public CalendarManager() {
        this.events = new ArrayList<>();
    }

    public void ajouterRdvPersonnel(EventTitle title, EventOwner proprietaire, EventDateTime dateDebut, EventDuration dureeMinutes) {
        events.add(new RdvPersonnel(title, proprietaire, dateDebut, dureeMinutes));
    }

    public void ajouterReunion(EventTitle title, EventOwner proprietaire, EventDateTime dateDebut, EventDuration dureeMinutes, EventLocation lieu, EventParticipants participants) {
        events.add(new Reunion(title, proprietaire, dateDebut, dureeMinutes, lieu, participants));
    }

    public void ajouterPeriodique(EventTitle title, EventOwner proprietaire, EventDateTime dateDebut, EventDuration dureeMinutes, EventFrequency frequenceJours) {
        events.add(new Periodique(title, proprietaire, dateDebut, dureeMinutes, frequenceJours));
    }

    public List<Event> eventsDansPeriode(EventDateTime debut, EventDateTime fin) {
        return events.stream()
                .filter(e -> e.estDansPeriode(debut, fin))
                .toList();
    }

    public boolean conflit(Event e1, Event e2) {
        return e2.chevauche(e1);
    }

    public void afficherEvenements() {
        for (Event e : events) {
            System.out.println(e.description());
        }
    }
}