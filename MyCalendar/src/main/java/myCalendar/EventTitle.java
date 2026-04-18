package myCalendar;

import java.util.Objects;

public record EventTitle(String valeur) {
    public EventTitle {
        Objects.requireNonNull(valeur, "Le titre ne peut pas être vide.");
        if (valeur.isBlank())
            throw new IllegalArgumentException("Le titre ne peut pas être vide.");
    }

    @Override
    public String toString() {
        return valeur;
    }
}
