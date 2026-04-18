package myCalendar;

import java.util.Objects;

public record EventOwner(String valeur) {
    public EventOwner {
        Objects.requireNonNull(valeur, "Le propriétaire ne peut pas être vide.");
        if (valeur.isBlank())
            throw new IllegalArgumentException("Le propriétaire ne peut pas être vide.");
    }

    @Override
    public String toString() {
        return valeur;
    }
}
