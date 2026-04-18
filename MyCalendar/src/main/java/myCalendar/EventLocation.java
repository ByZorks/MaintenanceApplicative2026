package myCalendar;

import java.util.Objects;

public record EventLocation(String valeur) {
    public EventLocation {
        Objects.requireNonNull(valeur, "Le lieu ne peut être nul ou vide.");
        if (valeur.isBlank())
            throw new IllegalArgumentException("Le lieu ne peut être nul ou vide.");
    }

    @Override
    public String toString() {
        return valeur;
    }
}
