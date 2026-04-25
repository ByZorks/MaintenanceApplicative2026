package myCalendar;

import java.util.Optional;

public record EventDuration(int valeur) {
    public EventDuration {
        Optional.of(valeur)
                .filter(v -> v > 0)
                .orElseThrow(() -> new IllegalArgumentException("La durée doit être positive."));
    }
}
