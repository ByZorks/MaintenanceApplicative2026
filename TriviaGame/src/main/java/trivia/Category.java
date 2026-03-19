package trivia;

public enum Category {
    POP("Pop"),
    SCIENCE("Science"),
    SPORTS("Sports"),
    ROCK("Rock");

    private final String label;

    Category(String label) {
        this.label = label;
    }

    public String label() {
        return label;
    }

    public static Category fromPlace(int place) {
        int slot = (place - 1) % 4;
        switch (slot) {
            case 0: return POP;
            case 1: return SCIENCE;
            case 2: return SPORTS;
            default: return ROCK;
        }
    }
}