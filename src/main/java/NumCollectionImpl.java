public abstract class NumCollectionImpl implements Iterable<Integer> {

    public void init(String[] pairs) throws UnsortedValuesException {
        Integer previousNumber = null;
        for (int i = 0; i < pairs.length; i++) {
            previousNumber = addPair(pairs[i], previousNumber);
        }
    }

    // returns the second number to check for unsorted
    private int addPair(String pair, Integer previousNumber) throws UnsortedValuesException {

        int firstNumber = 0;
        int secondNumber = 0;
        pair = pair.trim();

        int indexOfSeparatingDash = pair.indexOf("-", 1);

        if(indexOfSeparatingDash == -1) {
            // single number in pair
            firstNumber = Integer.parseInt(pair);
            secondNumber = firstNumber;
        }
        else {
            // two numbers
            String first = pair.substring(0, indexOfSeparatingDash).trim();
            String second = pair.substring(indexOfSeparatingDash + 1).trim();
            firstNumber = Integer.parseInt(first);
            secondNumber = Integer.parseInt(second);
        }

        // two validations because the message might be different
        if(previousNumber != null && previousNumber >= firstNumber) {
            throw new UnsortedValuesException(); // TODO: add message
        }
        else if(secondNumber < firstNumber) {
            throw new UnsortedValuesException(); // TODO: add message
        }

        addToCollection(firstNumber, secondNumber);
        return secondNumber;
}

    protected abstract void addToCollection(int firstNumber, int secondNumber) throws UnsortedValuesException;

    protected abstract boolean contains(int number);

}