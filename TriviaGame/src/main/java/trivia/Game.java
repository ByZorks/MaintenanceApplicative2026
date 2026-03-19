package trivia;

import java.util.ArrayList;
import java.util.LinkedList;

// REFACTOR ME
public class Game implements IGame {
   private final ArrayList<String> players = new ArrayList<>();
   private final int[] places = new int[6];
   private final int[] purses = new int[6];
   private final boolean[] inPenaltyBox = new boolean[6];

   private final LinkedList<String> popQuestions = new LinkedList<>();
   private final LinkedList<String> scienceQuestions = new LinkedList<>();
   private final LinkedList<String> sportsQuestions = new LinkedList<>();
   private final LinkedList<String> rockQuestions = new LinkedList<>();

   private int currentPlayer = 0;
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
      places[howManyPlayers()] = 1;
      purses[howManyPlayers()] = 0;
      inPenaltyBox[howManyPlayers()] = false;
      players.add(playerName);

      System.out.println(playerName + " was added");
      System.out.println("They are player number " + players.size());
      return true;
   }

   public int howManyPlayers() {
      return players.size();
   }

   public void roll(int roll) {
      System.out.println(players.get(currentPlayer) + " is the current player");
      System.out.println("They have rolled a " + roll);

      if (inPenaltyBox[currentPlayer]) {
         if (roll % 2 != 0) {
            isGettingOutOfPenaltyBox = true;

            System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");
            places[currentPlayer] = places[currentPlayer] + roll;
            if (places[currentPlayer] > 12) places[currentPlayer] = places[currentPlayer] - 12;

            System.out.println(players.get(currentPlayer)
                               + "'s new location is "
                               + places[currentPlayer]);
            System.out.println("The category is " + currentCategory());
            askQuestion();
         } else {
            System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
            isGettingOutOfPenaltyBox = false;
         }

      } else {

         places[currentPlayer] = places[currentPlayer] + roll;
         if (places[currentPlayer] > 12) places[currentPlayer] = places[currentPlayer] - 12;

         System.out.println(players.get(currentPlayer)
                            + "'s new location is "
                            + places[currentPlayer]);
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
      if (places[currentPlayer] - 1 == 0) return POP;
      if (places[currentPlayer] - 1 == 4) return POP;
      if (places[currentPlayer] - 1 == 8) return POP;
      if (places[currentPlayer] - 1 == 1) return SCIENCE;
      if (places[currentPlayer] - 1 == 5) return SCIENCE;
      if (places[currentPlayer] - 1 == 9) return SCIENCE;
      if (places[currentPlayer] - 1 == 2) return SPORTS;
      if (places[currentPlayer] - 1 == 6) return SPORTS;
      if (places[currentPlayer] - 1 == 10) return SPORTS;
      return ROCK;
   }

   public boolean handleCorrectAnswer() {
      if (inPenaltyBox[currentPlayer]) {
         if (isGettingOutOfPenaltyBox) {
            System.out.println("Answer was corrent!!!!");
            purses[currentPlayer]++;
            System.out.println(players.get(currentPlayer)
                               + " now has "
                               + purses[currentPlayer]
                               + " Gold Coins.");

            boolean winner = didPlayerWin();
            currentPlayer++;
            if (currentPlayer == players.size()) currentPlayer = 0;

            return winner;
         } else {
            currentPlayer++;
            if (currentPlayer == players.size()) currentPlayer = 0;
            return true;
         }


      } else {

         System.out.println("Answer was corrent!!!!");
         purses[currentPlayer]++;
         System.out.println(players.get(currentPlayer)
                            + " now has "
                            + purses[currentPlayer]
                            + " Gold Coins.");

         boolean winner = didPlayerWin();
         currentPlayer++;
         if (currentPlayer == players.size()) currentPlayer = 0;

         return winner;
      }
   }

   public boolean wrongAnswer() {
      System.out.println("Question was incorrectly answered");
      System.out.println(players.get(currentPlayer) + " was sent to the penalty box");
      inPenaltyBox[currentPlayer] = true;

      currentPlayer++;
      if (currentPlayer == players.size()) currentPlayer = 0;
      return true;
   }


   private boolean didPlayerWin() {
      return purses[currentPlayer] != 6;
   }
}
