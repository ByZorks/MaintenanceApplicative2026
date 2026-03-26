package myCalendar;

import java.time.LocalDateTime;

public class Event {
    private EventType type;
    private final EventTitle title;
    private final EventOwner proprietaire;
    private final LocalDateTime dateDebut;
    private final int dureeMinutes;
    private final String lieu; // utilisé seulement pour REUNION
    private final String participants; // séparés par virgules (pour REUNION uniquement)
    private final int frequenceJours; // uniquement pour PERIODIQUE

    public Event(EventType type, EventTitle title, EventOwner proprietaire, LocalDateTime dateDebut, int dureeMinutes,
                 String lieu, String participants, int frequenceJours) {
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

    public LocalDateTime getDateDebut() {
        return dateDebut;
    }

    public int getDureeMinutes() {
        return dureeMinutes;
    }

    public int getFrequenceJours() {
        return frequenceJours;
    }

    public void setType(final EventType type) {
        this.type = type;
    }
}