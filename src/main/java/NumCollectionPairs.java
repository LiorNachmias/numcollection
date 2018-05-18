import java.util.ArrayList;
import java.util.Iterator;

public class NumCollectionPairs extends NumCollectionImpl {

    private ArrayList<int[]> pairsList = new ArrayList<>();

    public void addToCollection(int firstNumber, int secondNumber) throws UnsortedValuesException {
        int[] pair = new int[2];
        pair[0] = firstNumber;
        pair[1] = secondNumber;
        pairsList.add(pair);
    }

    @Override
    protected boolean contains(int number) {

        for (int[] pair : pairsList) {

            if (number < pair[0])
                return false;

            if (number >= pair[0] && number <= pair[1])
                return true;
        }
        return false;
    }


    @Override
    public Iterator<Integer> iterator() {

        return new Iterator<Integer>() {

            private int pairsIndex = 0;
            private Integer number = pairsList.size() > 0 ? pairsList.get(0)[0] : null;

            public boolean hasNext() {
                if (pairsList.size() == 0) {
                    return false;
                }
                int[] lastPair = pairsList.get(pairsList.size() - 1);
                return number >= lastPair[1];
            }

            @Override
            public Integer next() {
                int retval = number;

                // move the iterator
                int[] pair = pairsList.get(pairsIndex);
                if (number < pair[1]) {
                    ++number;
                }
                else {
                    ++pairsIndex;
                    if(pairsIndex < pairsList.size()) {
                        number = pairsList.get(pairsIndex)[0];
                    }
                    else {
                        number = null;
                    }
                }

                return retval;
            }
        };
    }
}
