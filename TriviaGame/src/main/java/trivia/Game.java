package trivia;

import java.util.*;

// REFACTOR ME
public class Game implements IGame {
    private final ArrayList<Player> players = new ArrayList<>();
    private final EnumMap<Category, LinkedList<String>> questions = new EnumMap<>(Category.class);

    private int currentPlayerIndex = 0;

    public Game() {
        for (Category category : Category.values()) {
            questions.put(category, new LinkedList<>());
        }

        for (int i = 0; i < 50; i++) {
            for (Category category : Category.values()) {
                questions.get(category).addLast(buildQuestion(category, i));
            }
        }
    }

    private String buildQuestion(Category category, int index) {
        return category.label() + " Question " + index;
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
        System.out.println("The category is " + currentCategory().label());
        askQuestion();
    }

    private void askQuestion() {
        System.out.println(questions.get(currentCategory()).removeFirst());
    }


    private Category currentCategory() {
        return Category.fromPlace(players.get(currentPlayerIndex).getPlace());
    }

    public boolean handleCorrectAnswer() {
        Player player = players.get(currentPlayerIndex);
        if (player.isInPenaltyBox()) {
            if (!player.isGettingOutOfPenaltyBox()) {
                nextPlayer();
                return true;
            } else {
                player.setInPenaltyBox(false);
            }
        }

        System.out.println("Answer was correct!!!!");
        player.incrementPurse(1);
        System.out.println(player + " now has " + player.getPurse() + " Gold Coins.");

        boolean shouldContinueGame = shouldContinueGame();
        nextPlayer();
        return shouldContinueGame;
    }

    public boolean wrongAnswer() {
        Player player = players.get(currentPlayerIndex);
        System.out.println("Question was incorrectly answered");
        System.out.println(player + " was sent to the penalty box");
        player.setInPenaltyBox(true);

        nextPlayer();
        return true;
    }


    private boolean shouldContinueGame() {
        return players.get(currentPlayerIndex).getPurse() != 6;
    }

    private void nextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }
}
