package myCalendar;

import java.util.stream.Stream;

public class Periodique extends Event {

    private final EventFrequency frequenceJours;

    public Periodique(EventTitle title, EventOwner proprietaire, EventDateTime dateDebut, EventDuration dureeMinutes, EventFrequency frequenceJours) {
        super(title, proprietaire, dateDebut, dureeMinutes);
        this.frequenceJours = frequenceJours;
    }

    public Periodique(EventId id, EventTitle title, EventOwner proprietaire, EventDateTime dateDebut, EventDuration dureeMinutes, EventFrequency frequenceJours) {
        super(id, title, proprietaire, dateDebut, dureeMinutes);
        this.frequenceJours = frequenceJours;
    }

    @Override
    public String description() {
        return "Événement périodique : " + title + " tous les " + frequenceJours + " jours";
    }

    @Override
    public boolean estDansPeriode(EventDateTime debut, EventDateTime fin) {
        int step = Math.max(1, frequenceJours.valeur());
        return Stream.iterate(dateDebut, temp -> temp.isBefore(fin), temp -> temp.plusDays(step))
                .anyMatch(temp -> !temp.isBefore(debut));
    }

    @Override
    public boolean chevauche(Event other) {
        EventDateTime otherFin = other.dateDebut.plusMinutes(other.dureeMinutes.valeur());
        int step = Math.max(1, frequenceJours.valeur());
        return Stream.iterate(dateDebut, occurrence -> occurrence.isBefore(otherFin), occurrence -> occurrence.plusDays(step))
                .anyMatch(occurrence -> occurrence.plusMinutes(dureeMinutes.valeur()).isAfter(other.getDateDebut()));
    }
}
