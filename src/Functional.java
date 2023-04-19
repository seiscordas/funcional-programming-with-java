import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class Functional {
    public static void main(String[] args) {
        List<Integer> numbers = List.of(24, 25, 2, 9, 8, 6, 7, 3, 1, 5, 10);
        List<String> words = List.of("Car", "Moto", "House", "Factory", "Mouse", "Computers");
        List<String> fruits = List.of("banana", "strawberry", "pineapple");

        //printAllNumbersInList(numbers);
        //printEvenNumbersInList(numbers);
        //printOddNumbersInList(numbers);
        //printSquareOfOddNumbersInList(numbers);
        //printCubeOfEvenNumbersInList(numbers);
        //printWordsInList(words);
        //printWordWithMoreThanFourCharsInList(words);
        //printWordLengthCharsInList(words);
        optional(fruits);
    }

    private static void printAllNumbersInList(List<Integer> numbers) {
        numbers.forEach(System.out::println);
    }

    private static void printEvenNumbersInList(List<Integer> numbers) {
        numbers.stream()
                .filter(number -> number % 2 == 0)
                .forEach(System.out::println);
    }

    private static void printSquareOfOddNumbersInList(List<Integer> numbers) {
        numbers.stream()
                .filter(number -> number % 2 != 0)
                .map(number -> number * number)
                .forEach(System.out::println);
    }

    private static void printCubeOfEvenNumbersInList(List<Integer> numbers) {
        numbers.stream()
                .filter(number -> number % 2 == 0)
                .map(number -> number * number * number)
                .forEach(System.out::println);
    }

    private static void printOddNumbersInList(List<Integer> numbers) {
        numbers.stream()
                .filter(number -> number % 2 != 0)
                .forEach(System.out::println);
    }

    private static void printWordsInList(List<String> words) {
        words.forEach(System.out::println);
    }

    private static void printWordWithMoreThanFourCharsInList(List<String> words) {
        words.stream()
                .filter(word -> word.length() > 4)
                .forEach(System.out::println);
    }

    private static void printWordLengthCharsInList(List<String> words) {
        words.stream()
                .map(String::length)
                .forEach(System.out::println);
    }

    private static  void optional(List<String> fruits){
        Predicate<? super String> predicate = fruit -> fruit.startsWith("p");
        Optional<String> optional = fruits.stream().filter(predicate).findFirst();
        System.out.println(optional);
        System.out.println(optional.isEmpty());
        System.out.println(optional.isPresent());
        System.out.println(optional.orElse(null));
        System.out.println(optional.getClass());
    }
}
