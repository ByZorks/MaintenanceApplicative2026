package myCalendar;

import java.util.Optional;

public record EventFrequency(int valeur) {
    public EventFrequency {
        Optional.of(valeur)
                .filter(v -> v >= 0)
                .orElseThrow(() -> new IllegalArgumentException("La fréquence doit être positive."));
    }

    @Override
    public String toString() {
        return String.valueOf(valeur);
    }
}
