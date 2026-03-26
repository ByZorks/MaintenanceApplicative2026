package myCalendar;

public record EventDuration(int valeur) {
    public EventDuration {
        if (valeur <= 0)
            throw new IllegalArgumentException("La durée doit être positive.");
    }
}
