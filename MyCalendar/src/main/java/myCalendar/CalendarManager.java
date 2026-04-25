package myCalendar;

import java.util.ArrayList;
import java.util.List;

public class CalendarManager {
    private final List<Event> events;

    public CalendarManager() {
        this.events = new ArrayList<>();
    }

    public boolean ajouterRdvPersonnel(EventTitle title, EventOwner proprietaire, EventDateTime dateDebut, EventDuration dureeMinutes) {
        return ajouterSiPossible(new RdvPersonnel(title, proprietaire, dateDebut, dureeMinutes));
    }

    public boolean ajouterRdvPersonnel(EventId id, EventTitle title, EventOwner proprietaire, EventDateTime dateDebut, EventDuration dureeMinutes) {
        return ajouterSiPossible(new RdvPersonnel(id, title, proprietaire, dateDebut, dureeMinutes));
    }

    public boolean ajouterReunion(EventTitle title, EventOwner proprietaire, EventDateTime dateDebut, EventDuration dureeMinutes, EventLocation lieu, EventParticipants participants) {
        return ajouterSiPossible(new Reunion(title, proprietaire, dateDebut, dureeMinutes, lieu, participants));
    }

    public boolean ajouterReunion(EventId id, EventTitle title, EventOwner proprietaire, EventDateTime dateDebut, EventDuration dureeMinutes, EventLocation lieu, EventParticipants participants) {
        return ajouterSiPossible(new Reunion(id, title, proprietaire, dateDebut, dureeMinutes, lieu, participants));
    }

    public boolean ajouterPeriodique(EventTitle title, EventOwner proprietaire, EventDateTime dateDebut, EventDuration dureeMinutes, EventFrequency frequenceJours) {
        return ajouterSiPossible(new Periodique(title, proprietaire, dateDebut, dureeMinutes, frequenceJours));
    }

    public boolean ajouterPeriodique(EventId id, EventTitle title, EventOwner proprietaire, EventDateTime dateDebut, EventDuration dureeMinutes, EventFrequency frequenceJours) {
        return ajouterSiPossible(new Periodique(id, title, proprietaire, dateDebut, dureeMinutes, frequenceJours));
    }

    public boolean ajouterDeplacement(EventTitle title, EventOwner proprietaire, EventDateTime dateDebut, EventDuration dureeMinutes, EventLocation destination) {
        return ajouterSiPossible(new Deplacement(title, proprietaire, dateDebut, dureeMinutes, destination));
    }

    public boolean ajouterDeplacement(EventId id, EventTitle title, EventOwner proprietaire, EventDateTime dateDebut, EventDuration dureeMinutes, EventLocation destination) {
        return ajouterSiPossible(new Deplacement(id, title, proprietaire, dateDebut, dureeMinutes, destination));
    }

    public List<Event> eventsDansPeriode(EventDateTime debut, EventDateTime fin) {
        return events.stream()
                .filter(e -> e.estDansPeriode(debut, fin))
                .toList();
    }

    public boolean conflit(Event e1, Event e2) {
        return e1.chevauche(e2) || e2.chevauche(e1);
    }

    public boolean supprimerEvenement(EventId id) {
        return events.removeIf(e -> e.getId().equals(id));
    }

    private boolean ajouterSiPossible(Event nouvelEvenement) {
        if (events.stream().anyMatch(e -> conflit(e, nouvelEvenement)))
            return false;

        events.add(nouvelEvenement);
        return true;
    }

    public void afficherEvenements() {
        for (Event e : events) {
            System.out.println(e.description());
        }
    }
}