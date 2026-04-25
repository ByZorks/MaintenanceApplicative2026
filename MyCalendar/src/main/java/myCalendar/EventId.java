package myCalendar;

import java.util.Objects;
import java.util.UUID;

public record EventId(String valeur) {
    public EventId {
        Objects.requireNonNull(valeur, "L'identifiant ne peut pas être nul ou vide.");
        if (valeur.isBlank())
            throw new IllegalArgumentException("L'identifiant ne peut pas être nul ou vide.");
    }

    public static EventId nouveau() {
        return new EventId(UUID.randomUUID().toString());
    }

    @Override
    public String toString() {
        return valeur;
    }
}

