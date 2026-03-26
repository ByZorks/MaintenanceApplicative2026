package myCalendar;

import java.time.LocalDateTime;

public record EventDateTime(LocalDateTime valeur) {
    public EventDateTime {
        if  (valeur == null)
            throw new IllegalArgumentException("La date/heure ne peut être nulle.");
    }

    public boolean isBefore(EventDateTime other) {
        return this.valeur.isBefore(other.valeur);
    }

    public boolean isAfter(EventDateTime other) {
        return this.valeur.isAfter(other.valeur);
    }

    public EventDateTime minusSeconds(int seconds) {
        return new EventDateTime(this.valeur.minusSeconds(seconds));
    }

    public EventDateTime plusMinutes(int minutes) {
        return new EventDateTime(this.valeur.plusMinutes(minutes));
    }

    public EventDateTime plusDays(int days) {
        return new EventDateTime(this.valeur.plusDays(days));
    }

    public EventDateTime plusMonths(int months) {
        return new EventDateTime(this.valeur.plusMonths(months));
    }

    @Override
    public String toString() {
        return valeur.toString();
    }
}
