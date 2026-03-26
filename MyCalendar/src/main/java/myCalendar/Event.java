package myCalendar;

public abstract class Event {
    protected final EventTitle title;
    protected final EventOwner proprietaire;
    protected final EventDateTime dateDebut;
    protected final EventDuration dureeMinutes;

    protected Event(EventTitle title, EventOwner proprietaire, EventDateTime dateDebut, EventDuration dureeMinutes) {
        this.title = title;
        this.proprietaire = proprietaire;
        this.dateDebut = dateDebut;
        this.dureeMinutes = dureeMinutes;
    }

    public abstract String description();

    public abstract boolean estDansPeriode(EventDateTime debut, EventDateTime fin);

    public abstract boolean chevauche(Event other);

    public EventDateTime getDateDebut() {
        return dateDebut;
    }
}