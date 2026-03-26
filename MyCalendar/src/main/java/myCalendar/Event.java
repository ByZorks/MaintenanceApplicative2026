package myCalendar;

public class Event {
    private EventType type;
    private final EventTitle title;
    private final EventOwner proprietaire;
    private final EventDateTime dateDebut;
    private final EventDuration dureeMinutes;
    private final EventLocation lieu; // utilisé seulement pour REUNION
    private final EventParticipants participants; // séparés par virgules (pour REUNION uniquement)
    private final EventFrequency frequenceJours; // uniquement pour PERIODIQUE

    public Event(EventType type, EventTitle title, EventOwner proprietaire, EventDateTime dateDebut, EventDuration dureeMinutes,
                 EventLocation lieu, EventParticipants participants, EventFrequency frequenceJours) {
        this.type = type;
        this.title = title;
        this.proprietaire = proprietaire;
        this.dateDebut = dateDebut;
        this.dureeMinutes = dureeMinutes;
        this.lieu = lieu;
        this.participants = participants;
        this.frequenceJours = frequenceJours;
    }

    public String description() {
        return switch (type) {
            case RDV_PERSONNEL -> "RDV : " + title + " à " + dateDebut.toString();
            case REUNION -> "Réunion : " + title + " à " + lieu + " avec " + participants;
            case PERIODIQUE -> "Événement périodique : " + title + " tous les " + frequenceJours + " jours";
        };
    }

    public EventType getType() {
        return type;
    }

    public EventDateTime getDateDebut() {
        return dateDebut;
    }

    public EventDuration getDureeMinutes() {
        return dureeMinutes;
    }

    public EventFrequency getFrequenceJours() {
        return frequenceJours;
    }

    public void setType(final EventType type) {
        this.type = type;
    }
}