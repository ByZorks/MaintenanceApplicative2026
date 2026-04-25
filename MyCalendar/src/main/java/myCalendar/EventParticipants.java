package myCalendar;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public record EventParticipants(List<EventOwner> liste) {
    public EventParticipants {
        liste = List.copyOf(Objects.requireNonNull(liste, "La liste des participants ne peut être nulle ou vide."));
        Optional.of(liste)
                .filter(l -> !l.isEmpty())
                .orElseThrow(() -> new IllegalArgumentException("La liste des participants ne peut être nulle ou vide."));
    }

    @Override
    public String toString() {
        return String.join(", ", liste.stream().map(EventOwner::toString).toList());
    }
}
