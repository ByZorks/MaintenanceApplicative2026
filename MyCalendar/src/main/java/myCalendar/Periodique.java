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
        EventDateTime fin = dateDebut.plusMinutes(dureeMinutes.valeur());
        EventDateTime occurrence = dateDebut;
        while (occurrence.isBefore(fin)) {
            EventDateTime finOccurrence = occurrence.plusMinutes(dureeMinutes.valeur());
            if (occurrence.isBefore(fin) && finOccurrence.isAfter(dateDebut))
                return true;
            occurrence = occurrence.plusDays(frequenceJours.valeur());
        }
        return false;
    }
}
