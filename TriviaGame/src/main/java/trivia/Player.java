package trivia;

public class Player {

    private final String name;
    private int place = 1;
    private int purse = 0;
    private boolean inPenaltyBox = false;
    private boolean isGettingOutOfPenaltyBox = false;

    public Player(final String name) {
        this.name = name;
    }

    public void incrementPlace(int amount) {
        this.place = ((this.place - 1 + amount) % 12) + 1;
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

    public boolean isGettingOutOfPenaltyBox() {
        return isGettingOutOfPenaltyBox;
    }

    public void setInPenaltyBox(final boolean inPenaltyBox) {
        this.inPenaltyBox = inPenaltyBox;
    }

    public void setGettingOutOfPenaltyBox(final boolean gettingOutOfPenaltyBox) {
        isGettingOutOfPenaltyBox = gettingOutOfPenaltyBox;
    }

    @Override
    public String toString() {
        return name;
    }
}
