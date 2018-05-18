import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestNumCollectionStrategySet {

    @BeforeAll
    public static void init() throws UnsortedValuesException {
        // set threshold to make sure all tests will use StrategySet
        NumCollection.setStrategyThreshold(1000); // make sure we go for Set
        NumCollection collection = new NumCollection("1");
        assertSame(collection.getStrategyClass(), NumCollectionSet.class);
    }

    @Test
    void testPositiveContains() throws UnsortedValuesException {

        NumCollection collection = new NumCollection("1,2,3,10,11");

        assertTrue(collection.contains(1), "should contain 1");
        assertTrue(collection.contains(2), "should contain 2");
    }

    @Test
    void testPositiveContainsWithDash() throws UnsortedValuesException {

        NumCollection collection = new NumCollection("-20--10, 0 ,1-3,10-11");

        assertTrue(collection.contains(-20), "should contain -20");
        assertTrue(collection.contains(-19), "should contain -19");
        assertTrue(collection.contains(1), "should contain 1");
        assertTrue(collection.contains(2), "should contain 2");
        assertTrue(collection.contains(10), "should contain 10");
    }

    @Test
    void testNegativeToPositiveContainsWithDash() throws UnsortedValuesException {

        NumCollection collection = new NumCollection("-50-2,9 -11");

        assertTrue(collection.contains(-20), "should contain -15");
        assertTrue(collection.contains(0), "should contain 0");
        assertTrue(collection.contains(2), "should contain 2");
        Assertions.assertFalse(collection.contains(6), "should not contain 6");
    }

    @Test


    static Stream<Arguments> inputProvidedForPositiveTest() {
        return Stream.of(
                Arguments.of("1, 2,3,5,10-12, 2000", new int[]{1, 2, 3, 5, 10, 11, 12, 2000}),
                Arguments.of("-1-5,", new int[]{-1, 0, 1, 2, 3, 4, 5}),
                Arguments.of("-20 - -19 ,-5  --3", new int[]{-20, -19, -5, -4, -3})
        );
    }

    @ParameterizedTest
    @MethodSource("inputProvidedForPositiveTest")
    void testInputStringContained(String inputString, int[] array) throws UnsortedValuesException {

        NumCollection collection = new NumCollection(inputString);

        int i = 0;

        for (Integer number : collection) {
            assertEquals(number, Integer.valueOf(array[i]));
            i++;
        }
    }

    static Stream<Arguments> inputProvidedForNegativeTest() {
        return Stream.of(
                Arguments.of("1,2,3,5,10-20", new int[]{0, 100, -5}),
                Arguments.of("0-10, 15, 16, 20-21", new int[]{-1, 13, 19, 200}),
                Arguments.of("-100--20, 0, 2-40", new int[]{-200, -1, 1, 50})
        );
    }

    @ParameterizedTest
    @MethodSource("inputProvidedForNegativeTest")
    void testInputStringNotContained(String inputString, int[] array) throws UnsortedValuesException {

        NumCollection collection = new NumCollection(inputString);

        for (Integer number : array) {
            assertFalse(collection.contains(number));

        }

    }

    @ParameterizedTest
    @CsvSource({"'10-14, 13, 20'", "'10-14, 20-18, 100'", "'-20--28'", "'-14, -15'"})
    void testUnsortedThrowsExceotion(String inputString) {

        /*Throwable throwable = */
        assertThrows(UnsortedValuesException.class,
                () -> new NumCollection(inputString));

    }
}
