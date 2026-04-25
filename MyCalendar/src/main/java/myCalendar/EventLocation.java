package myCalendar;

import java.util.Objects;
import java.util.Optional;

public record EventLocation(String valeur) {
    public EventLocation {
        Objects.requireNonNull(valeur, "Le lieu ne peut être nul ou vide.");
        Optional.of(valeur)
                .filter(v -> !v.isBlank())
                .orElseThrow(() -> new IllegalArgumentException("Le lieu ne peut être nul ou vide."));
    }

    @Override
    public String toString() {
        return valeur;
    }
}
