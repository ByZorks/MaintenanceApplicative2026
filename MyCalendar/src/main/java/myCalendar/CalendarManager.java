package myCalendar;

import java.util.ArrayList;
import java.util.List;

public class CalendarManager {
    public List<Event> events;

    public CalendarManager() {
        this.events = new ArrayList<>();
    }

    public void ajouterEvent(EventType type, EventTitle title, EventOwner proprietaire, EventDateTime dateDebut,
                             EventDuration dureeMinutes, EventLocation lieu, String participants, EventFrequency frequenceJours) {
        Event e = new Event(type, title, proprietaire, dateDebut, dureeMinutes, lieu, participants, frequenceJours);
        events.add(e);
    }

    public List<Event> eventsDansPeriode(EventDateTime debut, EventDateTime fin) {
        List<Event> result = new ArrayList<>();
        for (Event e : events) {
            if (e.getType().equals(EventType.PERIODIQUE)) {
                EventDateTime temp = e.getDateDebut();
                while (temp.isBefore(fin)) {
                    if (!temp.isBefore(debut)) {
                        result.add(e);
                        break;
                    }
                    temp = temp.plusDays(e.getFrequenceJours().valeur());
                }
            } else if (!e.getDateDebut().isBefore(debut) && !e.getDateDebut().isAfter(fin)) {
                result.add(e);
            }
        }
        return result;
    }

    public boolean conflit(Event e1, Event e2) {
        EventDateTime fin1 = e1.getDateDebut().plusMinutes(e1.getDureeMinutes().valeur());
        EventDateTime fin2 = e2.getDateDebut().plusMinutes(e2.getDureeMinutes().valeur());

        if (e1.getType().equals(EventType.PERIODIQUE) || e2.getType().equals(EventType.PERIODIQUE)) {
            return false; // Simplification abusive
        }

        if (e1.getDateDebut().isBefore(fin2) && fin1.isAfter(e2.getDateDebut())) {
            return true;
        }
        return false;
    }

    public void afficherEvenements() {
        for (Event e : events) {
            System.out.println(e.description());
        }
    }
}