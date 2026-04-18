package myCalendar;

import java.util.List;

public record EventParticipants(List<EventOwner> liste) {
    public EventParticipants {
        if (liste == null || liste.isEmpty())
            throw new IllegalArgumentException("La liste des participants ne peut être nulle ou vide.");

        liste = List.copyOf(liste); // Pour l'immutabilité
    }

    @Override
    public String toString() {
        return String.join(", ", liste.stream().map(EventOwner::toString).toList());
    }
}
