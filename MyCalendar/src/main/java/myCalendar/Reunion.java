package myCalendar;

public class Reunion extends Event{

    private final EventLocation lieu;
    private final EventParticipants participants;

    public Reunion(EventTitle title, EventOwner proprietaire, EventDateTime dateDebut, EventDuration dureeMinutes, EventLocation lieu, EventParticipants participants) {
        super(title, proprietaire, dateDebut, dureeMinutes);
        this.lieu = lieu;
        this.participants = participants;
    }

    @Override
    public String description() {
        return "Réunion : " + title + " à " + lieu + " avec " + participants;
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
