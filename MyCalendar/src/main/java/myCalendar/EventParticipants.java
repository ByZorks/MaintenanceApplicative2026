package myCalendar;

import java.util.List;

public record EventParticipants(List<String> liste) {
    public EventParticipants {
        if (liste == null || liste.isEmpty())
            throw new IllegalArgumentException("La liste des participants ne peut être nulle ou vide.");

        liste = List.copyOf(liste); // Pour l'immutabilité
    }

    @Override
    public String toString() {
        return String.join(", ", liste);
    }
}
