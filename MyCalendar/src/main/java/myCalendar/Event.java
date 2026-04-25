package myCalendar;

import java.util.Objects;

public abstract class Event {
    protected final EventId id;
    protected final EventTitle title;
    protected final EventOwner proprietaire;
    protected final EventDateTime dateDebut;
    protected final EventDuration dureeMinutes;

    protected Event(EventTitle title, EventOwner proprietaire, EventDateTime dateDebut, EventDuration dureeMinutes) {
        this(EventId.nouveau(), title, proprietaire, dateDebut, dureeMinutes);
    }

    protected Event(EventId id, EventTitle title, EventOwner proprietaire, EventDateTime dateDebut, EventDuration dureeMinutes) {
        this.id = Objects.requireNonNull(id, "L'identifiant ne peut pas être nul.");
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

    public EventId getId() {
        return id;
    }
}