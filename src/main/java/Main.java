public class Main {


    public static void main(String[] args) {

        try {

            NumCollection collection = new NumCollection("1,2,3, 5-60");

            NumCollection collection1 = new NumCollection("-6 - -2, 0-100, 200");

        } catch (UnsortedValuesException e) {

            System.out.println("<Input string is not sorted>");


        }
    }
}
