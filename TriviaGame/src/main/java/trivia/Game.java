package trivia;

import java.util.ArrayList;
import java.util.LinkedList;

// REFACTOR ME
public class Game implements IGame {
    private final ArrayList<Player> players = new ArrayList<>();

    private final LinkedList<String> popQuestions = new LinkedList<>();
    private final LinkedList<String> scienceQuestions = new LinkedList<>();
    private final LinkedList<String> sportsQuestions = new LinkedList<>();
    private final LinkedList<String> rockQuestions = new LinkedList<>();

    private int currentPlayerIndex = 0;
    private boolean isGettingOutOfPenaltyBox;

    private static final String POP = "Pop";
    private static final String SCIENCE = "Science";
    private static final String SPORTS = "Sports";
    private static final String ROCK = "Rock";

    public Game() {
        for (int i = 0; i < 50; i++) {
            popQuestions.addLast(buildQuestion(POP, i));
            scienceQuestions.addLast(buildQuestion(SCIENCE, i));
            sportsQuestions.addLast(buildQuestion(SPORTS, i));
            rockQuestions.addLast(buildQuestion(ROCK, i));
        }
    }

    private String buildQuestion(String question, int index) {
        return question + " Question " + index;
    }

    public boolean isPlayable() {
        return (howManyPlayers() >= 2);
    }

    public boolean add(String playerName) {
        players.add(new Player(playerName));

        System.out.println(playerName + " was added");
        System.out.println("They are player number " + players.size());
        return true;
    }

    public int howManyPlayers() {
        return players.size();
    }

    public void roll(int roll) {
        Player player = players.get(currentPlayerIndex);
        System.out.println(player + " is the current player");
        System.out.println("They have rolled a " + roll);

        if (player.isInPenaltyBox()) {
            if (roll % 2 != 0) {
                isGettingOutOfPenaltyBox = true;

                System.out.println(player + " is getting out of the penalty box");
                player.incrementPlace(roll);

                System.out.println(player
                        + "'s new location is "
                        + player.getPlace());
                System.out.println("The category is " + currentCategory());
                askQuestion();
            } else {
                System.out.println(player + " is not getting out of the penalty box");
                isGettingOutOfPenaltyBox = false;
            }

        } else {
            player.incrementPlace(roll);

            System.out.println(player
                    + "'s new location is "
                    + player.getPlace());
            System.out.println("The category is " + currentCategory());
            askQuestion();
        }

    }

    private void askQuestion() {
        if (currentCategory().equals(POP))
            System.out.println(popQuestions.removeFirst());
        if (currentCategory().equals(SCIENCE))
            System.out.println(scienceQuestions.removeFirst());
        if (currentCategory().equals(SPORTS))
            System.out.println(sportsQuestions.removeFirst());
        if (currentCategory().equals(ROCK))
            System.out.println(rockQuestions.removeFirst());
    }


    private String currentCategory() {
        switch (players.get(currentPlayerIndex).getPlace() - 1) {
            case 0:
            case 4:
            case 8:
                return POP;
            case 1:
            case 5:
            case 9:
                return SCIENCE;
            case 2:
            case 6:
            case 10:
                return SPORTS;
            default:
                return ROCK;
        }
    }

    public boolean handleCorrectAnswer() {
        Player player = players.get(currentPlayerIndex);
        if (player.isInPenaltyBox()) {
            if (isGettingOutOfPenaltyBox) {
                System.out.println("Answer was correct!!!!");
                player.incrementPurse(1);
                System.out.println(player
                        + " now has "
                        + player.getPurse()
                        + " Gold Coins.");

                boolean winner = didPlayerWin();
                currentPlayerIndex++;
                if (currentPlayerIndex == players.size())
                    currentPlayerIndex = 0;

                return winner;
            } else {
                currentPlayerIndex++;
                if (currentPlayerIndex == players.size())
                    currentPlayerIndex = 0;
                return true;
            }


        } else {

            System.out.println("Answer was corrent!!!!");
            player.incrementPurse(1);
            System.out.println(player
                    + " now has "
                    + player.getPurse()
                    + " Gold Coins.");

            boolean winner = didPlayerWin();
            currentPlayerIndex++;
            if (currentPlayerIndex == players.size()) currentPlayerIndex = 0;

            return winner;
        }
    }

    public boolean wrongAnswer() {
        Player player = players.get(currentPlayerIndex);
        System.out.println("Question was incorrectly answered");
        System.out.println(player + " was sent to the penalty box");
        player.setInPenaltyBox(true);

        currentPlayerIndex++;
        if (currentPlayerIndex == players.size())
            currentPlayerIndex = 0;
        return true;
    }


    private boolean didPlayerWin() {
        return players.get(currentPlayerIndex).getPurse() != 6;
    }
}
