package trivia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

// REFACTOR ME
public class Game implements IGame {
    private final ArrayList<Player> players = new ArrayList<>();
    private final HashMap<String, LinkedList<String>> questions = new HashMap<>();

    private int currentPlayerIndex = 0;

    private static final String POP = "Pop";
    private static final String SCIENCE = "Science";
    private static final String SPORTS = "Sports";
    private static final String ROCK = "Rock";

    public Game() {
        String[] categories = {POP, SCIENCE, SPORTS, ROCK};

        for (String category : categories) {
            questions.put(category, new LinkedList<>());
        }

        for (int i = 0; i < 50; i++) {
            for (String category : categories) {
                questions.get(category).addLast(buildQuestion(category, i));
            }
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
                player.setGettingOutOfPenaltyBox(true);
                System.out.println(player + " is getting out of the penalty box");
            } else {
                System.out.println(player + " is not getting out of the penalty box");
                player.setGettingOutOfPenaltyBox(false);
                return;
            }

        }

        player.incrementPlace(roll);
        System.out.println(player + "'s new location is " + player.getPlace());
        System.out.println("The category is " + currentCategory());
        askQuestion();
    }

    private void askQuestion() {
        switch (currentCategory()) {
            case POP:
                System.out.println(questions.get(POP).removeFirst());
                break;
            case SCIENCE:
                System.out.println(questions.get(SCIENCE).removeFirst());
                break;
            case SPORTS:
                System.out.println(questions.get(SPORTS).removeFirst());
                break;
            case ROCK:
                System.out.println(questions.get(ROCK).removeFirst());
                break;
            default:
                System.out.println("Invalid category");
        }
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
        if (player.isInPenaltyBox() && !player.isGettingOutOfPenaltyBox()) {
            nextPlayer();
            return true;
        }

        System.out.println("Answer was correct!!!!");
        player.incrementPurse(1);
        System.out.println(player + " now has " + player.getPurse() + " Gold Coins.");

        boolean isWinning = didPlayerWin();
        nextPlayer();
        return isWinning;
    }

    public boolean wrongAnswer() {
        Player player = players.get(currentPlayerIndex);
        System.out.println("Question was incorrectly answered");
        System.out.println(player + " was sent to the penalty box");
        player.setInPenaltyBox(true);

        nextPlayer();
        return true;
    }


    private boolean didPlayerWin() {
        return players.get(currentPlayerIndex).getPurse() != 6;
    }

    private void nextPlayer() {
        currentPlayerIndex++;
        if (currentPlayerIndex == players.size())
            currentPlayerIndex = 0;
    }
}
