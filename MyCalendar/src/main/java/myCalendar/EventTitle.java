package myCalendar;

import java.util.Objects;
import java.util.Optional;

public record EventTitle(String valeur) {
    public EventTitle {
        Objects.requireNonNull(valeur, "Le titre ne peut pas être vide.");
        Optional.of(valeur)
                .filter(v -> !v.isBlank())
                .orElseThrow(() -> new IllegalArgumentException("Le titre ne peut pas être vide."));
    }

    @Override
    public String toString() {
        return valeur;
    }
}
