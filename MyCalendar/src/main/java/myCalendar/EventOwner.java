package myCalendar;

public record EventOwner(String valeur) {
    public EventOwner {
        if (valeur == null || valeur.isBlank())
            throw new IllegalArgumentException("Le propriétaire ne peut pas être vide.");
    }

    @Override
    public String toString() {
        return valeur;
    }
}
