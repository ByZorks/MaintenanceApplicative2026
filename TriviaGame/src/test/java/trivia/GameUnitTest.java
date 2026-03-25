package trivia;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameUnitTest {

    private Game game;

    @BeforeEach
    void setup() {
        game = new Game();
    }

    @Test
    void isPlayble_ShouldBeAtLeastTwoPlayer() {
        assertFalse(game.isPlayable());

        game.add("Chet");
        assertFalse(game.isPlayable());

        game.add("Pat");
        assertTrue(game.isPlayable());
    }

    @Test
    void wrongAnswer_shouldPassTurnToNextPlayer() {
        String out = extractOutput(() -> {
            game.add("Chet");
            game.add("Pat");
            game.wrongAnswer();
            game.roll(1);
        });

        assertTrue(out.contains("Chet was sent to the penalty box"));
        assertTrue(out.contains("Pat is the current player"));
    }

    @ParameterizedTest(name = "roll={0} escape={1}")
    @CsvSource({
            "2, false",
            "3, true"
    })
    void penaltyBox_evenRollStaysInBox_oddRollGetsOut(int roll, boolean escapes) {
        String output = extractOutput(() -> {
            game.add("Chet");
            game.wrongAnswer(); // Chet goes to penalty box
            game.roll(roll);
            game.handleCorrectAnswer();
        });

        if (escapes) {
            assertTrue(output.contains("is getting out of the penalty box"));
            assertTrue(output.contains("Chet now has 1 Gold Coins."));
        } else {
            assertTrue(output.contains("is not getting out of the penalty box"));
            assertFalse(output.contains("Chet now has 1 Gold Coins."));
        }
    }

    @Test
    void handleCorrectAnswer_shouldEndGameAtSixCoins() {
        game.add("Chet");

        for (int i = 0; i < 5; i++) {
            assertTrue(game.handleCorrectAnswer());
        }
        assertFalse(game.handleCorrectAnswer()); // 6e pièce => fin
    }

    private String extractOutput(Runnable action) {
        PrintStream old = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (PrintStream inmemory = new PrintStream(baos)) {
            // WARNING: System.out.println() doesn't work in this try {} as the sysout is captured and recorded in memory.
            System.setOut(inmemory);
            action.run();
        } finally {
            System.setOut(old);
        }
        return baos.toString();
    }
}
