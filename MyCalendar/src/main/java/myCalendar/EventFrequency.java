package myCalendar;

public record EventFrequency(int valeur) {
    public EventFrequency {
        if (valeur < 0)
            throw new IllegalArgumentException("La fréquence doit être positive.");
    }

    @Override
    public String toString() {
        return String.valueOf(valeur);
    }
}
