package trivia;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CategoryTest {

    @ParameterizedTest(name = "place {0} -> {1}")
    @CsvSource({
            "1, POP",
            "2, SCIENCE",
            "3, SPORTS",
            "4, ROCK",
            "5, POP",
            "8, ROCK",
            "12, ROCK",
            "13, POP",
            "17, POP"
    })
    void fromPlace_shouldReturnExpectedCategory(int place, Category expected) {
        assertEquals(expected, Category.fromPlace(place));
    }
}