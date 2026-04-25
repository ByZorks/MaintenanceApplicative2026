package myCalendar;

public class Deplacement extends Event {

    private final EventLocation destination;

    public Deplacement(EventTitle title, EventOwner proprietaire, EventDateTime dateDebut, EventDuration dureeMinutes, EventLocation destination) {
        super(title, proprietaire, dateDebut, dureeMinutes);
        this.destination = destination;
    }

    @Override
    public String description() {
        return "Déplacement : " + title + " vers " + destination;
    }

    @Override
    public boolean estDansPeriode(EventDateTime debut, EventDateTime fin) {
        return !dateDebut.isBefore(debut) && !dateDebut.isAfter(fin);
    }

    @Override
    public boolean chevauche(Event other) {
        EventDateTime thisEnd = dateDebut.plusMinutes(dureeMinutes.valeur());
        EventDateTime otherEnd = other.dateDebut.plusMinutes(other.dureeMinutes.valeur());
        return dateDebut.isBefore(otherEnd) && thisEnd.isAfter(other.getDateDebut());
    }
}

