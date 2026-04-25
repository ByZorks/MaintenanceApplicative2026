package myCalendar;

public class Periodique extends Event {

    private final EventFrequency frequenceJours;

    public Periodique(EventTitle title, EventOwner proprietaire, EventDateTime dateDebut, EventDuration dureeMinutes, EventFrequency frequenceJours) {
        super(title, proprietaire, dateDebut, dureeMinutes);
        this.frequenceJours = frequenceJours;
    }

    @Override
    public String description() {
        return "Événement périodique : " + title + " tous les " + frequenceJours + " jours";
    }

    @Override
    public boolean estDansPeriode(EventDateTime debut, EventDateTime fin) {
        EventDateTime temp = dateDebut;
        while (temp.isBefore(fin)) {
            if (!temp.isBefore(debut))
                return true;
            temp = temp.plusDays(frequenceJours.valeur());
        }
        
        return false;
    }

    @Override
    public boolean chevauche(Event other) {
        EventDateTime otherFin = other.dateDebut.plusMinutes(other.dureeMinutes.valeur());
        EventDateTime occurrence = dateDebut;
        while (occurrence.isBefore(otherFin)) {
            EventDateTime finOccurrence = occurrence.plusMinutes(dureeMinutes.valeur());
            if (occurrence.isBefore(otherFin) && finOccurrence.isAfter(other.getDateDebut()))
                return true;
            occurrence = occurrence.plusDays(frequenceJours.valeur());
        }
        return false;
    }
}
