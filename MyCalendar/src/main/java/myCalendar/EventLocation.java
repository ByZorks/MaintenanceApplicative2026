package myCalendar;

public record EventLocation(String valeur) {
    public EventLocation {
        if (valeur == null || valeur.isBlank())
            throw new IllegalArgumentException("Le lieu ne peut être nul ou vide.");
    }

    @Override
    public String toString() {
        return valeur;
    }
}
