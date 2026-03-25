package trivia;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PlayerTest {

    @ParameterizedTest(name = "roll {0} -> place {1}")
    @CsvSource({
            "1, 2",
            "2, 3",
            "11, 12",
            "12, 1",
            "13, 2"
    })
    void incrementPlace_shouldWrapOn12Slots(int roll, int expectedPlace) {
        Player player = new Player("Alice");

        player.incrementPlace(roll);

        assertEquals(expectedPlace, player.getPlace());
    }

    @Test
    void incrementPurse_shouldAddAmount() {
        Player player = new Player("Bob");

        player.incrementPurse(1);
        player.incrementPurse(2);

        assertEquals(3, player.getPurse());
    }
}