package myCalendar;

import java.util.List;
import java.util.Objects;

public record EventParticipants(List<EventOwner> liste) {
    public EventParticipants {
        Objects.requireNonNull(liste, "La liste des participants ne peut être nulle ou vide.");
        if (liste.isEmpty())
            throw new IllegalArgumentException("La liste des participants ne peut être nulle ou vide.");

        liste = List.copyOf(liste); // Pour l'immutabilité
    }

    @Override
    public String toString() {
        return String.join(", ", liste.stream().map(EventOwner::toString).toList());
    }
}
