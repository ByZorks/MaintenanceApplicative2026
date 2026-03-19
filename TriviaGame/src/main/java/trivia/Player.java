package trivia;

public class Player {

    private final String name;
    private int place = 1;
    private int purse = 0;
    private boolean inPenaltyBox = false;

    public Player(final String name) {
        this.name = name;
    }

    public void incrementPlace(int amount) {
        this.place += amount;
        if (this.place > 12)
            this.place -= 12;
    }

    public void incrementPurse(int amount) {
        this.purse += amount;
    }

    public int getPlace() {
        return place;
    }

    public int getPurse() {
        return purse;
    }

    public boolean isInPenaltyBox() {
        return inPenaltyBox;
    }

    public void setInPenaltyBox(final boolean inPenaltyBox) {
        this.inPenaltyBox = inPenaltyBox;
    }

    @Override
    public String toString() {
        return name;
    }
}
