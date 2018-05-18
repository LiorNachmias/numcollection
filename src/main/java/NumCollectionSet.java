import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class NumCollectionSet extends NumCollectionImpl {

    private ArrayList<Integer> numbersList = new ArrayList<>();

    public void addToCollection(int firstNumber, int secondNumber) throws UnsortedValuesException {
        for (int i = firstNumber; i <= secondNumber; i++) {
            numbersList.add(i);
        }
    }


    @Override
    public boolean contains(int number) {
         int index = Collections.binarySearch(numbersList, number);
         return index >= 0;
    }

    @Override
    public Iterator iterator() {
        return numbersList.iterator();
    }

}
