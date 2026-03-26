package myCalendar;

public record EventTitle(String valeur) {
    public EventTitle {
        if (valeur == null || valeur.isBlank())
            throw new IllegalArgumentException("Le titre ne peut pas être vide.");
    }

    @Override
    public String toString() {
        return valeur;
    }
}
