package myCalendar;

import java.util.Objects;
import java.util.Optional;

public record EventOwner(String valeur) {
    public EventOwner {
        Objects.requireNonNull(valeur, "Le propriétaire ne peut pas être vide.");
        Optional.of(valeur)
                .filter(v -> !v.isBlank())
                .orElseThrow(() -> new IllegalArgumentException("Le propriétaire ne peut pas être vide."));
    }

    @Override
    public String toString() {
        return valeur;
    }
}
