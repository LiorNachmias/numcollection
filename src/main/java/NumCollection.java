import java.util.Iterator;

public class NumCollection implements Iterable<Integer> {

    private static int strategyThreshold = 20;

    // we are a proxy - this is the actual implementation
    private NumCollectionImpl numCollectionImpl;

    public static void setStrategyThreshold(int updatedStrategyThreshold) {
        strategyThreshold = updatedStrategyThreshold;
    }

    public NumCollection(String numCollectionstr) throws UnsortedValuesException {

        String[] pairs = numCollectionstr.split(",");
        int numOfPairs = pairs.length;

        if (numOfPairs > strategyThreshold) {
            numCollectionImpl = new NumCollectionPairs();
        }
        else {
            numCollectionImpl = new NumCollectionSet();
        }
        numCollectionImpl.init(pairs);
    }

    public boolean contains(int number) {

        return numCollectionImpl.contains(number);

    }

    @Override
    public Iterator iterator() {
        return numCollectionImpl.iterator();
    }

    /*package*/ Class<? extends NumCollectionImpl> getStrategyClass() {
        return numCollectionImpl.getClass();
    }
}
