package myCalendar;

public class RdvPersonnel extends Event {

    public RdvPersonnel(EventTitle title, EventOwner proprietaire, EventDateTime dateDebut, EventDuration dureeMinutes) {
        super(title, proprietaire, dateDebut, dureeMinutes);
    }

    @Override
    public String description() {
        return "RDV : " + title + " à " + dateDebut.toString();
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
