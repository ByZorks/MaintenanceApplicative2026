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
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean chevauche(Event other) {
        throw new UnsupportedOperationException();
    }
}

